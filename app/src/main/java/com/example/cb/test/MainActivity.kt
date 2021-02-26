package com.example.cb.test

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.xlibrary.adapter.XRecyclerViewAdapter
import cn.sccl.xlibrary.adapter.XViewHolder
import com.example.cb.test.base.BaseActivity
import com.example.cb.test.bean.CommonMenuBean
import com.example.cb.test.download.DownLoadActivity
import com.example.cb.test.hook.HookDemoActivity
import com.example.cb.test.jetpack.JetPackActivity
import com.example.cb.test.kotlin.KotlinMainActivity
import com.example.cb.test.mvp.MvpActivity
import com.example.cb.test.rx.rxjava.RxJavaMainActivity
import com.example.cb.test.ui.aidl.AidlTestActivity
import com.example.cb.test.ui.anim.AnimTestActivity
import com.example.cb.test.ui.constrain.MotionLayoutActivity
import com.example.cb.test.ui.material.MaterialActivity
import com.example.cb.test.ui.view_pager.BannerActivity
import com.example.cb.test.upload.UploadActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.AnnotationTestActivity

//import me.devilsen.czxing.ScanBaseActivity

//import me.devilsen.czxing.ScanBaseActivity;
/**
 * @author bin
 * @desc 首页
 * @time 2019年7月19日17:04:09
 */
class MainActivity : BaseActivity() {

    private val mList: ArrayList<CommonMenuBean> = ArrayList()
    lateinit var mAdapter: MAdapter
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initUI() {
        setHeaderTitle("工具库")
        mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = MAdapter(mRecyclerView)
        mRecyclerView.adapter = mAdapter

        mList.add(CommonMenuBean("RxJava使用", RxJavaMainActivity::class.java))
        mList.add(CommonMenuBean("JetPack使用", JetPackActivity::class.java))
        mList.add(CommonMenuBean("Kotlin使用", KotlinMainActivity::class.java))
        mList.add(CommonMenuBean("HookDemoActivity", HookDemoActivity::class.java))
        mList.add(CommonMenuBean("AidlTestActivity", AidlTestActivity::class.java))
        mList.add(CommonMenuBean("MvpActivity", MvpActivity::class.java))
        mList.add(CommonMenuBean("BannerActivity", BannerActivity::class.java))
        mList.add(CommonMenuBean("AnimTestActivity", AnimTestActivity::class.java))
        mList.add(CommonMenuBean("downLoad", DownLoadActivity::class.java))
        mList.add(CommonMenuBean("Upload", UploadActivity::class.java))
        mList.add(CommonMenuBean("Material", MaterialActivity::class.java))
        mList.add(CommonMenuBean("MotionLayout", MotionLayoutActivity::class.java))
        mList.add(CommonMenuBean("注解", AnnotationTestActivity::class.java))
//        mList.add(new CommonMenuBean("生成二维码", QRcodeEncoderActivity.class));
//        mList.add( CommonMenuBean("扫一扫", ScanBaseActivity::class.java))
        mAdapter.dataLists = mList

    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { v: View?, position: Int ->
            val bean = mList[position]
            if (bean.getaClass() != null) {
                launchActivity(bean.getaClass(), null)
            } else {
                toast("暂未开放")
            }
        }
    }

    /****
     *
     */
    inner class MAdapter(mRecyclerView: RecyclerView) :
            XRecyclerViewAdapter<CommonMenuBean>(mRecyclerView, R.layout.item_main) {

        protected override fun bindData(holder: XViewHolder, data: CommonMenuBean, position: Int) {
            val textView = holder.itemView as TextView
            textView.text = data.name
        }
    }
}