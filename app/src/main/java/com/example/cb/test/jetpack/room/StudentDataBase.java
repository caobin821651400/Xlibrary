package com.example.cb.test.jetpack.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class}, version = 1)//多个逗号隔开
public abstract class StudentDataBase extends RoomDatabase {


    public abstract StudentDao studentDao();

}
