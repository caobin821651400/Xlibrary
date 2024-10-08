package cn.sccl.http.core


/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/19 12:52
 * @Desc :服务器返回数据的基类
 * 1.继承 BaseResponse
 * 2.errorCode, errorMsg,  data 要根据自己服务的返回的字段来定
 * 3.重写isSucces 方法，编写你的业务需求，根据自己的条件判断数据是否请求成功
 * 4.重写 getResponseCode、getResponseData、getResponseMsg方法，传入你的 code data msg
 * ====================================================
 */
data class ApiCodeResponse<T>(var code: Int, var msg: String, var data: T) : BaseResponse<T>() {


    override fun getResponseCode() = code

    override fun getResponseData() = data

    override fun getResponseMsg() = msg

    override fun isSuccess(): Boolean = code == 0

}