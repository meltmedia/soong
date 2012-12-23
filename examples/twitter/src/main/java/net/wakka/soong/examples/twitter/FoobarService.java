package net.wakka.soong.examples.twitter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.StrictMode;
import junit.framework.AssertionFailedError;
import junit.framework.Test;
import junit.framework.TestListener;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.JUnit4;

import java.io.PrintWriter;
import java.net.Socket;

public class FoobarService extends Service {

  /* So, you want to start a service from the adb shell. Try this:
   * am startservice net.wakka.soong.examples.twitter/.FoobarService
   */

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public void onCreate() {
    android.util.Log.i("FoobarService","onCreate");
    super.onCreate();
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    android.util.Log.i("FoobarService","onStartCommand");
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    try {
      Socket s = new Socket("10.0.2.2",36912);
      final PrintWriter out = new PrintWriter(s.getOutputStream(), true);
      out.write("Help me! I'm stuck inside a retarded device!");

      JUnitCore junit = new JUnitCore();
      junit.addListener(new RunListener() {
        int failureCount = 0;

        @Override
        public void testRunStarted(Description description) throws Exception {
          out.write("test run started\n");
        }

        @Override
        public void testRunFinished(Result result) throws Exception {
          if(failureCount == 0) {
            out.write("test run SUCCESS. :-)\n");
          } else {
            out.write("TEST RUN FAILURE!!!!! :-(");
          }
        }

        @Override
        public void testFailure(Failure failure) throws Exception {
          out.write("test failure: " + failure.getMessage() + "\n");
          failureCount++;
        }
      });
      junit.run(RoffelTest.class);

      out.close();
      android.util.Log.i("FoobarService","success!");
    } catch (Exception e) {
      android.util.Log.e("FoobarService","Caught an error while trying to phone home.", e);
    }
    return START_STICKY;
  }

  @Override
  public void onDestroy() {
    android.util.Log.i("FoobarService","onDestroy");
    super.onDestroy();    //To change body of overridden methods use File | Settings | File Templates.
  }
}
