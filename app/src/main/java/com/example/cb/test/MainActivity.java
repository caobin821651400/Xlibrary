package com.example.cb.test;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cb.test.base.BaseActivity;
import com.example.cb.test.dagger.DaggerTestActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cb.xlibrary.utils.XActivityStack;
import cb.xlibrary.utils.XLogUtils;
import cb.xlibrary.utils.XPermission;
import library.cb.imagepicker.GlideImageLoader;
import library.cb.imagepicker.ImagePicker;
import library.cb.imagepicker.bean.ImageItem;
import library.cb.imagepicker.ui.ImageGridActivity;

/**
 * @author bin
 * @desc 首页
 * @time 2019年7月19日17:04:09
 */
public class MainActivity extends BaseActivity {

    private Button btnDownLoad;
    private static final String WEATHRE_API_URL = "http://php.weather.sina.com.cn/xml.php?city=%s&password=DJOYnieT8234jlsK&day=0";
    //
    private String apkUrl = "https://codeload.github.com/jeasonlzy/okhttp-OkGo/zip/master";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XActivityStack.getInstance().addActivity(this);
        initView();
    }


    private void initView() {
        btnDownLoad = findViewById(R.id.btn_down_load);
        RecyclerView recyclerView = new RecyclerView(this);
        recyclerView.setNestedScrollingEnabled(false);


        XPermission.requestPermissions(MainActivity.this, 102,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA},
                new XPermission.OnPermissionListener() {
                    @Override
                    public void onPermissionGranted() {

                    }

                    @Override
                    public void onPermissionDenied() {

                    }
                });
        btnDownLoad.setOnClickListener(v -> {
//                launchActivity(MvpActivity.class, null);
//                launchActivity(KotlinSetActivity.class, null);
//                launchActivity(KotlinActivity.class, null);
//            launchActivity(DbTestActivity.class, null);
//            launchActivity(AnimTestActivity.class, null);
//            launchActivity(AidlTestActivity.class, null);
//            launchActivity(BannerActivity.class,null);
//            launchActivity(QRcodeDecoderActivity.class,null);
//            showChoseHeadDialog();
//            launchActivity(MvpActivity.class, null);
//            showChoseHeadDialog();
toast("");

        });


        //
        findViewById(R.id.btn_list).setOnClickListener(view -> launchActivity(DaggerTestActivity.class, null));

//        fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTest = new FragmentTest();
//        fragmentTransaction.add(R.id.test, fragmentTest);
//        fragmentTransaction.commit();
    }

    /**
     * 选择用户头像
     */
    private void showChoseHeadDialog() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());//设置图片加载器
        imagePicker.setShowCamera(false);//显示拍照按钮
        imagePicker.setCrop(true);//允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);//是否按矩形区域保存
        imagePicker.setMultiMode(false);//单选 false 多选true
        imagePicker.setSelectLimit(1);//最大选择张数
        imagePicker.setFocusWidth(800);//裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);//裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(320);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(320);//保存文件的高度。单位像素
        Intent intent = new Intent(MainActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, 1088);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1088) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                ArrayList<ImageItem> list = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                for (int i = 0; i < list.size(); i++) {
                    XLogUtils.v("图片地址 " + list.get(i).path);
                }
            }
        }
    }
//
//    private void showLoadingDialog() {
//        if (xLoadingDialog == null) {
//            xLoadingDialog = new XLoadingDialog(MainActivity.this);
//            xLoadingDialog.setMessage("正在加载中。。。");
//            xLoadingDialog.show();
//        } else {
//            xLoadingDialog.show();
//        }
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                xLoadingDialog.dismiss();
//            }
//        }, 2000);
//    }

//    private void showInputDialog() {
//        new XInputDialog.Builder(MainActivity.this)
//                .leftBtnTxt("取消")
//                .rightBtnTxt("确定")
//                .setCancelable(false)
//                .title("请输入")
//                .setSureClickListener(new XInputDialog.InputDialogBtnClickListener() {
//                    @Override
//                    public void onClick(String content) {
//                        XLog.d(content);
//                    }
//                }).create().show();
//    }

    /**
     * 日期选择
     */
    private void showDateDialog() {
//        DatePickerDialogFragment datePickerDialogFragment = new DatePickerDialogFragment();
//        datePickerDialogFragment.setOnDateChooseListener(new DatePickerDialogFragment.OnDateChooseListener() {
//            @Override
//            public void onDateChoose(int year, int month, int day) {
//                Toast.makeText(getApplicationContext(), year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
//            }
//        });
//        datePickerDialogFragment.setSelectedDate(2018,11,28);
//        datePickerDialogFragment.show(getSupportFragmentManager(), "DatePickerDialogFragment");
    }

//    /**
//     * retrofit+okhttp实现图片上传
//     */
//    private void uploadImg() {
//        String filePath = "/storage/emulated/0/DCIM/Camera/IMG_20180301_163032.jpg";
//        MovieHttpRequest.getInstance().uploadImage(new File(filePath), map, new XHttpCallback<UploadBean>() {
//            @Override
//            public void onSuccess(UploadBean userInfoResp) {
//
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//        }, listener);
//    }
//
//    ProgressListener listener = new ProgressListener() {
//        @Override
//        public void onProgress(ProgressInfo progressInfo) {
//
//            System.err.println("进度 " + progressInfo.getPercent() + " / " + "100");
//        }
//
//        @Override
//        public void onError(long id, Exception e) {
//
//        }
//    };
//
//    /**
//     * 将参数value转变为RequestBody
//     *
//     * @param param
//     * @return
//     */
//    private RequestBody convertRequestBody(String param) {
//        return RequestBody.create(MediaType.parse("text/plain"), param);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == XUserHeadDialog.CHANGE_HEAD_REQUEST_CODE) {
//            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
//                ArrayList<ImageItem> list = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
//                Glide.with(MainActivity.this).load(list.get(0).path).into(mImageView);
//            }
//        }
//    }
//
//    private void downLoad1() {
//        Map<String, String> map = new HashMap<>();
//        map.put("type", "top");
//        map.put("key", "f323c09a114635eb935ed8dd19f7284e");
//        MovieHttpRequest.getInstance().sendNewsRequest(map, new XHttpCallback<NewsResp>() {
//            @Override
//            public void onSuccess(NewsResp newsResp) {
//                System.err.println("哈哈 " + newsResp.getResult().getData().get(0).getTitle());
//            }
//
//            @Override
//            public void onError(String error) {
//                System.err.println("哈哈 error " + error);
//            }
//        });
//    }
//

    /*****************************************服务开始**********************************/
    //启动服务
//    Intent intent1 = new Intent(MainActivity.this, XService.class);
//    Bundle bundle = new Bundle();
//        bundle.putString("taskName", "task2");
//        intent1.putExtras(bundle);
//    startService(intent1);
//
////    绑定服务
//Intent intent1 = new Intent(MainActivity.this, XService.class);
//    bindService(intent1, conn, Context.BIND_AUTO_CREATE);
//
//    //
//    XService xService;
//    ServiceConnection conn = new ServiceConnection() {
//        @Override
//        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
//            XService.MyBinder binder = (XService.MyBinder) iBinder;
//            xService = binder.getService();
//            xService.aa();
//        }
//
//        @Override
//        public void onServiceDisconnected(ComponentName componentName) {
//
//        }
//    };
    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onRestart() {

        super.onRestart();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    /**********************************服务结束**********************************/


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Activity销毁时，取消网络请求
        // MovieHttpRequest.getInstance().dispose();
        XActivityStack.getInstance().finishActivity();
    }
}
