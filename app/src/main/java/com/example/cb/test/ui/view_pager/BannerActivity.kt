package com.example.cb.test.ui.view_pager

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_view_pager.*
import java.util.*


class BannerActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_view_pager;
    }

    override fun initEvent() {
    }

    private val images = arrayOf(R.drawable.img_banner_one, R.drawable.img_banner_two,
            R.drawable.img_banner_three, R.drawable.img_banner_four, R.drawable.img_banner_five)
    private var imageLists = ArrayList<Int>()
    private var currentPosition: Int = 0


    override fun initUI() {
        //array转list
        imageLists = images.toList() as ArrayList<Int>
        imageLists.add(0, imageLists[imageLists.size - 1])
        imageLists.add(imageLists[1])
        //设置ViewPager缓存数量
        view_pager.offscreenPageLimit = 5
        //每个pager之间的间距
        view_pager.pageMargin = 20
        //适配器
        view_pager.adapter = object : PagerAdapter() {
            override fun getCount(): Int {
                return imageLists.size
            }

            override fun destroyItem(container: ViewGroup, position: Int, any: Any) {
                container.removeView(any as View?)
            }

            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                val imageView = ImageView(this@BannerActivity)
                imageView.setImageResource(imageLists[position])
                imageView.scaleType = ImageView.ScaleType.FIT_XY
                container.addView(imageView)
                return imageView
            }

            override fun isViewFromObject(view: View, any: Any): Boolean {
                return view == any
            }
        }
        view_pager.setCurrentItem(1, false)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    if (currentPosition == 0) {
                        view_pager.setCurrentItem(imageLists.size - 2, false)
                    } else if (currentPosition == imageLists.size - 1) {
                        view_pager.setCurrentItem(1, false)
                    }
                }
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                currentPosition = position
            }

        })
    }
}
