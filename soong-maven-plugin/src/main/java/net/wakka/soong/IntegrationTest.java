package net.wakka.soong;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

@Mojo( name = "integration-test", defaultPhase = LifecyclePhase.INTEGRATION_TEST)
public class IntegrationTest extends AbstractMojo {

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    System.out.println("### ### ### ### HELLO, WORLD!!");
  }

}
