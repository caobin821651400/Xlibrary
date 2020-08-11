package com.example.cb.test.jetpack.hilt

import android.annotation.SuppressLint
import android.widget.LinearLayout
import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.jetpack.hilt.other.HiltObject
import com.example.cb.test.jetpack.hilt.other.HiltTextView
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.android.synthetic.main.activity_hilt_main.*
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


    @ActivityModule.Type1
    @Inject
    lateinit var stringValue: String

    @ActivityModule.Type2
    @Inject
    lateinit var stringValue2: String

    @ActivityScoped
    @Inject
    lateinit var beanValue: HiltBean

    @ActivityScoped
    @Inject
    lateinit var hiltObject: HiltObject

//    private val viewModule by viewModels<HiltViewModule>()

    override fun getLayoutId(): Int {
        return R.layout.activity_hilt_main
    }

    override fun initUI() {
        setHeaderTitle("Hilt使用")

        hiltObject?.let {
            XLogUtils.e(hiltObject.getName())
        }

//        textView.text = "new one"
//        rootView.addView(textView, LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 100))
    }


    @SuppressLint("SetTextI18n")
    override fun initEvent() {
        button.setOnClickListener {
            tvContent.text = "值=${stringValue}\n " +
                    "值2=${stringValue2} \n" +
                    "值3=${beanValue?.value} \n"
        }
    }
}
