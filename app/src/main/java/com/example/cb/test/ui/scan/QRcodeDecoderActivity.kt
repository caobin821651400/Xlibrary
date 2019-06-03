package com.example.cb.test.ui.scan

import android.os.Bundle
import com.cb.qrcode.library.util.QRCodeDecoder
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_qrcode_encoder.*

/**
 * 二维码解码
 */
class QRcodeDecoderActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode_encoder)
        initView()
    }

    private fun initView() {
        chose_pic.setOnClickListener {
            //""" 三个这个里面代表的字符串不需要转义
            val result = QRCodeDecoder.syncDecodeQRCode("""/storage/emulated/0/11.png""")
            qrcode_result.text = result
        }
    }
}