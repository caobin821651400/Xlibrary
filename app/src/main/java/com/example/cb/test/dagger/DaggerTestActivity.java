package com.example.cb.test.dagger;

import android.os.Bundle;

import com.example.cb.test.R;
import com.example.cb.test.base.BaseActivity;

import javax.inject.Inject;

/**
 *
 */
public class DaggerTestActivity extends BaseActivity {

    @Inject
    AObject aObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dagger_test);
        initView();
    }

    private void initView() {
        //
        DaggerMainComponent.builder()
                .mainModule(new MainModule())
                .build()
                .inject(this);
        aObject.eat();
    }
}
