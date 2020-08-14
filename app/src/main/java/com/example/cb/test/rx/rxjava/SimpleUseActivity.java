package com.example.cb.test.rx.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cb.test.R;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import cn.sccl.xlibrary.utils.XLogUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/17 22:47
 * @Desc :RxJava基本使用
 * ====================================================
 */
public class SimpleUseActivity extends AppCompatActivity {

    private EditText name;
    private EditText pwd;
    private Button login;
    private Button cutdownbtn;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_use);
        name = findViewById(R.id.name);
        pwd = findViewById(R.id.pwd);
        login = findViewById(R.id.login);
        cutdownbtn = findViewById(R.id.cutdownbtn);

        cutdownbtn.setOnClickListener(v -> cutDownBtn());

        btnEnable();
    }

    /**
     * @param view
     */
    public void presss(View view) {
        demo();
    }

    //1. 定义
    //RxJava是一个基于事件流、实现异步操作的库

    //2. 特点
    //A:逻辑简单  B:实现优雅  C:使用简单  D:链式调用代码简洁

    //3. 原理
    //①:被观察者(Observable)-->产生事件
    //②:观察者(Observer)-->接收事件，并作出响应
    //③:订阅(Subscribe)-->连接 观察者和被观察者
    //④:事件(Event)-->观察者和被观察者之间的载体
    //⑤:即RxJava原理可总结为：被观察者 （Observable） 通过 订阅（Subscribe） 按顺序发送事件 给观察者 （Observer），
    //:观察者（Observer） 按顺序接收事件 & 作出对应的响应动作

    //4. 例子
    //顾客(被观察者)   服务员(订阅)  厨房(观察者)  菜(事件)
    //顾客坐下餐桌-->(产生事件)找到服务员点菜(菜品1、菜品2)-->服务员与后厨建立连接-->后厨收到服务员传递过来的菜单-->后厨收到菜单开始做菜

    //5. 事件类型
    //a: Next      普通事件
    //b: Complete  事件完成    当 被观察者 发送Complete后 观察者 不在接收事件，但是被观察者可以继续发送事件。
    //c: Error     发生异常    当 被观察者 发送Error后 观察者 不在接收事件，但是被观察者可以继续发送事件。
    //注意点: 当一个正常的事件顺序中Complete和Error互斥并且唯一。


    /*******==========================================================================**********/
    @SuppressLint("CheckResult")
    public void demo() {
        //---->步骤1. 创建被观察者Observable
        //****方式1.
        Observable<Integer> observable = Observable.create(new ObservableOnSubscribe<Integer>() {
            //create是RxJava最基本的创建事件序列的方法
            //当Observable被订阅时，OnSubscribe的Call()方法会自动被调用，即事件会依次触发
            //即观察者依次调用事件的复写方法从而响应事件
            //从而实现被观察者调用观察者的回调方法 & 由被观察者想观察者事件传递即观察者模式

            //从写Subscribe定义需要发送的事件
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                //通过ObservableEmitter类产生事件并通知观察者
                emitter.onNext(1);
                emitter.onNext(2);
//                emitter.onError(new RuntimeException("异常"));
                emitter.onNext(3);
                emitter.onComplete();
            }
        });

        //****方式2. 创建方式类似上面
        Observable<Integer> observable2 = Observable.just(1, 2, 3);


        //---->步骤2. 创建观察者

        //****方式1. 采用Observer接口
        Observer<Integer> observer = new Observer<Integer>() {

            //观察者接收事件前，最先调用onSubscribe
            @Override
            public void onSubscribe(Disposable d) {
                XLogUtils.d("开始采用subscribe连接");
            }

            //当被观察者产生next事件&观察者接收到事件时回调此方法
            @Override
            public void onNext(Integer integer) {
                XLogUtils.d("我收到事件--> " + integer.toString());
            }

            //当被观察者产生Error事件&观察者接收到事件时回调此方法
            @Override
            public void onError(Throwable e) {
                XLogUtils.d("收到Error事件-> " + e.getLocalizedMessage());
            }

            //当被观察者产生Complete事件&观察者接收到事件时回调此方法
            @Override
            public void onComplete() {
                XLogUtils.d("收到Complete事件");
            }
        };


        //$$$$$$$$$$$两种方式的区别
        //相同点：两者使用方式完全一样，实际上在RxJava的subscribe过程中，Observer会被转换成Subscriber再使用
        //不同点：Subscriber抽象类对Observer进行扩展，新增了两个方法
        //      1.onStart(): 在未响应事件之前调用
        //      2.unSubscribe():用于取消订阅。改方法调用后，观察者不在接收&响应事件。
        //调用此方法前应先使用isUnSubscribed()判断被观察者是否持有观察者的引用，防止内存泄漏。


        //---->步骤3. 通过订阅连接观察者和被观察者
        observable2.subscribe(observer);


        //$$$$$$$$$$$优雅链式调用
        Observable.just(11, 22, 33).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                XLogUtils.d("开始采用subscribe连接");
            }

            @Override
            public void onNext(Integer integer) {
                XLogUtils.d("我收到事件--> " + integer.toString());
            }

            @Override
            public void onError(Throwable e) {
                XLogUtils.d("收到Error事件-> " + e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {
                XLogUtils.d("收到Complete事件");
            }
        });
    }

    /**
     * 防止多次点击
     *
     * @param view
     */
    @SuppressLint("CheckResult")
    public void nomore(View view) {
        RxView.clicks(findViewById(R.id.nomoreview))
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(unit -> XLogUtils.d("按钮被点击了"));
    }

    /**
     * 例如登录按钮符合输入条件才允许点击
     */
    @SuppressLint("CheckResult")
    private void btnEnable() {
        Observable<CharSequence> nameObservable = RxTextView.textChanges(name);
        Observable<CharSequence> pwdObservable = RxTextView.textChanges(pwd);
        Observable.combineLatest(nameObservable, pwdObservable,
                (name, pwd) -> name.toString().length() == 11 && pwd.toString().length() == 6
        ).subscribe(login::setEnabled);
    }

    private Disposable mCutDownDisposable;

    /**
     * 验证码
     */
    @SuppressLint("CheckResult")
    private void cutDownBtn() {
        Observable.interval(0, 1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Long>() {
                    @Override
                    protected void onSuccess(Long aLong) {
                        cutdownbtn.setEnabled(aLong == 10);
                        cutdownbtn.setText(10 - aLong + "S后重试");
                        if (aLong == 10) {
                            mCutDownDisposable.dispose();
                            cutdownbtn.setText("发送验证码");
                        }
                        XLogUtils.i(aLong + "");
                    }

                    @Override
                    protected void onError(String msg) {

                    }

                    @Override
                    public void doOnSubscribe(Disposable d) {
                        mCutDownDisposable = d;

                    }
                });
    }

}
