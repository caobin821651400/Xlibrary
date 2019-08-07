package me.devilsen.czxing.util;

import android.content.Context;
import android.os.Vibrator;

/**
 * ====================================================
 *
 * @User :caobin
 * @Date :2019/8/7 10:37
 * @Desc :震动工具类
 * ====================================================
 */
public class VibratorUtils {

    public static void startOneVibrator(Context context) {
        try {
            long[] array = {30L, 400L};
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(array, -1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
