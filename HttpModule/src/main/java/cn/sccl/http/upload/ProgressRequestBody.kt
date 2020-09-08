package cn.sccl.http.upload

import android.os.Handler
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.*
import java.io.IOException

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/8 10:42
 * @Desc :
 * ====================================================
 */
class ProgressRequestBody(
        val listener: OnUpLoadListener<*>?,
        val tag: String,
        val requestBody: RequestBody,
        val handler: Handler?)
    : RequestBody() {

    private var bufferedSink: BufferedSink? = null
    private var lastProgress = 0

    @Throws(IOException::class)
    override fun contentLength(): Long {
        return requestBody.contentLength()
    }

    override fun contentType(): MediaType? {
        return requestBody.contentType()
    }

    @Throws(IOException::class)
    override fun writeTo(sink: BufferedSink) {
        if (bufferedSink == null) {
            bufferedSink = sink(sink).buffer()
        }
        requestBody.writeTo(bufferedSink!!)
        bufferedSink!!.flush()
    }


    private fun sink(sink: Sink): Sink {
        return object : ForwardingSink(sink) {
            var bytesWriting = 0L
            var contentLength = 0L

            @Throws(IOException::class)
            override fun write(source: Buffer, byteCount: Long) {
                super.write(source, byteCount)
                if (0L == contentLength) contentLength = contentLength()
                bytesWriting += byteCount
                //调用接口，把上传文件的进度传过去
                val progress = (bytesWriting.toFloat() / contentLength * 100).toInt() // 计算百分比
                if (lastProgress != progress) {
                    lastProgress = progress
                    System.err.println("进度=$progress")
                    handler?.post {
                        listener?.onUploadProgress(
                                tag,
                                bytesWriting,
                                contentLength,
                                progress
                        )
                    }
                }
            }
        }
    }
}