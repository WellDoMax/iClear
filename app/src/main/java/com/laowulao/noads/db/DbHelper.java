package com.laowulao.noads.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "iclear";

    private static final int DB_VERSION = 1;

    private static DbHelper HELPER = null;

    private static SQLiteDatabase db = null;

    private static DbHelper getInstance(Context context){
        if(HELPER == null){
            HELPER = new DbHelper(context,DB_NAME,null,DB_VERSION);
        }
        return HELPER;
    }

    public static SQLiteDatabase getDb(Context context){
        if(db == null){
            db = getInstance(context).getWritableDatabase();
        }
        return db;
    }


    private DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Table.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(Table.CREATE_TABLE);
    }
}
