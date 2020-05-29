package com.laowulao.noads.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.laowulao.noads.BaseActivity;
import com.laowulao.noads.R;

public class TestActivity extends BaseActivity{

    int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setBackVisible(View.VISIBLE);

        new Thread(){
            @Override
            public void run() {
                super.run();
                while (count > 0){
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count--;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(TestActivity.this,MainActivity.class));
                        finish();
                    }
                });
            }
        }.start();
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_test;
    }

}
