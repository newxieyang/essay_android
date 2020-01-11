package com.cullen.tatu.utils;

import java.util.TimeZone;

public class DateUtils {


    /***
     * 当天凌晨的毫秒
     */
    private static Long currentDayMills;

    public static Long getCurrentDayMills() {
        if (currentDayMills == null) {

            //当前时间毫秒数
            long current = System.currentTimeMillis();
            //今天零点零分零秒的毫秒数
            currentDayMills = current / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();
        }

        return currentDayMills;

    }
}
