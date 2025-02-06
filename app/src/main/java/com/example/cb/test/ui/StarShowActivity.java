package com.example.cb.test.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.cb.test.R;

/**
 * 动态设置阴影页
 */
public class StarShowActivity extends AppCompatActivity {
    private cn.sccl.xlibrary.view.shadow.ShadowFrameLayout shadowLayout;
    private SeekBar skbar_x;
    private SeekBar skbar_y;
    private SeekBar skbar_limit;
    private SeekBar skbar_corner;
    private int alpha;
    private SeekBar skbar_alpha;
    private int red;
    private SeekBar skbar_red;
    private int green;
    private SeekBar skbar_green;
    private int blue;
    private SeekBar skbar_blue;

    private View lineTop;
    private View lineBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starshow);
        shadowLayout = findViewById(R.id.ShadowLayout);
        skbar_x = findViewById(R.id.skbar_x);
        skbar_y = findViewById(R.id.skbar_y);
        skbar_limit = findViewById(R.id.skbar_limit);
        skbar_corner = findViewById(R.id.skbar_corner);
        skbar_alpha = findViewById(R.id.skbar_alpha);
        skbar_red = findViewById(R.id.skbar_red);
        skbar_green = findViewById(R.id.skbar_green);
        skbar_blue = findViewById(R.id.skbar_blue);
        lineTop = findViewById(R.id.topLine);
        lineBottom = findViewById(R.id.bottomLine);

        shadowLayout.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                shadowLayout.removeOnLayoutChangeListener(this);
                //底部
                ConstraintLayout.LayoutParams lp= (ConstraintLayout.LayoutParams) lineBottom.getLayoutParams();
                lp.bottomMargin = (int) shadowLayout.getShadowOffsetY();
                lineBottom.post(() -> lineBottom.setLayoutParams(lp));

                //顶部
                ConstraintLayout.LayoutParams lp2= (ConstraintLayout.LayoutParams) lineTop.getLayoutParams();
                lp2.topMargin = (int) shadowLayout.getShadowOffsetY();
                lineTop.post(() -> lineTop.setLayoutParams(lp2));
            }
        });

        skbar_corner.setMax((int) (shadowLayout.getCornerRadius() * 3));
        skbar_corner.setProgress((int) shadowLayout.getCornerRadius());
        skbar_corner.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shadowLayout.setCornerRadius(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        skbar_limit.setMax((int) (shadowLayout.getShadowLimit() * 3));
        skbar_limit.setProgress((int) shadowLayout.getShadowLimit());
        skbar_limit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shadowLayout.setShadowLimit(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        skbar_x.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shadowLayout.setShadowOffsetX(progress - 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        skbar_y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shadowLayout.setShadowOffsetY(progress - 100);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        skbar_alpha.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                alpha = progress;
                shadowLayout.setShadowColor(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        skbar_red.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                red = progress;
                shadowLayout.setShadowColor(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        skbar_green.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                green = progress;
                shadowLayout.setShadowColor(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        skbar_blue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                blue = progress;
                shadowLayout.setShadowColor(Color.argb(alpha, red, green, blue));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }

    public boolean select(ImageView imageView) {
        if (imageView.isSelected()) {
            imageView.setSelected(false);
            return false;
        } else {
            imageView.setSelected(true);
            return true;
        }
    }
}
