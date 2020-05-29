package com.laowulao.noads.activity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.laowulao.noads.BaseActivity;
import com.laowulao.noads.R;
import com.laowulao.noads.db.RecordDao;
import com.laowulao.noads.model.Record;
import com.laowulao.noads.utils.AppUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public class TopListActivity extends BaseActivity {

    TextView tvEmpty;
    ListView lvTop;

    private List<AppRecord> list = new ArrayList<>();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
    }

    private void initView() {

        tvEmpty = findViewById(R.id.tv_empty);
        lvTop = findViewById(R.id.lv_toplist);

        setTitle("广告榜");
        setRightBtnVisible(View.VISIBLE);
        setRightBtnText("清除");
        setRightBtnListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(TopListActivity.this);
                builder.setMessage("确认清除所有记录？");
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new RecordDao(TopListActivity.this).cleanData();
                        initData();
                    }
                });
                final AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void initData(){
        new Thread(){
            @Override
            public void run() {
                super.run();
                list.clear();
                List<Record> listRecord = new RecordDao(TopListActivity.this).getRecordList();
                if(list != null && listRecord.size() > 0){
                    for(Record record : listRecord){
                        AppRecord appRecord = new AppRecord();
                        appRecord.record = record;
                        appRecord.drawable = AppUtils.getDrawable(TopListActivity.this,record.getPackageName());
                        list.add(appRecord);
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setData();
                    }
                });
            }
        }.start();
    }

    private void setData(){
        if(list.size() > 0){
            tvEmpty.setVisibility(View.GONE);
            lvTop.setVisibility(View.VISIBLE);
        } else {
            tvEmpty.setVisibility(View.VISIBLE);
            lvTop.setVisibility(View.GONE);
        }

        lvTop.setAdapter(new TopAdapter());
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_toplist;
    }

    public class TopAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            ViewHolder holder;
            if(view == null){
                holder = new ViewHolder();
                view = LayoutInflater.from(TopListActivity.this).inflate(R.layout.item_app_times,null);
                holder.ivLog = view.findViewById(R.id.iv_logo);
                holder.tvLastTime = view.findViewById(R.id.tv_last_time);
                holder.tvName = view.findViewById(R.id.tv_name);
                holder.tvTimes = view.findViewById(R.id.tv_times);
                view.setTag(holder);
            } else {
                holder = (ViewHolder) view.getTag();
            }
            AppRecord record = list.get(i);
            holder.ivLog.setImageDrawable(record.drawable);
            holder.tvName.setText(record.record.getAppName());
            holder.tvTimes.setText(record.record.getTimes() + "次");
            holder.tvLastTime.setText("上次拦截时间：" + record.record.getModifyTime());
            return view;
        }

        class ViewHolder{
            ImageView ivLog;
            TextView tvName,tvTimes,tvLastTime;
        }
    }

    public class AppRecord{
        Drawable drawable;
        Record record;
    }
}
