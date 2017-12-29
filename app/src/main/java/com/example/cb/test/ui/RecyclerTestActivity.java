package com.example.cb.test.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cb.xlibrary.ItemDecoration.XGridItemDecoration;
import com.cb.xlibrary.ItemDecoration.XPaddingDividerDecoration;
import com.cb.xlibrary.adapter.XRecyclerViewAdapter;
import com.cb.xlibrary.adapter.XViewHolder;
import com.example.cb.test.BaseActivity;
import com.example.cb.test.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerTestActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_test);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this,6));
        mAdapter = new MyAdapter(mRecyclerView);
//        XPaddingDividerDecoration decoration = new XPaddingDividerDecoration.Builder(this)
//                .setColor(Color.RED).setHeight(R.dimen.dp_10).build();
        XGridItemDecoration decoration = new XGridItemDecoration.Builder(this)
                .setColor(Color.RED).setHorizontal(10f).setVertical(10f)
                .build();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addItemDecoration(decoration);

        TextView textView = new TextView(this);
        textView.setText("h哈哈");

     //   mAdapter.addHeaderView(textView);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("我是第几个 " + (i + 1));
        }

        mAdapter.setDataLists(list);

    }


    class MyAdapter extends XRecyclerViewAdapter<String> {
        private TextView mTextView;

        public MyAdapter(@NonNull RecyclerView mRecyclerView) {
            super(mRecyclerView, R.layout.item_list_recycler);
        }

        @Override
        protected void bindData(XViewHolder holder, String data, int position) {
            View view = holder.getConvertView();
            mTextView = view.findViewById(R.id.textview);
            mTextView.setText(data);

        }
    }
}
