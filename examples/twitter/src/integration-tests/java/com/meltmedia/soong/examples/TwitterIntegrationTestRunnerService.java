package com.meltmedia.soong.examples.twitter;

import com.meltmedia.soong.IntegrationTestRunnerService;

import java.util.HashSet;
import java.util.Set;

public class TwitterIntegrationTestRunnerService  extends IntegrationTestRunnerService {

  private Set<Class> testClasses;

  @Override
  public Set<Class> getTestClasses() {
    if (testClasses == null) {
      testClasses = new HashSet<Class>();
      testClasses.add(RoffelTest.class);
    }
    return testClasses;
  }



}
