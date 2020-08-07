package com.example.cb.test.rx.rxjava;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cb.test.R;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import cb.xlibrary.utils.XLogUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/9/23 9:38
 * @Desc :组合操作符
 * ====================================================
 */
public class RxMergeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_merge);
    }

    @SuppressLint("CheckResult")
    public void start(View view) {

        //1. -->concat()||concatArray() 组合多个被观察者合并后一起发送事件（按照先后顺序）
        Observable.concat(Observable.just(1, 2), Observable.just(3, 4), Observable.just(5, 6), Observable.just(7, 8))
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.d("concat-> " + integer);
                    }
                });
        //$$$$$$$$$$$$$$$$$$concat()组合数量<=4 concatArray()则是>4的情况


        //2. -->merge()||mergeArray() 与concat类似，组合事件发布
        Observable.merge(
                Observable.intervalRange(0, 3, 0, 0, TimeUnit.SECONDS),
                Observable.intervalRange(2, 3, 0, 0, TimeUnit.SECONDS))
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        XLogUtils.d("merge-> " + aLong);
                    }
                });
        //$$$$$$$$$$$$$  merge()<=4   mergeArray()>4
        //$$$$$$$$$$$$$  merge()与concat区别：merge()是把一个observable事件执行完才会去执行下一个；而merge()是同时执行所有的Observable里的事件


        //3. --> concatDelayError()||mergeDelayError() 与concat()||merge()类似，只是将onError事件放到所有事件发送完毕后在执行
        Observable.concatArrayDelayError(Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                emitter.onNext(1);
                emitter.onNext(2);
                emitter.onError(new RuntimeException("发生错误"));
                emitter.onComplete();
            }
        }), Observable.just(3, 4)).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {
                XLogUtils.d("concatArrayDelayError-> " + integer);
            }

            @Override
            public void onError(Throwable e) {
                XLogUtils.d("concatArrayDelayError-> 发生错误");
            }

            @Override
            public void onComplete() {
                XLogUtils.d("concatArrayDelayError-> complete");
            }
        });


        //4. --> Zip() 合并多个被观察者发送的事件，生成一个新的序列，最终在发送
        Observable.zip(
                Observable.create(new ObservableOnSubscribe<Integer>() {
                    @Override
                    public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
                        emitter.onNext(1);
                        emitter.onNext(2);
                        emitter.onNext(3);
                        emitter.onComplete();
                    }
                }),
                Observable.create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                        emitter.onNext("A");
                        emitter.onNext("B");
                        emitter.onNext("C");
                        XLogUtils.i("zip-> 发送事件D");
                        emitter.onNext("D");
                        emitter.onComplete();
                    }
                }), new BiFunction<Integer, String, String>() {
                    @Override
                    public String apply(Integer integer, String s) throws Exception {
                        return integer + s;
                    }
                }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                XLogUtils.i("zip-> " + s);
            }
        });
        //$$$$$$$$$$$注意点：
        //                 A:组合的顺序是严格按照原先的顺序例如:事件1->1,2,3  事件2->A,B,C 合并后则是 1B,2B,3C
        //                 B:最终合并的事件==所有被观察者中事件最少的
        //                 C:第二个被观察者的事件D还是会发送,但是观察者不接收。


        //5. --> combineLatest() 将第一个Observable发送的最后一个事件与另一个Observable的每个事件合并
        Observable.combineLatest(Observable.just(1, 2), Observable.just("A", "B", "C"), new BiFunction<Integer, String, String>() {
            @Override
            public String apply(Integer integer, String s) throws Exception {
                return integer + s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                XLogUtils.v("combineLatest-> " + s);
            }
        });


        //6. --> combineLatestDelayError与concatDelayError()||mergeDelayError()类似不在多说


        //7. --> reduce() 把被观察者事件聚合成一个发送
        Observable.just(1, 2, 3, 4).reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer * integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                XLogUtils.d("reduce-> " + integer);
            }
        });
        //$$$$$$$$$$$上面相当于 1*2*3*4;将所有事件聚合成一个事件


        //8. --> collect() 将所有被观察者事件收集到一个数据结构里
        Observable.just(1, 2, 3, 4).collect(new Callable<ArrayList<Integer>>() {
            @Override
            public ArrayList<Integer> call() throws Exception {
                return new ArrayList<>();
            }
        }, new BiConsumer<ArrayList<Integer>, Integer>() {
            @Override
            public void accept(ArrayList<Integer> list, Integer integer) throws Exception {
                list.add(integer);
            }
        }).subscribe(new Consumer<ArrayList<Integer>>() {
            @Override
            public void accept(ArrayList<Integer> integers) throws Exception {
                for (Integer i : integers) {
                    XLogUtils.i("collect-> " + i);
                }
            }
        });


        //9. --> startWith()&&startWithArray() 在被观察者发送事件之前追加一个新的被观察者
        Observable.just(55, 66, 77).startWith(44).startWithArray(11, 22, 33)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.v("startWith-> " + integer);
                    }
                });


        //10. -->count() 统计被观察者发送事件数量
        Observable.just(2, 3, 4, 5).count().subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                XLogUtils.d("count-> " + aLong);
            }
        });

    }
}
