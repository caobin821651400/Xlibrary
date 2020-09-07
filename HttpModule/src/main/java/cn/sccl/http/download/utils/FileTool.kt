package cn.sccl.http.download.utils

import cn.sccl.http.download.DownLadProgressListener.OnDownLoadListener
import cn.sccl.http.download.DownLoadPool
import cn.sccl.http.exception.NetException
import cn.sccl.http.exception.NetException.Companion.DOWN_LOAD_ERROR
import cn.sccl.http.exception.NetException.Companion.DOWN_LOAD_PATH_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.RandomAccessFile
import java.nio.channels.FileChannel
import java.text.DecimalFormat

val deFormat = DecimalFormat("###.0")

object FileTool {

    //定义GB的计算常量
    private const val GB = 1024 * 1024 * 1024

    //定义MB的计算常量
    private const val MB = 1024 * 1024

    //定义KB的计算常量
    private const val KB = 1024


    /**
     * 下载文件
     */
    suspend fun downToFile(
            tag: String,
            savePath: String,
            saveName: String,
            currentLength: Long,
            responseBody: ResponseBody,
            loadListener: OnDownLoadListener
    ) {
        val filePath = getFilePath(savePath, saveName)

        kotlin.runCatching {
            if (filePath == null) {
                withContext(Dispatchers.Main) {
                    loadListener.onError(tag, NetException(DOWN_LOAD_PATH_ERROR))
                }
                DownLoadPool.remove(tag)
                return
            }
            //保存到文件
            saveToFile(currentLength, responseBody, filePath, tag, loadListener)
        }.onFailure {
            withContext(Dispatchers.Main) {
                loadListener.onError(tag, NetException(DOWN_LOAD_ERROR))
            }
            DownLoadPool.remove(tag)
        }
    }

    /**
     * 保存文件
     */
    private suspend fun saveToFile(
            currentLength: Long,
            responseBody: ResponseBody,
            filePath: String,
            tag: String,
            loadListener: OnDownLoadListener
    ) {

        val fileLength = getFileLength(currentLength, responseBody)
        val inputStream = responseBody.byteStream()
        val accessFile = RandomAccessFile(File(filePath), "rwd")
        val channel = accessFile.channel
        val mapBuffer = channel.map(
                FileChannel.MapMode.READ_WRITE,
                currentLength,
                fileLength - currentLength
        )
        val buffer = ByteArray(1024 * 4)
        var len = 0
        var lastProgress = 0
        var currentSaveLength = currentLength //当前的长度

        while (inputStream.read(buffer).also { len = it } != -1) {
            mapBuffer.put(buffer, 0, len)
            currentSaveLength += len

            val progress = (currentSaveLength.toFloat() / fileLength * 100).toInt() // 计算百分比
            if (lastProgress != progress) {
                lastProgress = progress
                //记录已经下载的长度
                ShareDownLoadUtil.putLong(tag, currentSaveLength)

                withContext(Dispatchers.Main) {
                    loadListener.onProgress(tag, progress)
                    loadListener.onUpdate(
                            tag,
                            progress,
                            currentSaveLength,
                            fileLength,
                            currentSaveLength == fileLength
                    )
                }

                if (currentSaveLength == fileLength) {
                    withContext(Dispatchers.Main) {
                        loadListener.onSuccess(tag, filePath)
                    }
                    DownLoadPool.remove(tag)
                }
            }
        }

        runCatching {
            inputStream.close()
            accessFile.close()
            channel.close()
        }
    }


    /**
     * 数据总长度
     */
    private fun getFileLength(
            currentLength: Long,
            responseBody: ResponseBody
    ): Long {
        return if (currentLength == 0L) {
            responseBody.contentLength()
        } else {
            currentLength + responseBody.contentLength()
        }
    }

    /**
     * 获取下载地址
     */
    private fun getFilePath(savePath: String, saveName: String): String? {
        if (!createFile(savePath)) {
            return null
        }
        return "$savePath/$saveName"

    }


    /**
     * 创建文件夹
     */
    private fun createFile(downLoadPath: String): Boolean {
        val file = File(downLoadPath)
        if (!file.exists()) {
            return file.mkdirs()
        }
        return true
    }


    /**
     * byte 转kb
     */
    fun byte2kb(bytes: Long): String {
        return when {
            bytes / GB >= 1 -> {
                deFormat.format(bytes / GB) + "GB";
            }
            bytes / MB >= 1 -> {
                deFormat.format(bytes / MB) + "MB";
            }
            bytes / KB >= 1 -> {
                deFormat.format(bytes / KB) + "KB";
            }
            else -> {
                "${bytes}B";
            }
        }
    }

}