package cn.sccl.xlibrary.utils;

import java.text.DecimalFormat;

/**
 * 用来计算显示的时间是多久之前的！
 */

public class XFormatTimeUtils {

    private XFormatTimeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 格式化友好的时间差显示方式
     *
     * @param millis
     * @return
     */
    public static String getTimeSpanByNow(String millis) {
        return getTimeSpanByNow(XDateUtils.string2Millis(millis));
    }

    /**
     * 格式化友好的时间差显示方式
     *
     * @param millis
     * @param pattern
     * @return
     */
    public static String getTimeSpanByNow(String millis, String pattern) {
        return getTimeSpanByNow(XDateUtils.string2Millis(millis, pattern));
    }

    /**
     * 格式化友好的时间差显示方式
     *
     * @param millis 开始时间戳
     * @return
     */
    public static String getTimeSpanByNow(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        if (span < 1000) {
            return "刚刚";
        } else if (span < XDateUtils.MIN) {
            return String.format("%d秒前", span / XDateUtils.SEC);
        } else if (span < XDateUtils.HOUR) {
            return String.format("%d分钟前", span / XDateUtils.MIN);
        } else if (span < XDateUtils.DAY) {
            return String.format("%d小时前", span / XDateUtils.HOUR);
        } else if (span < XDateUtils.WEEK) {
            return String.format("%d天前", span / XDateUtils.DAY);
        } else if (span < XDateUtils.MONTH) {
            return String.format("%d周前", span / XDateUtils.WEEK);
        } else if (span < XDateUtils.YEAR) {
            return String.format("%d月前", span / XDateUtils.MONTH);
        } else {
            return String.format("%d年前", span / XDateUtils.YEAR);
        }
    }


    /**
     * 格式化友好的时间差显示方式
     *
     * @param millis 开始时间戳
     * @return
     */
    public static String getTimeSpanByNow2(long millis) {
        long now = System.currentTimeMillis();
        long span = now - millis;
        long day = span / XDateUtils.DAY;
        if (day == 0) {// 今天
            long hour = span / XDateUtils.HOUR;
            if (hour <= 4) {
                return String.format("凌晨%tR", millis);
            } else if (hour > 4 && hour <= 6) {
                return String.format("早上%tR", millis);
            } else if (hour > 6 && hour <= 11) {
                return String.format("上午%tR", millis);
            } else if (hour > 11 && hour <= 13) {
                return String.format("中午%tR", millis);
            } else if (hour > 13 && hour <= 18) {
                return String.format("下午%tR", millis);
            } else if (hour > 18 && hour <= 19) {
                return String.format("傍晚%tR", millis);
            } else if (hour > 19 && hour <= 24) {
                return String.format("晚上%tR", millis);
            } else {
                return String.format("今天%tR", millis);
            }
        } else if (day == 1) {// 昨天
            return String.format("昨天%tR", millis);
        } else if (day == 2) {// 前天
            return String.format("前天%tR", millis);
        } else {
            return String.format("%tF", millis);
        }
    }

    /**
     * 将秒转化为 HH:mm:ss 的格式
     *
     * @param time 秒
     * @return
     */
    public static String formatTime2Hour(int time) {
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String hh = decimalFormat.format(time / 3600);
        String mm = decimalFormat.format(time % 3600 / 60);
        String ss = decimalFormat.format(time % 60);
        return hh + ":" + mm + ":" + ss;
    }

    /**
     * 将秒转化为 dd:HH:mm:ss 的格式
     *
     * @param time 秒
     * @return
     */
    public static String formatTime2Day(long time) {
        long diff = Math.abs(time);
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        day = diff / (24 * 60 * 60);
        hour = (diff / (60 * 60) - day * 24);
        min = ((diff / (60)) - day * 24 * 60 - hour * 60);
        sec = (diff - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        DecimalFormat decimalFormat = new DecimalFormat("00");
        String dd = decimalFormat.format(day);
        String hh = decimalFormat.format(hour);
        String mm = decimalFormat.format(min);
        String ss = decimalFormat.format(sec);
        return dd + "天" + hh + "小时" + mm + "分" + ss + "秒";
    }

}
