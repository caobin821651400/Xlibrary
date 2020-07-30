package com.example.cb.test;

import android.annotation.SuppressLint;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cb.test.base.BaseActivity;
import com.example.cb.test.bean.CommonMenuBean;
import com.example.cb.test.hook.HookDemoActivity;
import com.example.cb.test.jetpack.lifecycles.LifeCyclesActivity;
import com.example.cb.test.jetpack.livedata.LiveDataActivity;
import com.example.cb.test.jetpack.navigation.NavigationActivity;
import com.example.cb.test.jetpack.room.RoomActivity;
import com.example.cb.test.jetpack.viewmodule.ViewModuleActivity;
import com.example.cb.test.jetpack.workmanager.WorkManagerActivity;
import com.example.cb.test.rx.rxjava.RxJavaMainActivity;
import com.example.cb.test.service.MyService;
import com.example.cb.test.ui.aidl.AidlTestActivity;
import com.example.cb.test.ui.scan.QRcodeEncoderActivity;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cb.xlibrary.adapter.XRecyclerViewAdapter;
import cb.xlibrary.adapter.XViewHolder;
//import me.devilsen.czxing.ScanBaseActivity;

/**
 * @author bin
 * @desc 首页
 * @time 2019年7月19日17:04:09
 */
public class MainActivity extends BaseActivity {
    private List<CommonMenuBean> mList = new ArrayList<>();
    RecyclerView mRecyclerView;
    MAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("CheckResult")
    @Override
    protected void initUI() {
        mRecyclerView = findViewById(R.id.mRecyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mList.add(new CommonMenuBean("测试拉起APK", CommentWebViewActivity.class));
        mList.add(new CommonMenuBean("AidlTestActivity", AidlTestActivity.class));
        mList.add(new CommonMenuBean("RxJava", RxJavaMainActivity.class));
        mList.add(new CommonMenuBean("LifeCycles", LifeCyclesActivity.class));
        mList.add(new CommonMenuBean("LiveData", LiveDataActivity.class));
        mList.add(new CommonMenuBean("ViewModule", ViewModuleActivity.class));
        mList.add(new CommonMenuBean("RoomActivity", RoomActivity.class));
        mList.add(new CommonMenuBean("NavigationActivity", NavigationActivity.class));
        mList.add(new CommonMenuBean("WorkManagerActivity", WorkManagerActivity.class));
        mList.add(new CommonMenuBean("HookDemoActivity", HookDemoActivity.class));
//        mList.add(new CommonMenuBean("扫一扫", ScanBaseActivity.class));
        mList.add(new CommonMenuBean("生成二维码", QRcodeEncoderActivity.class));

        mAdapter.setDataLists(mList);

        mAdapter.setOnItemClickListener((v, position) -> {
            if (position == 0) {
//                hookIActivityManager();
//                test();
//                Intent intent = new Intent(this, com.zero.activityhookdemo.MainActivity.class);
//                startActivity(intent);
//                return;
                aaa();
            }
            CommonMenuBean bean = mList.get(position);
            if (bean.getaClass() != null) {
//                launchActivity(bean.getaClass(), null);
                startForegroundService(new Intent(this, MyService.class));
            }
        });

//        btnDownLoad.setOnClickListener(v -> {
//                launchActivity(MvpActivity.class, null);
//                launchActivity(KotlinSetActivity.class, null);
//            launchActivity(DbTestActivity.class, null);
//            launchActivity(AnimTestActivity.class, null);
//            launchActivity(AidlTestActivity.class, null);
//            launchActivity(BannerActivity.class,null);
//            showChoseHeadDialog();
//            launchActivity(MvpActivity.class, null);
//            launchActivity(QRcodeEncoderActivity.class, null);
//            launchActivity(KotlinTestActivity.class, null);
//            showChoseHeadDialog();

//        });
    }

    private void aaa() {
        //判断当前系统版本
        if (Build.VERSION.SDK_INT >= 23) {
            //判断权限是否已经申请过了（加上这个判断，则使用的悬浮窗的时候；如果权限已经申请则不再跳转到权限开启界面）
            if (!Settings.canDrawOverlays(this)) {
                //申请权限
                Intent intent2 = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent2, 1);
            } else {

            }
        } else {

        }
        System.out.println("Build.VERSION.SDK_INT::::" + Build.VERSION.SDK_INT);

    }

    /**
     * 小米后台弹出界面检测方法
     *
     * @param context
     * @return
     */
    public static boolean canBackgroundStart(Context context) {
        AppOpsManager ops = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        }
        try {
            int op = 10021;
            Method method = ops.getClass().getMethod("checkOpNoThrow", new Class[]{int.class, int.class, String.class});
            Integer result = (Integer) method.invoke(ops, op, android.os.Process.myUid(), context.getPackageName());
            return result == AppOpsManager.MODE_ALLOWED;
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    protected void initEvent() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /****
     *
     */
    class MAdapter extends XRecyclerViewAdapter<CommonMenuBean> {

        public MAdapter(@NonNull RecyclerView mRecyclerView) {
            super(mRecyclerView, R.layout.item_main);
        }

        @Override
        protected void bindData(XViewHolder holder, CommonMenuBean data, int position) {
            TextView textView = (TextView) holder.itemView;
            textView.setText(data.getName());
        }
    }
}
