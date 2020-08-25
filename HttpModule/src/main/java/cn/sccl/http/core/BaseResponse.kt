package cn.sccl.http.core

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/17 22:46
 * @Desc :服务器返回数据的基类
 * 1.如果需要框架帮你处理服务器状态码请继承它！！
 * 2.必须实现抽象方法，根据自己的业务判断返回请求结果是否成功
 * ====================================================
 */
abstract class BaseResponse<T> {

    /**
     * 需重写改方法，比如后台code的1000=成功，return code=1000
     */
    abstract fun isSuccess(): Boolean

    /**
     * 获取后台的状态码
     */
    abstract fun getResponseCode(): Int

    /**
     * 获取后台的msg
     */
    abstract fun getResponseMsg(): String

    /**
     * 请求成功后真正关心的数据
     */
    abstract fun getResponseData(): T

}