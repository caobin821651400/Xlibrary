package com.example.cb.test.jetpack.hilt

import android.annotation.SuppressLint
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import cn.sccl.xlibrary.kotlin.lazyNone
import cn.sccl.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.jetpack.hilt.provider.HiltProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * ====================================================
 * @User :caobin
 * @Date :2020/8/11 11:50
 * @Desc :Hilt使用
 * ====================================================
 */
@AndroidEntryPoint
class HiltMainActivity : BaseActivity() {


    private val tvContent by lazyNone { findViewById<TextView>(R.id.tvContent) }
    private val button by lazyNone { findViewById<Button>(R.id.button) }
    private val rootView by lazyNone { findViewById<LinearLayout>(R.id.root_view) }

    @Inject
    lateinit var mHiltProvider: HiltProvider

    private val viewModule by viewModels<HiltViewModule>()

    override fun getLayoutId(): Int {
        return R.layout.activity_hilt_main
    }


    override fun initUI() {
        setHeaderTitle("Hilt使用")

        XLogUtils.v("joker log1=${mHiltProvider.getValue1()}");
        XLogUtils.e("joker log2=${mHiltProvider.getChildValue1()}");

    }


    @SuppressLint("SetTextI18n")
    override fun initEvent() {
        button.setOnClickListener {

        }
    }
}
