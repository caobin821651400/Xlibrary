package com.example.cb.test.ui.material

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.sccl.xlibrary.statusbar.StatusBarUtils
import com.example.cb.test.R
import com.example.cb.test.base.BaseActivity
/**
 * ====================================================
 * @User :caobin
 * @Date :2020/9/11 11:45
 * @Desc :
 * ====================================================
 */
class MaterialActivity : BaseActivity() {


    override fun initUI() {
        StatusBarUtils.immersive(this)
    }

    override fun getLayoutId()=R.layout.activity_material2

    override fun initEvent() {
    }
}