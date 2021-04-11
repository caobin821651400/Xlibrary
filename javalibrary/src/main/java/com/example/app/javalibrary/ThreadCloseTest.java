package com.example.app.javalibrary;

import java.util.Timer;
import java.util.TimerTask;

public class ThreadCloseTest {

    public static void main(String[] args) {
        Thread thread = new Thread(new Work(), "Thread1");
        thread.start();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                System.err.println("通知线程中断");
                thread.interrupt();
            }
        }, 2000);
    }


    static class Work implements Runnable {
        private int count = 1;
        @Override
        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.err.println("count:" + count);
                    count++;
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //特别要注意 调用sleep()、wait()等等，会抛出InterruptedException
                    //如果抛出异常会清除‘thread.interrupt()’的标志位,也就是isInterrupted=false,需要在设置一次
                    //不然走到catch语句后，线程不会停止
                    //为什么这样做的原因是，程序员在知道发生catch后做一些资源释放操作
                    System.err.println("标志位="+Thread.currentThread().isInterrupted());
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}