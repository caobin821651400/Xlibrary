package java;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cb.test.R;

public class AnnotationTestActivity extends AppCompatActivity {


    @BindView(R.id.tv)
    private TextView tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_annotation_text);
        InjectUtils.bindView(this);

        tv.setText("2135");
    }
}