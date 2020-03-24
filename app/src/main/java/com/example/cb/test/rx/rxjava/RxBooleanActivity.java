package com.example.cb.test.rx.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cb.test.R;

import cb.xlibrary.utils.XLogUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/29 14:56
 * @Desc :Rx条件操作符
 * ====================================================
 */
public class RxBooleanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_boolean);
    }

    @SuppressLint("CheckResult")
    public void start(View view) {
        //1. --> all判断条件是否都满足
        Observable.just(1, 2, 3, 4)
                .all(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 5;
                    }
                }).subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                XLogUtils.d("all-> " + aBoolean);
            }
        });


        //2. --> takeWhile 判断每一条是否满足;满足才发送
        Observable.just(5, 6, 7, 8)
                .takeWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 8;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.i("takeWhile-> " + integer);
            }
        });


        //3. --> skipWhile 判断每项是否满足条件；直到false时才发送事件，没有为false的则不发送事件
        Observable.just(9, 10, 11, 12)
                .skipWhile(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        return integer < 11;
//                        return integer<13;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.v("skipWhile-> " + integer);
            }
        });


        //4. --> takeUntil 执行到某个条件时，停止发送事件,没有则发送全部事件
        Observable.just(13, 14, 15, 16)
                .takeUntil(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
//                        return integer < 12;
                        return integer < 14;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.d("takeUntil-> " + integer);
            }
        });


        //5. --> skipUntil 等到skipUntil传入的Observable开始发送事件时，原始Observable才开始发送事件
//        Observable.interval(1, TimeUnit.SECONDS)
//                .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))
//                .skipUntil(Observable.timer(3, TimeUnit.SECONDS))
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        XLogUtils.i("skipUntil-> " + aLong);
//                    }
//                });


        //6. --> SequenceEqual 判断两个Observable发送的事件是否相同
//        Observable.sequenceEqual(Observable.just(1, 2, 3), Observable.just(1, 2, 3))//true
        Observable.sequenceEqual(Observable.just(1, 2, 5), Observable.just(1, 2, 3))//false
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        XLogUtils.v("sequenceEqual-> " + aBoolean);
                    }
                });


        //7. --> contains 判断发送数据是否包含指定数据;用法类似String.contains
        Observable.just(1, 2, 3, 4)
                .contains(4)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        XLogUtils.d("contains-> " + aBoolean);
                    }
                });


        //8. --> isEmpty 判断发送的数据是否为空
        Observable.just(5, 6, 7, 8)
                .isEmpty()
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        XLogUtils.i("isEmpty-> " + aBoolean);
                    }
                });


        //9. --> defaultIfEmpty 空的话默认发送一个事件
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onComplete();
            }
        }).defaultIfEmpty(1).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.v("defaultIfEmpty-> " + integer);
            }
        });
    }
}
