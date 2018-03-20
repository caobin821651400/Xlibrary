package com.example.cb.test;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cb.xlibrary.dialog.XUserHeadDialog;
import com.cb.xlibrary.imagepicker.ImagePicker;
import com.cb.xlibrary.imagepicker.bean.ImageItem;
import com.cb.xlibrary.picker.date.DatePickerDialogFragment;
import com.cb.xlibrary.utils.XActivityStack;
import com.cb.xlibrary.utils.XPermission;
import com.example.cb.test.bean.UploadBean;
import com.example.cb.test.event.EventBusActivity;
import com.example.cb.test.event.MessageEvent;
import com.example.cb.test.rx.MovieHttpRequest;
import com.example.cb.test.rx.NewsResp;
import com.example.cb.test.rx.UserInfoResp;
import com.example.cb.test.rx.XHttpCallback;
import com.example.cb.test.rx.body.ProgressInfo;
import com.example.cb.test.rx.body.ProgressListener;
import com.example.cb.test.ui.RXActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;


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

            }

            @Override
            public void onPermissionDenied() {

            }
        });
        btnDownLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchActivity(EventBusActivity.class, null);
            }
        });
        //
        findViewById(R.id.btn_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                launchActivity(RecyclerTestActivity.class, null);
                launchActivity(RXActivity.class, null);
            }
        });
        MessageEvent event = new MessageEvent();
        event.setName("传进去");
        EventBus.getDefault().postSticky(event);
    }

    /**
     * 选择用户头像
     */
    private void showChoseHeadDialog() {
//                XUserHeadDialog xUserHeadDialog = new XUserHeadDialog(MainActivity.this);
//                xUserHeadDialog.setImageLoader(new GlideImageLoader());
//                xUserHeadDialog.showChoseSexDialog();
    }

    /**
     * 日期选择
     */
    private void showDateDialog() {
        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
        datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
            @Override
            public void onDateChoose(int year, int month, int day) {
                Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
            }
        });
        datePickerDialogFragment.show(getSupportFragmentManager(), "DatePickerDialogFragment");
    }

    /**
     * retrofit+okhttp实现图片上传
     */
    private void uploadImg() {
        Map<String, RequestBody> map = new HashMap<>();
        map.put("itvNum", convertRequestBody("DMT2015122206@ITVP"));
        map.put("contentType", convertRequestBody("1"));
        map.put("tag", convertRequestBody("曹斌测"));
        map.put("desp", convertRequestBody("12344321"));
        map.put("phone", convertRequestBody("15108460749"));

        String filePath = "/storage/emulated/0/DCIM/Camera/IMG_20180301_163032.jpg";
        MovieHttpRequest.getInstance().uploadImage(new File(filePath), map, new XHttpCallback<UploadBean>() {
            @Override
            public void onSuccess(UploadBean userInfoResp) {

            }

            @Override
            public void onError(String error) {

            }
        }, listener);
    }

    ProgressListener listener = new ProgressListener() {
        @Override
        public void onProgress(ProgressInfo progressInfo) {

            System.err.println("进度 " + progressInfo.getPercent() + " / " + "100");
        }

        @Override
        public void onError(long id, Exception e) {

        }
    };

    /**
     * 将参数value转变为RequestBody
     *
     * @param param
     * @return
     */
    private RequestBody convertRequestBody(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XUserHeadDialog.CHANGE_HEAD_REQUEST_CODE) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                ArrayList<ImageItem> list = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                Glide.with(MainActivity.this).load(list.get(0).path).into(mImageView);
            }
        }
    }

    private void downLoad1() {
        Map<String, String> map = new HashMap<>();
        map.put("type", "top");
        map.put("key", "f323c09a114635eb935ed8dd19f7284e");
        MovieHttpRequest.getInstance().sendNewsRequest(map, new XHttpCallback<NewsResp>() {
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

    private void downLoad2() {
        Map<String, String> map = new HashMap<>();
        map.put("staffAccount", "gNwTv7GOKr+9tK+bHVlw5A==");
        MovieHttpRequest.getInstance().getUesrInfo(map, new XHttpCallback<UserInfoResp>() {
            @Override
            public void onSuccess(UserInfoResp userInfoResp) {
            }

            @Override
            public void onError(String error) {
                Toast.makeText(MainActivity.this, error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        MovieHttpRequest.getInstance().dispose();
        XActivityStack.getInstance().finishActivity();
    }
}
