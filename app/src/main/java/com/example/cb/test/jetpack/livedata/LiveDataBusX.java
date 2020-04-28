package com.example.cb.test.jetpack.livedata;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/4/22 14:00
 * @Desc :事件总线
 * ====================================================
 */
public class LiveDataBusX {

    private Map<String, BusMutableLiveData<Object>> liveDataMap;

    private LiveDataBusX() {
        liveDataMap = new HashMap<>();
    }

    private static LiveDataBusX liveDataBus = new LiveDataBusX();

    public static LiveDataBusX getInstance() {
        return liveDataBus;
    }


    /***
     *
     * @param key
     * @param aClass
     * @param <T>
     * @return
     */
    public synchronized <T> BusMutableLiveData<T> with(String key, Class<T> aClass) {
        if (!liveDataMap.containsKey(key)) {
            liveDataMap.put(key, new BusMutableLiveData<>());
        }
        return (BusMutableLiveData<T>) liveDataMap.get(key);
    }

    //new LiveData()->绑定observer->setValue()/postValue()  正常流程
    //new LiveData()->setValue()/postValue()->绑定observer(界面显示)->setValue()/postValue()
    //                   hook忽略这一步

    //mLastVersion->ObserverWrapper observer->ObserverWrapper initiator-> private SafeIterableMap<Observer<? super T>, ObserverWrapper> mObservers

    /****
     * hook
     * @param <T>
     */
    public static class BusMutableLiveData<T> extends MutableLiveData {

        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer observer) {
            super.observe(owner, observer);
            hook(observer);
        }

        private void hook(Observer<? super T> observer) {
            try {
                //***********1.得到mLastVersion
                Class<LiveData> liveDataClass = LiveData.class;
                Field mObserversField = liveDataClass.getDeclaredField("mObservers");
                //private需要设置权限
                mObserversField.setAccessible(true);
                //获取到成员变量的对象
                Object mObserversObject = mObserversField.get(this);
                //拿到class对象
                Class<?> mObservesClass = mObserversObject.getClass();

                //执行mObservers的get方法
                Method get = mObservesClass.getDeclaredMethod("get", Object.class);
                get.setAccessible(true);
                Object invokeEntry = get.invoke(mObserversObject, observer);//ObserverWrapper

                Object observerWrapper = null;
                if (invokeEntry instanceof Map.Entry) {
                    observerWrapper = ((Map.Entry) invokeEntry).getValue();
                }
                if (observerWrapper == null) {
                    throw new NullPointerException("ObserverWrapper is null");
                }

                //使用了泛型，找到他的父类
                Class<?> superclass = observerWrapper.getClass().getSuperclass();
                if (superclass == null) {
                    throw new NullPointerException("ObserverWrapper Superclass is null");
                }
                Field mLastVersion = superclass.getDeclaredField("mLastVersion");
                mLastVersion.setAccessible(true);

                //***********2.得到mVersion
                Field mVersion = liveDataClass.getDeclaredField("mVersion");
                mVersion.setAccessible(true);

                //***********3.mLastVersion填到mVersion
                Object mVersionValue = mVersion.get(this);
                mLastVersion.set(observerWrapper, mVersionValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
