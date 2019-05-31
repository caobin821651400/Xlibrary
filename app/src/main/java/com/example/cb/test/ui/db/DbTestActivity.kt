package com.example.cb.test.ui.db

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.view.View
import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_db_test.*

/**
 * 数据库练习
 */
class DbTestActivity : BaseActivity(), View.OnClickListener {

    private var dbHelper: DbHelper? = null
    private var db: SQLiteDatabase? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_db_test)
        initView()
    }

    private fun initView() {
        btn_create.text = "查询"
        btn_create.setOnClickListener(this)
        btn_update.setOnClickListener(this)
        btn_add.setOnClickListener(this)
        btn_delete.setOnClickListener(this)

        dbHelper = DbHelper(this, "student.db", null, 1)
        db = dbHelper!!.readableDatabase

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_create -> query()
            R.id.btn_update -> update()
            R.id.btn_add -> add()
            R.id.btn_delete -> delete()
        }
    }

    fun delete() {
        db!!.delete("student", "name=?", arrayOf("曹斌"))
    }

    fun add() {
        var value = ContentValues()
        value.put("name", "曹斌")
        value.put("age", "24")
        value.put("grade", "aa")
        db!!.insert("student", null, value)

        value.clear()

        value.put("name", "曹斌1")
        value.put("age", "25")
        value.put("grade", "aa1")
        db!!.insert("student", null, value)
    }

    /**
     *
     */
    private fun query() {
        var cursor: Cursor = db!!.query("student", null, null, null, null, null, null)
        while (cursor.moveToNext()) {
            var name: String = cursor.getString(cursor.getColumnIndex("name"))
            var age: Int = cursor.getInt(cursor.getColumnIndex("age"))
            var grade: String = cursor.getString(cursor.getColumnIndex("grade"))
            XLogUtils.d("姓名：$name")
            XLogUtils.d("年龄：$age")
            XLogUtils.d("课程：$grade")
        }
        cursor.close()
    }

    /**
     * 更新
     */
    fun update() {
        var mContentValues = ContentValues()
        mContentValues.put("name", "武历婷")
        db!!.update("student", mContentValues, "name=?", arrayOf("曹斌1"))
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
