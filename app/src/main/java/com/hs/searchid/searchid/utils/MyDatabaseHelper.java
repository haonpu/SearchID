package com.hs.searchid.searchid.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by User on 2015/8/24.
 * author haosuo
 * 自定义帮助类，继承已有的SQLiteOpenHelper
 * 创建数据库用于管理已有的身份证信息
 */
public class MyDatabaseHelper extends SQLiteOpenHelper{

    public final String CREATE_ID_RECORD = "create table Id_records (" +
            "id integer primary key autoincrement," +
            "id_num text," +         //身份证号
            "name text," +
            "sex text," +          //性别
            "address text," +
            "birthday text)" ;

    private Context mContext;

    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("debug","execute create sql tables --->");
        db.execSQL(CREATE_ID_RECORD);
        Log.d("debug","execute create sql tables success --- --->");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("drop table if exists Id_records");
        onCreate(db);

    }
}
