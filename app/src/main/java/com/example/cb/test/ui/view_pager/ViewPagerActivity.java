package com.example.cb.test.ui.view_pager;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.cb.test.R;
import com.example.cb.test.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewPagerActivity extends BaseActivity {

    private ViewPager mViewPager;
    private Integer[] images = {R.drawable.img_banner_one, R.drawable.img_banner_two, R.drawable.img_banner_three, R.drawable.img_banner_four, R.drawable.img_banner_five};
    private List<Integer> imageLists = new ArrayList<>();
    private int currentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
    }

    private void initView() {
        mViewPager = findViewById(R.id.view_pager);

        imageLists.addAll(Arrays.asList(images));


        imageLists.add(0, imageLists.get(imageLists.size() - 1));
        imageLists.add(imageLists.get(1));

        //设置缓存的页面数量
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.setPageMargin(20);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageLists.size();
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(ViewPagerActivity.this);
                imageView.setImageResource(imageLists.get(position));
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });

        mViewPager.setCurrentItem(1, false);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (currentPosition == 0) {
                        mViewPager.setCurrentItem(imageLists.size() - 2, false);
                    } else if (currentPosition == imageLists.size() - 1) {
                        mViewPager.setCurrentItem(1, false);
                    }
                }
//                else if (state == 1) {
//                    if (currentPosition == imageLists.size() - 1) {
//                        mViewPager.setCurrentItem(1, false);
//                    } else if (currentPosition == 0) {
//                        mViewPager.setCurrentItem(imageLists.size() - 1, false);
//                    }
//                }

            }
        });
    }
}
