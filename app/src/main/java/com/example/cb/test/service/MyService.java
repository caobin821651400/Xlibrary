package com.example.cb.test.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.cb.test.R;
import com.example.cb.test.rx.rxjava.RxJavaMainActivity;

import java.util.concurrent.TimeUnit;

import cn.sccl.xlibrary.utils.XLogUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MyService extends Service {
    private static final String TAG = "jiatai";
    private MyHandler handler;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "service oncreate");
        handler = new MyHandler();


                Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        i++;
                        XLogUtils.d("执行=" + i);
                        if (i == 10) {
                            Intent i = new Intent(MyService.this, RxJavaMainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(i);

//                            NotificationUtils notificationUtils = new NotificationUtils(MyService.this);
//                            String content = "fullscreen intent test";
//                            notificationUtils.sendNotificationFullScreen(getString(R.string.app_name), content, "type");
                        }
                    }
                });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, final int startId) {
        int type = intent.getIntExtra("type",1);
        Log.d(TAG, "the create notification type is " + type + "----" + (type == 1 ? "true" : "false"));
        if(type == 1){
            createNotificationChannel();
        }else{
            createErrorNotification();
        }
//        new Thread(){
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                handler.sendEmptyMessage(startId);
//            }
//        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void createErrorNotification() {
        Notification notification = new Notification.Builder(this).build();
        startForeground(0, notification);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        // 通知渠道的id
        String id = "my_channel_01";
        // 用户可以看到的通知渠道的名字.
        CharSequence name = "11111111";
//         用户可以看到的通知渠道的描述
        String description = "2222222222222";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel = new NotificationChannel(id, name, importance);
//         配置通知渠道的属性
        mChannel.setDescription(description);
//         设置通知出现时的闪灯（如果 android 设备支持的话）
        mChannel.enableLights(true); mChannel.setLightColor(Color.RED);
//         设置通知出现时的震动（如果 android 设备支持的话）
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
//         最后在notificationmanager中创建该通知渠道 //
        mNotificationManager.createNotificationChannel(mChannel);

        // 为该通知设置一个id
        int notifyID = 1;
        // 通知渠道的id
        String CHANNEL_ID = "my_channel_01";
        // Create a notification and set the notification channel.
        Notification notification = new Notification.Builder(this)
                .setContentTitle("New Message") .setContentText("You've received new messages.")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setChannelId(CHANNEL_ID)
                .build();
        startForeground(1,notification);
    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            stopSelf(msg.what);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopForeground(true);
    }
    private int i = 0;
}
//import android.annotation.SuppressLint;
//import android.app.Service;
//import android.content.Intent;
//import android.os.IBinder;
//
//import androidx.annotation.Nullable;
//
//import com.example.cb.test.MainActivity;
//import com.example.cb.test.R;
//import com.example.cb.test.rx.rxjava.RxJavaMainActivity;
//import com.example.cb.test.utils.NotificationUtils;
//
//import java.util.concurrent.TimeUnit;
//
//import cb.xlibrary.utils.XLogUtils;
//import io.reactivex.Observable;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.functions.Consumer;
//import io.reactivex.schedulers.Schedulers;
//
//public class MyService extends Service {
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//    @SuppressLint("CheckResult")
//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//
//        Observable.interval(1, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        i++;
//                        XLogUtils.d("执行=" + i);
//                        if (i == 10) {
////                            Intent i = new Intent(MyService.this, RxJavaMainActivity.class);
////                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                            startActivity(i);
//
//                            NotificationUtils notificationUtils = new NotificationUtils(MyService.this);
//                            String content = "fullscreen intent test";
//                            notificationUtils.sendNotificationFullScreen(getString(R.string.app_name), content, "type");
//                        }
//                    }
//                });
//    }
//}
