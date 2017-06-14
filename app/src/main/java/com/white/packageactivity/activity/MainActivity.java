package com.white.packageactivity.activity;

import android.os.Bundle;

import com.white.packageactivity.activity.base.BaseTitleActivity;

public class MainActivity extends BaseTitleActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected String getTitleText() {
        return "标题";
    }
}
