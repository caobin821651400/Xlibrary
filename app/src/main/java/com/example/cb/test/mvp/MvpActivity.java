package com.example.cb.test.mvp;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import cb.xlibrary.utils.XLogUtils;
import com.example.cb.test.R;
import com.example.cb.test.base.BaseMvpActivity;
import com.example.cb.test.mvp.presenter.NewListPresenter;
import com.example.cb.test.mvp.view.INewListView;
import com.example.cb.test.rx.http.NewsResp;

public class MvpActivity extends BaseMvpActivity<NewListPresenter> implements INewListView {

    private FragmentManager fragmentManager;
    private MvpFragment mvpFragment;

    @Override
    protected NewListPresenter createPresenter() {
        return new NewListPresenter(this);
    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_mvp;
    }

    @Override
    public void initView() {
        if (fragmentManager == null) {
            fragmentManager = getSupportFragmentManager();
        }

        mvpFragment = new MvpFragment();

        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.content, mvpFragment, "mvpFragment");
        transaction.commit();

    }

    @Override
    public void initData() {
        // mPresenter.fetchData();
    }

    @Override
    public void initListener() {


    }

    @Override
    public void onRequestSuccess(NewsResp data) {
        XLogUtils.d(data.getResult().getData().get(0).getAuthor_name());
    }

    @Override
    public void onError(String error) {
        XLogUtils.e(error);
    }


}
