package com.cb.filepicker.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.cb.filepicker.PickerManager;
import com.cb.filepicker.R;
import com.cb.filepicker.callback.FileResultCallback;
import com.cb.filepicker.models.Document;
import com.cb.filepicker.models.FileType;
import com.cb.filepicker.utils.MediaStoreHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * 文件选择
 */
public class FilePickerActivity extends AppCompatActivity {

    private ListView listView;
    private FilePickerAdapter mAdapter;
    private ArrayList<Document> fileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_picker);
        initView();
    }

    private void initView() {
        listView = findViewById(R.id.list_view);
        initData();
    }

    private void initData() {
        //添加扫描类型
        String[] pdfs = {"pdf"};
        String[] docs = {"doc", "docx", "dot", "dotx"};
        String[] ppts = {"ppt", "pptx"};
        PickerManager.getInstance().addFileType(new FileType("WORD", docs, R.drawable.ic_word));
        PickerManager.getInstance().addFileType(new FileType("PDF", pdfs, R.drawable.ic_pdf));
        PickerManager.getInstance().addFileType(new FileType("ppt", ppts, R.drawable.ic_ppt));

        //异步扫描
        MediaStoreHelper.getDocs(this, new FileResultCallback<Document>() {
            @Override
            public void onResultCallback(List<Document> files) {
                if (files != null) {
                    mAdapter = new FilePickerAdapter(FilePickerActivity.this, files, fileList);
                    listView.setAdapter(mAdapter);
                }
            }
        });
    }
}
