package com.example.cb.test.ui.scan;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cb.qrcode.library.util.QRCodeDecoder;
import com.example.cb.test.base.BaseActivity;
import com.example.cb.test.R;

/**
 * 图库识别二维码
 */
public class QRcodeEncoderActivity extends BaseActivity {

    private final int INTENT_GET_PHOTO = 100;
    private TextView qrcodeTv;
    private ImageView qrcodeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_encoder);
        initView();
    }

    private void initView() {
        qrcodeTv = (TextView) findViewById(R.id.qrcode_result);
        qrcodeImg = (ImageView) findViewById(R.id.qrcode_img);
    }

    /**
     * 相册选择图片识别
     *
     * @param view
     */
    public void doTakePhoto(View view) {
        //穿个二维码图片的路径
        String result = QRCodeDecoder.syncDecodeQRCode("/storage/emulated/0/DCIM/Camera/IMG_20180912_105539.jpg");
        qrcodeTv.setText("识别内容为：\n" + result);
    }
}
