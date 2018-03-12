package com.example.cb.test.ui.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 描述：
 * 作者：曹斌
 * date:2018/3/8 11:32
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static Integer Version = 1;

    /**
     * 必须有构造方法
     *
     * @param context
     * @param name    数据库名
     * @param factory 通常为NULL
     * @param version 数据库版本
     */
    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public DatabaseHelper(Context context, String name) {
        this(context, name, null, Version);
    }


    /**
     * 当数据库第一次创建时调用
     * 作用：创建数据库表 & 初始化数据
     * SQLite数据库创建支持的数据类型： 整型数据、字符串类型、日期类型、二进制
     *
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table user(id int primary key,name varchar(200))";
        db.execSQL(sql);
    }

    /**
     * 当数据库版本发生变化时自动调用,更新数据库表结构
     * 在onCreate()时传入的version与当前不一致则调用该方法
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("更新数据库版本为:" + newVersion);
    }
}
