package com.example.cb.test;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cb.xlibrary.permission.XPermission;
import com.cb.xlibrary.utils.XActivityStack;
import com.cb.xlibrary.utils.XAppUtils;
import com.cb.xlibrary.utils.log.XLog;
import com.cb.xlibrary.version.CheckVersionAlert;
import com.cb.xlibrary.version.DownLoadListener;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;

import java.io.File;
import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity implements DownLoadListener {

    private Button btnDownLoad;
    private TextView downloadSize;
    private TextView tvProgress;
    private TextView netSpeed;
    private ProgressBar pbProgress;

    //
//    private String apkUrl = "https://codeload.github.com/jeasonlzy/okhttp-OkGo/zip/master";
    private String apkUrl = "http://60.28.125.1/f4.market.mi-img.com/download/AppStore/06954949fcd48414c16f726620cf2d52200550f56/so.ofo.labofo.apk";
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
        numberFormat.setMinimumFractionDigits(2);

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
                downLoad();
            }
        });
    }

    private void downLoad() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/aaa/";
        String content = "1.修复登录奔溃问题.\n2.修复查找好友时找不到BUG.\n3.修复登录奔溃问题\n4.我不想改BUG";
        String updateMsg = "新版本号: " + "1.1.5" + "\n" + "更新内容: \n" + content;
        CheckVersionAlert alert = new CheckVersionAlert(this, this);
        alert.showUpdateAlert(updateMsg, "检查到有新版本", "测试.apk", path, apkUrl, true);
    }

    @Override
    public void downComplete(String path, String apkName) {
        XAppUtils.installApk("", new File(path + apkName));
    }


    /**
     * Android M 全局权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        XPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void refreshUi(Progress progress) {
        String currentSize = Formatter.formatFileSize(this, progress.currentSize);
        String totalSize = Formatter.formatFileSize(this, progress.totalSize);
        downloadSize.setText(currentSize + "/" + totalSize);
        String speed = Formatter.formatFileSize(this, progress.speed);
        netSpeed.setText(String.format("%s/s", speed));
        tvProgress.setText(numberFormat.format(progress.fraction));
        pbProgress.setMax(10000);
        pbProgress.setProgress((int) (progress.fraction * 10000));
        switch (progress.status) {
            case Progress.NONE:
                btnDownLoad.setText("下载");
                break;
            case Progress.LOADING:
                btnDownLoad.setText("暂停");
                break;
            case Progress.PAUSE:
                btnDownLoad.setText("继续");
                break;
            case Progress.WAITING:
                btnDownLoad.setText("等待");
                break;
            case Progress.ERROR:
                btnDownLoad.setText("出错");
                break;
            case Progress.FINISH:
                XLog.d("下载完成");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        XActivityStack.getInstance().finishActivity();
        OkGo.getInstance().cancelTag(this);
    }
}
