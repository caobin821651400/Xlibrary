package com.example.cb.test.ui.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import cn.sccl.xlibrary.utils.XLogUtils

class DbHelper(context: Context?, name: String?, factory: SQLiteDatabase.CursorFactory?, version: Int) : SQLiteOpenHelper(context, name, factory, version) {

    companion object {
        var CREATE_STUDENT: String = ("create table student("
                + "id integer primary key autoincrement,"
                + "name text,"
                + "age integer,"
                + "grade text)")
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_STUDENT)
        XLogUtils.d("数据库创建成功")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        XLogUtils.d("数据库更新-版本-->  $newVersion")
    }

}