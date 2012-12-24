package net.wakka.soong.examples.twitter;

import org.junit.Test;

import static junit.framework.Assert.*;

public class RoffelTest {

  @Test
  public void testNothingAndFail() {
    fail("Fuck your boat.");
  }

  @Test
  public void testReality() {
    assertEquals(1,1);
  }

  @Test
  public void testNothing() { }

}
