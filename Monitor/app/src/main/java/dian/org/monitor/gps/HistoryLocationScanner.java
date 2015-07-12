package dian.org.monitor.gps;

import android.content.Context;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 这个类可以用来浏览地理位置数据，也就是{@link LocationDB}里面的数据。
 */
public class HistoryLocationScanner {
    private static final String LOG_TAG = "HistoryLocationScanner";

    /**
     * 一个数据库
     */
    private LocationDB db;
    /**
     *
     */
    private long date;

    private long ONE_DAY_TIME = 24 * 3600 * 1000;

    private boolean isDateSet = false;

    public HistoryLocationScanner(Context context) {
        db = LocationDB.getInstance(context);
    }

    /**
     * 将当前数
     * 如果没有调用这个函数就获取数据，则发生错误
     *
     * @param date 将当前数
     */
    /*public void setDate(long date) {
        this.date = getStartTime(date);
        isDateSet = true;
    }*/

    /**
     * 前一天
     *
     * @return 没有调用{@link #setDate}就返回false
     */
    /*public boolean nextDay() {
        if (!isDateSet) {
            return false;
        } else {
            this.date += ONE_DAY_TIME;
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            String msg = sdf.format(new Date(date));
            Log.e(LOG_TAG, msg);
            while (date < System.currentTimeMillis()) {
                if (db.isHaveDataInADay(date)) {
                    msg = sdf.format(new Date(date));
                    Log.e(LOG_TAG, msg);
                    return true;
                }
                date += ONE_DAY_TIME;
            }
            date -= ONE_DAY_TIME;
            return false;
        }
    }*/

    /**
     * 前一天
     *
     * @return 没有调用{@link #setDate}就返回false
     */
    /*public boolean previousDay() {
        if (!isDateSet) {
            return false;
        } else {
            this.date -= ONE_DAY_TIME;
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
            String msg = sdf.format(new Date(date));
            Log.e(LOG_TAG, msg);
            //代表数据库里第一个时间，所在的那天的开始时间
            long firstTime = getStartTime(getTimeOfFirstPoint());
            while (date >= firstTime) {
                if (db.isHaveDataInADay(date)) {
                    msg = sdf.format(new Date(date));
                    Log.e(LOG_TAG, msg);
                    return true;
                }
                date -= ONE_DAY_TIME;
            }
            date += ONE_DAY_TIME;
            return false;
        }
    }*/

    /**
     * 今天
     *
     * @return
     */
    /*public boolean today() {
        setDate(System.currentTimeMillis());
        return db.isHaveDataInADay(date);
    }*/

    /**
     * 返回当前日期的数据
     *
     * @return
     */
    public ArrayList<OneLocationRecord> getLocationRecordData(String patrol_name) {
        return db.getLocationsInaProject(patrol_name);
    }

    /**
     * 获取数据库第一个点的时
     *
     * @return
     */
    /*public long getTimeOfFirstPoint() {
        return db.getTimeOfFirstPoint();
    }
*/
    /**
     * 获取某一天的开始时间
     *
     * @param time 时间戳
     * @return
     */
    /*private long getStartTime(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(time);
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        date = date + " 00:00:00";
        long startTime = 0;
        try {
            startTime = sdf2.parse(date).getTime();
            Log.i(LOG_TAG,""+startTime);
        } catch (ParseException e) {
            Log.e(LOG_TAG, "日期格式有问题");
            e.printStackTrace();
        }
        return startTime;
    }*/

    /*public void setOnDataChangedListener(OnDataChangedListener listener) {
        db.addOnDataChangedListener(listener);
    }*/

    /**
     * 这个监听器负责监听{@link LocationDB}里面的数据是否发生了变化。
     * 数据变化的接口
     */
    /*public interface OnDataChangedListener {
        *//**
         * 如果数据变化，则执行此回调
         *//*
        public void onDataChanged();
    }*/
}
