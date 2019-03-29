package com.cb.filepicker.utils;

//import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.cb.filepicker.callback.FileResultCallback;
import com.cb.filepicker.cursors.DocScannerTask;
import com.cb.filepicker.models.Document;


public class MediaStoreHelper {

//  public static void getPhotoDirs(FragmentActivity activity, Bundle args, FileResultCallback<PhotoDirectory> resultCallback) {
//    if(activity.getSupportLoaderManager().getLoader(FilePickerConst.MEDIA_TYPE_IMAGE)!=null)
//      activity.getSupportLoaderManager().restartLoader(FilePickerConst.MEDIA_TYPE_IMAGE, args, new PhotoDirLoaderCallbacks(activity, resultCallback));
//    else
//      activity.getSupportLoaderManager().initLoader(FilePickerConst.MEDIA_TYPE_IMAGE, args, new PhotoDirLoaderCallbacks(activity, resultCallback));
//  }
//
//  public static void getVideoDirs(FragmentActivity activity, Bundle args, FileResultCallback<PhotoDirectory> resultCallback) {
//    if(activity.getSupportLoaderManager().getLoader(FilePickerConst.MEDIA_TYPE_VIDEO)!=null)
//      activity.getSupportLoaderManager().restartLoader(FilePickerConst.MEDIA_TYPE_VIDEO, args, new PhotoDirLoaderCallbacks(activity, resultCallback));
//    else
//      activity.getSupportLoaderManager().initLoader(FilePickerConst.MEDIA_TYPE_VIDEO, args, new PhotoDirLoaderCallbacks(activity, resultCallback));
//  }

    public static void getDocs(FragmentActivity activity, FileResultCallback<Document> fileResultCallback) {
        new DocScannerTask(activity, fileResultCallback).execute();
    }
}