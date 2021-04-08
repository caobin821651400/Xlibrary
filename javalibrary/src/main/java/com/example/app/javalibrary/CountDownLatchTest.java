package com.example.app.javalibrary;

import java.util.concurrent.CountDownLatch;
/**
 * ====================================================
 * @User :caobin
 * @Date :2021/4/8 22:41
 * @Desc :所有线程执行完
 * ====================================================
 */
public class CountDownLatchTest {
    public static void main(String[] args) {
        int count = 100;
        CountDownLatch doneSignal = new CountDownLatch(count);
        for (int i = 0; i < count; ++i) {
            new Thread(new Worker(doneSignal), "thread" + i).start();
        }

        try {
            doneSignal.await();
            System.err.println("所有线程执行完毕");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    static class Worker implements Runnable {
        private  CountDownLatch doneSignal;
        Worker(CountDownLatch doneSignal) {
            this.doneSignal = doneSignal;
        }
        public void run() {
            try {
                System.err.println("thread name:" + Thread.currentThread().getName());
                doneSignal.countDown();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}