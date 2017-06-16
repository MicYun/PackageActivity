package com.white.packageactivity.fragment.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.white.packageactivity.R;
import com.white.packageactivity.activity.base.BaseTitleActivity;
import com.white.packageactivity.utils.ViewUtils;

import java.lang.reflect.Field;

/**
 * Created by MicYun on 2017/6/14.
 */

public abstract class BaseFragment extends Fragment {

  protected View mContentView;
  protected boolean mIsInflated;

  private boolean isVisible;
  private boolean isPrepared;
  private boolean isLoaded;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    mContentView = inflater.inflate(getLayoutResId(), container, false);
    isPrepared = true;
    checkLazyLoad();
    return mContentView;
  }

  /**
   * 状态栏动态显示或隐藏
   * @param hide
   */
  private void setSystemUiVisibility(boolean hide) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
      return;
    }
    if (hide) {
      mContentView.setSystemUiVisibility(View.INVISIBLE);
    } else {
      mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
    }
  }

  protected boolean hideStatusBar() {
    return false;
  }

  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);
    if (mContentView != null) {
      onInflated(mContentView, savedInstanceState);
      mIsInflated = true;
    }
  }

  public boolean onKeyDown(int keyCode, KeyEvent event) {
    return false;
  }

  /**
   * Called when the Fragment is inflated
   *
   * @param contentView
   * @param savedInstanceState
   */
  protected abstract void onInflated(View contentView, Bundle savedInstanceState);

  /**
   * @return the layout resource id of the fragment content
   */
  protected abstract int getLayoutResId();


  /**
   * Get the root view of the fragment.
   *
   * @deprecated use {@link android.support.v4.app.Fragment#getView()} instead,
   *             but need to check that getView will return null after onDetached, so be careful.
   * @return the root view
   */
  public View getContentView() {
    return mContentView;
  }


  /**
   * set activity title on actionbar
   *
   * @param title
   */
  public void setTitle(CharSequence title) {
    FragmentActivity activity = getActivity();
    if (activity instanceof BaseTitleActivity) {
      activity.setTitle(title);
    }
  }


  //onCreateView()之前调用
  @Override
  public void setUserVisibleHint(boolean isVisibleToUser) {
    isVisible = isVisibleToUser;
    checkLazyLoad();
    super.setUserVisibleHint(isVisibleToUser);
  }


  private void checkLazyLoad() {
    if (isVisible && isPrepared && !isLoaded) {
      loadingData();
      isLoaded = true;
    }
  }

  protected void loadingData() {}

  @Override
  public void onDetach() {
    super.onDetach();
    try {
      Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
      childFragmentManager.setAccessible(true);
      childFragmentManager.set(this, null);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public void onResume() {
    super.onResume();
    setSystemUiVisibility(hideStatusBar());
  }

  protected boolean isChildView(View v) {
    return ViewUtils.isParentSonView(v, getView());
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  protected void recordEvent(View view) {}

  public void onNewIntent(Intent intent) {

  }

  protected void replaceFragment(Fragment newFragment) {
    replaceFragment(newFragment, null, false);
  }

  protected void replaceFragment(Fragment newFragment, Bundle arguments, boolean isAddStack) {
    if (!isAdded()) {
      return;
    }
    FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
    if (arguments != null) {
      newFragment.setArguments(arguments);
    }
    transaction.replace(R.id.fragment_container, newFragment);
    if (isAddStack) {
      transaction.addToBackStack(null);
    }
    if (!isDetached()) {
      transaction.commitAllowingStateLoss();
    }
  }
}
