package com.laowulao.noads.activity;

import android.os.Bundle;
import android.view.View;

import com.laowulao.noads.BaseActivity;
import com.laowulao.noads.R;

import androidx.annotation.Nullable;

public class AppLogActivity extends BaseActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
    }

    private void initView() {
        setTitle("应用日志");
        setBackVisible(View.VISIBLE);

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_applog;
    }
}
