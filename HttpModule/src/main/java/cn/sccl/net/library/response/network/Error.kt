package cn.sccl.net.library.response.network

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/17 22:15
 * @Desc :错误状态码 枚举类
 * ====================================================
 */
enum class Error(private val code: Int, private val errorMsg: String) {

    /**
     * 未知错误
     */
    UNKNOWN(80, "请求失败，请稍后再试"),

    /**
     * 解析错误
     */
    PARSE_ERROR(81, "解析错误，请稍后再试"),

    /**
     * 网络错误
     */
    NETWORK_ERROR(82, "网络连接错误，请稍后重试"),

    /**
     * 证书出错
     */
    SSL_ERROR(83, "证书出错，请稍后再试"),

    /**
     * 连接超时
     */
    TIMEOUT_ERROR(84, "网络连接超时，请稍后重试");

    /**
     * 获取状态码
     */
    fun getCode(): Int {
        return code
    }

    /**
     * 获取错误信息
     */
    fun getErrorMsg(): String {
        return errorMsg
    }
}