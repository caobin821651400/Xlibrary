package com.cb.xlibrary.version;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/15
 * desc   :下载成功回调
 */
public interface DownLoadListener {
    void downComplete(String path, String apkName);
}
