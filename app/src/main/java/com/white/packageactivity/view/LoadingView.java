package com.white.packageactivity.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.white.packageactivity.R;
import com.white.packageactivity.utils.StringUtils;
import com.white.packageactivity.utils.ViewUtils;

/**
 * Created by MicYun on 2017/6/15.
 */

public class LoadingView extends FrameLayout {

  private TextView mLoadingText;
  private LinearLayout mLoadingLayout;

  private String mText;
  private boolean mIsCancellable = true;

  private boolean mIsShown = false;

  public LoadingView(@NonNull Context context) {
    super(context);
  }

  public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public LoadingView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  public static LoadingView newInstanceDecorView(ViewGroup decorView) {
    if (!ViewUtils.isViewAttachedToDecorView(decorView)) {
      throw new IllegalArgumentException("need a decorView for parent");
    }

    LoadingView loadingView = null;
    try {
      loadingView = (LoadingView) ViewUtils.newInstance(decorView, R.layout.loading_view);
    } catch (OutOfMemoryError error) {

    }

    if(loadingView != null){
      decorView.addView(loadingView, new ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT));
    }
    return loadingView;
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    initView();
    initText();
    initListener();
  }

  private void initListener() {
    setOnKeyListener(new OnKeyListener() {
      @Override
      public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
          if (mIsCancellable) {
            if (getVisibility() == VISIBLE) {
              dismiss();
              return true;
            }
          } else {
            return true;
          }
        }
        return false;
      }
    });

    mLoadingLayout.setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
      }
    });

    setOnTouchListener(new OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {

        if (MotionEvent.ACTION_DOWN == event.getAction()
            || MotionEvent.ACTION_MOVE == event.getAction()) {
          return true;
        }
        if (MotionEvent.ACTION_UP == event.getAction()) {
          if (mIsCancellable && getVisibility() == VISIBLE) {
            dismiss();
          }
          return true;
        }
        return false;
      }
    });
  }

  private void initText() {
    mText = getContext().getString(R.string.base_default_loading);
  }

  private void initView() {
    mLoadingText = (TextView) findViewById(R.id.loading_text);
    mLoadingLayout = (LinearLayout) findViewById(R.id.loading_layout);
  }

  public void dismiss() {
    mIsShown = false;
    setVisibility(View.GONE);
  }

  public void setCancelable(boolean isCancellable) {
    this.mIsCancellable = isCancellable;
  }

  public void setLoadingText(String text) {
    this.mText = text;
    mLoadingText.setText(text);
  }

  /**
   * use default loading text, 努力加载中&#8230;
   */
  public void show() {
    show(StringUtils.getString(R.string.base_default_loading));
  }

  /**
   * use user define loading text
   *
   * @param title if title is empty or null, will hide loading text view
   */
  public void show(String title) {
    setLoadingText(title);
    if (TextUtils.isEmpty(mText)) {
      mLoadingText.setVisibility(GONE);
    } else {
      mLoadingText.setVisibility(VISIBLE);
    }
    mIsShown = true;
    setVisibility(View.VISIBLE);
    requestFocus();
    bringToFront();
  }

  public boolean isShown() {
    return mIsShown;
  }
}
