package com.white.packageactivity.activity.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by MicYun on 2017/6/2.
 */

public class BaseActivity extends AppCompatActivity {

  private boolean mIsDestroyed = false;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutId());
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mIsDestroyed = true;
  }

  public void replaceFragment(Fragment newFragment) {
    replaceFragment(newFragment, null, false);
  }

  public void replaceFragment(Fragment newFragment, Bundle arguments, boolean isAddStack) {

    if (isFinishing()) return;

    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
    if (null != arguments) {
      newFragment.setArguments(arguments);
    }
    transaction.replace(0, newFragment);

    if (isAddStack)
      transaction.addToBackStack(null);

    if (!isDestroyed())
      transaction.commitAllowingStateLoss();
  }

  public boolean isDestroyed() {
    try {
      return super.isDestroyed();
    } catch (NoSuchMethodError e) {
      return mIsDestroyed;
    }
  }

  protected int getLayoutId() {
    return 0;
  }
}
