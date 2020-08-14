package com.example.cb.test.jetpack.room

import androidx.room.Room
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_room.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/4/30 12:43
 * @Desc :
 * ====================================================
 */
class RoomActivity : BaseActivity() {

    lateinit var studentDataBase: StudentDataBase
    lateinit var studentDao: StudentDao

    override fun getLayoutId(): Int {
        return R.layout.activity_room
    }

    override fun initUI() {
        setHeaderTitle("Room使用")

        studentDataBase = Room.databaseBuilder(applicationContext,
                StudentDataBase::class.java, "studio11").build()
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
                    val list = studentDao.two
                    studentDao?.let { XLogUtils.e(Gson().toJson(list)) }
                }
            }).start()
        }

        //插入 子线程
        btnInsert.setOnClickListener {
            DbTest().start()
        }
    }


    override fun initEvent() {
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
