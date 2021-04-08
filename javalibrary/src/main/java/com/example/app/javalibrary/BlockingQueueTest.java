package com.example.app.javalibrary;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BlockingQueueTest {
    public static void main(String[] args) {
        BlockingQueue<Thread> blockingQueue = new LinkedBlockingQueue<Thread>();
        Thread thread1 = new Thread(new Work(),"thread1");
        Thread thread2 = new Thread(new Work(),"thread2");
        Thread thread3 = new Thread(new Work(),"thread3");

        blockingQueue.add(thread1);
        blockingQueue.add(thread2);
        blockingQueue.add(thread3);
        for (int i = 0; i < 3; i++) {
            Thread thread = null;
            try {
                thread = blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            thread.start();
        }
    }
    static class Work implements Runnable {
        public void run() {
            System.out.println("thread name:" + Thread.currentThread().getName());
        }
    }
}