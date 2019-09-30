package com.example.cb.test.ui.scan

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.provider.MediaStore
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.utils.UriUtils
import kotlinx.android.synthetic.main.activity_qrcode_decoder.*
import me.devilsen.czxing.BarcodeReader
import me.devilsen.czxing.ScannerManager
import me.devilsen.czxing.view.ScanActivityDelegate

/**
 * 生成二维码
 */
class QRcodeEncoderActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_qrcode_decoder
    }


    override fun initEvent() {
    }

    private var mColor: Int = Color.BLACK
    val manager = ScannerManager(this)


    override fun initUI() {
        qrcode_bt.setOnClickListener {
            manager.setOnClickAlbumDelegate(object : ScanActivityDelegate.OnClickAlbumDelegate {
                override fun onClickAlbum(activity: Activity) {
                    val pickIntent = Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                    activity.startActivityForResult(pickIntent, 1065)
                }

                override fun onSelectData(requestCode: Int, data: Intent?) {
                    val path = UriUtils.INSTANCE.getImagePath(this@QRcodeEncoderActivity, data)
                    val bitmap = compressBitmap(path)
                    var result = BarcodeReader.getInstance().read(bitmap)
                    toast(result.text)
//                            val reader= BarcodeReader()
//                            reader.read(compressBitmap())
                }

            })
                    .setOnScanResultDelegate { result ->
                        val data = result
                    }
                    .start()
        }


    }

    /**
     * 压缩图片
     * @param path
     * @return
     */
    private fun compressBitmap(path: String): Bitmap {

        val newOpts = BitmapFactory.Options()
        // 开始读入图片，此时把options.inJustDecodeBounds 设回true了
        newOpts.inJustDecodeBounds = true//获取原始图片大小
        BitmapFactory.decodeFile(path, newOpts)// 此时返回bm为空
        val w = newOpts.outWidth
        val h = newOpts.outHeight
        val width = 800f
        val height = 480f
        // 缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        var be = 1// be=1表示不缩放
        if (w > h && w > width) {// 如果宽度大的话根据宽度固定大小缩放
            be = (newOpts.outWidth / width).toInt()
        } else if (w < h && h > height) {// 如果高度高的话根据宽度固定大小缩放
            be = (newOpts.outHeight / height).toInt()
        }
        if (be <= 0)
            be = 1
        newOpts.inSampleSize = be// 设置缩放比例
        // 重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了
        newOpts.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, newOpts)
    }
}
