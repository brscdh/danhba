package com.example.danhba.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.Nullable;

import com.example.danhba.activity.HomeActivity;
import com.example.danhba.activity.LikeActivity;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    public void query(String sql){
        SQLiteDatabase query = getWritableDatabase();
        query.execSQL(sql);
    }

    public void INSERT(String ten, int sdt){
        SQLiteDatabase insret = getWritableDatabase();
        String sql = "INSERT INTO CALL VALUES(null,?,?)";
        SQLiteStatement statement =insret.compileStatement(sql);
        statement.bindString(1,ten);
        statement.bindString(2,sdt+"");
        statement.executeInsert();
    }
    public void INSERT_YT(Class<LikeActivity> context, String ten, int sdt){
        SQLiteDatabase insret = getWritableDatabase();
        String sql = "INSERT INTO CALL VALUES(null,?,?)";
        SQLiteStatement statement =insret.compileStatement(sql);
        statement.bindString(1,ten);
        statement.bindString(2,sdt+"");
        statement.executeInsert();
    }

    public boolean UPDATE(Class<HomeActivity> context, Call calls ){
        SQLiteDatabase update = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("TEN", calls.getTen());
        values.put("SDT",calls.getSdt());
        int row = update.update("CALL", values, "Id=?", new String[]{calls.getId()+""});
        return (row>0);
    }
    public boolean DELETE(Class<HomeActivity> context, int id){
        SQLiteDatabase delete = getWritableDatabase();
        int row = delete.delete("CALL", "Id=?", new String[]{id+""});
        return (row>0);
    }
    public boolean DELETE_YT(Class<LikeActivity> context, int id){
        SQLiteDatabase delete = getWritableDatabase();
        int row = delete.delete("CALL", "Id=?", new String[]{id+""});
        return (row>0);
    }
    public Cursor select(String sql){
        SQLiteDatabase select = getReadableDatabase();
        return select.rawQuery(sql, null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
