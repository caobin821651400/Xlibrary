package com.example.cb.test.ui.scan

import android.content.Context
import android.os.Bundle
import android.os.Vibrator
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.ScaleAnimation
import cb.xlibrary.utils.XDensityUtils
import cb.xlibrary.utils.XLogUtils
import com.cb.qrcode.library.view.ZXingScannerView
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.google.zxing.Result
import kotlinx.android.synthetic.main.activity_scan_code.*
import kotlinx.android.synthetic.main.view_view_finder.*

class ScanCodeActivity : BaseActivity(), ZXingScannerView.ResultHandler {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_code)
        initView()
    }

    override fun onResume() {
        super.onResume()
        scan_view.setResultHandler(this)
        scan_view.startCamera()
    }

    private fun initView() {
        val screenWidth = XDensityUtils.getScreenWidth(this)
        val marginLeft = (screenWidth - XDensityUtils.dp2px(this, 220F)) / 2
        val viewTop = XDensityUtils.dp2px(this, 180F)
        val viewRight = screenWidth - marginLeft
        val viewBottom = XDensityUtils.dp2px(this, 380F)

        scan_view.setScanRect(marginLeft, viewTop, viewRight, viewBottom)


        var animation = ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f)
        animation.repeatCount = -1
        animation.repeatMode = Animation.RESTART
        animation.interpolator = LinearInterpolator()
        animation.duration = 2000
        capture_scan_line.animation = animation
    }

    /**
     * 扫描结果回调
     */
    override fun handleResult(result: Result) {
        XLogUtils.d("结果 ->"+result.text)
        toast(result.text)
        //震动  as在java中是类型强转
        val vibrator: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(longArrayOf(30, 400), -1)

        resumeCameraPreview()
    }

    /**
     * 扫描成功后再次启动扫描
     */
    private fun resumeCameraPreview() {
        scan_view.postDelayed({
            scan_view.resumeCameraPreview(this)
        }, 4000)
    }

    override fun onStop() {
        super.onStop()
        scan_view.stopCameraPreview()
        scan_view.stopCamera()
    }
}