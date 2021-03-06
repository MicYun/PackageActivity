package com.white.packageactivity.fragment.base;

import android.app.Activity;
import android.os.Bundle;

import com.white.packageactivity.activity.base.BaseAsyncActivity;

/**
 * Created by MicYun on 2017/6/15.
 */

public abstract class AsyncLoadFragment extends BaseFragment {

  private boolean allowLoading = true;
  private boolean pendingToLoad = false;

  @Override
  public final void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (!needToLoadData()) {
      return;
    }
    onPrepareLoading();
    // We need to post it to make sure scroll state of ViewPager is changed before requestLoad,
    // so we can check the param allowLoading
    mContentView.post(new Runnable() {
      @Override
      public void run() {
        // Because we post this, so we need to check if this fragment is not attached
        if (!isAdded()) {
          return;
        }
        if (allowLoading) {
          onStartLoading();
        } else {
          pendingToLoad = true;
        }
      }
    });
  }

  /**
   * Called before loading, subclass should override this if sometimes it needn't to load
   * data. For example, if the network is disconnected and you need network to load data,
   * then you should show network disconnect tip here and return false.
   *
   * @return true if need to load, false otherwise
   */
  protected boolean needToLoadData() {
    return mIsInflated;
  }

  /**
   * Called before loading, you should show loading tips here.
   * <p>
   * <b>Do not launch to load data here.</b>
   * </p>
   */
  protected void onPrepareLoading() {}

  /**
   * Called after onPrepareLoading, you should do actual loading here, like starting the fetcher.
   */
  protected abstract void onStartLoading();

  /**
   * This will request loading data, and needToLoadData will not being checked.
   * Normally you don't need to call this, unless you need to manually refresh your UI.
   */
  protected final void requestLoad() {
    if (!needToLoadData()) {
      return;
    }
    onPrepareLoading();
    if (allowLoading) {
      onStartLoading();
    } else {
      pendingToLoad = true;
    }
  }

  /**
   * This is used to notify the fragment that if it should load data.
   *
   * @param allowLoading whether the fragment should launch loading
   */
  public final void setAllowLoading(boolean allowLoading) {
    this.allowLoading = allowLoading;
    if (allowLoading && pendingToLoad) {
      pendingToLoad = false;
      requestLoad();
    }
  }

  /**
   * get is allow load data
   * @return
   */
  public final boolean isAllowLoading() {
    return allowLoading;
  }

  /**
   * show default loading view
   */
  public void showLoadingView() {
    if (getActivity() instanceof BaseAsyncActivity) {
      ((BaseAsyncActivity) getActivity()).showLoadingView();
    }
  }

  /**
   * show loading view with user define text
   *
   * @param loadingPrompt if is empty or null, will hide loading text view
   */
  public void showLoadingView(String loadingPrompt) {
    if (getActivity() instanceof BaseAsyncActivity) {
      ((BaseAsyncActivity) getActivity()).showLoadingView(loadingPrompt);
    }
  }

  /**
   * dismiss default loading view
   */
  public void dismissLoadingView() {
    if (getActivity() instanceof BaseAsyncActivity) {
      ((BaseAsyncActivity) getActivity()).disMissLoadingView();
    }
  }

  public void setLoadingViewCancelable(boolean cancelable) {
    Activity activity = getActivity();
    if (activity != null && activity instanceof BaseAsyncActivity) {
      ((BaseAsyncActivity) activity).setCancelable(cancelable);
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    dismissLoadingView();
  }
}
