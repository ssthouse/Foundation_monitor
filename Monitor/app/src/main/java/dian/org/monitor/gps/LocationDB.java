package dian.org.monitor.gps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;


public class LocationDB extends SQLiteOpenHelper {
    private static final String LOG_TAG = "LocationDB";

    //数据库名---表名---数据名---
    private static final String DATABASE_NAME = "locationtracker.db";
    private static final String TABLE_LOCATION = "location";
    private static final String TIME = "time";
    private static final String DATE = "datetime";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String IS_FIRST_OF_TIMELINE = "is_first_of_timeline";
    private static final String PATROL_ID="patrol";
    //新建一个表
    String sql = "create table if not exists " + TABLE_LOCATION +
            " (_id integer primary key autoincrement, " + LONGITUDE + " double, " + LATITUDE + " double, " + TIME + " long, " +
            DATE + " varchar(50), " + PATROL_ID + " varchar(50), " + IS_FIRST_OF_TIMELINE + " integer)";
    /**
     * 两条线之间的最小时间间隔，单位：毫秒
     */
    private static final long MAX_TIME = 10000;
    /**
     * 判断两个不同点之间的最长间距，单位：米
     */
    private static final int MIN_DISTANCE = 10;
    private static double LastDistance=20;
    /**
     * 单例
     */
    private static LocationDB instance = null;

    /**
     * 监听器的列表
     */
    /*private ArrayList<HistoryLocationScanner.OnDataChangedListener> mOnDataChangedListeners =
            new ArrayList<>();*/

    private SQLiteDatabase db;

    /**
     * 私有的构造方法
     *
     * @param context
     */
    private LocationDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /**
     * 获取唯一的单例
     *
     * @param context
     * @return
     */
    public static LocationDB getInstance(Context context) {
        if (instance == null) {
            instance = new LocationDB(context);
        }
        return instance;
    }

    /*
     * 添加监听器
     *
     * @param listener
     */
   /* public void addOnDataChangedListener(HistoryLocationScanner.OnDataChangedListener listener) {
        if (listener != null) {
            mOnDataChangedListeners.add(listener);
        }
    }*/

    private void initDataBase() {
        if (db == null) {
            db = getWritableDatabase();
        }
    }

    /**
     * 如果不存在创建历史Location的table
     */
    private void creatHistoryLocationTable() {
        //初始化db
        initDataBase();
        // sqlite 没有boolean类型的数据，IS_FIRST_OF_TIMELINE 这个字段 1表示true，0表示false
        db.execSQL(sql);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
    ////////////////////////////////////////////////////////////////////////////////////////////
    /// 对外接口
    /////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 记录一个位置信息
     *
     * @param time      位置的生成时间
     * @param longitude 经度
     * @param latitude  纬度
     */
    public void recordLocation(long time, double longitude, double latitude, String patrol_name, String data) {
        //如果不存在就创建table
        creatHistoryLocationTable();
        Cursor cursor = db.query(TABLE_LOCATION, null, null, null, null, null, TIME + " desc");
        // 数据库里面已经记录了数据
        if (cursor != null && cursor.moveToFirst()) {
            long lastTime = cursor.getLong(cursor.getColumnIndex(TIME));
            // 每隔一段时间记录一个点
            if (time - lastTime> MAX_TIME) {
                //超过最小距离，记录一个点
                Double NowDistance=Distance(longitude,latitude,
                        cursor.getDouble(cursor.getColumnIndex(LONGITUDE)),
                        cursor.getDouble(cursor.getColumnIndex(LATITUDE)));
                if (NowDistance> MIN_DISTANCE && NowDistance<5*LastDistance)
                {
                        LastDistance=Distance(longitude,latitude,
                                cursor.getDouble(cursor.getColumnIndex(LONGITUDE)),
                                cursor.getDouble(cursor.getColumnIndex(LATITUDE)));
                        Log.i(LOG_TAG, "记录了一条数据，记录信息:" + longitude + "   " + latitude + "   " + patrol_name + "   " + data);
                        recordNewLocation(longitude, latitude, false, patrol_name, data, time);
                }
            }
            cursor.close();
        } else {
            //如果数据库为空---创建新的Location数据
            recordNewLocation(longitude, latitude, true, patrol_name, data, time);
            Log.i(LOG_TAG, "我创建了新的数据库，记录信息:" + longitude + "   " + latitude + "   " + patrol_name + "   " + data);
        }
    }

    /**
     * 记录一条新的时间线上的第一个点
     *
     * @param date      位置的生成时间
     * @param longitude 经度
     * @param latitude  纬度
     * @param isFirst   是否为一条线的第一个点
     */
    private void recordNewLocation(final double longitude, final double latitude, final Boolean isFirst,final String Patrol_name,final String date, final long time) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                return TrackerUtils.getAddressByPoint(longitude, latitude);
            }

            @Override
            protected void onPostExecute(String addrName) {
                super.onPostExecute(addrName);
//               DebugDataBuilder dataBuilder = new DebugDataBuilder();
                if (addrName == null) {
//                    dataBuilder.desc("获取位置  失败")
//                            .name("经度").value(longitude)
//                            .name("纬度").value(latitude)
//                            .end();
                } else {
//                    dataBuilder.desc("获取位置成功")
//                            .name("经度").value(longitude)
//                            .name("纬度").value(latitude)
//                            .name("地名").value(addrName)
//                            .end();
                }
                ContentValues cv = new ContentValues();
                int iIsFirst = isFirst ? 1 : 0;
                cv.put(DATE,date);
                cv.put(LONGITUDE, longitude);
                cv.put(LATITUDE, latitude);
                cv.put(IS_FIRST_OF_TIMELINE, iIsFirst);
                cv.put(PATROL_ID,Patrol_name);
                cv.put(TIME,time);
                db.insert(TABLE_LOCATION, null, cv);
                // 通知数据库变化·
                /*for (HistoryLocationScanner.OnDataChangedListener listener : mOnDataChangedListeners) {
                    listener.onDataChanged();
                }*/
//                Log.e(LOG_TAG, dataBuilder);
            }
        }.execute();
    }

    /**
     * 更新一个位置点的持续时间
     *
     * @param cursor
     */
    /*private void updateLocation(Cursor cursor) {
        ContentValues cv = new ContentValues();
        cv.put(DURING_TIME, System.currentTimeMillis() - cursor.getLong(cursor.getColumnIndex(TIME)));
        db.update(TABLE_HISTORY_LOCATION, cv, "_id = ?", new String[]{"" +
                cursor.getInt(cursor.getColumnIndex("_id"))});
    }*/


    /**
     *
     * @param oneLocationRecord
     */
    public void recordLocation(OneLocationRecord oneLocationRecord) {
        recordLocation(oneLocationRecord.getTime(), oneLocationRecord.getLongitude(),
                oneLocationRecord.getLatitude(),oneLocationRecord.getPatrol_name(),oneLocationRecord.getDate());
    }

    /**
     * 查询某一巡查工程的路径
     *
     * @param patrol_name 同一工程的名字
     * @return
     */
    public ArrayList<OneLocationRecord> getLocationsInaProject(String patrol_name) {
        creatHistoryLocationTable();
        ArrayList<OneLocationRecord> records = new ArrayList<OneLocationRecord>();
        Cursor cursor = db.query(TABLE_LOCATION, null,PATROL_ID + "=" + "'"+patrol_name+"'", null, null, null, DATE);
        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                OneLocationRecord record = new OneLocationRecord();
                record.setLongitude(cursor.getDouble(cursor.getColumnIndex(LONGITUDE)));
                record.setLatitude(cursor.getDouble(cursor.getColumnIndex(LATITUDE)));
                record.setDate(cursor.getString(cursor.getColumnIndex(DATE)));
                record.setPatrol_name(cursor.getString(cursor.getColumnIndex(PATROL_ID)));
                record.set_first_of_line(cursor.getInt(cursor.getColumnIndex(IS_FIRST_OF_TIMELINE)) == 1);
                records.add(record);
            }
            cursor.close();
        }
        return records;
    }

    /**
     * 调用百度API计算两点间距离
     * @param longitude
     * @param latitude
     * @param nextlongitude
     * @param nextlatitude
     * @return
     */
    private double Distance(double longitude, double latitude,double nextlongitude, double nextlatitude){
        double distance= DistanceUtil.getDistance(new LatLng(latitude,longitude),new LatLng(nextlatitude,nextlongitude));
        return distance;
    }
    /**
     * 当天是否有数据
     *
     * @param time 一天的起始时间
     * @retur
     */
    /*public boolean isHaveDataInADay(long time) {
        creatHistoryLocationTable();
        long nextDayTime = time + 24 * 3600 * 1000;
        Cursor cursor = db.query(TABLE_LOCATION, null, TIME + " > " + time + " AND " + TIME + " < " + nextDayTime, null, null, null, TIME);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            return false;
        }
    }*/

    /**
     * 获取数据库第一个点的时间
     *
     * @return
     */
    /*public long getTimeOfFirstPoint() {
        creatHistoryLocationTable();
        Cursor cursor = db.query(TABLE_LOCATION, null, null, null, null, null, "_id asc");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getLong(cursor.getColumnIndex(TIME));
        } else {
            return Long.MAX_VALUE;
        }
    }*/
}
