package com.laowulao.noads.activity;

import android.os.Bundle;
import android.view.View;

import com.laowulao.noads.BaseActivity;
import com.laowulao.noads.R;
import com.laowulao.noads.utils.ToastUtils;

import androidx.annotation.Nullable;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_about).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                ToastUtils.showToast(AboutActivity.this,"已是最新版本");
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_about;
    }
}
