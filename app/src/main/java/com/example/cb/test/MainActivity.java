package com.example.cb.test;

import android.widget.TextView;

import androidx.annotation.NonNull;
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
import com.example.cb.test.ui.aidl.AidlTestActivity;

import java.util.ArrayList;
import java.util.List;

import cb.xlibrary.adapter.XRecyclerViewAdapter;
import cb.xlibrary.adapter.XViewHolder;

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

    @Override
    protected void initUI() {
        mRecyclerView = findViewById(R.id.mRecyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mList.add(new CommonMenuBean("AidlTestActivity", AidlTestActivity.class));
        mList.add(new CommonMenuBean("RxJava", RxJavaMainActivity.class));
        mList.add(new CommonMenuBean("LifeCycles", LifeCyclesActivity.class));
        mList.add(new CommonMenuBean("LiveData", LiveDataActivity.class));
        mList.add(new CommonMenuBean("ViewModule", ViewModuleActivity.class));
        mList.add(new CommonMenuBean("RoomActivity", RoomActivity.class));
        mList.add(new CommonMenuBean("NavigationActivity", NavigationActivity.class));
        mList.add(new CommonMenuBean("WorkManagerActivity", WorkManagerActivity.class));
        mList.add(new CommonMenuBean("HookDemoActivity", HookDemoActivity.class));

        mAdapter.setDataLists(mList);

        mAdapter.setOnItemClickListener((v, position) -> {
//            if (position == 0) {
//                hookIActivityManager();
//                test();
//                Intent intent = new Intent(this, com.zero.activityhookdemo.MainActivity.class);
//                startActivity(intent);
//                return;
//            }
            CommonMenuBean bean = mList.get(position);
            if (bean.getaClass() != null) {
                launchActivity(bean.getaClass(), null);
            }
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
