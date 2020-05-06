package com.example.cb.test.jetpack.room

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_room.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/4/30 12:43
 * @Desc :
 * ====================================================
 */
class RoomActivity : AppCompatActivity() {


    lateinit var studentDataBase: StudentDataBase
    lateinit var studentDao: StudentDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)


        studentDataBase = Room.databaseBuilder(applicationContext,
                StudentDataBase::class.java, "studio").build()
        studentDao = studentDataBase.studentDao()

        //查询 子线程
        btnInquire.setOnClickListener {

            Thread(object : Runnable {
                override fun run() {
                    studentDao?.let { XLogUtils.e(studentDao.findByName("曹斌5").pwd) }
                }
            }).start()
        }

        //查询多条件必须新建一个实体类 子线程
        btnInquireMore.setOnClickListener {

            Thread(object : Runnable {
                override fun run() {
                    val list=studentDao.two
                    studentDao?.let { XLogUtils.e(Gson().toJson(list)) }
                }
            }).start()
        }

        //插入 子线程
        btnInsert.setOnClickListener {
            DbTest().start()
        }

    }

    public inner class DbTest : Thread() {
        override fun run() {
            super.run()

            studentDao.insert(Student("曹斌1", "密码1"))
            studentDao.insert(Student("曹斌2", "密码2"))
            studentDao.insert(Student("曹斌3", "密码3"))
            studentDao.insert(Student("曹斌4", "密码4"))
            studentDao.insert(Student("曹斌5", "密码5"))

            val list = studentDao.all

            XLogUtils.d(Gson().toJson(list))
        }
    }
}
