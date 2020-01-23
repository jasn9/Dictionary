package com.example.dictionary;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Dictionary";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "words";

    public DBHandler(Context context,String name,SQLiteDatabase.CursorFactory factory,int version)
    {
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String query = "CREATE TABLE "+TABLE_NAME+"(WORD STRING PRIMARY KEY,MEANING TEXT,MARK INTEGER);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public void addWord(Word word){
        ContentValues values = new ContentValues();
        values.put("WORD",word.get_word());
        values.put("MEANING",word.get_meaning());
        values.put("MARK",word.get_mark());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_NAME,null,values);
        db.close();
    }
    public String getMeaning(String s)
    {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM "+TABLE_NAME+" WHERE WORD = \""+s+"\";";
        Cursor cursor = db.rawQuery(q,null);
        String val;
        if(cursor.moveToFirst()){
             val = cursor.getString(1);
        }
        else {
            val = "Not Found";
        }
        return val;

    }

    public void setMark(String s,String p)
    {
        String q = "UPDATE "+TABLE_NAME+" SET MARK = \""+p+"\" WHERE WORD=\""+s+"\";";
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL(q);
    }


    public String getMark(String s)
    {
        SQLiteDatabase db = getWritableDatabase();
        String q = "SELECT * FROM "+TABLE_NAME+" WHERE WORD = \""+s+"\";";
        Cursor cursor = db.rawQuery(q,null);
        cursor.moveToFirst();
        return cursor.getString(2);
    }

    public ArrayList<String> getAllWord() {
        ArrayList<String> w = new ArrayList<String>();

        String q = "SELECT * FROM "+TABLE_NAME;

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(q,null);

        if(cursor.moveToFirst())
        {
            do{
                w.add(cursor.getString(0));
            }
            while(cursor.moveToNext());
        }

        return w;
    }

    public ArrayList<String> getAllWordWithMark() {
        ArrayList<String> w = new ArrayList<String>();

        String q = "SELECT * FROM "+TABLE_NAME+" WHERE MARK=\"1\";";

        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery(q,null);

        if(cursor.moveToFirst())
        {
            do{
                w.add(cursor.getString(0));
            }
            while(cursor.moveToNext());
        }

        return w;
    }



    public void deleteWord(String s)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME+" WHERE WORD=\""+s+"\";");
    }



}
