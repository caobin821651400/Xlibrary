package com.example.app.javalibrary;

import java.util.concurrent.CountDownLatch;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2021/4/8 22:41
 * @Desc :所有线程依次执行
 * ====================================================
 */
public class CountDownLatchTest2 {
    public static void main(String[] args) {
        CountDownLatch latch1 = new CountDownLatch(0); //计数器为0
        CountDownLatch latch2 = new CountDownLatch(1); //计数器为1
        CountDownLatch latch3 = new CountDownLatch(1); //计数器为1
        CountDownLatch latch4 = new CountDownLatch(1); //计数器为1

        Thread thread1 = new Thread(new Work(latch1, latch2), "Thread1");
        Thread thread2 = new Thread(new Work(latch2, latch3), "Thread2");
        Thread thread3 = new Thread(new Work(latch3, latch4), "Thread3");
        Thread thread4 = new Thread(new Work(latch4, latch4), "Thread4");

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }
    static class Work implements Runnable {
        CountDownLatch latch1;
        CountDownLatch latch2;
        Work(CountDownLatch c1, CountDownLatch c2) {
            super();
            this.latch1 = c1;
            this.latch2 = c2;
        }
        public void run() {
            try {
                latch1.await();//前一线程为0才可以执行
                System.out.println("thread name:" + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //注意之前代码如果抛异常countDown执行不了 就永远少1 最好在try/catch的finally里
                latch2.countDown();//本线程计数器减少
            }
        }
    }
}