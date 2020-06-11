package com.example.cb.test.interview.handler;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/11 22:35
 * @Desc :
 * ====================================================
 */
public class Handler {

    private MessageQueue mQueue;
    private Looper mLooper;


    public Handler() {
        mLooper = Looper.myLooper();
        mQueue = mLooper.mQueue;
    }

    /****
     * 发送消息
     */
    public void sendMessage(Message message) {
        enqueueMessage(message);
    }

    /****
     * 发送消息
     */
    public void enqueueMessage(Message message) {
        message.target = this;
        mQueue.enqueueMessage(message);
    }

    /****
     * 分发消息
     * @param msg
     */
    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }


    /****
     * 处理消息
     * @param msg
     */
    public void handleMessage(Message msg) {
    }
}
