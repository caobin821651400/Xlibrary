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
public class LiveDataBus {

    private Map<String, MutableLiveData<Object>> liveDataMap;

    private LiveDataBus() {
        liveDataMap = new HashMap<>();
    }

    private static LiveDataBus liveDataBus = new LiveDataBus();

    public static LiveDataBus getInstance() {
        return liveDataBus;
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
}
