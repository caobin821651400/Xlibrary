package com.example.cb.test;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cb.xlibrary.permission.XPermission;
import com.cb.xlibrary.utils.XActivityStack;
import com.example.cb.test.rx.MovieHttpRequest;
import com.example.cb.test.rx.NewsResp;
import com.example.cb.test.rx.XHttpCallback;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends BaseActivity {

    private Button btnDownLoad;
    private TextView downloadSize;
    private TextView tvProgress;
    private TextView netSpeed;
    private ProgressBar pbProgress;
    private ImageView mImageView;
    private static final String WEATHRE_API_URL = "http://php.weather.sina.com.cn/xml.php?city=%s&password=DJOYnieT8234jlsK&day=0";
    private String result;

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
                downLoad1();
            }
        });
    }

    private void downLoad1() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "top");
        map.put("key", "f323c09a114635eb935ed8dd19f7284e");
        MovieHttpRequest.sendNewsRequest(map, new XHttpCallback<NewsResp>() {
            @Override
            public void onSuccess(NewsResp newsResp) {
                System.err.println("哈哈 " + newsResp.getResult().getData().get(0).getTitle());
            }

            @Override
            public void onError(String error) {
                System.err.println("哈哈 error " + error);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (MovieHttpRequest.getCompositeSubscription() != null)
            MovieHttpRequest.getCompositeSubscription().unsubscribe();
        //Activity销毁时，取消网络请求
        XActivityStack.getInstance().finishActivity();
    }
}
