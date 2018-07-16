package com.cb.xlibrary.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 描述：通用ViewPagerFragmentAdapter
 * 作者：曹斌
 * date:2018/6/8 09:28
 */
public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> listData;

    private List<String> listTitle;

    public ViewPagerFragmentAdapter(FragmentManager fm, List<Fragment> listData) {
        this(fm, listData, null);
    }

    public ViewPagerFragmentAdapter(FragmentManager fm, List<Fragment> listData, List<String> listTitle) {
        super(fm);
        this.listData = listData;
        this.listTitle = listTitle;
    }


    public List<Fragment> getListData() {
        return listData;
    }

    public void setListData(List<Fragment> listData) {
        this.listData = listData;
    }

    public List<String> getListTitle() {
        return listTitle;
    }

    public void setListTitle(List<String> listTitle) {
        this.listTitle = listTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return listData == null ? null : listData.get(position);
    }

    @Override
    public int getCount() {
        return listData == null ? 0 : listData.size();
    }


    @Override
    public String getPageTitle(int position) {
        if (listTitle != null && listTitle.size() != 0) {
            return listTitle.get(position);
        }
        return (String) super.getPageTitle(position);
    }
}
