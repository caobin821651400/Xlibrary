package cn.sccl.http.exception

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/17 22:22
 * @Desc :自定义错误信息异常
 * ====================================================
 */
class NetException : Exception {

    companion object {
        const val UNKNOWN = 80//请求失败，请稍后再试
        const val PARSE_ERROR = 81//解析错误，请稍后再试
        const val NETWORK_ERROR = 82//网络连接错误，请稍后重试
        const val SSL_ERROR = 83//证书出错，请稍后再试
        const val TIMEOUT_ERROR = 84//网络连接超时，请稍后重试
    }

    var errorMsg: String //错误消息
    var errCode: Int = 0 //错误码
    var errLog: String? //错误日志

    constructor(errCode: Int, error: String? = null, errLog: String? = "") : super(error) {
        this.errorMsg = error ?: when (errCode) {
            UNKNOWN -> "请求失败，请稍后再试"
            PARSE_ERROR -> "解析错误，请稍后再试"
            NETWORK_ERROR -> "网络连接错误，请稍后重试"
            SSL_ERROR -> "证书出错，请稍后再试"
            TIMEOUT_ERROR -> "网络连接超时，请稍后重试"
            else -> "请求失败，请稍后再试"
        }
        this.errCode = errCode
        this.errLog = errLog ?: this.errorMsg
    }
}