package com.example.cb.test.rx;

import android.os.Bundle;

import cb.xlibrary.utils.XLogUtils;
import com.example.cb.test.base.BaseActivity;
import com.example.cb.test.R;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;


/**
 * RxJava操作符
 */
public class RxJavaTestActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java_test);
        initView();
    }

    private void initView() {
//        /**
//         * concatArray组合多个事件（>4）
//         * 注意：按顺序执行
//         */
//        Observable.concatArray(Observable.just(1, 2, 3),
//                Observable.just(11, 22, 33),
//                Observable.just(111, 222, 333),
//                Observable.just(1111, 2222, 3333),
//                Observable.just(11111, 22222, 33333))
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//
//                        XLogUtils.d("收到事件 " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


//        /**
//         * concat组合多个事件（<=4）
//         * 注意：按顺序执行
//         */
//        Observable.concat(Observable.just(1, 2, 3)
//                , Observable.just(4, 5, 6)
//                , Observable.just(7, 8, 9))
//                .subscribe(new Observer<Integer>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Integer integer) {
//                        XLogUtils.d("收到事件 " + integer);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


//        /**
//         *merge组合多个事件 并行执行
//         */
//        Observable.merge(Observable.interval(0, 1, TimeUnit.SECONDS)
//                , Observable.interval(0, 1, TimeUnit.SECONDS)
//                , Observable.interval(0, 1, TimeUnit.SECONDS))
//                .subscribe(new Observer<Long>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        XLogUtils.d("事件 " + aLong);
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


//        /**
//         * 聚合事件发送
//         */
//        Observable.just(1, 2, 3, 4)
//                /**
//                 * reduce 的apply是具体聚合操作
//                 */
//                .reduce(new BiFunction<Integer, Integer, Integer>() {
//                    @Override
//                    public Integer apply(Integer integer, Integer integer2) throws Exception {
//                        return integer + integer2;
//                    }
//                }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                XLogUtils.d("结果 " + integer);
//            }
//        });


//        /**
//         *map  函数类型转换
//         */
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//            }
//        }).map(new Function<Integer, String>() {
//            /**
//             * 具体的转换类型自己在这里写
//             * @param integer
//             * @return
//             * @throws Exception
//             */
//            @Override
//            public String apply(Integer integer) throws Exception {
//                return "第几 " + integer;
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                XLogUtils.d("结果 " + s);
//            }
//        });


//        /**
//         * flatMap 作用：将被观察者发送的事件序列进行 拆分  & 单独转换，再合并成一个新的事件序列，最后再进行发送
//         * 原理:
//         * 为事件序列中每个事件都创建一个 Observable 对象；
//         * 将对每个 原始事件 转换后的 新事件 都放入到对应 Observable对象；
//         * 将新建的每个Observable 都合并到一个 新建的、总的Observable 对象；
//         * 新建的、总的Observable 对象 将 新合并的事件序列 发送给观察者（Observer）
//         *
//         * ConcatMap是有序的
//         */
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//            }
//        }).flatMap(new Function<Integer, ObservableSource<String>>() {
//            @Override
//            public ObservableSource<String> apply(Integer integer) throws Exception {
//                List<String> list = new ArrayList<>();
//                for (int i = 0; i < 3; i++) {
//                    list.add("我是第 " + integer + " 的 " + i);
//                }
//                return Observable.fromIterable(list);
//            }
//        }).subscribe(new Consumer<String>() {
//            @Override
//            public void accept(String s) throws Exception {
//                XLogUtils.d("结果 " + s);
//            }
//        });

//
//        /**
//         * filter 过滤 在test方法中自己判断事件是否执行逻辑
//         */
//        Observable.create(new ObservableOnSubscribe<Integer>() {
//            @Override
//            public void subscribe(ObservableEmitter<Integer> emitter) throws Exception {
//                emitter.onNext(1);
//                emitter.onNext(2);
//                emitter.onNext(3);
//                emitter.onNext(4);
//                emitter.onNext(5);
//                emitter.onNext(6);
//            }
//        }).filter(new Predicate<Integer>() {
//            @Override
//            public boolean test(Integer integer) throws Exception {
//                return integer >=2;
//            }
//        }).subscribe(new Consumer<Integer>() {
//            @Override
//            public void accept(Integer integer) throws Exception {
//                XLogUtils.d("结果 "+integer);
//            }
//        });


//        /**
//         * 指定类型 ofType
//         */
//        Observable.just(1, 2, "a", 3, "e")
//                .ofType(String.class)
//                .subscribe(new Consumer<String>() {
//                    @Override
//                    public void accept(String i) throws Exception {
//                        XLogUtils.d("结果 " + i);
//                    }
//                });


//        /**
//         * skip跳过前N个
//         * skipLast跳过后N个
//         */
//        Observable.just(1, 2, 3, 4, 5)
////                .skip(3)//跳过前N个
//                .skipLast(2)//跳过后N个
//                .subscribe(new Consumer<Integer>() {
//                    @Override
//                    public void accept(Integer integer) throws Exception {
//                        XLogUtils.d("结果 " + integer);
//                    }
//                });


        /**
         * distinct 过滤重复的数据
         * distinctUntilChanged 过滤连续重复的
         */
        Observable.just(1, 2, 1, 2, 1, 1)
                .distinct()
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        XLogUtils.d("结果 " + integer);
                    }
                });
    }
}
