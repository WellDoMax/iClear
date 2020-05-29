package com.laowulao.noads.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.laowulao.noads.BaseActivity;
import com.laowulao.noads.R;
import com.laowulao.noads.db.RecordDao;

public class MainActivity extends BaseActivity implements View.OnClickListener{

    private TextView tvTimes,tvSeconds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        findViewById(R.id.rl_about).setOnClickListener(this);
        findViewById(R.id.ll_circle).setOnClickListener(this);
        findViewById(R.id.rl_help).setOnClickListener(this);
        findViewById(R.id.rl_top_list).setOnClickListener(this);
        findViewById(R.id.rl_white_list).setOnClickListener(this);
        tvTimes = findViewById(R.id.tv_times);
        tvSeconds = findViewById(R.id.tv_seconds);
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    void initData(){
        int count = new RecordDao(MainActivity.this).getClearCount();

        tvTimes.setText( count + "次");

        tvSeconds.setText(getTime(count));
    }

    private String getTime(int times){
        int per = 5;
        int count = times * per;
        if(count > 100){
            int min = count / 60;
            int mod = count % 60;
            if(min > 100){
                int hour = min / 60;
                min = min % 60;
                return hour + "时" + min + "分" + mod + "秒";
            } else {
                return min + "分" + mod + "秒";
            }
        } else {
            return count + "秒";
        }

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.rl_about:
                about();
                break;
            case R.id.rl_help:
                help();
                break;
            case R.id.rl_top_list:
            case R.id.ll_circle:
                topList();
                break;
            case R.id.rl_white_list:
                whiteList();
                break;
        }

    }

    private void whiteList() {
        startActivity(new Intent(MainActivity.this, AppLogActivity.class));
    }

    private void topList() {
        startActivity(new Intent(MainActivity.this, TopListActivity.class));
    }

    private void help() {
        startActivity(new Intent(MainActivity.this, HelpActivity.class));
    }

    private void about() {
        startActivity(new Intent(MainActivity.this, AboutActivity.class));
    }
}
