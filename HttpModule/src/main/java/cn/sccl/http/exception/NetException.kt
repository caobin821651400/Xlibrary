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
        const val DOWN_URL_ERROR = 4001//下载地址有误
        const val DOWN_LOAD_ERROR = 4002//下载失败
        const val UP_LOAD_ERROR = 4003//上传失败
        const val DOWN_LOAD_PATH_ERROR = 4004//保存路径错误
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
            DOWN_URL_ERROR -> "下载地址有误"
            DOWN_LOAD_ERROR -> "下载失败请重试"
            UP_LOAD_ERROR -> "上传失败请重试"
            DOWN_LOAD_PATH_ERROR -> "文件保存路径为空"
            else -> "请求失败，请稍后再试"
        }
        this.errCode = errCode
        this.errLog = errLog ?: this.errorMsg
    }
}