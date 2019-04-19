package com.example.cb.test.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Student implements Parcelable {

    public String name;

    protected Student(Parcel in) {
        name = in.readString();
    }

    public Student() {
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }
}
