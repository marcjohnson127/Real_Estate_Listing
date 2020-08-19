package com.example.playground;

import android.app.ActionBar;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class mySQLiteDBHandler extends SQLiteOpenHelper {
    //Initialize Database
    public static final String DATABASE_NAME = "database_name";
    public static final String TABLE_NAME = "table_name";

    mySQLiteDBHandler(Context context) {super(context, DATABASE_NAME,null,1);}

    @Override
    public void onCreate(SQLiteDatabase db) {
    //Create Table
        String createTable = "CREATE TABLE " + TABLE_NAME + "(id INTEGER PRIMARY KEY, street TEXT, city TEXT, area TEXT, room Integer, bath Double, sqft Integer, price Integer, hoa Integer, tax Integer, type TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    //Drop older table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String street, String city, String area, int room, double bath, int sqft, int price, int hoa, int tax, String type) {
    //Get WriteAble Database
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
    //Create ContentValues
        ContentValues contentValues = new ContentValues();
        contentValues.put("street", street);
        contentValues.put("city", city);
        contentValues.put("area",area);
        contentValues.put("room", room);
        contentValues.put("bath",bath);
        contentValues.put("price",price);
        contentValues.put("sqft",sqft);
        contentValues.put("hoa",hoa);
        contentValues.put("tax",tax);
        contentValues.put("type",type);
    //Add Values into Database
        sqLiteDatabase.insert(TABLE_NAME,null,contentValues);
        return true;
    }

    public ArrayList allData() {
    //Get ReadAble Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String >();
    //Create Cursor to Select All Values
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("street")));
            cursor.moveToNext();
        }
        return arrayList;
    }

    public ArrayList allData2() {
        //Get ReadAble Database
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<String> arrayList = new ArrayList<String >();
        //Create Cursor to Select All Values
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            arrayList.add(cursor.getString(cursor.getColumnIndex("area")));
            cursor.moveToNext();
        }
        return arrayList;

    }

    public Cursor getList(String addr) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM table_name WHERE street = '" + addr + "'",null);
        return cursor;
    }

    public void deleteEntry(String addr) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        String dltEntry = "DELETE FROM table_name WHERE street = '" + addr + "'";
        sqLiteDatabase.execSQL(dltEntry);
    }


}