package com.example.cb.test.ui.rv;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cb.xlibrary.adapter.XRecyclerViewAdapter;
import com.cb.xlibrary.adapter.XViewHolder;
import com.cb.xlibrary.ItemDecoration.XPaddingDividerDecoration;
import com.cb.xlibrary.utils.XLogUtils;
import com.example.cb.test.base.BaseActivity;
import com.example.cb.test.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerTestActivity extends BaseActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    List<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_test);
        initView();
    }

    private void initView() {
        mRecyclerView = findViewById(R.id.recycler_view);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(mRecyclerView);
        XPaddingDividerDecoration decoration = new XPaddingDividerDecoration.Builder(this)
                .setColor(Color.RED).setHeight(R.dimen.dp_10).build();
        mRecyclerView.addItemDecoration(decoration);
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.setNestedScrollingEnabled(false);



        //mAdapter.setDataLists(list);
        mAdapter.showNetworkError(true);

        mAdapter.setNerworkErrorListener(new XRecyclerViewAdapter.OnNetworkErrorListener() {
            @Override
            public void onRetry() {
                mAdapter.showNetworkError(false);
                for (int i = 0; i < 30; i++) {
                    list.add("我是第几个 " + (i + 1));
                }
                mAdapter.setDataLists(list);
                XLogUtils.d("!11111111");
            }
        });
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
