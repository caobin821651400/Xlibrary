package com.example.cb.test;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cb.xlibrary.bean.BottomPopupBean;
import com.cb.xlibrary.dialog.XActionSheetDialog;
import com.cb.xlibrary.dialog.XCheckVersionAlert;
import com.cb.xlibrary.dialog.XDownLoadDialog;
import com.cb.xlibrary.dialog.XInputDialog;
import com.cb.xlibrary.dialog.XUserHeadDialog;
import com.cb.xlibrary.imagepicker.ImagePicker;
import com.cb.xlibrary.imagepicker.bean.ImageItem;
import com.cb.xlibrary.permission.XPermission;
import com.cb.xlibrary.utils.XActivityStack;
import com.example.cb.test.utils.GlideImageLoader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


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
//                showAlert();
//                showBottomDialog();s
                showBottomDialog();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XUserHeadDialog.CHANGE_HEAD_REQUEST_CODE) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                if (data != null) {
                    Glide.with(MainActivity.this).load(((ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS)).get(0).path)
                            .placeholder(R.drawable.ic_default_image)
                            .into(mImageView);
                }
            }
        }
    }

    private void showBottomDialog() {
        List<BottomPopupBean> list = new ArrayList<>();
        list.add(new BottomPopupBean("男", 1));
        list.add(new BottomPopupBean("女", 2));
        XActionSheetDialog dialog = new XActionSheetDialog(this);
        dialog.setMenusList(list);
        dialog.setPopTitle("选择性别");
        dialog.setOnItemClickListener(new XActionSheetDialog.XMenuListener() {
            @Override
            public void onItemSelected(int position, BottomPopupBean item) {
                System.err.println("哈哈 " + item.getTitle() + "   " + item.getValue());
            }
        });
        dialog.show();
    }

    private XInputDialog.SureBtnClickListener sureBtnClickListener = new XInputDialog.SureBtnClickListener() {
        @Override
        public void onClick(String content) {
            System.err.println("哈哈 " + content);
        }
    };

    private void showAlert() {
        String content = "1.修复登录奔溃问题.\n2.修复查找好友时找不到BUG.\n3.修复登录奔溃问题\n4.我不想改BUG";
        String updateMsg = "新版本号: " + "1.1.5" + "\n" + "更新内容: \n" + content;
        XCheckVersionAlert versionAlert = new XCheckVersionAlert(this, new XCheckVersionAlert.BtnClickListener() {
            @Override
            public void leftClick() {
                XActivityStack.getInstance().appExit();
            }

            @Override
            public void rightClick() {
                downLoad();
            }
        });
        versionAlert.showUpdateAlert(updateMsg, "发现新版本", false);
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
