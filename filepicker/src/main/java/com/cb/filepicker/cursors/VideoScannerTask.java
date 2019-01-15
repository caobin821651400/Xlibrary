package com.cb.filepicker.cursors;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.provider.MediaStore;


import com.cb.filepicker.models.VideoItemBean;
import com.cb.filepicker.callback.FileResultCallback;

import java.util.ArrayList;
import java.util.List;


/**
 * 扫描视频类
 * Created by caobin on 01/08/16.
 */
public class VideoScannerTask extends AsyncTask<Void, Void, List<VideoItemBean>> {

    final String[] VIDEO_PROJECTION = {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
//            MediaStore.Video.Media.ALBUM,
//            MediaStore.Video.Media.ARTIST,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DURATION,
            MediaStore.Video.Media.SIZE
    };
    private final FileResultCallback<VideoItemBean> resultCallback;

    private final Context context;

    public VideoScannerTask(Context context, FileResultCallback<VideoItemBean> fileResultCallback) {
        this.context = context;
        this.resultCallback = fileResultCallback;
    }

    @Override
    protected List<VideoItemBean> doInBackground(Void... voids) {
        ArrayList<VideoItemBean> videoList = new ArrayList<>();
        final String[] projection = VIDEO_PROJECTION;
        final Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);

        if (cursor != null) {
            videoList = getVideoFromCursor(cursor);
            cursor.close();
        }
        return videoList;
    }

    @Override
    protected void onPostExecute(List<VideoItemBean> list) {
        super.onPostExecute(list);
        if (resultCallback != null) {
            resultCallback.onResultCallback(list);
        }
    }

    private ArrayList<VideoItemBean> getVideoFromCursor(Cursor data) {
        ArrayList<VideoItemBean> list = new ArrayList<>();
        while (data.moveToNext()) {

            int id = data.getInt(data.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
            String path = data.getString(data.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
            String title = data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.TITLE));
            String mimeType = data.getString(data.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE));
            long size = data.getLong(data.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE));
            long duration = data.getInt(data.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));

            VideoItemBean videoItemBean = new VideoItemBean(id, title, path, size, duration, mimeType);
            if (!mimeType.contains(".mov")) {
                list.add(videoItemBean);
            }
        }

        return list;
    }
}
