package com.example.cb.test.download;

import java.util.HashMap;

import okhttp3.Call;
import okhttp3.OkHttpClient;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2018/01/19
 * desc   :
 */
public class DownLoadManager {
    private static DownLoadManager downLoadManager = null;
    private HashMap<String, Call> downCalls;//用来存放各个下载的请求
    private OkHttpClient okHttpClient;


    /**
     * 单例
     *
     * @return
     */
    public static DownLoadManager getInstance() {
        if (downLoadManager == null) {
            synchronized (DownLoadManager.class) {
                if (downLoadManager == null) {
                    downLoadManager = new DownLoadManager();
                }
            }
        }
        return downLoadManager;
    }

    public DownLoadManager() {
        downCalls = new HashMap<>();
        okHttpClient = new OkHttpClient.Builder().build();
    }
}
