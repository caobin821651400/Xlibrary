package com.example.cb.test.rx.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cb.test.R;
import cn.sccl.xlibrary.utils.XLogUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/23 9:32
 * @Desc :变化操作符
 * ====================================================
 */
public class RxChangeOperatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_change_operator);
    }

    @SuppressLint("CheckResult")
    public void presss(View view) {

        //-->1. map() 发送的事件通过指定函数，从而变换成另一个事件
        Observable.just(1, 2, 3)
                .map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return "我是变换之后的 " + integer.toString();
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        XLogUtils.d(s);
                    }
                });


        //-->2. FlatMap() 将被观察者发送的事件进行 拆分&单独转换 在合并成新的事件
        //这里说我的理解：就是两个接口，第二个接口需要用到第一个接口的返回数据，
        // 把第一个接口的返回数据当成第二个接口的参数；就可以用到flatMap组合操作符
        Observable.create(
                new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                    }
                })
                .flatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            list.add("我是事件 " + integer + "拆分后的子事件" + i);
                            // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                            // 最终合并，再发送给被观察者
                        }
                        return Observable.fromIterable(list);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        XLogUtils.i("flatMap-> " + s);
                    }
                });


        //-->3.concatMap() 与flatMap类似； 区别是flatMap是无序的concatMap是有序的
        Observable.create(
                new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                    }
                })
                .concatMap(new Function<Integer, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(Integer integer) throws Exception {
                        List<String> list = new ArrayList<>();
                        for (int i = 0; i < 2; i++) {
                            list.add("我是事件 " + integer + "拆分后的子事件" + i);
                            // 通过flatMap中将被观察者生产的事件序列先进行拆分，再将每个事件转换为一个新的发送三个String事件
                            // 最终合并，再发送给被观察者
                        }
                        return Observable.fromIterable(list);
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        XLogUtils.v("concatMap-> " + s);
                    }
                });


        //-->4. Buffer() 从被观察者发送事件中取一部分放到缓冲区，最终在发送
        Observable.just(1, 2, 3, 4, 5)
                .buffer(4, 1)
                .subscribe(new Consumer<List<Integer>>() {
                    @Override
                    public void accept(List<Integer> stringList) throws Exception {
                        XLogUtils.d("缓冲区数量-> " + stringList.size());
                        for (Integer value : stringList) {
                            XLogUtils.d("buffer-> " + value);
                        }
                    }
                });
        //$$$$$$$$$$$$上面例子相当于： 第一次1234 第二次2345  第三次345 第四次 45 第五次 5
    }
}
