package com.white.packageactivity.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.white.packageactivity.R;
import com.white.packageactivity.interfacce.BaseView;
import com.white.packageactivity.interfacce.TitleContainer;
import com.white.packageactivity.utils.ActivityUtils;
import com.white.packageactivity.utils.ColorResUtils;
import com.white.packageactivity.utils.StringUtils;
import com.white.packageactivity.utils.ViewUtils;

/**
 * Created by MicYun on 2017/6/5.
 */

public class CommonTitleView extends RelativeLayout implements BaseView, TitleContainer{

  protected TextView mTitle;
  protected ImageView mBackImageView;
  private LinearLayout mLeftLayout;
  private LinearLayout mRightLayout;
  private RightViewChangedListener mRightViewChangedListener;
  private View mBottomDivide;


  public CommonTitleView(Context context) {
    super(context);
  }

  public CommonTitleView(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  public static CommonTitleView newInstance(ViewGroup parent) {
    return (CommonTitleView) ViewUtils.newInstance(parent, R.layout.common_title_view_layout);
  }

  public static CommonTitleView newInstance(Context context) {
    return (CommonTitleView) ViewUtils.newInstance(context, R.layout.common_title_view_layout);
  }

  @Override
  protected void onFinishInflate() {
    super.onFinishInflate();
    mTitle = (TextView) findViewById(R.id.common_title_view_layout_title);
    mBackImageView = (ImageView) findViewById(R.id.common_title_view_layout_left_arrow);
    mLeftLayout = (LinearLayout) findViewById(R.id.common_title_view_layout_left_container);
    mRightLayout = (LinearLayout) findViewById(R.id.common_title_view_layout_right_container);
    mBottomDivide = findViewById(R.id.common_title_view_layout_divide);
    initListener();
    setTitleStyle();
  }

  private void initListener() {
    mLeftLayout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Activity activity = ActivityUtils.findActivity(v);
        if (activity != null) {
          try {
            activity.onBackPressed();
          } catch (Exception e) {
            activity.finish();
          }
        }
      }
    });
  }

  @Override
  public void setTitle(CharSequence title) {
    mTitle.setText(title);
  }

  @Override
  public void setTitle(int resId) {
    mTitle.setText(StringUtils.getString(resId));
  }

  @Override
  public View getView() {
    return this;
  }

  @Override
  public void setLeftView(View v, ViewGroup.LayoutParams layoutParams) {
    if (v == null) {
      return;
    }
    mLeftLayout.removeAllViews();
    if (layoutParams == null) {
      mLeftLayout.addView(v);
    } else {
      mLeftLayout.addView(v, layoutParams);
    }
  }

  @Override
  public void setRightView(View v, ViewGroup.LayoutParams layoutParams) {
    if (v == null) {
      return;
    }
    mRightLayout.removeAllViews();
    if (layoutParams == null) {
      mRightLayout.addView(v);
    } else {
      mRightLayout.addView(v, layoutParams);
    }
    if (mRightViewChangedListener != null) {
      mRightViewChangedListener.onRightViewChangedListener(v);
    }
  }

  @Override
  public View getRightView() {
    return mRightLayout;
  }

  public TextView getTitle() {
    return mTitle;
  }

  public ImageView getBackImageView() {
    return mBackImageView;
  }

  protected void setTitleStyle() {
    setBackgroundColor(ColorResUtils.getColor(R.color.colorPrimary));
    if (mTitle != null) {
      mTitle.setTextColor(ColorResUtils.getColor(R.color.theme_title_text_color));
    }
    if (mBackImageView != null) {
      mBackImageView.setImageResource(R.drawable.base_title_back);
    }
  }

  public View getBottomDivide() {
    return mBottomDivide;
  }

  public void setBottomDivide(View mBottomDivide) {
    this.mBottomDivide = mBottomDivide;
  }

  public interface RightViewChangedListener {
    void onRightViewChangedListener(View view);
  }

  public void setOnRightViewChangedListener(RightViewChangedListener listener) {
    mRightViewChangedListener = listener;
  }
}
