package com.white.packageactivity.application;

import android.app.Application;
import android.content.Context;

import com.white.packageactivity.utils.GlobalConfig;

/**
 * Created by MicYun on 2017/6/5.
 */

public class PackageActivityApplication extends Application {

  private static Context mContent;

  @Override
  public void onCreate() {
    super.onCreate();
    mContent = this;

    GlobalConfig.setAppContext(mContent);
  }
}
