package com.example.cb.test.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.cb.test.aidl.IMyService;
import com.example.cb.test.aidl.Student;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import cb.xlibrary.utils.XLogUtils;

public class IStudentService extends Service {

    private List<Student> mList = new ArrayList<>();
    private boolean mCanRun = true;

    private final IMyService.Stub stub = new IMyService.Stub() {
        @Override
        public List<Student> getStudent() {
            synchronized (mList) {
                return mList;
            }
        }

        @Override
        public void addStudent(Student student) {
            synchronized (mList) {
                if (!mList.contains(student)) {
                    mList.add(student);
                }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Thread thread = new Thread(null, new ServiceWork(), "testThread");
        thread.start();

        synchronized (mList) {
            for (int i = 1; i < 6; i++) {
                Student student = new Student();
                student.name = "student#" + i;
                mList.add(student);
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }


    class ServiceWork implements Runnable {

        long count = 0;

        @Override
        public void run() {
            while (mCanRun) {
                XLogUtils.d("线程 " + count);
                count++;
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        mCanRun = false;
        super.onDestroy();
    }
}
