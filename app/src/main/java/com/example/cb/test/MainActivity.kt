package com.example.cb.test

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.sccl.xlibrary.adapter.XRecyclerViewAdapter
import cn.sccl.xlibrary.adapter.XViewHolder
import cn.sccl.xlibrary.utils.XLogUtils
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
import com.example.cb.test.ui.material.MaterialActivity
import com.example.cb.test.ui.service.BackgroundService
import com.example.cb.test.ui.view_pager.BannerActivity
import com.example.cb.test.upload.UploadActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

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
//        mList.add(new CommonMenuBean("生成二维码", QRcodeEncoderActivity.class));
//        mList.add(new CommonMenuBean("扫一扫", ScanBaseActivity.class));
        mAdapter.dataLists = mList

    }

    override fun initEvent() {
        mAdapter.setOnItemClickListener { v: View?, position: Int ->
//            val bean = mList[position]
//            if (bean.getaClass() != null) {
//                launchActivity(bean.getaClass(), null)
//            }
            toast("开始")
            CoroutineScope(Dispatchers.Main).launch {
                aaa()
            }
        }
    }
    private suspend fun aaa() {
        withContext(Dispatchers.IO) {
            delay(5000)
            withContext(Dispatchers.Main) {
                XLogUtils.d("启动了")

                val intent = Intent(this@MainActivity, UploadActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
    var mBackgroundService: BackgroundService? = null

    /**
     * service连接监听
     */
    private var mServiceConnection = object : ServiceConnection {
        /**
         * 服务连接成功
         */
        override fun onServiceConnected(name: ComponentName?, iBinder: IBinder?) {
            //通过服务端onBind方法返回的binder对象得到IMyService的实例，得到实例就可以调用它的方法了
            mBackgroundService = (iBinder as? BackgroundService.MBinder)?.getService()

            mBackgroundService?.mViewModel?.observe(this@MainActivity, Observer {
                //or Intent.FLAG_ACTIVITY_CLEAR_TASK
                XLogUtils.d("启动了")
                val intent = Intent(this@MainActivity, UploadActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            })
        }

        /**
         * 服务连接失败
         */
        override fun onServiceDisconnected(name: ComponentName?) {
            mBackgroundService = null
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