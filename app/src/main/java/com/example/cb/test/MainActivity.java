package com.example.cb.test;

import com.example.cb.test.base.BaseActivity;
import com.example.cb.test.rx.rxjava.RxJavaMainActivity;

/**
 * @author bin
 * @desc 首页
 * @time 2019年7月19日17:04:09
 */
public class MainActivity extends BaseActivity {


    Class[] mClass = {RxJavaMainActivity.class};

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initUI() {


        //RxJava
        findViewById(R.id.rxjava).setOnClickListener(v -> {
            launchActivity(mClass[0], null);
        });

//        btnDownLoad.setOnClickListener(v -> {
//                launchActivity(MvpActivity.class, null);
//                launchActivity(KotlinSetActivity.class, null);
//            launchActivity(DbTestActivity.class, null);
//            launchActivity(AnimTestActivity.class, null);
//            launchActivity(AidlTestActivity.class, null);
//            launchActivity(BannerActivity.class,null);
//            launchActivity(QRcodeDecoderActivity.class,null);
//            showChoseHeadDialog();
//            launchActivity(MvpActivity.class, null);
//            launchActivity(QRcodeEncoderActivity.class, null);
//            launchActivity(KotlinTestActivity.class, null);
//            showChoseHeadDialog();

//        });


        //
//        findViewById(R.id.btn_list).setOnClickListener(view ->
//                launchActivity(DaggerTestActivity.class, null));

//        fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTest = new FragmentTest();
//        fragmentTransaction.add(R.id.test, fragmentTest);
//        fragmentTransaction.commit();
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
