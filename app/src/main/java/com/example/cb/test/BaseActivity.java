package com.example.cb.test;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cb.xlibrary.permission.XPermission;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/15
 * desc   :
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

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
     * 简化findviewbyid
     * 此处用不到，此基类已使用ButterKnife注解
     * 在不使用注解的时候可以使用
     *
     * @param resId
     * @param <T>
     * @return
     */
    public <T extends View> T mFindViewById(int resId) {
        return (T) findViewById(resId);
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
