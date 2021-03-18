package com.example.cb.test.skin

import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_skin.*

/**
 * ====================================================
 * @User :caobin
 * @Date :2021/3/18 15:18
 * @Desc :插件化换肤
 * ====================================================
 */
class SkinActivity : BaseActivity() {


    override fun getLayoutId(): Int {
        return R.layout.activity_skin
    }

    override fun initUI() {

    }

    override fun initEvent() {

        changSkin.setOnClickListener {

        }

        reset.setOnClickListener {

        }

        imageView.setOnClickListener {  }
    }
}