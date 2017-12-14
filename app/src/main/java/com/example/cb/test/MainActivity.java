package com.example.cb.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cb.xlibrary.ItemDecoration.PaddingDividerDecoration;
import com.cb.xlibrary.adapter.XRecyclerViewAdapter;
import com.cb.xlibrary.adapter.XViewHolder;
import com.cb.xlibrary.utils.log.XLog;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter myAdapter;
    private List<String> mList;
    private SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = findViewById(R.id.recycler_view);
        mRefreshLayout = findViewById(R.id.swipe);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                myAdapter.setDataLists(mList);
            }
        });
        myAdapter = new MyAdapter(mRecyclerView);
        mRecyclerView.setAdapter(myAdapter);

        PaddingDividerDecoration divider = new PaddingDividerDecoration.Builder(this)
                .setHeight(R.dimen.default_divider_height)
                .setPadding(R.dimen.default_divider_padding)
                .setColorResource(R.color.colorAccent)
                .build();

        //mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(divider);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("我是 " + i);
        }
        myAdapter.setDataLists(mList);

        myAdapter.setNerworkErrorListener(new XRecyclerViewAdapter.OnNetworkErrorListener() {
            @Override
            public void onRetry() {
                XLog.d(" 哈哈");
            }
        });
    }

    public void update(View view) {
//        for (int i = 0; i < 10; i++) {
//            mList.add("新增加 " + i);
//        }
        // mList.clear();
        myAdapter.showNetworkError(true);
    }

    class MyAdapter extends XRecyclerViewAdapter<String> {
        TextView textView;

        public MyAdapter(@NonNull RecyclerView mRecyclerView) {
            super(mRecyclerView, R.layout.item_list_recycler);
        }

        @Override
        protected void bindData(XViewHolder holder, String data, int position) {
            View view = holder.getConvertView();
            textView = view.findViewById(R.id.textview);
            textView.setText(data);
        }
    }
}
