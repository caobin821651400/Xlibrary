package library.cb.imagepicker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;

import library.cb.imagepicker.bean.ImageItem;
import library.cb.imagepicker.ui.ImageGridActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                byAlbum();
            }
        });
    }

    /**
     * 通过相册
     */
    private void byAlbum() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);//显示拍照按钮
        imagePicker.setCrop(true);//允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true);//是否按矩形区域保存
        imagePicker.setMultiMode(false);//单选 false
        imagePicker.setSelectLimit(9);
        imagePicker.setFocusWidth(800);//裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);//裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(320);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(320);//保存文件的高度。单位像素
        Intent intent = new Intent(MainActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, 1088);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1088) {
            if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
                ArrayList<ImageItem> list = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                System.err.println("图片地址 " + list.get(0).path);
            }
        }
    }
}
