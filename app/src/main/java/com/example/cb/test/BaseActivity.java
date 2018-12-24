package com.example.cb.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cb.xlibrary.utils.XPermission;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/15
 * desc   :
 */
public class BaseActivity extends AppCompatActivity {

    private static long mPreTime;
    private static Activity mCurrentActivity;// 对所有activity进行管理

    /**
     * 展示图片
     *
     * @param path
     * @param mImageView
     */
    public void showImg(String path, ImageView mImageView) {
        Glide.with(getApplicationContext()).load(path)
                .placeholder(R.drawable.ic_default_image)
                .into(mImageView);
    }


    /**
     * 跳转activity
     *
     * @param cls    跳转的类
     * @param bundle 携带的数据
     */
    public void launchActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * Toast任何类型的数据
     *
     * @param object
     */
    public void toast(Object object) {
        Toast.makeText(BaseActivity.this, object.toString(), Toast.LENGTH_SHORT).show();
    }


    /**
     * 弹出AlertDialog
     *
     * @param msg
     */

    protected void showAlert(String msg) {
        new AlertDialog.Builder(this).setIcon(R.mipmap.ic_launcher).setTitle("温馨提示").setMessage(msg)
                .setPositiveButton("确定", null).create().show();
    }


    /**
     * 统一退出控制
     */
    @Override
    public void onBackPressed() {
        if (mCurrentActivity instanceof MainActivity) {
            //如果是主页面
            if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2秒
                Toast.makeText(this, "再按一次，退出应用", Toast.LENGTH_SHORT).show();
                mPreTime = System.currentTimeMillis();
                return;
            }
        }
        super.onBackPressed();// finish()
    }

    /**
     * 获取前台activity
     *
     * @return
     */
    public static Activity getCurrentActivity() {
        return mCurrentActivity;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCurrentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mCurrentActivity = null;
    }

    /**
     * Android M 全局权限申请回调
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        XPermission.onRequestPermissionsResult(requestCode, permissions, grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
