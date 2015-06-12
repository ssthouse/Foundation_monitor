package dian.org.monitor.util;

import java.util.Calendar;

/**
 * Created by ssthouse on 2015/6/10.
 */
public class StringUtil {

    public static String getFormatDate(Calendar calendar) {
        String str = "" + calendar.get(Calendar.YEAR) + "-" +
                (calendar.get(Calendar.MONTH)+1) + "-" +
                calendar.get(Calendar.DAY_OF_MONTH);
        return str;
    }
}
