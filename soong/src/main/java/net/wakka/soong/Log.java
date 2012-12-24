package net.wakka.soong;

/**
 * a facade for android.util.Log because one character method identifiers are lame
 */
public class Log {

  public static int debug(String tag, String message) {
    return android.util.Log.d(tag, message);
  }

  public static int debug(String tag, String message, Throwable throwable) {
    return android.util.Log.d(tag, message, throwable);
  }

  public static int error(String tag, String message) {
    return android.util.Log.e(tag, message);
  }

  public static int error(String tag, String message, Throwable throwable) {
    return android.util.Log.e(tag, message, throwable);
  }

  public static String getStackTraceString(Throwable throwable) {
    return android.util.Log.getStackTraceString(throwable);
  }

  public static int info(String tag, String message) {
    return android.util.Log.i(tag, message);
  }

  public static int info(String tag, String message, Throwable throwable) {
    return android.util.Log.i(tag, message, throwable);
  }

  public static boolean isLoggable(String tag, int level) {
    return android.util.Log.isLoggable(tag, level);
  }

  public static int println(int priority, String tag, String message) {
    return android.util.Log.println(priority, tag, message);
  }

  public static int verbose(String tag, String message) {
    return android.util.Log.v(tag, message);
  }

  public static int verbose(String tag, String message, Throwable throwable) {
    return android.util.Log.v(tag, message, throwable);
  }

  public static int warning(String tag, String message) {
    return android.util.Log.w(tag, message);
  }

  public static int warning(String tag, String message, Throwable throwable) {
    return android.util.Log.w(tag, message, throwable);
  }

}
