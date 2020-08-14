package com.example.cb.test;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cb.test.base.BaseActivity;
import com.example.cb.test.bean.CommonMenuBean;
import com.example.cb.test.hook.HookDemoActivity;
import com.example.cb.test.jetpack.JetPackActivity;
import com.example.cb.test.kotlin.KotlinMainActivity;
import com.example.cb.test.mvp.MvpActivity;
import com.example.cb.test.rx.rxjava.RxJavaMainActivity;
import com.example.cb.test.ui.aidl.AidlTestActivity;
import com.example.cb.test.ui.anim.AnimTestActivity;
import com.example.cb.test.ui.view_pager.BannerActivity;

import java.util.ArrayList;
import java.util.List;

import cn.sccl.xlibrary.adapter.XRecyclerViewAdapter;
import cn.sccl.xlibrary.adapter.XViewHolder;
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

    @Override
    protected void initUI() {
        setHeaderTitle("工具库");

        mRecyclerView = findViewById(R.id.mRecyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MAdapter(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);

        mList.add(new CommonMenuBean("RxJava使用", RxJavaMainActivity.class));
        mList.add(new CommonMenuBean("JetPack使用", JetPackActivity.class));
        mList.add(new CommonMenuBean("Kotlin使用", KotlinMainActivity.class));
        mList.add(new CommonMenuBean("HookDemoActivity", HookDemoActivity.class));
        mList.add(new CommonMenuBean("AidlTestActivity", AidlTestActivity.class));
        mList.add(new CommonMenuBean("MvpActivity", MvpActivity.class));
        mList.add(new CommonMenuBean("BannerActivity", BannerActivity.class));
        mList.add(new CommonMenuBean("AnimTestActivity", AnimTestActivity.class));
//        mList.add(new CommonMenuBean("生成二维码", QRcodeEncoderActivity.class));
//        mList.add(new CommonMenuBean("扫一扫", ScanBaseActivity.class));

        mAdapter.setDataLists(mList);

        mAdapter.setOnItemClickListener((v, position) -> {
            CommonMenuBean bean = mList.get(position);
            if (bean.getaClass() != null) {
                launchActivity(bean.getaClass(), null);
            }
        });
    }

    @Override
    protected void initEvent() {
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
