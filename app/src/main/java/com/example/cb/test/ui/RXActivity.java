package com.example.cb.test.ui;

import android.os.Bundle;

import com.cb.xlibrary.utils.log.XLog;
import com.example.cb.test.BaseActivity;
import com.example.cb.test.R;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RXActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
//        initSubscribe();
        initAll();
    }

    /**
     * 链式调用
     */
    private void initAll() {
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onComplete();
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                XLog.d("onSubscribe执行了");
            }

            @Override
            public void onNext(Integer value) {
                XLog.d("onNext执行了--> " + value);
            }

            @Override
            public void onError(Throwable e) {
                XLog.d("onError执行了");

            }

            @Override
            public void onComplete() {
                XLog.d("onComplete执行了");

            }
        });
    }

    /**
     * 订阅
     */
    private void initSubscribe() {
        initObservable().subscribe(initObserver());
        //initObservable().subscribe(subscriber);
    }

    /**
     * 观察者
     */
    private Observer<Integer> initObserver() {
        //方式1 实现Observer接口
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                XLog.d("onSubscribe执行了");
            }

            @Override
            public void onNext(Integer value) {
                XLog.d("onNext执行了--> " + value);

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                XLog.d("onError执行了");

            }

            @Override
            public void onComplete() {
                XLog.d("onComplete执行了");

            }
        };


        //方式2 Subscriber抽象类
        Subscriber<Integer> subscriber = new Subscriber<Integer>() {


            @Override
            public void onSubscribe(Subscription s) {

            }

            @Override
            public void onNext(Integer integer) {

            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        };
        //两者使用方法一致 实际上在rxjava的subscribe的过程中Observer总是被转换成subscriber来使用
        //不同点 subscriber抽象类对Obscriber进行了扩展，添加了onStart()和unsubscribe()方法,
        //unsubscribe()用于取消订阅事件，取消后观察者不在接收事件
        //调用该方法前，先使用 isUnsubscribed() 判断状态，确定被观察者Observable是否还持有观察者Subscriber的引用，如果引用不能及时释放，就会出现内存泄露
        return observer;
    }

    /**
     * 被观察着
     * onNext();     普通事件
     * onComplete(); 结束事件
     * onError();    失败事件
     * onComplete和onError互斥。
     */
    private Observable<Integer> initObservable() {
//        //1
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            //当observable被订阅时，OnSubscribe的cell方法被调用 ，事件会按照发送顺序依次触发
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(1);
                e.onNext(2);
                e.onNext(3);
                e.onError(new RuntimeException("发生错了"));
            }
        });

        return observable;

         //2 just依次发送 意思和上面方法一致
//        return Observable.just(1, 2, 3);

        //3 传入数组
//        Integer[] integer = {1, 2, 3};
//        return  Observable.fromArray(integer);
    }


}
