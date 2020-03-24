package com.example.cb.test.rx.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cb.test.R;

import java.util.concurrent.TimeUnit;

import cb.xlibrary.utils.XLogUtils;
import io.reactivex.Notification;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BooleanSupplier;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/23 12:19
 * @Desc :功能操作符
 * ====================================================
 */
public class RxFunctionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_function);
    }

    @SuppressLint("CheckResult")
    public void start(View view) {
        //1. --> delay() 延迟操作
        Observable.just(1, 2, 3).delay(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.d("delay-> " + integer);
                    }
                });
        //$$$$$$$$$$多个重载方法：
        //                  A:delay(long delay, TimeUnit.SECONDS)
        //                  B:delay(long delay, TimeUnit.SECONDS,Scheduler scheduler)//参数3是线程调度器
        //                  C:delay(long delay, TimeUnit.SECONDS,boolean delayError)//参数3 = 错误延迟参数
        //                  D:delay(long delay,TimeUnit unit,mScheduler scheduler,boolean delayError)

        //2. -->生命周期
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new Throwable("错误"));
            }
        }).doOnEach(new Consumer<Notification<Integer>>() {
            @Override
            public void accept(Notification<Integer> integerNotification) throws Exception {
                //当Observable每发送一此事件就会调一次
                XLogUtils.i("doOnEach-> 调用了");
            }
        }).doOnNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.i("doOnNext-> 调用了");
            }
        }).doAfterNext(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.i("doAfterNext-> 调用了");
            }
        }).doOnComplete(new Action() {
            @Override
            public void run() throws Exception {
                XLogUtils.i("doOnComplete-> 调用了");
            }
        }).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                XLogUtils.i("doOnError-> 调用了");
            }
        }).doOnSubscribe(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                XLogUtils.i("doOnSubscribe-> 调用了");
            }
        }).doAfterTerminate(new Action() {
            @Override
            public void run() throws Exception {
                XLogUtils.i("doAfterTerminate-> 调用了");
            }
        }).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                XLogUtils.i("doFinally-> 调用了");
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                XLogUtils.i("onNext-> " + integer);
            }

            @Override
            public void onError(Throwable e) {
                XLogUtils.i("onError->");
            }

            @Override
            public void onComplete() {
                XLogUtils.i("onComplete->");
            }
        });


        //3. -->onErrorReturn()  类似于java的try/catch捕捉异常
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onNext(3);
                emitter.onError(new Throwable("错误"));
                emitter.onNext(4);
            }
        }).onErrorReturn(new Function<Throwable, Integer>() {
            @Override
            public Integer apply(Throwable throwable) throws Exception {
                return -1;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.v("onErrorReturn-> " + integer);
            }
        });


        //4. -->onErrorResumeNext() 遇到错误返回一个新的Observable
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(4);
                emitter.onNext(5);
                emitter.onError(new Throwable("错误"));
                emitter.onNext(6);
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> apply(Throwable throwable) throws Exception {
                return Observable.just(1, 2, 3);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.d("onErrorResumeNext-> " + integer);
            }
        });


        //5. --> onExceptionResumeNext 与 onErrorResumeNext 类似
        //$$$$$$$$$$$$$$区别：
        //           A:onExceptionResumeNext拦截的错误是onExceptionResumeNext
        //           B:onErrorResumeNext拦截的则是Throwable


        //6. --> retryUntil() 发生错误时重试  Throwable和Exception都可以拦截
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new RuntimeException("错误"));
                emitter.onNext(3);
            }
        }).retryUntil(new BooleanSupplier() {
            @Override
            public boolean getAsBoolean() throws Exception {
                //返回 true 则不重新发送数据事件
                //必须要实现onError自己处理异常
                return true;
            }
        }).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                XLogUtils.i("retryUntil-> " + integer);
            }

            @Override
            public void onError(Throwable e) {
                XLogUtils.i("retryUntil错误信息-> " + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        });


        //7. --> repeat()无条件的重复发送事件
        Observable.just(1, 2)
                .repeat(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.v("repeat-> " + integer);
                    }
                });


        //8. --> repeatWhen()有条件的重复发送事件
        Observable.just(1, 2, 4)
                .repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Object> objectObservable) throws Exception {

                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(Object o) throws Exception {
                                //情况1-->返回onComplete||onError,则不重新订阅&发送原来的事件
                                return Observable.empty();//相当于empty()相当于onComplete事件

                                //情况2--> 返回一个新的Observable,只要不是 onComplete||onError
//                               return Observable.just(1);
                            }
                        });
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.d("repeatWhen-> " + integer);
            }
        });
    }
}
