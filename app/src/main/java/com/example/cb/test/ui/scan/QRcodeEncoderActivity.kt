package com.example.cb.test.ui.scan

import android.graphics.Color
import android.os.Bundle
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity

/**
 * 生成二维码
 */
class QRcodeEncoderActivity : BaseActivity() {

    private var mColor: Int = Color.BLACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_decoder)
        initView()
    }

    private fun initView() {
//        qrcode_bt.setOnClickListener {
//            val txt: String = qrcode_ed.text.toString().trim()
//            if (TextUtils.isEmpty(txt)) {
//                toast("请输入内容")
//                return@setOnClickListener
//            }
//            var bitmap: Bitmap
//            bitmap = if (!qrcode_cb.isChecked) {
//                QRCodeEncoder.syncEncodeQRCode(txt, 500, mColor)
//            } else {
//                val logo: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.xloading_empty_view_cblibrary)
//                QRCodeEncoder.syncEncodeQRCode(txt, 500, mColor, logo)
//            }
//            qrcode_img.setImageBitmap(bitmap)
//        }
//
//        color_bt.setOnClickListener {
//            mColor = Color.BLUE
//        }
    }
}
