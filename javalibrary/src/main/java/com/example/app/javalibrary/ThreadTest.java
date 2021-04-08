package com.example.app.javalibrary;

public class ThreadTest {
    public static void main(String[] args) {
        Thread thread1 = new Thread(new MyRunnable(null),"thread1");
        Thread thread2 = new Thread(new MyRunnable(thread1),"thread2");
        Thread thread3 = new Thread(new MyRunnable(thread2),"thread3");
        thread1.start();
        thread2.start();
        thread3.start();
    }
    static class MyRunnable implements Runnable {
        private Thread thread;
        public MyRunnable(Thread t) {
            this.thread = t;
        }
        public void run() {
            if (thread != null) {
                try {
                    thread.join();
                    System.out.println("thread name:" + Thread.currentThread().getName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("thread name:" + Thread.currentThread().getName());
            }
        }
    }
}