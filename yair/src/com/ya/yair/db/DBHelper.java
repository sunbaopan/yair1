
package com.ya.yair.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "yteair.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        // CursorFactory����Ϊnull,ʹ��Ĭ��ֵ
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // �豸��mac��ַ�洢���������(��ͬһ���������в�ֹһ̨�豸)
        db.execSQL("CREATE TABLE IF NOT EXISTS sbmac" +
                "(_id INTEGER PRIMARY KEY AUTOINCREMENT, mac VARCHAR(100))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
        db.execSQL("ALTER TABLE sbmac ADD COLUMN other STRING");
    }

}
