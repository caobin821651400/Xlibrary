package com.example.cb.test.rx.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cb.test.R;

import cn.sccl.xlibrary.utils.XLogUtils;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/29 16:48
 * @Desc :过滤操作符
 * ====================================================
 */
public class RxFilterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_filter);
    }

    @SuppressLint("CheckResult")
    public void start(View view) {
        //1.--> 过滤特定的事件
        Observable.just(1, 2, 3, 4)
                .filter(new Predicate<Integer>() {
                    @Override
                    public boolean test(Integer integer) throws Exception {
                        //返回true发送事件，false不发送
                        return integer == 3;
                    }
                }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.d("filter-> " + integer);
            }
        });


        //2.--> ofType 过滤特定类型的事件
        Observable.just(1, "2", 3, "4")
                .ofType(Integer.class)//过滤除了Integer以外的事件
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.i("ofType-> " + integer);
                    }
                });


        //3.--> skip/skipLast 跳过事件
        Observable.just(1, 2, 3, 4, 5)
                .skip(2)//跳过2之前的事件
                .skipLast(2)//跳过2之后的事件
                //两个同时调用，两个都有效
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.v("skip-> " + integer);
                    }
                });


        //4.--> distinct/distinctChanged 过滤重复的事件/连续重复的事件
        Observable.just(1, 1, 2, 3, 1, 4, 5)
//                .distinct()
                .distinctUntilChanged()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.d("distinct-> " + integer);
                    }
                });


        //5.--> take：指定观察者最多能接收的事件数量
        Observable.just(1, 2, 3, 4, 5)
                .take(2)//前两个
                .takeLast(2)//后两个
                //两个同时调用支取第一次的
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.i("take-> " + integer);
                    }
                });


        //6.--> firstElement/lastElement /
        Observable.just(1, 2, 3, 4)
                .lastElement()//最后一个事件
//                .firstElement()//仅选第一个事件
                //两个操作符互斥
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.v("firstElement-> " + integer);
                    }
                });


        //7.--> elementAt：指定接收某个事件 类似List.get()
        Observable.just(1, 2, 3)
                .elementAt(4)//从0开始,越界不发送事件
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.d("element-> " + integer);
                    }
                });


        //8.--> elementAtError 类似element当越界后抛出异常
        Observable.just(1, 2, 3)
//                .elementAtOrError(4)//程序奔溃
                .elementAtOrError(2)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.i("elementAtOrError-> " + integer);
                    }
                });
    }
}
