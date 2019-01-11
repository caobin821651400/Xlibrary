package com.example.cb.test;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cb.xlibrary.utils.XLogUtils;

/**
 * 描述：
 * 作者：曹斌
 * date:2018/7/23 10:31
 */
public class FragmentTest extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        XLogUtils.d("onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        XLogUtils.d("onViewCreated");
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        XLogUtils.d("onCreateView");
        View view = inflater.inflate(R.layout.x_stateview_empty_cblibrary, null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        XLogUtils.d("onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        XLogUtils.d("onPause");
        super.onPause();
    }

    @Override
    public void onResume() {
        XLogUtils.d("onResume");
        super.onResume();
    }

    @Override
    public void onStop() {
        XLogUtils.d("onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        XLogUtils.d("onDestroy");
        super.onDestroy();
    }

    @Override
    public void onStart() {
        XLogUtils.d("onStart");
        super.onStart();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        XLogUtils.d("onAttach");
    }
}
