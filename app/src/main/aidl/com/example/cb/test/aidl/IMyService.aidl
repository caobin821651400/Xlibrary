// IMyService.aidl
package com.example.cb.test.aidl;

// Declare any non-default types here with import statements
import com.example.cb.test.aidl.Student;
interface IMyService {

         List<Student> getStudent();

         void addStudent(in Student student);
}
