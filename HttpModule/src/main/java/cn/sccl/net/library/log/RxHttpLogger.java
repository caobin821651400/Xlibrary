package cn.sccl.net.library.log;

import android.util.Log;

import cn.sccl.net.library.BuildConfig;


/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/8/21 9:56
 * @Desc :日志打印格式化处理
 * ====================================================
 */
public class RxHttpLogger implements RxLogInterceptor.Logger {
    private StringBuffer mMessage = new StringBuffer();

    @Override
    public void log(String message) {
        // 请求或者响应开始
        if (message.startsWith("--> POST")) {
            mMessage.setLength(0);
            mMessage.append(" ");
            mMessage.append("\r\n");
        }
        if (message.startsWith("--> GET")) {
            mMessage.setLength(0);
            mMessage.append(" ");
            mMessage.append("\r\n");
        }
        // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
//        if ((message.startsWith("{") && message.endsWith("}"))
//                || (message.startsWith("[") && message.endsWith("]"))) {
//            message = JsonUtil.formatJson(JsonUtil.decodeUnicode(message));
//        }
//        message=JsonUtil.decodeUnicode(message);
        mMessage.append(message.concat("\n"));
        // 请求或者响应结束，打印整条日志
        if (message.startsWith("<-- END HTTP")) {
            if (BuildConfig.DEBUG)
                Log.d("RxHttp", mMessage.toString());
        }
    }
}
