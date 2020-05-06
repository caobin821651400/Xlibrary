package com.example.cb.test.jetpack.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface StudentDao {

    @Insert
    void insert(Student... students);

    @Delete
    void delete(Student students);

    @Update
    void update(Student students);


    @Query("SELECT * FROM Student")
    List<Student> getAll();

    @Query("SELECT * FROM Student WHERE name LIKE :first")
    Student findByName(String first);

    @Query("SELECT name,pwd FROM Student")
    List<StudentMore> getTwo();
}
