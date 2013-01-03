package net.wakka.soong.examples.twitter;

import net.wakka.soong.IntegrationTestRunnerServiceStarter;

public class TwitterIntegrationTestStarter extends IntegrationTestRunnerServiceStarter {

  @Override
  public Class getTestRunnerClass() {
    return TwitterIntegrationTestRunnerService.class;
  }

}
