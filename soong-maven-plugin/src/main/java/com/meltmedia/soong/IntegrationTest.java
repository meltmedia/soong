package com.meltmedia.soong;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

@Mojo( name = "integration-test", defaultPhase = LifecyclePhase.INTEGRATION_TEST)
public class IntegrationTest extends AbstractMojo {

  private static final int TEN_SECONDS = 10000;

  private static final String START_SERVICE_COMMAND =
      "adb shell am broadcast -n ";

  // MAGIC SPELLS!
  //adb shell am startservice -n com.meltmedia.soong.examples.twitter/.TwitterIntegrationTestRunnerService
  //adb shell am broadcast -n com.meltmedia.soong.examples.twitter/.TwitterIntegrationTestStarter
  @Parameter( property = "integrationTestRunnerServiceIntent", required = true )
  private String intent;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    try {
      boolean integrationTestingFailed = false;
      ServerSocket cs = new ServerSocket(36912);
      cs.setSoTimeout(TEN_SECONDS);
      DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
      new DefaultExecutor().execute(CommandLine.parse(START_SERVICE_COMMAND + intent), resultHandler);

      Socket s = cs.accept();
      BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
      String nextLine = "";
      while ((nextLine = in.readLine()) != null) {
        System.out.println(nextLine);
        if (nextLine.equals("TEST RUN FAILURE!!!!! :-(")) {
         integrationTestingFailed = true;
        }
      }
      in.close();
      s.close();

      if(integrationTestingFailed) {
        throw new MojoFailureException("integration testing failed");
      }
      System.out.println("Huzzah! Integration testing successful! (Unless something else says otherwise...)");
    } catch (IOException e) {
      throw new MojoExecutionException("Something bad happened. (PROTIP: make sure that adb is on your path)",e);
    }
  }

}
