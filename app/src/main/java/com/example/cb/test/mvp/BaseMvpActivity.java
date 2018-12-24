package com.example.cb.test.mvp;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.example.cb.test.BaseActivity;
import com.example.cb.test.MainActivity;

/**
 * Created by bin on 2018/12/24.
 */

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity {
    protected T mPresenter;
    private static long mPreTime;
    private static Activity mCurrentActivity;// 对所有activity进行管理

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        mPresenter = createPresenter();

        initView();
        initData();
        initListener();

    }

    //用于创建Presenter和判断是否使用MVP模式(由子类实现)
    protected abstract T createPresenter();

    //得到当前界面的布局文件id(由子类实现)
    protected abstract int provideContentViewId();

    public void initView() {
    }

    public void initData() {
    }

    public void initListener() {
    }


    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof MainActivity){
            //如果是主页面
            if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2秒
//                UIUtils.showToast("再按一次，退出应用");
                mPreTime = System.currentTimeMillis();
                return;
            }
        }
        super.onBackPressed();// finish()
    }

    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }
}
