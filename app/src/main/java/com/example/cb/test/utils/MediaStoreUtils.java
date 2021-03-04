package com.example.cb.test.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 该工具类主要用于APP对 公共存储目录下 照片/视频/音频 这类媒体文件的操作(保存和取出，通过 MediaStore API 进行操作)
 * 即允许用户看到
 * 适配 Android Q 版本，重点：AndroidQ中共享目录不支持文件路径访问，只能通过uri方式访问
 * <p>
 * 外部存储目录，创建和访问应用自己的文件夹不需要存储权限，创建和访问其他应用的 media 文件需要存储权限
 * 公有目录：Download、Documents、Pictures 、DCIM、Movies、Music、Ringtones等
 * 地址：/storage/emulated/0/Downloads(Pictures)，无法直接使用路径访问公共目录文件
 * 公有目录下的文件不会跟随APP卸载而删除。
 * <p>
 * APP私有目录，读写均不需要存储权限
 * 地址：/storage/emulated/0/Android/data/包名/files
 * 私有目录存放app的私有文件，会随着App的卸载而删除。
 * <p>
 * 核心思路：通过将最终获得的数据流inputsteam或者FileDescriptor等方式，写入到ContentValues和MediaStore选好保存类型和形式中去，以实现保存
 *
 * @author xuetaotao
 * 2020.12.01
 */
public class MediaStoreUtils {
//
//    public static final String IMG_PUBLIC_DCIM_DIR = Environment.DIRECTORY_DCIM + File.separator + "com.jlpay.partner";///DCIM/com.jlpay.partner
//    public static final String IMG_PUBLIC_PIC_DIR = Environment.DIRECTORY_PICTURES + File.separator + "com.jlpay.partner";///Pictures/com.jlpay.partner
//    public static final String DOWNLOADS_PUBLIC_DIR = Environment.DIRECTORY_DOWNLOADS + File.separator + "com.jlpay.partner";//Download/com.jlpay.partner
//
//    /**
//     * 保存图片到相册，即允许用户看到的图片
//     * 存储在 DCIM/ 或 Pictures/ 目录中，系统将这些文件添加到 MediaStore.Images 表格中
//     * <p>
//     * 注：存储在外部APP私有目录下的图片能否用context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + dir + "/" + filename)))
//     * 的方式显示在相册中？   答：不可以，Android Q版本使用，将图片存放到沙盒文件内，图库无法刷新，无法显示
//     * 可以通过下面的方法以复制的形式copy到外部公共目录下，进而实现相册中的显示，但是不太好
//     * <p>
//     * 注：APP自己保存在外部共享目录下的图片，自己具有删除权限，但是不建议删除，因为部分手机(如华为)会弹出删除通知
//     *
//     * @param baseActivity 上下文环境
//     * @param inputStream  要保存到本地外部公共存储目录的输入流
//     */
//    public static void imgInsert(BaseActivity baseActivity, InputStream inputStream) {
//        //照片、视频、音频这类媒体文件。使用 MediaStore 访问，访问其他应用的媒体文件时需要 READ_EXTERNAL_STORAGE 权限。
//        String finalFileName = "IMG" + System.currentTimeMillis() + ".jpg";
//        //在共享目录下创建和访问应用自己的文件夹，可以不需要存储权限；创建和访问其他应用的 media 文件需要存储权限
//        //这里为避免意外情况，采取保守策略
//        PermissionUtils.getStoragePermission(baseActivity, () -> {
//            ContentResolver contentResolver = baseActivity.getApplicationContext().getContentResolver();
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, finalFileName);//设置带扩展名的文件名，如：IMG1024.JPG，必须
//            contentValues.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis());//设置文件第一次被添加的时间，非必须
//            contentValues.put(MediaStore.Images.ImageColumns.MIME_TYPE, getImgMimeType(finalFileName));//设置文件类型，如：IMG1024，必须
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                //AndroidQ 中不再使用DATA字段，而用RELATIVE_PATH代替
//                //RELATIVE_PATH是相对路径不是绝对路径，DCIM是系统文件夹
//                contentValues.put(MediaStore.Images.ImageColumns.RELATIVE_PATH, IMG_PUBLIC_DCIM_DIR);//文件存储的相对路径，必须
//            } else {
//                //AndroidQ以下版本，直接使用外部公共存储目录下的图片绝对路径，不能去掉，因为上面的要求API>=29，在10.0以下设备中下一步insert产生的Uri会为空
//                String fileDir = Constants.external_storage_dir + File.separator + IMG_PUBLIC_DCIM_DIR;
//                if (createDirs(fileDir)) {
//                    String filepath = fileDir + File.separator + finalFileName;
//                    contentValues.put(MediaStore.Images.ImageColumns.DATA, filepath);//文件存储的绝对路径，必须
//                }
//            }
//            //执行insert操作，向系统文件夹中添加文件，若生成了uri，则表示该文件添加成功，使用流将内容写入该uri中即可
//            //这一步会在DCIM目录下创建partner文件夹(没有该文件夹时)
//            Uri uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//            if (uri != null) {
//                try {
//                    OutputStream outputStream = contentResolver.openOutputStream(uri);
//                    if (outputStream != null) {
//                        streamOperator(inputStream, outputStream);
//                        ToastUtils.showToastApplication(baseActivity, "图片已成功保存至手机相册");
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//    }
//
//
//    /**
//     * 將APP私有目录下的图片插入到共享目录，进而显示到相册(插入到共享目录后相册会自动刷新，不需要通知)
//     *
//     * @param baseActivity 上下文环境
//     * @param filePath     私有目录下的图片路径
//     */
//    public static void imgInsert(BaseActivity baseActivity, @NonNull String filePath) {
//        PermissionUtils.getStoragePermission(baseActivity, () -> {
//            File file = new File(filePath);
//            try {
//                FileInputStream fileInputStream = new FileInputStream(file);
//                imgInsert(baseActivity, fileInputStream);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//
//    /**
//     * 将公共共享目录下的图片转为输入流
//     *
//     * @param uri 将公共共享目录下的图片Uri
//     * @return 输入流
//     */
//    public static InputStream getImgStream(@NonNull Uri uri) {
//        return getImgStream(AppUtils.getContext(), uri);
//    }
//
//    public static InputStream getImgStream(Context context, @NonNull Uri uri) {
//        ContentResolver contentResolver = context.getContentResolver();
//        try {
//            return contentResolver.openInputStream(uri);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    /**
//     * 将公共共享目录下的图片转为输入流
//     * 通过文件描述符方式
//     *
//     * @param uri 将公共共享目录下的图片Uri
//     * @return 输入流
//     */
//    public static InputStream getImgStream2(Uri uri) {
//        if (uri == null) {
//            return null;
//        }
//        ContentResolver contentResolver = AppUtils.getContext().getContentResolver();
//        try {
//            ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
//            if (parcelFileDescriptor != null) {
//                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
//                return new FileInputStream(fileDescriptor);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//
//    /**
//     * 将公共共享目录下的图片复制到 APP外部私有目录下
//     * 注：这样复制后，在压缩上传完后，建议把复制后的原图删除，但是切勿删除真正的原图！！
//     * <p>
//     * PS：不建议使用，因为 Android 11 又恢复了使用直接文件路径访问访问媒体文件，不再需要这一步，仅是AndroidQ的妥协
//     *
//     * @return APP私有目录下的图片存储路径(Android Q后只有APP私有目录的文件路径可以访问到文件, 共享目录只能通过Uri访问)
//     */
//    @Deprecated
//    public static String copyImgUriToExternalFilesPath(Uri uri) {
//        if (uri == null) {
//            return null;
//        }
//        createDirs(Constants.IMAGE_SAVE_DIR);
//        String fileName = "IMG" + System.currentTimeMillis() + ".jpg";
//        File file = new File(Constants.IMAGE_SAVE_DIR, fileName);
//        InputStream inputStream = getImgStream(uri);
//        try {
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            streamOperator(inputStream, fileOutputStream);
//            return file.getAbsolutePath();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//
//    /**
//     * 将共享目录下的图片/音频/视频 复制到APP外部私有目录下
//     *
//     * @param uri      共享目录下的文件Uri
//     * @param fileName 带文件扩展名的文件名
//     * @return true:成功
//     */
//    public static boolean copyUriToExternalFilesPath(@NonNull Context context, @NonNull Uri uri, @NonNull String fileName) {
//        if (TextUtils.isEmpty(fileName)) {
//            return false;
//        }
//        createDirs(Constants.FILE_SAVE_DIR);
//        File file = new File(Constants.FILE_SAVE_DIR, fileName);
//        ContentResolver contentResolver = context.getContentResolver();
//        try {
//            InputStream inputStream = contentResolver.openInputStream(uri);
//            FileOutputStream fileOutputStream = new FileOutputStream(file);
//            streamOperator(inputStream, fileOutputStream);
//            return true;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//
//    /**
//     * 通过Uri获取Bitmap
//     *
//     * @param context 上下文环境
//     * @param uri     图片uri
//     * @return Bitmap
//     */
//    public static Bitmap getBitmapFromUri(Context context, Uri uri) {
//        //方式一
////        ContentResolver contentResolver = context.getContentResolver();
////        try {
////            ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
////            if (parcelFileDescriptor != null) {
////                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
////                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
////                parcelFileDescriptor.close();
////                return bitmap;
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////        return null;
//
//        //方式二
//        InputStream inputStream = getImgStream(context, uri);
//        return BitmapFactory.decodeStream(inputStream);
//    }
//
//
//    /**
//     * 流操作
//     *
//     * @param inputStream  输入流
//     * @param outputStream 输出流
//     */
//    private static void streamOperator(InputStream inputStream, OutputStream outputStream) {
//        if (inputStream == null || outputStream == null) {
//            return;
//        }
//        byte[] buffer = new byte[1024];
//        int readLength;
//        try {
//            while ((readLength = inputStream.read(buffer)) != -1) {
//                outputStream.write(buffer, 0, readLength);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                outputStream.flush();
//                outputStream.close();
//                inputStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//
//    /**
//     * 创建文件目录
//     *
//     * @param dir 目录名
//     * @return 创建结果
//     */
//    private static boolean createDirs(String dir) {
//        if (TextUtils.isEmpty(dir)) {
//            return false;
//        }
//        File dirFile = new File(dir);
//        if (!dirFile.exists()) {
//            return dirFile.mkdirs();
//        }
//        return true;
//    }
//
//
//    /**
//     * 获取图片的 mime_type
//     *
//     * @param filePath 图片的文件路径
//     * @return 图片的 mime_type
//     */
//    private static String getImgMimeType(String filePath) {
//        String lowerPath = filePath.toLowerCase();
//        if (lowerPath.endsWith("jpg") || lowerPath.endsWith("jpeg")) {
//            return "image/jpeg";
//        } else if (lowerPath.endsWith("png")) {
//            return "image/png";
//        } else if (lowerPath.endsWith("gif")) {
//            return "image/gif";
//        }
//        return "image/jpeg";
//    }
//
//
//    /**
//     * 媒体类资源
//     * 插入时初始化公共字段
//     *
//     * @param fileName 文件名（带文件名后缀，如：.jpg），非文件路径地址
//     */
//    private static ContentValues commonMediaColumn(String fileName) {
//        ContentValues contentValues = new ContentValues();
////        contentValues.put(MediaStore.MediaColumns.TITLE, file.getName());//不带扩展名的文件名，如：IMG1024
//        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);//带扩展名的文件名，如：IMG1024.JPG
////        contentValues.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());//文件绝对路径，已过期
////        contentValues.put(MediaStore.MediaColumns.SIZE, file.length());//文件大小，单位为 byte
//        contentValues.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis());//文件第一次被添加的时间
//        return contentValues;
//    }
//
//
//
//    /**
//     * 下载的文件：
//     * 存储在 storage/emulated/0/Download/com.jlpay.partner 目录中
//     * 在搭载 Android 10 (API级别29)及更高的设备上，这些文件存储在 MediaStore.Downloads 表格中，此表格在 Android 9 (API级别28)及更低版本中不可用
//     *
//     * @param baseActivity 上下文环境
//     * @param inputStream  下载文件的输入流
//     * @param fileName     带扩展名的文件名
//     */
//    public static void downloadInsert(BaseActivity baseActivity, InputStream inputStream, String fileName) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//            ContentResolver contentResolver = baseActivity.getContentResolver();
//            //Android 10在MediaStore中新增了一种Downloads集合，专门用于执行文件下载操作
//            ContentValues contentValues = new ContentValues();
//            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);//带扩展名的文件名，如：IMG1024.JPG，必须
//            contentValues.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis());//文件第一次被添加的时间，非必须
//            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, DOWNLOADS_PUBLIC_DIR);//文件存储的相对路径，必须
//            //执行insert操作，向系统文件夹中添加文件，若生成了uri，则表示该文件添加成功，使用流将内容写入该uri中即可
//            //这一步会在Download目录下创建partner文件夹(没有该文件夹时)
//            //MediaStore.Downloads是Android 10中新增的API，至于Android 9及以下的系统版本，仍然使用之前的代码来进行文件下载
//            Uri uri = contentResolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);
//            if (uri != null) {
//                try {
//                    OutputStream outputStream = contentResolver.openOutputStream(uri);
//                    if (outputStream != null) {
//                        streamOperator(inputStream, outputStream);
//                        ToastUtils.showToastApplication(baseActivity, "文件下载完成");
//                    }
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        } else {
//            //AndroidQ以下版本，直接使用外部公共存储目录下的文件绝对路径
//            String fileDir = Constants.external_storage_dir + File.separator + DOWNLOADS_PUBLIC_DIR;
//            if (createDirs(fileDir)) {
//                String filePath = fileDir + File.separator + fileName;
//                try {
//                    FileOutputStream fileOutputStream = new FileOutputStream(filePath);
//                    streamOperator(inputStream, fileOutputStream);
//                    ToastUtils.showToastApplication(baseActivity, "文件下载完成");
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}