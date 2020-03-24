package com.example.cb.test.rx.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cb.test.R;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import cb.xlibrary.utils.XLogUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/18 15:47
 * @Desc :Rx的基本操作符
 * ====================================================
 */
public class RxBaseOperatorActivity extends AppCompatActivity {

    private int mI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_operator);
    }

    @SuppressLint("CheckResult")
    public void presss(View view) {
        //-->1. create()：创建一个被观察者对象
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.d("create-> " + integer);
            }
        });


        //-->2. 快速创建&发送事件
        //注意：最多只能发送10个
        Observable.just(1, 2, 3).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.i("just-> " + integer);
            }
        });


        //-->3. fromArray() 以数组形式创建被观察者
        //可以发送10个数据以上
        String[] array = {"A", "B", "C", "D"};
        Observable.fromArray(array).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                XLogUtils.v("fromArray-> " + s);
            }
        });


        //-->4. fromIterable() 跟上面类似，直接可以传入List集合
        List<String> list = Arrays.asList(array);
        Observable.fromIterable(list).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                XLogUtils.d("fromIterable-> " + s);
            }
        });


        //-->5. defer()延迟事件。只有观察者订阅时才去创建被观察者，且创建最新的
        mI = 10;//第一次赋值
        Observable<Integer> deferObservable = Observable.defer(new Callable<ObservableSource<? extends Integer>>() {
            @Override
            public ObservableSource<? extends Integer> call() throws Exception {
                return Observable.just(mI);
            }
        });
        mI = 15;//第二次赋值
        deferObservable.subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer s) throws Exception {
                //这里只会打印15，印证了创建最新的
                XLogUtils.i("defer-> " + s);
            }
        });


        //-->6. timer()延迟发送一个事件
        Observable.timer(5, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        XLogUtils.v("timer-> " + aLong);
                    }
                });
        //$$$$$$$$$$$$$$注意
        //timer()运行在新的线程上
        //也可以自定义线程调度器:Observable.timer(5, TimeUnit.SECONDS, AndroidSchedulers.mainThread())


        //-->7. interval() 定时间隔时间发送事件
        //参数1 = 第一次延迟
        //参数2 = 周期延迟
        //参数3 = 时间单位
//        Observable.interval(3, 1, TimeUnit.SECONDS)
//                .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))//取消订阅
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        XLogUtils.d("interval-> " + aLong);
//                    }
//                });
        //$$$$$$$$$$$$注意：改操作符执行后不会自动停止,不及时释放会导致Activity不能及时回收导致内存泄漏
        //这里我使用的RxLifeCycle指定在DESTROY时取消订阅


        //-->8. intervalRange() 每隔一段时间发送指定数量的事件
        //参数1 = 事件起点
        //参数1 = 事件总数
        //参数1 = 起始延迟时间
        //参数1 = 周期间隔时间
        //参数1 = 单位
//        Observable.intervalRange(5, 10, 5, 2, TimeUnit.SECONDS)
//        .compose(this.<Long>bindUntilEvent(ActivityEvent.DESTROY))//取消订阅
//                .subscribe(new Consumer<Long>() {
//                    @Override
//                    public void accept(Long aLong) throws Exception {
//                        XLogUtils.d("intervalRange-> " + aLong);
//                    }
//                });
        //$$$$$$$$$$$$注意：改操作符执行后不会自动停止,不及时释放会导致Activity不能及时回收导致内存泄漏
        //这里我使用的RxLifeCycle指定在DESTROY时取消订阅


        //-->9. range() 发送指定区间的事件
        Observable.range(1, 3)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.d("range-> " + integer);
                    }
                });


        //-->10.rangeLong() 与上面类似 只不过类型是Long
        Observable.rangeLong(5, 3)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        XLogUtils.i("rangeLong-> " + aLong);
                    }
                });
    }
}
