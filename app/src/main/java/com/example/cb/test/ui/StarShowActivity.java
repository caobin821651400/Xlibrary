package com.example.cb.test.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cb.test.R;

/**
 * 动态设置阴影页
 */
public class StarShowActivity extends AppCompatActivity {
    private cn.sccl.xlibrary.view.shadow.ShadowConstraintLayout shadowLayout;
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
    private SeekBar skbar_stroke;
    private CheckBox check_stroke;

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
        skbar_stroke = findViewById(R.id.skbar_stroke);
        check_stroke = findViewById(R.id.check_stroke);

        check_stroke.setOnCheckedChangeListener((buttonView, isChecked) -> {
            shadowLayout.enableStrokeWidth(isChecked);
        });

        shadowLayout.post(() -> {
            int color = shadowLayout.getShadowColor();
            alpha = Color.alpha(color);
            blue = Color.blue(color);
            red = Color.red(color);
            green = Color.green(color);
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


        skbar_limit.setMax((int) (shadowLayout.getShadowBlur() * 3));
        skbar_limit.setProgress((int) shadowLayout.getShadowBlur());
        skbar_limit.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shadowLayout.setShadowBlur(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        skbar_x.setMax((int) (shadowLayout.getShadowBlur()*2));
        skbar_x.setProgress((int) (shadowLayout.getShadowOffsetX()));
        skbar_x.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shadowLayout.setShadowOffsetX(progress - shadowLayout.getShadowBlur());
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        skbar_y.setMax((int) (shadowLayout.getShadowBlur()*2));
        skbar_y.setProgress((int) (shadowLayout.getShadowOffsetY()));
        skbar_y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                shadowLayout.setShadowOffsetY(progress - shadowLayout.getShadowBlur());
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

        skbar_stroke.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int max = 30;
                shadowLayout.setStrokeWidth((progress / 100f) * max);
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
