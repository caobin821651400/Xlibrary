package com.example.cb.test;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cb.xlibrary.dialog.XDownLoadDialog;
import com.cb.xlibrary.dialog.XInputDialog;
import com.cb.xlibrary.dialog.XUserHeadDialog;
import com.cb.xlibrary.imagepicker.ImagePicker;
import com.cb.xlibrary.imagepicker.bean.ImageItem;
import com.cb.xlibrary.permission.XPermission;
import com.cb.xlibrary.utils.XActivityStack;
import com.cb.xlibrary.utils.XIdCardUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;


public class MainActivity extends BaseActivity {

    private Button btnDownLoad;
    private TextView downloadSize;
    private TextView tvProgress;
    private TextView netSpeed;
    private ProgressBar pbProgress;
    private XDownLoadDialog XDownLoadDialog;
    private XInputDialog dialog2;
    private ImageView mImageView;

    //
    private String apkUrl = "https://codeload.github.com/jeasonlzy/okhttp-OkGo/zip/master";
    //    private String apkUrl = "http://60.28.125.1/f4.market.mi-img.com/download/AppStore/06954949fcd48414c16f726620cf2d52200550f56/so.ofo.labofo.apk";
    private NumberFormat numberFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XActivityStack.getInstance().addActivity(this);
        initView();
    }

    private void initView() {
        btnDownLoad = findViewById(R.id.btn_down_load);
        downloadSize = findViewById(R.id.downloadSize);
        tvProgress = findViewById(R.id.tvProgress);
        netSpeed = findViewById(R.id.netSpeed);
        pbProgress = findViewById(R.id.pbProgress);
        mImageView = findViewById(R.id.imageview);

        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(0);

        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setNestedScrollingEnabled(false);

        XDownLoadDialog = new XDownLoadDialog(this);

        XPermission.requestPermissions(MainActivity.this, 102, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, new XPermission.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                System.err.println("权限申请到了");

            }

            @Override
            public void onPermissionDenied() {

            }
        });
        btnDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, ScrollingActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
                System.err.println("哈哈 "+ XIdCardUtils.isValidatedAllIdcard("652123199507052"));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XUserHeadDialog.CHANGE_HEAD_REQUEST_CODE) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                if (data != null) {
                    showImg(((ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS)).get(0).path, mImageView);
                }
            }
        }
    }


    private void downLoad() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaa/";
        OkGo.<File>get(apkUrl)//
                .tag(this)//
                .execute(new FileCallback(path, "哈哈.zip") {

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        XDownLoadDialog.show();
                    }

                    @Override
                    public void onSuccess(Response<File> response) {
                        XDownLoadDialog.dismiss();
                    }

                    @Override
                    public void onError(Response<File> response) {
                        XDownLoadDialog.dismiss();
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        XDownLoadDialog.getProgressTextView().setText(numberFormat.format(progress.fraction));
                        XDownLoadDialog.getProgressBar().setMax(100);
                        XDownLoadDialog.getProgressBar().setProgress((int) (progress.fraction * 100));
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog2 != null) dialog2.cancel();
        //Activity销毁时，取消网络请求
        XActivityStack.getInstance().finishActivity();
        OkGo.getInstance().cancelTag(this);
    }
}
