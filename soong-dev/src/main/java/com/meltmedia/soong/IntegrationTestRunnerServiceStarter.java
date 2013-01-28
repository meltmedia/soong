package com.meltmedia.soong;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public abstract class IntegrationTestRunnerServiceStarter extends BroadcastReceiver {

  /**
   * Extend this class within your android application
   * @return a the class of your TestRunner
   */
  public abstract Class getTestRunnerClass();

  @Override
  public void onReceive(Context context, Intent intent) {
    Log.info("Soong", "starting test runner service");
    context.startService(new Intent(context, getTestRunnerClass()));
  }

}
