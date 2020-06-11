package com.example.cb.test.interview.handler;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/11 22:35
 * @Desc : 生产者消费者设计模式
 * ====================================================
 */
public class MessageQueue {

    BlockingQueue<Message> queue;

    public MessageQueue() {
        //创建10个大小的消息仓库,改仓库是阻塞似的
        queue = new ArrayBlockingQueue<>(10);
    }

    /****
     * 往消息队列添加消息
     * @param message
     */
    public void enqueueMessage(Message message) {
        try {
            queue.put(message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    /****
     * 重消息队列里取消息
     * @return
     */
    public Message next() {
        Message message = null;
        try {
            message = queue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return message;
    }
}
