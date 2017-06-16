package com.white.packageactivity.activity.base;

import android.os.Bundle;

import com.white.packageactivity.view.LoadingView;

/**
 * Created by MicYun on 2017/6/15.
 */

public abstract class BaseAsyncActivity extends BaseTitleActivity {

  private LoadingView mLoadingView;

  @Override
  protected void onCreate(Bundle onSaveInstanceState) {
    super.onCreate(onSaveInstanceState);
    mLoadingView =
        LoadingView.newInstanceDecorView((android.view.ViewGroup) getWindow()
            .getDecorView());
    if(mLoadingView != null ){
      mLoadingView.setCancelable(true);
    }
  }


  public void setCancelable(boolean cancelable) {
    if (mLoadingView != null) {
      mLoadingView.setCancelable(cancelable);
    }
  }


  public void showLoadingView() {
    if (mLoadingView != null && !mLoadingView.isShown()) {
      mLoadingView.show();
    }
  }

  public void showLoadingView(String content) {
    if (mLoadingView != null && !mLoadingView.isShown()) {
      mLoadingView.show(content);
    }
  }

  public void disMissLoadingView() {
    if (mLoadingView != null && mLoadingView.isShown()) {
      mLoadingView.dismiss();
    }
  }
}
