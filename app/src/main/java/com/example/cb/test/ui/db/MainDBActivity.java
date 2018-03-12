package com.example.cb.test.ui.db;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;

import com.example.cb.test.BaseActivity;
import com.example.cb.test.R;

public class MainDBActivity extends BaseActivity implements View.OnClickListener {
    private SQLiteOpenHelper mSqLiteOpenHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_db);
        initView();
    }

    private void initView() {
        SQLiteDatabase database = mSqLiteOpenHelper.getWritableDatabase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.instablish:
                //点击创建数据库库
                SQLiteOpenHelper sqLiteOpenHelper = new DatabaseHelper(MainDBActivity.this, "test_db");
                //数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开
                SQLiteDatabase database = sqLiteOpenHelper.getWritableDatabase();
                break;
            case R.id.upgrade:
                //点击更新数据
                SQLiteOpenHelper sqLiteOpenHelper_1 = new DatabaseHelper(MainDBActivity.this, "test_db", 2);
                //数据库实际上是没有被创建或者打开的，直到getWritableDatabase() 或者 getReadableDatabase() 方法中的一个被调用时才会进行创建或者打开
                SQLiteDatabase database_1 = sqLiteOpenHelper_1.getWritableDatabase();
                break;
            case R.id.insert:
                //点击插入数据到数据库
                break;
            case R.id.query:
                //点击查询数据库
                break;
            case R.id.modify:
                //点击修改数据
                break;
            case R.id.delete:
                //点击删除数据
                break;
            case R.id.delete_database:
                //点击删除数据库
                break;
            default:
                break;
        }
    }
}
