package com.lingkj.common.utils;

/**
 * FormatDuration
 *
 * @author chen yongsong
 * @className FormatDuration
 * @date 2019/7/11 16:36
 */

public class FormatDuration {
    private static int OneSecond = 60;
    private static int OneMinute = 3600;
    private static int OneMillisecond = 60000;
    private static int OneMinuteMillisecond = 3600000;
    private static int Millisecond = 1000;

    private static String getString(int t) {
        String m = "";
        if (t > 0) {
            if (t < 10) {
                m = "0" + t;
            } else {
                m = t + "";
            }
        } else {
            m = "00";
        }
        return m;
    }

    private static String getStringByLong(Long t) {
        String m = "";
        if (t > 0) {
            if (t < 10) {
                m = "0" + t;
            } else {
                m = t + "";
            }
        } else {
            m = "00";
        }
        return m;
    }

    /**
     * @param t 毫秒
     * @return
     * @author song
     */
    public static String format(int t) {
        if (t < OneMillisecond) {
            return (t % OneMillisecond) / Millisecond + "秒";
        } else if ((t >= OneMillisecond) && (t < OneMinuteMillisecond)) {
            return getString((t % OneMinuteMillisecond) / OneMillisecond) + ":" + getString((t % OneMillisecond) / Millisecond);
        } else {
            return getString(t / OneMinuteMillisecond) + ":" + getString((t % OneMinuteMillisecond) / OneMillisecond) + ":" + getString((t % OneMillisecond) / Millisecond);
        }
    }

    public static String formatMinuteBySecond(Long t) {
        if (t < OneSecond) {
            return (t % 600) + "秒";
        } else {
            return getStringByLong(t / OneMinute) + ":" + getStringByLong((t % OneMinute) / OneSecond);
        }
    }


    public static void main(String[] args) {//测试
        System.out.println(FormatDuration.formatMinuteBySecond(24  * 60L));
    }

}
