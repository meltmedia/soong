package net.wakka.soong;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.StrictMode;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Set;

/**
 * An android service that executes JUnit tests and reports the results to the host
 * workstation via a simple socket connection.
 *
 * HOW TO USE: Extend this class, implement the getTestClasses method, build and
 * deploy your application, then use the soong-maven-plugin to execute your tests.
 * Don't forget to add your newly created service to your application manifest,
 * your clod!
 *
 * This class is for development use only and should be invoked via the adb shell
 * rather than by integration into an android application.
 *
 * The soong-maven-plugin integration-test goal knows how to interact with this
 * service and is the recommended use case.
 *
 * WARNING: This class is not well tested and probably contains one or more insidious
 * defects. If it proves to be invaluable in its limited distribution in this state
 * someone shall most certainly robustify it.
 */
public abstract class IntegrationTestRunnerService extends Service {

  private static final String HOST_LOOPBACK = "10.0.2.2";
  private static final int    DEFAULT_PORT = 36912;

  // Since this service is intended for use from the adb shell we simply return null.
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    performIntegrationTests();
    return START_STICKY;
  }

  /**
   * Extend this class within your android application
   * @return a Set of classes which contain JUnit tests
   */
  public abstract Set<Class> getTestClasses();

  public void performIntegrationTests() {
    Log.info("net.wakka.soong","### ### ### PERFORMING INTEGRATION TESTS");

    // disable all thread policy restrictions so that we may use sockets
    try {
      StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
      StrictMode.setThreadPolicy(policy);
    } catch (NoClassDefFoundError e) {
      // LOL!
    }

    try {
      // TODO: make the socket address configurable
      //       so that we may (at least) set it to the actual IP address of a workstaton
      //       and report results of integration tests performed on physical devices
      // TODO: make the port number configurable
      //       just in case it is ever unavailable
      Socket s = new Socket(HOST_LOOPBACK,DEFAULT_PORT);
      final PrintWriter out = new PrintWriter(s.getOutputStream(), true);

      JUnitCore junit = new JUnitCore();
      junit.addListener(new RunListener() {
        int failureCount = 0;
        int testCount = 0;

        // Hey, don't go changing the string literals in this class. Widgets that receive
        // test results from this service depend upon their stability!

        @Override
        public void testRunStarted(Description description) throws Exception {
          out.write("test run started\n");
        }

        @Override
        public void testRunFinished(Result result) throws Exception {
          out.write(String.format("%d tests executed, %d tests failed\n",testCount,failureCount));
          if(failureCount == 0) {
            out.write("test run SUCCESS. :-)\n");
          } else {
            out.write("TEST RUN FAILURE!!!!! :-(");
          }
        }

        @Override
        public void testFailure(Failure failure) throws Exception {
          out.write(String.format("TEST FAILURE @ : %s.%s\n",
              failure.getDescription().getClassName(), failure.getDescription().getMethodName()));
          out.write(String.format("   %s\n",failure.getMessage()));
          out.write(String.format("   %s\n",failure.getTestHeader()));
          out.write(String.format("   %s\n", failure.getTrace()));
          failureCount++;
        }

        @Override
        public void testStarted(Description description) throws Exception {
          testCount++;
        }
      });

      for(Class clazz : getTestClasses()) {
        try {
          Method method = clazz.getDeclaredMethod("setContext", Context.class);
          method.invoke(null,this.getApplicationContext());
          Log.info("net.wakka.soong","successfully setContext on test class " + clazz.getCanonicalName());
        } catch (NoSuchMethodException e) {
          Log.info("net.wakka.soong",clazz.getCanonicalName() + " does not accept setContext invokations");
        }
        junit.run(clazz);
      }

      out.close();
    } catch (Exception e) {
      Log.error("net.wakka.soong.IntegrationTestRunnerService","Caught an error while trying to phone home.", e);
    } finally {
      Log.info("net.wakka.soong","### ### ### FINISHED INTEGRATION TESTS");
    }
  }

}
