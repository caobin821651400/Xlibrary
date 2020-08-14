package com.example.cb.test;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

public String name;
public String sex;
public int age;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.sex);
        dest.writeInt(this.age);
    }

    public Student() {
    }

    protected Student(Parcel in) {
        this.name = in.readString();
        this.sex = in.readString();
        this.age = in.readInt();
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel source) {
            return new Student(source);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public static void main(String[] args) {
        Student student = new Student();
        student.name = "caobin";
        System.err.println(student.toString());
    }
}
