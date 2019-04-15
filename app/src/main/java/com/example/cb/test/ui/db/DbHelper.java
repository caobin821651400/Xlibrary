package com.example.cb.test.ui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import cb.xlibrary.utils.XLogUtils;

public class DbHelper extends SQLiteOpenHelper {

    public static String CREATE_STUDENT = "create table student("
            + "id integer primary key autoincrement,"
            + "name text,"
            + "age integer,"
            + "grade text)";


    public DbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT);
        XLogUtils.d("创建成果");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
