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
public class DownLoadManagerOld {
    private static DownLoadManagerOld downLoadManager = null;
    private HashMap<String, Call> downCalls;//用来存放各个下载的请求
    private OkHttpClient okHttpClient;


    /**
     * 单例
     *
     * @return
     */
    public static DownLoadManagerOld getInstance() {
        if (downLoadManager == null) {
            synchronized (DownLoadManagerOld.class) {
                if (downLoadManager == null) {
                    downLoadManager = new DownLoadManagerOld();
                }
            }
        }
        return downLoadManager;
    }

    public DownLoadManagerOld() {
        downCalls = new HashMap<>();
        okHttpClient = new OkHttpClient.Builder().build();
    }
}
