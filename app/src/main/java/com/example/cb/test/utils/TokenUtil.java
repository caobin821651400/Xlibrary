package com.example.cb.test.utils;


/**
 * tkoen
 *
 * @author bin
 * @date 2018/2/9 9:46
 */
public class TokenUtil {

    /**
     * @param source
     * @return
     */
    public static String getSafeStr(String source) {
        long time = System.currentTimeMillis();
        String safeData = Long.toString(time) + getCheckNo(time, source);
        safeData = Base64.encode(safeData.getBytes());
        return safeData.substring(0, safeData.length() - 1);
    }

    private static char getCheckNo(long mark, String source) {
        char[] stcs = source.toCharArray();
        int len = stcs.length;
        int pos = 0;
        long count = 0;
        while (pos != 10 && pos < len) {
            count += stcs[pos++];
        }
        count += mark;
        int mod = (int) count % 122;
        while (mod < '0' || (mod > '9' && mod < 'A') || (mod > 'Z' && mod < 'a')) {
            mod += 24;
        }
        return (char) mod;
    }

}
