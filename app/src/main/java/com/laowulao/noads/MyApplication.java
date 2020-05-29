package com.laowulao.noads;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;

public class MyApplication extends Application {

    private static MyApplication myApplication;

    public static MyApplication getInstance(){
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        myApplication = this;

        CrashReport.initCrashReport(getApplicationContext(), "b4616e23bb", false);
    }
}
