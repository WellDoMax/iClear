package com.laowulao.noads.activity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;

import com.laowulao.noads.BaseActivity;
import com.laowulao.noads.R;

import androidx.annotation.Nullable;

public class HelpActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.btn_help).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS));
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_help;
    }
}
