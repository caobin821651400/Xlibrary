package com.example.cb.test.rx.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cb.test.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.TimeUnit;

import cb.xlibrary.utils.XLogUtils;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/25 9:57
 * @Desc :背压
 * ====================================================
 */
public class RxBackPressureActivity extends AppCompatActivity {
    Subscription ss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_back_pressure);
    }

    /**
     * 正确使用
     *
     * @param view
     */
    @SuppressLint("CheckResult")
    public void correct(View view) {
        //1. -->背压简单实用
//        Flowable.create(new FlowableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(11);
//                emitter.onNext(22);
//                emitter.onNext(33);
//            }
//        }, BackpressureStrategy.ERROR).subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//                s.request(3);
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//
//                XLogUtils.d("背压-> " + integer);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });


        //2. -->背压简单实用
        Flowable.create(new FlowableOnSubscribe<Integer>() {
            @Override
            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 4; i++) {
                    emitter.onNext(i);
                }
            }
        }, BackpressureStrategy.ERROR).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Integer>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        //与Disposable类似
                        //不同点则是多了个request(int count)方法;
                        //代表依次接收多少个事件，多余的事件放到缓冲区，
                        ss = s;
                        ss.request(2);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        try {
                            XLogUtils.d("接收事件-> " + integer);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {
                        XLogUtils.d("错误 -" + t);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //观察者去缓存区拿下2条事件
                ss.request(2);
            }
        }, 2000);
        //$$$$$$$注意点：
        //          A:s.request(3);但是被观察者依然可以发送事件，只不过事件是放在缓存区中。
        //          B:缓存区大小为128；超出128则会报错


        //3. -->同步调用,被观察者调用requested()去知道观察者一次接收几个事件
//        Flowable.create(new FlowableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(FlowableEmitter<Integer> emitter) throws Exception {
//                //这里的数量就是onSubscribe中返回的数量
//                //==0时代表观察者不接受事件，如果在发送则会抛异常
//                //特性：实时更新
//                //     可以叠加:就是在onSubscribe调两次request(2) request(2) 等同于调了request(4)
//                long count = emitter.requested();
//                for (int i = 0; i < count; i++) {
//                    emitter.onNext(i);
//                }
//            }
//        }, BackpressureStrategy.ERROR).subscribe(new Subscriber<Integer>() {
//            @Override
//            public void onSubscribe(Subscription s) {
//                s.request(2);
//            }
//
//            @Override
//            public void onNext(Integer integer) {
//                XLogUtils.i("用多少发多少-> " + integer);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
    }

    /**
     * 错误的示范
     *
     * @param view
     */
    @SuppressLint("CheckResult")
    public void error(View view) {
        asynchronousFun(null);
    }

    /**
     * 同步调用
     *
     * @param view
     */
    @SuppressLint("CheckResult")
    public void synchronizeFun(View view) {
        // 观察者消费事件速度慢，因为是同步的，所有只有当onNext(1)执行后，一直阻塞到观察者处理完被观察者才会执行onNext(2)
        //被观察者和观察者在同一个线程中
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 3; i++) {
                    XLogUtils.d("发送事件 " + (i + 1));
                    emitter.onNext(i + 1);
                }
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                try {
                    Thread.sleep(1000);
                    XLogUtils.d("同步-> " + integer);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 异步调用
     *
     * @param view
     */
    @SuppressLint("CheckResult")
    private void asynchronousFun(View view) {
        //被观察者和观察者不在同一个线程中
        //被观察者会不断发送事件不管观察者是否已经消费完,这种情况就容易阻塞被观察者线程，容易导致OOM
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                for (int i = 0; i < 3; i++) {
                    Thread.sleep(100);
                    XLogUtils.v("发送事件" + i);
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io())//被观察者线程
                .observeOn(AndroidSchedulers.mainThread())//观察者线程
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer aLong) throws Exception {
                        try {
                            Thread.sleep(2000);
                            XLogUtils.i("异步-> " + aLong);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        //日志如下：注意观察时间差
        //2019-09-25 10:33:46.413 V/RxJava: 发送事件0
        //2019-09-25 10:33:46.515 V/RxJava: 发送事件1
        //2019-09-25 10:33:46.616 V/RxJava: 发送事件2
        //2019-09-25 10:33:48.419 I/RxJava: 异步-> 0
        //2019-09-25 10:33:50.421 I/RxJava: 异步-> 1
        //2019-09-25 10:33:52.426 I/RxJava: 异步-> 2

    }


    //$$$$$$$$$$$$$$-->背压模式类型
    //                            A:BackpressureStrategy.ERROR：超出128就会抛异常
    //                            B:BackpressureStrategy.BUFFER:设置缓存区无限大
    //                            C:BackpressureStrategy.MISSING：友好提示缓存区满了
    //                            D:BackpressureStrategy.DROP：超出128的事件丢弃
    //                            E:BackpressureStrategy.LATEST：只保存最新的事件，超出128的丢弃


    @SuppressLint("CheckResult")
    public void other(View view) {
        Flowable.interval(1, TimeUnit.MILLISECONDS)
                .onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        try {
                            Thread.sleep(500);
                            XLogUtils.v("其他-> " + aLong);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
