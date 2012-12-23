package net.wakka.soong;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.IOException;

@Mojo( name = "integration-test", defaultPhase = LifecyclePhase.INTEGRATION_TEST)
public class IntegrationTest extends AbstractMojo {

  private static final String START_SERVICE_COMMAND =
      "adb shell am startservice ";

  // net.wakka.soong.examples.twitter/.FoobarService
  @Parameter( property = "integrationTestRunnerServiceIntent", required = true )
  private String intent;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    int exitValue = 0;
    try {
      exitValue = new DefaultExecutor().execute(CommandLine.parse(START_SERVICE_COMMAND + intent));
      if(exitValue != 0) {
        throw new MojoExecutionException("I think I failed to execute the integration test runner service. (exit code was " + exitValue + ")");
      }
    } catch (IOException e) {
      throw new MojoExecutionException("Something bad happened.",e);
    }
  }

}
