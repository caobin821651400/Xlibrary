package com.cb.xlibrary.dialog;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;

import com.cb.xlibrary.bean.BottomPopupBean;
import com.cb.xlibrary.imagepicker.ImagePicker;
import com.cb.xlibrary.imagepicker.loader.ImageLoader;
import com.cb.xlibrary.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * author : caobin
 * e-mail : 821651400@qq.com
 * time   : 2017/12/21
 * desc   : 选择用户头像
 */
public class XUserHeadDialog implements XActionSheetDialog.XMenuListener {
    public static final int CHANGE_BY_ALBUM = 0;//从相册选择照片
    public static final int CHANGE_BY_CAMERA = 1;//直接拍照
    public static final int CHANGE_HEAD_REQUEST_CODE = 1088;//
    private Context mContext;
    private ImageLoader mImageLoader;
    private XActionSheetDialog dialog;
    private List<BottomPopupBean> mList = new ArrayList<>();
    private Handler mHandler = new Handler();


    public XUserHeadDialog(Context mContext) {
        this.mContext = mContext;
        mList.add(new BottomPopupBean("拍照", CHANGE_BY_CAMERA));
        mList.add(new BottomPopupBean("从相册选择图片", CHANGE_BY_ALBUM));
    }

    /**
     * 设置图片加载器
     *
     * @param imageLoader
     */
    public void setImageLoader(ImageLoader imageLoader) {
        this.mImageLoader = imageLoader;
    }

    public void show() {
        if (mImageLoader == null) {
            throw new RuntimeException("必须先调用setImageLoader()方法。");
        }
        dialog = new XActionSheetDialog(mContext);
        dialog.setMenusList(mList);
        dialog.setPopTitle("选择更换方式");
        dialog.setOnItemClickListener(this);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (mHandler != null) mHandler.removeCallbacksAndMessages(null);
            }
        });
        dialog.show();
    }

    @Override
    public void onItemSelected(int position, final BottomPopupBean item) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (((int) item.getValue()) == CHANGE_BY_ALBUM) {
                    byAlbum();
                } else {
                    byCamera();
                }
            }
        }, 550);
    }

    /**
     * 通过相册
     */
    private void byAlbum() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(mImageLoader);   //设置图片加载器
        imagePicker.setShowCamera(false);//显示拍照按钮
        imagePicker.setCrop(true);//允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);//是否按矩形区域保存
        imagePicker.setMultiMode(false);
        imagePicker.setSelectLimit(1);
        imagePicker.setFocusWidth(800);//裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);//裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(320);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(320);//保存文件的高度。单位像素
        Intent intent = new Intent(mContext, ImageGridActivity.class);
        if (mContext instanceof Activity) {
            ((Activity) mContext).startActivityForResult(intent, CHANGE_HEAD_REQUEST_CODE);
        } else {
            throw new ClassCastException("Context Must  Activity!");
        }
    }

    /**
     * 通过相机
     */
    private void byCamera() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(mImageLoader);   //设置图片加载器
        imagePicker.setCrop(true);//允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);//是否按矩形区域保存
        imagePicker.setMultiMode(false);
        imagePicker.setFocusWidth(800);//裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);//裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(320);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(320);//保存文件的高度。单位像素
        Intent intent = new Intent(mContext, ImageGridActivity.class);
        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
        if (mContext instanceof Activity) {
            ((Activity) mContext).startActivityForResult(intent, CHANGE_HEAD_REQUEST_CODE);
        } else {
            throw new ClassCastException("Context Must Activity!");
        }
    }
}
