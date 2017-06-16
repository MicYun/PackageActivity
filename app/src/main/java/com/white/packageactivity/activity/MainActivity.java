package com.white.packageactivity.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.white.packageactivity.activity.base.BaseAsyncActivity;
import com.white.packageactivity.fragment.HomeFragment;
import com.white.packageactivity.fragment.base.BaseFragment;

public class MainActivity extends BaseAsyncActivity {

  private BaseFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      fragment = (BaseFragment) Fragment.instantiate(this,
          HomeFragment.class.getName(), getIntent().getExtras());
      replaceFragment(fragment);
    }

  @Override
    protected String getTitleText() {
        return "标题";
    }
}
