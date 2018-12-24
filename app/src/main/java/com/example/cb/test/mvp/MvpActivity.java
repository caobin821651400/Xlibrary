package com.example.cb.test.mvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.cb.test.R;
import com.example.cb.test.mvp.presenter.NewListPresenter;

public class MvpActivity extends BaseMvpActivity<NewListPresenter> {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
    }

    @Override
    protected NewListPresenter createPresenter() {
        return null;
    }

    @Override
    protected int provideContentViewId() {
        return 0;
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initListener() {
        super.initListener();
    }
}
