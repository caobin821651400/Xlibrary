package com.example.cb.test.mvp;

import android.view.View;
import android.widget.TextView;

import cb.xlibrary.utils.XLogUtils;
import com.example.cb.test.R;
import com.example.cb.test.base.BaseMvpFragment;
import com.example.cb.test.mvp.presenter.MvpFragmentPresenter;
import com.example.cb.test.mvp.view.IMvpFragmentView;
import com.example.cb.test.rx.http.NewsResp;

import butterknife.BindView;

/**
 * Created by bin on 2018/12/25.
 */
public class MvpFragment extends BaseMvpFragment<MvpFragmentPresenter> implements IMvpFragmentView {

    @BindView(R.id.aa)
    TextView textView;

    @Override
    protected MvpFragmentPresenter createPresenter() {
        return new MvpFragmentPresenter(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_mvp;
    }

    @Override
    public void initData() {
        mPresenter.fetchData();
    }

    @Override
    public void initView(View v) {
    }

    @Override
    public void initListener() {

    }

    @Override
    public void onNewsSuccess(NewsResp newsResp) {
        XLogUtils.d(newsResp.getResult().getData().get(0).getTitle());
        textView.setText(newsResp.getResult().getData().get(0).getTitle());
    }

    @Override
    public void onNewsError(String error) {
        XLogUtils.e(error);
    }
}
