package com.meltmedia.soong.examples.twitter;

import android.content.Context;
import com.meltmedia.soong.Log;
import org.junit.Test;

import static junit.framework.Assert.*;

public class RoffelTest {

  private static Context context;
  public static void setContext(Context context) {
    Log.info("### ### ###", "my context was set!");
    RoffelTest.context = context;
  }

//  @Test
//  public void testNothingAndFail() {
//    fail("Fuck your boat.");
//  }

  @Test
  public void testReality() {
    assertEquals(1,1);
  }

  @Test
  public void testNothing() { }

}
