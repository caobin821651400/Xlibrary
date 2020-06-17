package com.example.cb.test.hook

import android.content.Intent
import cb.xlibrary.utils.XLogUtils
import com.example.cb.test.R
import com.example.cb.test.TestActivity
import com.example.cb.test.base.BaseActivity
import kotlinx.android.synthetic.main.activity_hook.*

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/6/17 14:20
 * @Desc :
 * ====================================================
 */
class HookDemoActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_hook
    }

    override fun initUI() {}
    override fun initEvent() {

        btnHook1.setOnClickListener { hookConstant() }

        btnHook2.setOnClickListener {
            HookNoResActivityHelper. hookIActivityManager()
            startActivity(Intent(this, TestActivity::class.java));
        }

    }

    /**
     * 简单的反射
     */
    private fun hookConstant() {
        val hookObject = HookObject()
        try {
            val mfSdk = Class.forName("com.example.cb.test.hook.HookObject")
            val mGroupFlagsField = mfSdk.getDeclaredField("mGroupFlags")
            mGroupFlagsField.isAccessible = true
            //如果hook的对象是静态的可以不传，不是静态的就要传图该类的对象
            val i = mGroupFlagsField[null] as Int
            //            int i = (int) mGroupFlagsField.get(moreFunSDK);
            XLogUtils.e("获取到的值->>$i")
            mGroupFlagsField[null] = 100
            //            mGroupFlagsField.set(moreFunSDK, 100);
            hookObject.prlin()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}