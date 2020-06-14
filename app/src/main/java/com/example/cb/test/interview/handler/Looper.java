package com.example.cb.test.interview.handler;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/11 22:36
 * @Desc :
 * ====================================================
 */
public class Looper {


    public MessageQueue mQueue;
    public static ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();

    private Looper() {
        mQueue = new MessageQueue();
    }

    public static void perpare() {
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one Looper may be created per thread");
        }
        sThreadLocal.set(new Looper());
    }

    /****
     * 获取独一无二的Looper,也就是与线程绑定的Looper
     * @return
     */
    public static Looper myLooper() {
        return sThreadLocal.get();
    }


    /****
     *可以理解为调度器，让MessageQueue run起来
     */
    public static void loop() {
        final Looper me = myLooper();
        final MessageQueue queue = me.mQueue;
        for (;;) {
            Message msg = queue.next();
            if (msg != null) {
                msg.target.dispatchMessage(msg);
            }
        }
    }
}
