package com.laowulao.noads.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.laowulao.noads.model.Record;
import com.laowulao.noads.utils.AppUtils;
import com.laowulao.noads.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RecordDao {

    static final int MAX_THREAD = 5;
    static ExecutorService service = Executors.newFixedThreadPool(MAX_THREAD);

    private SQLiteDatabase db;

    public RecordDao(Context context) {
        this.db = DbHelper.getDb(context);
    }

    public static void saveOrUpdateByPkg(final String pkg,final Context context){

        service.submit(new Runnable() {
            @Override
            public void run() {
                RecordDao dao = new RecordDao(context);
                Record record = dao.getRecordByPkgName(pkg);
                if(record == null || TextUtils.isEmpty(record.getPackageName())){
                    record = new Record();
                    record.setPackageName(pkg);
                    record.setModifyTime(DateUtils.getCurrentDateStr());
                    record.setAppName("");
                    record.setTimes(1);
                    record.setAppName(AppUtils.getAppName(context,pkg));
                    dao.add(record);
                } else {
                    int times = record.getTimes();
                    times++;
                    record.setTimes(times);
                    record.setModifyTime(DateUtils.getCurrentDateStr());
                    dao.update(record);
                }
            }
        });
    }

    public boolean add(Record record){
        if(record == null){
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(Table.APP_NAME,record.getAppName());
        values.put(Table.PACKAGE_NAME,record.getPackageName());
        values.put(Table.TIMES,record.getTimes());
        values.put(Table.MODIFY_TIME,record.getModifyTime());
        long result = db.insert(Table.NAME,null,values);
        return result != -1;
    }

    public boolean update(Record record){
        if(record == null){
            return false;
        }
        ContentValues values = new ContentValues();
        values.put(Table.APP_NAME,record.getAppName());
        values.put(Table.PACKAGE_NAME,record.getPackageName());
        values.put(Table.TIMES,record.getTimes());
        values.put(Table.MODIFY_TIME,record.getModifyTime());
        int result = db.update(Table.NAME,values,Table.PACKAGE_NAME+"=?",new String[]{record.getPackageName()});
        return result != -1;
    }


    public Record getRecordByPkgName(String pkgName){
        Cursor cursor = db.query(Table.NAME,new String[]{Table.PACKAGE_NAME,Table.APP_NAME,Table.MODIFY_TIME,
                        Table.TIMES},Table.PACKAGE_NAME + "=?",new String[]{pkgName},null,null,null);
        if(cursor == null){
            return null;
        }
        Record record = new Record();
        while(cursor.moveToNext()){
            record.setAppName(cursor.getString(Table.ID_APP_NAME));
            record.setPackageName(cursor.getString(Table.ID_PACKAGE_NAME));
            record.setModifyTime(cursor.getString(Table.ID_MODIFY_TIME));
            record.setTimes(cursor.getInt(Table.ID_TIMES));
            break;
        }
        return record;
    }

    public List<Record> getRecordList(){
        List<Record> list = new ArrayList<>();
        Cursor cursor = db.query(Table.NAME,new String[]{Table.PACKAGE_NAME,Table.APP_NAME,Table.MODIFY_TIME,
                        Table.TIMES},null,null,null,null,Table.TIMES+" desc");
        if(cursor == null){
            return list;
        }

        while(cursor.moveToNext()){
            Record record = new Record();
            record.setAppName(cursor.getString(Table.ID_APP_NAME));
            record.setPackageName(cursor.getString(Table.ID_PACKAGE_NAME));
            record.setModifyTime(cursor.getString(Table.ID_MODIFY_TIME));
            record.setTimes(cursor.getInt(Table.ID_TIMES));
            list.add(record);
        }
        return list;
    }

    public int getClearCount(){
        int count = 0;
        Cursor cursor = db.query(Table.NAME,new String[]{Table.PACKAGE_NAME,Table.APP_NAME,Table.MODIFY_TIME,
                Table.TIMES},null,null,null,null,Table.TIMES+" desc");
        if(cursor == null){
            return count;
        }

        while(cursor.moveToNext()){
            int times = cursor.getInt(Table.ID_TIMES);
            count += times;
        }
        return count;
    }

    public boolean cleanData(){
        db.delete(Table.NAME,null,null);
        return true;
    }


}
