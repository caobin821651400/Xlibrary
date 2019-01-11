package com.example.cb.test.ui.scan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.cb.qrcode.library.view.ZXingScannerView;

import cb.xlibrary.utils.XDensityUtils;
import cb.xlibrary.utils.XLogUtils;
import com.example.cb.test.base.BaseActivity;
import com.example.cb.test.R;
import com.google.zxing.Result;

public class ScanCodeActivity extends BaseActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code);
        initView();
    }

    private void initView() {
        mScannerView = findViewById(R.id.scan_view);

        //布局里固定DP，计算扫描框的区域,
        //也可以通过view.getTop getLeft..来获取,注意需要在view.post中获取
        int screenWidth = XDensityUtils.getScreenWidth(this);
        //220是布局里写死的 具体查看@layout/view_view_finder
        int marginLeft = (screenWidth - XDensityUtils.dp2px(this, 200)) / 2;
        int viewTop = XDensityUtils.dp2px(this, 180);
        int viewRight = screenWidth - marginLeft;
        int viewBottom = XDensityUtils.dp2px(this, 380);
        mScannerView.setScanRect(marginLeft, viewTop, viewRight, viewBottom);

        initScanAnimation();
    }

    @Override
    public void handleResult(Result result) {
        XLogUtils.d("扫描结果 " + result.getText());
        toast(result.getText() + "");

        showVibrator();

        //重新启动扫描
        resumeCameraPreview();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCameraPreview();
        mScannerView.stopCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    ScaleAnimation animation;

    /**
     * 扫描上下动画
     */
    private void initScanAnimation() {
        ImageView mQrLineView = findViewById(R.id.capture_scan_line);
        animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f);
        animation.setRepeatCount(-1);
        animation.setRepeatMode(Animation.RESTART);
        animation.setInterpolator(new LinearInterpolator());
        animation.setDuration(2000);
        mQrLineView.startAnimation(animation);
    }

    /**
     * 重新启动扫描
     */
    public void resumeCameraPreview() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScanCodeActivity.this);
            }
        }, 4000);
    }

    /**
     * 震动效果
     */
    private void showVibrator() {
        Vibrator vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);
        long[] pattern = {30, 400};
        vibrator.vibrate(pattern, -1);
    }
}
