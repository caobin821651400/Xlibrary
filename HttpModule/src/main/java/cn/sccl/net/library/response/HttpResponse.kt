package cn.sccl.net.library.response

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/17 9:43
 * @Desc :处理请求结果
 * ====================================================
 */
sealed class HttpResponse<out T : Any> {

    data class Success<out T : Any>(val data: T) : HttpResponse<T>()
    data class Error(val code: Int, val msg: String) : HttpResponse<Nothing>()


    /**
     * inline多用于有lambda表达式的方法中，不建议在普通方法中使用
     * 在编译时期，把调用这个函数的地方用这个函数的方法体进行替换。
     */
    inline fun result(success: (T) -> Unit, error: (code: Int, msg: String) -> Unit) {

        when (this) {
            is Success -> {
                success(data)
            }
            is Error -> {
                error(code, msg)
            }
        }
    }
}