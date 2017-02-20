package com.luong.blocksmsrecycle.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pham on 2/17/2017.
 */

public class DataHelper extends SQLiteOpenHelper {

    public static final String TB_SMS_GARBAGE = "tblGarbage";
    public static final String TB_SMS_GARBAGE_ID = "id";
    public static final String TB_SMS_GARBAGE_PRENUM = "prefixNum";

    public static final String TB_SMS_INSERT = "tblGarbageINSERT";
    public static final String TB_SMS_INSERT_BODY = "body";
    public static final String TB_SMS_INSERT_ADDRESS = "address";
    public static final String TB_SMS_INSERT_TIME = "time";

    SQLiteDatabase sqLiteDatabase;

    public DataHelper(Context context) {
        super(context, "data_smsgarbage", null, 1);
        sqLiteDatabase = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tbSMSgarbage = "CREATE TABLE " + TB_SMS_GARBAGE + "(" + TB_SMS_GARBAGE_ID + " text primary key, "
                + TB_SMS_GARBAGE_PRENUM + " text);";
        String tbINSERT = "CREATE TABLE " + TB_SMS_INSERT + "(" + TB_SMS_INSERT_BODY + " text, "
                + TB_SMS_INSERT_ADDRESS + " text, " + TB_SMS_INSERT_TIME + " text);";
        sqLiteDatabase.execSQL(tbSMSgarbage);
        sqLiteDatabase.execSQL(tbINSERT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<String> getListPrePhone() {
        List<String> strings = new ArrayList<>();

        String []columns ={TB_SMS_GARBAGE_ID, TB_SMS_GARBAGE_PRENUM};
        Cursor cusor = sqLiteDatabase.query(TB_SMS_GARBAGE, columns , null, null, null, null, null);
        cusor.moveToFirst();
        String data="";
        while(cusor.isAfterLast()==false)
        {
            data= (String) cusor.getString(0).toString();//+" - " + (String)cusor.getString(1).toString();
            strings.add(data);
            cusor.moveToNext();
        }
        return strings;
    }

    public List<String> getListSMSGar() {
        List<String> strings = new ArrayList<>();

        String []columns ={TB_SMS_INSERT_ADDRESS, TB_SMS_INSERT_BODY, TB_SMS_INSERT_TIME};
        Cursor cusor = sqLiteDatabase.query(TB_SMS_INSERT, columns , null, null, null, null, null);
        cusor.moveToFirst();
        String data="";
        while(cusor.isAfterLast()==false)
        {
            data= (String) cusor.getString(0).toString();//+" - " + (String)cusor.getString(1).toString();
            strings.add(data);
            cusor.moveToNext();
        }
        return strings;
    }

    public boolean insertData(String PrefixNum)
    {
        try {
            //sqlite = openOrCreateDatabase("qlsach.db", MODE_PRIVATE, null);
            ContentValues _values = new ContentValues();
            _values.put(TB_SMS_GARBAGE_ID, PrefixNum);
            _values.put(TB_SMS_GARBAGE_PRENUM, PrefixNum);
            if(sqLiteDatabase.insert(TB_SMS_GARBAGE, null, _values) != -1) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public boolean insertSMSGar(String address, String body, String time)
    {
        try {
            //sqlite = openOrCreateDatabase("qlsach.db", MODE_PRIVATE, null);
            ContentValues _values = new ContentValues();
            _values.put(TB_SMS_INSERT_ADDRESS, address);
            _values.put(TB_SMS_INSERT_BODY, body);
            _values.put(TB_SMS_INSERT_TIME, time);
            if(sqLiteDatabase.insert(TB_SMS_INSERT, null, _values) != -1) {
                return true;
            }
            else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

    public  boolean update(String idUpdate, String value)
    {
//        sqlite = openOrCreateDatabase("smsgarbage.db", MODE_PRIVATE, null);
        ContentValues values = new ContentValues();
        //values.put("id", idUpdate);
        values.put(TB_SMS_GARBAGE_PRENUM, value);
        if(sqLiteDatabase.update(TB_SMS_GARBAGE, values, "id=?", new String[]{idUpdate}) != -1) {
           return true;
        }
        else {
            return false;
        }
    }

    public boolean deletePreNum(String id) {
        if(sqLiteDatabase.delete(TB_SMS_GARBAGE, "id=?", new String []{id}) != -1)
        {
           return true;
        }
        else {
            return false;
        }
    }

    public boolean checkDumpNumber(String f)
    {
        Cursor c = sqLiteDatabase.query(TB_SMS_GARBAGE, null, TB_SMS_GARBAGE_PRENUM+"=?", new String[]{f}, null, null, null, null);
        if(c.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
}
