package com.white.packageactivity.utils;

import android.content.Context;

/**
 * Created by MicYun on 2017/6/5.
 */

public class GlobalConfig {

  private static Context appContext;

  public GlobalConfig() {
  }

  public static Context getAppContext() {
    return appContext;
  }

  public static void setAppContext(Context context) {
    appContext = context;
  }
}
