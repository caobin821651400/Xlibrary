package cn.sccl.xlibrary.utils;

import android.app.AppOpsManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.lang.reflect.Method;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2020/8/7 21:40
 * @Desc :设备相关工具类
 * ====================================================
 */
public class XDeviceUtils {

    private static String deviceName = android.os.Build.BRAND;

   public enum DEVICE_NAME {
        HUAWEI,
        XIAOMI,
        VIVO,
        MEIZU,
        SAMSUNG,
        OPPO,
        UNLNOW
    }


    /**
     * 获取厂商的rom名称
     *
     * @return
     */
    public static DEVICE_NAME getRomName() {
        String lower = deviceName.toLowerCase();
        switch (lower) {
            case "xiaomi":
                return DEVICE_NAME.XIAOMI;
            case "huawei":
                return DEVICE_NAME.HUAWEI;
            case "vivo":
                return DEVICE_NAME.VIVO;
            case "meizu":
                return DEVICE_NAME.MEIZU;
            case "samsung":
                return DEVICE_NAME.SAMSUNG;
            case "oppo":
                return DEVICE_NAME.OPPO;
            default:
                return DEVICE_NAME.UNLNOW;
        }
    }

    /**
     * 判断是否打开后台启动新界面权限
     *
     * @param context
     * @return
     */
    public static boolean isHasBgStartActivityPermission(Context context) {
        if (getRomName() == DEVICE_NAME.XIAOMI) {
            return getXiaoMiBgStartActivityPermissionStatus(context);
        } else if (getRomName() == DEVICE_NAME.VIVO) {
            return getVivoBgStartActivityPermissionStatus(context);
        }
        return false;
    }

    /**
     * 小米后台弹出界面检测方法
     *
     * @param context
     * @return
     */
    public static boolean getXiaoMiBgStartActivityPermissionStatus(Context context) {
        try {
            AppOpsManager ops = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                ops = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            }
            int op = 10021;
            Method method = ops.getClass().getMethod("checkOpNoThrow",
                    new Class[]{int.class, int.class, String.class});
            Integer result = (Integer) method.invoke(ops, op, android.os.Process.myUid(),
                    context.getPackageName());
            return result == AppOpsManager.MODE_ALLOWED;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断vivo后台弹出界面 1未开启 0开启
     *
     * @param context
     * @return
     */
    public static boolean getVivoBgStartActivityPermissionStatus(Context context) {
        try {
            String packageName = context.getPackageName();
            Uri uri2 = Uri.parse("content://com.vivo.permissionmanager.provider.permission/start_bg_activity");
            String selection = "pkgname = ?";
            String[] selectionArgs = new String[]{packageName};

            Cursor cursor = context
                    .getContentResolver()
                    .query(uri2, null, selection, selectionArgs, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    int currentmode = cursor.getInt(cursor.getColumnIndex("currentstate"));
                    cursor.close();
                    return currentmode == 0;
                } else {
                    cursor.close();
                    return false;
                }
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            return false;
        }
        return false;
    }

//    /**
//     * 判断是否有悬浮窗权限
//     *
//     * @param context
//     */
//    private void aaa(Context context) {
//        //判断当前系统版本
//        if (Build.VERSION.SDK_INT >= 23) {
//            //判断权限是否已经申请过了（加上这个判断，则使用的悬浮窗的时候；如果权限已经申请则不再跳转到权限开启界面）
//            if (!Settings.canDrawOverlays(this)) {
//                //申请权限
//                Intent intent2 = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                contextstartActivityForResult(intent2, 1);
//            } else {
//
//            }
//        } else {
//
//        }
//        System.out.println("Build.VERSION.SDK_INT::::" + Build.VERSION.SDK_INT);
//
//    }
}
