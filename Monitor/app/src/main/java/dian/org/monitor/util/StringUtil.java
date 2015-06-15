package dian.org.monitor.util;

import java.util.Calendar;

/**
 * Created by ssthouse on 2015/6/10.
 */
public class StringUtil {

    /**
     * 获取用于展示的Data的字符串
     * @param calendar
     * @return
     */
    public static String getFormatDate(Calendar calendar) {
        if(calendar == null){
            calendar = Calendar.getInstance();
        }
        String str = "" + calendar.get(Calendar.YEAR) + "-" +
                (calendar.get(Calendar.MONTH) + 1) + "-" +
                calendar.get(Calendar.DAY_OF_MONTH);
        return str;
    }


    /**
     * 根据给定的时间字符串-----获取calendar
     *
     * @param strTime
     * @return
     */
    public static Calendar getCalendarFromTimeInMiles(String strTime) {
        //如果是空字符串的话---直接传回当前的Calendar
        if (strTime.equals("") || strTime == null) {
            return Calendar.getInstance();
        }
        long timeInMiles = Long.parseLong(strTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMiles);
        return calendar;
    }
}
