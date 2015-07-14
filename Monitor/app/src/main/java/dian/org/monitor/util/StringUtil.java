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
     * 根据时间字符串----获取标准时间形式字符串
     * @param timeInMilesStr
     * @return
     */
    public static String getFormatDate(String timeInMilesStr){
        Calendar calendar = getCalendarFromTimeInMiles(timeInMilesStr);
        return getFormatDate(calendar);
    }


    /**
     * 根据给定的时间字符串-----获取calendar
     *
     * @param timeInMilesStr
     * @return
     */
    public static Calendar getCalendarFromTimeInMiles(String timeInMilesStr) {
        //如果是空字符串的话---直接传回当前的Calendar
        if (timeInMilesStr.equals("") || timeInMilesStr == null) {
            return Calendar.getInstance();
        }
        long timeInMiles = Long.parseLong(timeInMilesStr);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMiles);
        return calendar;
    }
}
