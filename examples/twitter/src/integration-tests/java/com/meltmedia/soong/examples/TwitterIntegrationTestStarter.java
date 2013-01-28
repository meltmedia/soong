package com.meltmedia.soong.examples.twitter;

import com.meltmedia.soong.IntegrationTestRunnerServiceStarter;

public class TwitterIntegrationTestStarter extends IntegrationTestRunnerServiceStarter {

  @Override
  public Class getTestRunnerClass() {
    return TwitterIntegrationTestRunnerService.class;
  }

}
