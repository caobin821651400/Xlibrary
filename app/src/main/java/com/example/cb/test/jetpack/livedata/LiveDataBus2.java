package com.example.cb.test.jetpack.livedata;

import androidx.lifecycle.MutableLiveData;

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
public class LiveDataBus2 {

    private Map<String, MutableLiveData<Object>> liveDataMap;

    private LiveDataBus2() {
        liveDataMap = new HashMap<>();
    }

    private static LiveDataBus2 liveDataBus2 = new LiveDataBus2();

    public static LiveDataBus2 getInstance() {
        return liveDataBus2;
    }


    /***
     *
     * @param key
     * @param aClass
     * @param <T>
     * @return
     */
    public synchronized <T> MutableLiveData<T> with(String key, Class<T> aClass) {
        if (!liveDataMap.containsKey(key)) {
            liveDataMap.put(key, new MutableLiveData<>());
        }
        return (MutableLiveData<T>) liveDataMap.get(key);
    }

    //new LiveData()->绑定observer->setValue()/postValue()  正常流程
    //new LiveData()->setValue()/postValue()->绑定observer(界面显示)->setValue()/postValue()

    //mLastVersion->ObserverWrapper observer->ObserverWrapper initiator-> private SafeIterableMap<Observer<? super T>, ObserverWrapper> mObservers
}
