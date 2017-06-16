package com.white.packageactivity.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.white.packageactivity.R;
import com.white.packageactivity.fragment.base.AsyncLoadFragment;

/**
 * Created by MicYun on 2017/6/16.
 */

public class HomeFragment extends AsyncLoadFragment {

  private TextView textView;

  @Override
  protected void onStartLoading() {

  }

  @Override
  protected void onInflated(View contentView, Bundle savedInstanceState) {

    textView = (TextView) contentView.findViewById(R.id.textView);
    textView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        showLoadingView();
      }
    });
  }

  @Override
  protected int getLayoutResId() {
    return R.layout.activity_main;
  }
}
