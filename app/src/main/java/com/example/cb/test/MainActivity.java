package com.example.cb.test;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cb.xlibrary.bean.BottomPopupBean;
import com.cb.xlibrary.dialog.XActionSheetDialog;
import com.cb.xlibrary.dialog.XCheckVersionAlert;
import com.cb.xlibrary.dialog.XDownLoadDialog;
import com.cb.xlibrary.dialog.XInputDialog;
import com.cb.xlibrary.permission.XPermission;
import com.cb.xlibrary.utils.XActivityStack;
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

        numberFormat = NumberFormat.getPercentInstance();
        numberFormat.setMinimumFractionDigits(0);

        XDownLoadDialog = new XDownLoadDialog(this);

        XPermission.requestPermissions(MainActivity.this, 102, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, new XPermission.OnPermissionListener() {
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
                //  showAlert();
                showBottomDialog();
            }
        });
    }

    private void showBottomDialog() {
        List<BottomPopupBean> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new BottomPopupBean("我是" + i, i));
        }
        XActionSheetDialog dialog = new XActionSheetDialog(this);
        dialog.setMenusList(list);
        dialog.setPopTitle("选择");
        dialog.setOnItemClickListener(new XActionSheetDialog.XMenuListener() {
            @Override
            public void onItemSelected(int position, BottomPopupBean item) {

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
