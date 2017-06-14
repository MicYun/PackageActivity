package com.white.packageactivity.activity.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.white.packageactivity.R;
import com.white.packageactivity.interfacce.TitleContainer;
import com.white.packageactivity.utils.ColorResUtils;
import com.white.packageactivity.view.CommonTitleView;

/**
 * Created by MicYun on 2017/6/2.
 */

public abstract class BaseTitleActivity extends BaseActivity {

  private SystemBarTintManager mTintManager;
  protected TitleContainer mCustomTitleView;
  private TitleContainer myTitleContainer;
  private ActionBar mActionBar;

  @Override
  protected int getLayoutId() {
    return R.layout.base_title_fragment_activity;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initWindow();
    mCustomTitleView = getMyTitleContainer();
    customizeMyTitle(mCustomTitleView);
    initToolbar();
    setCustomTitleView(mCustomTitleView);
    onCreateMenu();
  }

  private void initToolbar() {
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    customizeToolbar(toolbar);
    setSupportActionBar(toolbar);
    mActionBar = getSupportActionBar();
    mActionBar.setHomeAsUpIndicator(R.drawable.base_title_back);
    mActionBar.setDisplayHomeAsUpEnabled(true);

    setTitle(getTitleText());
  }

  /**
   * 给出标题
   */
  protected abstract String getTitleText();

  protected void customizeToolbar(Toolbar toolbar) {

  }

  protected void customizeMyTitle(TitleContainer titleView) {
    // empty implement
  }

  /**
   * 设置自定义 view 使用
   *
   * @param view
   * @param <V>
   */
  protected <V extends TitleContainer> void setCustomTitleView(V view) {
    if (view instanceof View) {

      mActionBar.setDisplayHomeAsUpEnabled(false);
      mActionBar.setDisplayShowTitleEnabled(false);
      mActionBar.setDisplayShowHomeEnabled(false);

      mActionBar.setDisplayShowCustomEnabled(true);

      ActionBar.LayoutParams layoutParams =
          new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT,
              ActionBar.LayoutParams.MATCH_PARENT);
      mActionBar.setCustomView((View) view, layoutParams);

      if (((View) view).getParent() instanceof Toolbar) {
        Toolbar parent = (Toolbar) ((View) view).getParent();
        parent.setContentInsetsAbsolute(0, 0);
      }
      mCustomTitleView = view;
    } else if (view == null) {
      mActionBar.setDisplayHomeAsUpEnabled(true);
      mActionBar.setDisplayShowTitleEnabled(true);
      mActionBar.setDisplayShowHomeEnabled(true);
      mActionBar.setDisplayShowCustomEnabled(false);
    }
  }

  protected void initWindow() {
    if(Build.VERSION.SDK_INT >= 19) {
      this.getWindow().addFlags(67108864);
      this.mTintManager = new SystemBarTintManager(this);
      this.mTintManager.setStatusBarTintEnabled(true);
      this.mTintManager.setNavigationBarTintEnabled(true);
      this.mTintManager.setTintColor(ColorResUtils.getColor(R.color.colorPrimary));
    }
  }

  public TitleContainer getMyTitleContainer() {
    return CommonTitleView.newInstance(this);
  }

  protected void onCreateMenu() {

  }

  @Override
  public final boolean onCreateOptionsMenu(Menu menu) {
    return false;
  }

  @Override
  public final boolean onOptionsItemSelected(MenuItem item) {
    return false;
  }

  public void setLeftTitleView(View v) {
    if (mCustomTitleView != null) {
      mCustomTitleView.setLeftView(v, null);
    }
  }

  public void setRightTitleView(View v) {
    if (mCustomTitleView != null) {
      mCustomTitleView.setRightView(v, null);
    }
  }

  public void setRightTitleView(View v, ViewGroup.LayoutParams layoutParams) {
    if (mCustomTitleView != null) {
      mCustomTitleView.setRightView(v, layoutParams);
    }
  }

  public View getRightView() {
    if (mCustomTitleView != null) {
      return mCustomTitleView.getRightView();
    }
    return null;
  }

  public void setLeftTitleView(View v, ViewGroup.LayoutParams layoutParams) {
    if (mCustomTitleView != null) {
      mCustomTitleView.setLeftView(v, layoutParams);
    }
  }

  @Override
  public void setTitle(CharSequence title) {
    if (mCustomTitleView != null) {
      mCustomTitleView.setTitle(title);
      return;
    }
    super.setTitle(title);
  }

  /**
   * 设置标题home图标
   */
  protected void setHomeDrawable(int res) {
    mActionBar.setHomeAsUpIndicator(res);
  }

  protected void hideActionBar() {
    if (null != mActionBar) {
      mActionBar.hide();
    }
  }

  @TargetApi(19)
  protected void disableStatusBarTint() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      if (mTintManager != null) {
        mTintManager.setStatusBarTintEnabled(false);
      }
    }
  }
}
