package dian.org.monitor.gps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import java.util.ArrayList;

public class LocationDB extends SQLiteOpenHelper {
    private static final String LOG_TAG = "LocationDB";

    //数据库名---表名---数据名---
    private static final String DATABASE_NAME = "location.db";
    private static final String TABLE_HISTORY_LOCATION = "history_location";
    private static final String TIME = "time";
    private static final String LATITUDE = "latitude";
    private static final String LONGITUDE = "longitude";
    private static final String ADDRESS = "address";
    private static final String DURING_TIME = "during_time";
    private static final String IS_FIRST_OF_TIMELINE = "is_first_of_timeline";

    /**
     * 两条线之间的最小时间间隔，单位：毫秒
     */
    private static final long MAX_TIME = 30 * 60 * 1000;
    /**
     * 判断两个不同点之间的最长间距，单位：米
     */
    private static final int MIN_DISTANCE = 300;
    /**
     * 单例
     */
    private static LocationDB instance = null;

    /**
     * 监听器的列表
     */
    private ArrayList<HistoryLocationScanner.OnDataChangedListener> mOnDataChangedListeners =
            new ArrayList<>();

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

    /**
     * 添加监听器
     *
     * @param listener
     */
    public void addOnDataChangedListener(HistoryLocationScanner.OnDataChangedListener listener) {
        if (listener != null) {
            mOnDataChangedListeners.add(listener);
        }
    }

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
        String createHistoryLocation = "create table if not exists " + TABLE_HISTORY_LOCATION +
                " (_id integer primary key autoincrement, " + LONGITUDE + " double, " + LATITUDE + " double, " + TIME + " long, " +
                ADDRESS + " varchar(50), " + DURING_TIME + " long, " + IS_FIRST_OF_TIMELINE + " integer)";
        db.execSQL(createHistoryLocation);
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
    public void recordLocation(long time, double longitude, double latitude) {
        //如果不存在就创建table
        creatHistoryLocationTable();
        Cursor cursor = db.query(TABLE_HISTORY_LOCATION, null, null, null, null, null, TIME + " desc");
        // 数据库里面已经记录了数据
        if (cursor != null && cursor.moveToFirst()) {
            long lastTime = cursor.getLong(cursor.getColumnIndex(TIME));
            long duration = cursor.getLong(cursor.getColumnIndex(DURING_TIME));
            // 超过最大时间间隔，标志为一条新的线的开始
            if (time - lastTime - duration > MAX_TIME) {
                recordNewLocation(time, longitude, latitude, true);
            } else {
                OneLocationRecord lastPoint = new OneLocationRecord();
                lastPoint.setLongitude(cursor.getDouble(cursor.getColumnIndex(LONGITUDE)));
                lastPoint.setLatitude(cursor.getDouble(cursor.getColumnIndex(LATITUDE)));
                OneLocationRecord currentPoint = new OneLocationRecord();
                currentPoint.setLongitude(longitude);
                currentPoint.setLatitude(latitude);
                if (TrackerUtils.isLocANearbyLocB(lastPoint, currentPoint, MIN_DISTANCE)) {
                    updateLocation(cursor);
                } else {
                    recordNewLocation(time, longitude, latitude, false);
                }
            }
            cursor.close();
        } else {
            //如果数据库为空---创建新的Location数据
            recordNewLocation(time, longitude, latitude, true);
        }
    }

    /**
     * 记录一条新的时间线上的第一个点
     *
     * @param time      位置的生成时间
     * @param longitude 经度
     * @param latitude  纬度
     * @param isFirst   是否为一条线的第一个点
     */
    private void recordNewLocation(final long time, final double longitude, final double latitude, final Boolean isFirst) {
        new AsyncTask<Void, Void, String>() {

            @Override
            protected String doInBackground(Void... params) {
                return TrackerUtils.getAddressByPoint(longitude, latitude);
            }

            @Override
            protected void onPostExecute(String addrName) {
                super.onPostExecute(addrName);
//                DebugDataBuilder dataBuilder = new DebugDataBuilder();
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
                cv.put(TIME, time);
                cv.put(LONGITUDE, longitude);
                cv.put(LATITUDE, latitude);
                cv.put(ADDRESS, addrName);
                cv.put(DURING_TIME, 3 * 60 * 1000);
                cv.put(IS_FIRST_OF_TIMELINE, iIsFirst);
                db.insert(TABLE_HISTORY_LOCATION, null, cv);
                // 通知数据库变化·
                for (HistoryLocationScanner.OnDataChangedListener listener : mOnDataChangedListeners) {
                    listener.onDataChanged();
                }
//                Log.e(LOG_TAG, dataBuilder);
            }
        }.execute();
    }

    /**
     * 更新一个位置点的持续时间
     *
     * @param cursor
     */
    private void updateLocation(Cursor cursor) {
        ContentValues cv = new ContentValues();
        cv.put(DURING_TIME, System.currentTimeMillis() - cursor.getLong(cursor.getColumnIndex(TIME)));
        db.update(TABLE_HISTORY_LOCATION, cv, "_id = ?", new String[]{"" +
                cursor.getInt(cursor.getColumnIndex("_id"))});
    }

    /**
     * @see #recordLocation(long, double, double)
     */
    public void recordLocation(OneLocationRecord oneLocationRecord) {
        recordLocation(oneLocationRecord.getTime(), oneLocationRecord.getLongitude(),
                oneLocationRecord.getLatitude());
    }

    /**
     * 生成求救时发送的位置信息.
     * 获取某一天的历史位置，作为求救时发送的历史位置.
     *
     * @param time 一天的起始时间
     * @return
     */
    public ArrayList<OneLocationRecord> getLocationsInADay(long time) {
        creatHistoryLocationTable();
        ArrayList<OneLocationRecord> records = new ArrayList<OneLocationRecord>();
        long nextDayTime = time + 24 * 3600 * 1000;
        Cursor cursor = db.query(TABLE_HISTORY_LOCATION, null, TIME + " > " + time + " AND " + TIME + " < " + nextDayTime, null, null, null, TIME);
        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                OneLocationRecord record = new OneLocationRecord();
                record.setLongitude(cursor.getDouble(cursor.getColumnIndex(LONGITUDE)));
                record.setLatitude(cursor.getDouble(cursor.getColumnIndex(LATITUDE)));
                record.setTime(cursor.getLong(cursor.getColumnIndex(TIME)));
                record.setAddrName(cursor.getString(cursor.getColumnIndex(ADDRESS)));
                record.setDuration(cursor.getLong(cursor.getColumnIndex(DURING_TIME)));
                record.set_first_of_line(cursor.getInt(cursor.getColumnIndex(IS_FIRST_OF_TIMELINE)) == 1);
                records.add(record);
            }
            cursor.close();
        }
        return records;
    }

    /**
     * 当天是否有数据
     *
     * @param time 一天的起始时间
     * @retur
     */
    public boolean isHaveDataInADay(long time) {
        creatHistoryLocationTable();
        long nextDayTime = time + 24 * 3600 * 1000;
        Cursor cursor = db.query(TABLE_HISTORY_LOCATION, null, TIME + " > " + time + " AND " + TIME + " < " + nextDayTime, null, null, null, TIME);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.close();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取数据库第一个点的时间
     *
     * @return
     */
    public long getTimeOfFirstPoint() {
        creatHistoryLocationTable();
        Cursor cursor = db.query(TABLE_HISTORY_LOCATION, null, null, null, null, null, "_id asc");
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursor.getLong(cursor.getColumnIndex(TIME));
        } else {
            return Long.MAX_VALUE;
        }
    }
}
