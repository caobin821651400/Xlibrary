package com.example.cb.test.interview.handler;

import java.util.UUID;

public class HandlerTest {


    public static void main(String[] args) {
        Looper.perpare();
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                System.out.println("Thread Id: "
                        + Thread.currentThread().getName()
                        + " received msg: " + msg.toString());
            }
        };


        new Thread(new Runnable() {
            int count = 10;

            @Override
            public void run() {
                while (count > 0) {
                    Message message = new Message(UUID.randomUUID().toString());
                    System.out.println(Thread.currentThread().toString()
                            + " send message: "
                            + message.toString()
                            + " count: " + count);
                    handler.sendMessage(message);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count--;
                }
            }
        }).start();

        Looper.loop();
    }


}
