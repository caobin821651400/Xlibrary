package com.example.cb.test.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.cb.test.R;

/**
 * Created by caobin on 2018/5/4.
 */

public class TestAdapter extends BaseAdapter {
    String[] sss = {"1", "2", "3", "4"};
    private Context mContext;

    public TestAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return sss.length;
    }

    @Override
    public String getItem(int i) {
        return sss[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1 = LayoutInflater.from(mContext).inflate(R.layout.item_list_recycler, null, false);
        TextView textView = view1.findViewById(R.id.textview);
        textView.setText(getItem(i));
        return view1;
    }
}
