package net.wakka.soong.examples.twitter;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.StrictMode;

import java.io.PrintWriter;
import java.net.Socket;

public class FoobarService extends Service {

  /* So, you want to start a service from the adb shell. Try this:
   * am startservice net.wakka.soong.examples.twitter/.FoobarService
   */

  @Override
  public IBinder onBind(Intent intent) {
    return null;  //To change body of implemented methods use File | Settings | File Templates.
  }

  @Override
  public void onCreate() {
    android.util.Log.i("FoobarService","onCreate");
    super.onCreate();    //To change body of overridden methods use File | Settings | File Templates.
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
    android.util.Log.i("FoobarService","onStartCommand");
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);
    try {
      Socket s = new Socket("10.0.2.2",36912);
      PrintWriter out = new PrintWriter(s.getOutputStream(), true);
      out.write("Help me! I'm stuck inside a retarded device!");
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
