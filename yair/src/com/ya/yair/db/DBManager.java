
package com.ya.yair.db;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DBManager {

    private DBHelper helper;
    private SQLiteDatabase db;

    public DBManager(Context context) {
        helper = new DBHelper(context);
        // ��ΪgetWritableDatabase�ڲ�������mContext.openOrCreateDatabase(mName, 0,
        // mFactory);
        // ����Ҫȷ��context�ѳ�ʼ��,���ǿ��԰�ʵ����DBManager�Ĳ������Activity��onCreate��
        db = helper.getWritableDatabase();
    }

    /**
     * add persons
     * 
     * @param persons
     */
    public void add(String mac) {
        db.beginTransaction(); // ��ʼ����
        try {
            db.execSQL("INSERT INTO sbmac VALUES (null, ?)", new Object[] {
                mac
            });
            db.setTransactionSuccessful(); // ��������ɹ����
        } finally {
            db.endTransaction(); // ��������
        }
    }

    /**
     * update person's age
     * 
     * @param person
     */
    /*
     * public void updateAge(Person person) { ContentValues cv = new
     * ContentValues(); cv.put("age", person.age); db.update("person", cv,
     * "name = ?", new String[]{person.name}); }
     */

    /**
     * delete old person
     * 
     * @param person
     */
    // public void deleteOldPerson(Person person) {
    // db.delete("person", "age >= ?", new
    // String[]{String.valueOf(person.age)});
    // }

    /**
     * query all persons, return list
     * 
     * @return List<Person>
     */
    public List<String> query() {
        ArrayList<String> macs = new ArrayList<String>();
        Cursor c = queryTheCursor();
        while (c.moveToNext()) {
            String mac = c.getString(c.getColumnIndex("mac"));
            macs.add(mac);
        }
        c.close();
        return macs;
    }

    /**
     * �������ݿ��������е�mac�豸
     * 
     * @return Cursor
     */
    public Cursor queryTheCursor() {
        Cursor c = db.rawQuery("SELECT * FROM sbmac", null);
        return c;
    }

    /**
     * close database
     */
    public void closeDB() {
        db.close();
    }

}
