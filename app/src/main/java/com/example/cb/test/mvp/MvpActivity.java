package com.example.cb.test.mvp;

import android.os.Bundle;
import android.widget.TextView;

import com.cb.xlibrary.utils.XLogUtils;
import com.example.cb.test.R;
import com.example.cb.test.mvp.presenter.NewListPresenter;
import com.example.cb.test.mvp.presenter.view.INewListView;
import com.example.cb.test.rx.http.NewsResp;

public class MvpActivity extends BaseMvpActivity<NewListPresenter> implements INewListView {

    TextView textView;

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
        textView = findViewById(R.id.aa);
    }

    @Override
    public void initData() {
        mPresenter.fetchData();
    }

    @Override
    public void initListener() {
    }

    @Override
    public void onRequestSuccess(NewsResp data) {
        XLogUtils.d(data.getResult().getData().get(0).getAuthor_name());
        textView.setText(data.getResult().getData().get(0).getAuthor_name());
    }

    @Override
    public void onError(String error) {
        XLogUtils.e(error);
    }


}
