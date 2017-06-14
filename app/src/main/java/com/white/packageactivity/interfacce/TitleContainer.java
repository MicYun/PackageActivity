package com.white.packageactivity.interfacce;

import android.view.View;
import android.view.ViewGroup;

/**
 * Created by MicYun on 2017/6/5.
 */

public interface TitleContainer {

  void setTitle(CharSequence title);

  void setTitle(int resId);

  void setLeftView(View view, ViewGroup.LayoutParams layoutParams);

  void setRightView(View view, ViewGroup.LayoutParams layoutParams);

  View getRightView();
}
