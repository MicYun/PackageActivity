package com.white.packageactivity.utils;

/**
 * Created by MicYun on 2017/6/5.
 */

public class ColorResUtils {

  public static int getColor(int res) {
    return GlobalConfig.getAppContext().getResources().getColor(res);
  }

}
