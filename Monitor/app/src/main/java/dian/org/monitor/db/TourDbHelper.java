package dian.org.monitor.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import dian.org.monitor.touritem.ConstructState;
import dian.org.monitor.touritem.MonitorFacility;
import dian.org.monitor.touritem.SupportStruct;
import dian.org.monitor.touritem.SurroundEnv;
import dian.org.monitor.touritem.TourInfo;
import dian.org.monitor.touritem.TourItem;
import dian.org.monitor.touritem.WeatherState;

/**
 * 控制单个database---也就是单个TOurIten的数据库
 * 功能有:
 * 1.根据数据库----获取一个TourItem
 * Created by ssthouse on 2015/6/14.
 */
public class TourDbHelper {
    private static final String TAG = "TourDbHelper****";

    //table------tour_info
    public static final String TABLE_TOUR_INFO = "tour_info";
    //item_name
    public static final String ITEM_TOUR_INFO_PRJ_NAME_ITEM1 = "prj_name_item1";
    public static final String ITEM_TOUR_INFO_TOUR_NUMBER_ITEM2 = "tour_number_item2";
    public static final String ITEM_TOUR_INFO_OBSERVER_ITEM3 = "observer_item3";
    public static final String ITEM_TOUR_INFO_TIME_IN_MILES_ITEM4 = "time_in_miles_item4";
    public static final String ITEM_TOUR_INFO_SUMMARY_ITEM5 = "summary_item5";

    //table------weather_state
    public static final String TABLE_WEATHER_STATE = "weather_state";
    //item_name
    public static final String ITEM_WEATHER_STATE_TEMPERATURE_ITEM1 = "temperature_item1";
    public static final String ITEM_WEATHER_STATE_RAIN_FALL_ITEM2 = "rain_fall_item2";
    public static final String ITEM_WEATHER_STATE_WIND_SPEED_ITEM3 = "wind_speed_item3";
    public static final String ITEM_WEATHER_STATE_WATER_LEVEL_ITEM4 = "water_level_item4";


    //table------support_struct
    public static final String TABLE_SUPPORT_STRUCT = "support_struct";
    //item_name
    public static final String ITEM_SUPPORT_STRUCT_QUALITY_ITEM1 = "quality_item1";
    public static final String ITEM_SUPPORT_STRUCT_CRACK_ITEM2 = "crack_item2";
    public static final String ITEM_SUPPORT_STRUCT_TRANSFORM_ITEM3 = "transform_item3";
    public static final String ITEM_SUPPORT_STRUCT_LEAK_ITEM4 = "leak_item4";
    public static final String ITEM_SUPPORT_STRUCT_SLIP_ITEM5 = "slip_item5";
    public static final String ITEM_SUPPORT_STRUCT_POUR_ITEM6 = "pour_item6";
    public static final String ITEM_SUPPORT_STRUCT_OTHER_ITEM7 = "other_item7";


    //table------construct_state
    public static final String TABLE_CONSTRUCT_STATE = "construct_state";
    //item_name
    public static final String ITEM_CONSTRUCT_STATE_SOLID_STATE_ITEM1 = "soil_state_item1";
    public static final String ITEM_CONSTRUCT_STATE_LENGTH_WIDTH_ITEM2 = "length_width_item2";
    public static final String ITEM_CONSTRUCT_STATE_UNDER_WATER_ITEM3 = "under_water_item3";
    public static final String ITEM_CONSTRUCT_STATE_GROUND_STATE_ITEM4 = "ground_state_item4";
    public static final String ITEM_CONSTRUCT_STATE_OTHER_ITEM5 = "other_item5";


    //table------surround_env
    public static final String TABLE_SURROUND_ENV = "surround_env";
    //item_name
    public static final String ITEM_SURROUND_ENV_BREAK_STATE_ITEM1 = "break_state_item1";
    public static final String ITEM_SURROUND_ENV_LEAK_ITEM2 = "leak_item2";
    public static final String ITEM_SURROUND_ENV_SINK_ITEM3 = "sink_item3";
    public static final String ITEM_SURROUND_ENV_SURROUND_STATE_ITEM4 = "surround_state_item4";
    public static final String ITEM_SURROUND_ENV_OTHER_ITEM5 = "other_item5";


    //table------monitor_facility
    public static final String TABLE_MONITOR_FACILITY = "monitor_facility";
    //item_name
    public static final String ITEM_MONITOR_FACILITY_BASE_STATE_ITEM1 = "baseStateItem1";
    public static final String ITEM_MONITOR_FACILITY_DEVICE_STATE_ITEM2 = "deviceStateItem2";
    public static final String ITEM_MONITOR_FACILITY_WORK_STATE_ITEM3 = "workStateItem3";

    /**
     * 将给定TourItem保存到指定的数据库里面
     *
     * @param db
     * @param tourItem
     */
    public static void setTourItem(SQLiteDatabase db, TourItem tourItem) {
        /*
        将TourItem的5种数据----还有一个Info依次保存进去
         */
        //保存TourInfo
        ContentValues tourInfoValue = new ContentValues();
        //填充数据
        tourInfoValue.put(ITEM_TOUR_INFO_PRJ_NAME_ITEM1,
                tourItem.getTourInfo().getPrjName());
        tourInfoValue.put(ITEM_TOUR_INFO_TOUR_NUMBER_ITEM2,
                tourItem.getTourInfo().getTourNumber());
        tourInfoValue.put(ITEM_TOUR_INFO_OBSERVER_ITEM3,
                tourItem.getTourInfo().getObserver());
        tourInfoValue.put(ITEM_TOUR_INFO_TIME_IN_MILES_ITEM4,
                tourItem.getTourInfo().getTimeInMilesStr());
        tourInfoValue.put(ITEM_TOUR_INFO_SUMMARY_ITEM5,
                tourItem.getTourInfo().getSummary());
        //将数据插入表中
        clearTable(db, TABLE_TOUR_INFO);
        db.insert(TABLE_TOUR_INFO, null, tourInfoValue);

        //保存WeatherState---1
        ContentValues weatherStateValue = new ContentValues();
        //填充数据
        weatherStateValue.put(ITEM_WEATHER_STATE_TEMPERATURE_ITEM1,
                tourItem.getWeatherState().getTemperatureItem1());
        weatherStateValue.put(ITEM_WEATHER_STATE_RAIN_FALL_ITEM2,
                tourItem.getWeatherState().getRainFallItem2());
        weatherStateValue.put(ITEM_WEATHER_STATE_WIND_SPEED_ITEM3,
                tourItem.getWeatherState().getWindSpeedItem3());
        weatherStateValue.put(ITEM_WEATHER_STATE_WATER_LEVEL_ITEM4,
                tourItem.getWeatherState().getWaterLevelItem4());
        //将数据插入表中
        clearTable(db, TABLE_WEATHER_STATE);
        db.insert(TABLE_WEATHER_STATE, null, weatherStateValue);

        //保存SupportStruct---2
        ContentValues supportStructValue = new ContentValues();
        //填充数据
        supportStructValue.put(ITEM_SUPPORT_STRUCT_QUALITY_ITEM1,
                tourItem.getSupportStruct().getQualityItem1());
        supportStructValue.put(ITEM_SUPPORT_STRUCT_CRACK_ITEM2,
                tourItem.getSupportStruct().getCrackItem2());
        supportStructValue.put(ITEM_SUPPORT_STRUCT_TRANSFORM_ITEM3,
                tourItem.getSupportStruct().getTransformItem3());
        supportStructValue.put(ITEM_SUPPORT_STRUCT_LEAK_ITEM4,
                tourItem.getSupportStruct().getLeakItem4());
        supportStructValue.put(ITEM_SUPPORT_STRUCT_SLIP_ITEM5,
                tourItem.getSupportStruct().getSlipItem5());
        supportStructValue.put(ITEM_SUPPORT_STRUCT_POUR_ITEM6,
                tourItem.getSupportStruct().getPourItem6());
        supportStructValue.put(ITEM_SUPPORT_STRUCT_OTHER_ITEM7,
                tourItem.getSupportStruct().getOtherItem7());
        //将数据插入表中
        clearTable(db, TABLE_SUPPORT_STRUCT);
        db.insert(TABLE_SUPPORT_STRUCT, null, supportStructValue);

        //保存constructState---3
        ContentValues constructStateValue = new ContentValues();
        //填充数据
        constructStateValue.put(ITEM_CONSTRUCT_STATE_SOLID_STATE_ITEM1,
                tourItem.getConstructState().getSoilStateItem1());
        constructStateValue.put(ITEM_CONSTRUCT_STATE_LENGTH_WIDTH_ITEM2,
                tourItem.getConstructState().getLengthWidthItem2());
        constructStateValue.put(ITEM_CONSTRUCT_STATE_UNDER_WATER_ITEM3,
                tourItem.getConstructState().getUnderWaterItem3());
        constructStateValue.put(ITEM_CONSTRUCT_STATE_GROUND_STATE_ITEM4,
                tourItem.getConstructState().getGroundStateItem4());
        constructStateValue.put(ITEM_CONSTRUCT_STATE_OTHER_ITEM5,
                tourItem.getConstructState().getOtherItem5());
        //将数据插入表中
        clearTable(db, TABLE_CONSTRUCT_STATE);
        db.insert(TABLE_CONSTRUCT_STATE, null, constructStateValue);

        //保存SurroundEnv---4
        ContentValues surroundEnvValue = new ContentValues();
        //填充数据
        surroundEnvValue.put(ITEM_SURROUND_ENV_BREAK_STATE_ITEM1,
                tourItem.getSurroundEnv().getBreakStateItem1());
        surroundEnvValue.put(ITEM_SURROUND_ENV_LEAK_ITEM2,
                tourItem.getSurroundEnv().getLeakItem2());
        surroundEnvValue.put(ITEM_SURROUND_ENV_SINK_ITEM3,
                tourItem.getSurroundEnv().getSinkItem3());
        surroundEnvValue.put(ITEM_SURROUND_ENV_SURROUND_STATE_ITEM4,
                tourItem.getSurroundEnv().getSurroundStateItem4());
        surroundEnvValue.put(ITEM_SURROUND_ENV_OTHER_ITEM5,
                tourItem.getSurroundEnv().getOtherItem5());
        //将数据插入表中
        clearTable(db, TABLE_SURROUND_ENV);
        db.insert(TABLE_SURROUND_ENV, null, surroundEnvValue);

        //保存MonitorFacility---5
        ContentValues moniotrFacilityValue = new ContentValues();
        //填充数据
        moniotrFacilityValue.put(ITEM_MONITOR_FACILITY_BASE_STATE_ITEM1,
                tourItem.getMonitorFacility().getBaseStateItem1());
        moniotrFacilityValue.put(ITEM_MONITOR_FACILITY_DEVICE_STATE_ITEM2,
                tourItem.getMonitorFacility().getDeviceStateItem2());
        moniotrFacilityValue.put(ITEM_MONITOR_FACILITY_WORK_STATE_ITEM3,
                tourItem.getMonitorFacility().getWorkStateItem3());
        //将数据插入表中
        clearTable(db, TABLE_MONITOR_FACILITY);
        db.insert(TABLE_MONITOR_FACILITY, null, moniotrFacilityValue);
        Log.e(TAG, "成功插入数据");
    }

    /**
     * 根据给定的数据库----获取TourItem
     *
     * @param db
     * @return
     */
    public static TourItem getTourItem(SQLiteDatabase db) {
        /*
        依次获取五种数据---还有TOurInfo
         */
        //获取TOurInfo
        TourInfo tourInfo;
        if (!isTableExist(db, TABLE_TOUR_INFO)) {
            createTable(db, TABLE_TOUR_INFO);
            tourInfo = null;
        } else {
            tourInfo = getTourInfo(db);
        }
        //获取WeatherState---1
        WeatherState weatherState;
        if (!isTableExist(db, TABLE_WEATHER_STATE)) {
            createTable(db, TABLE_WEATHER_STATE);
            weatherState = null;
        } else {
            weatherState = getWeatherState(db);
        }
        //获取SupportStruct---2
        SupportStruct supportStruct;
        if (!isTableExist(db, TABLE_SUPPORT_STRUCT)) {
            createTable(db, TABLE_SUPPORT_STRUCT);
            supportStruct = null;
        } else {
            supportStruct = getSupportStruct(db);
        }
        //获取ConstructState---3
        ConstructState constructState;
        if (!isTableExist(db, TABLE_CONSTRUCT_STATE)) {
            createTable(db, TABLE_CONSTRUCT_STATE);
            constructState = null;
        } else {
            constructState = getConstructState(db);
        }
        //获取SurroundEnv---4
        SurroundEnv surroundEnv;
        if (!isTableExist(db, TABLE_SURROUND_ENV)) {
            createTable(db, TABLE_SURROUND_ENV);
            surroundEnv = null;
        } else {
            surroundEnv = getSurroundEnv(db);
        }
        //获取MonitorFacility---5
        MonitorFacility monitorFacility;
        if (!isTableExist(db, TABLE_MONITOR_FACILITY)) {
            createTable(db, TABLE_MONITOR_FACILITY);
            monitorFacility = null;
        } else {
            monitorFacility = getMonitorFacility(db);
        }
        //判断是否成功获取tourItem数据
        if (tourInfo == null || weatherState == null ||
                supportStruct == null ||
                constructState == null ||
                surroundEnv == null ||
                monitorFacility == null) {
            return null;
        } else {
            TourItem tourItem = new TourItem(tourInfo, weatherState, supportStruct,
                    constructState, surroundEnv, monitorFacility);
            return tourItem;
        }
    }

    /**
     * 根据给定的数据库---获取TOurInfo
     *
     * @param db
     * @return
     */
    public static TourInfo getTourInfo(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_TOUR_INFO, null, null, null, null, null, null);
        //判断数据是否为空
        if (cursor.moveToFirst()) {
            //temperatureItem1---rainFallItem2---windSpeedItem3---waterLevelItem4
            String prjName = cursor.getString(0);
            int tourNumber = cursor.getInt(1);
            String observer = cursor.getString(2);
            String timeInMiles = cursor.getString(3);
            String summary = cursor.getString(4);
            Log.e(TAG, prjName + "*********" + tourNumber + "*********"
                    + observer);
            //创建数据---并返回
            TourInfo tourInfo = new TourInfo(prjName, tourNumber,
                    observer, timeInMiles, summary);
            return tourInfo;
        } else {
            return null;
        }
    }

    /**
     * 根据给定的数据库---获取其中的weather_state表的内容
     *
     * @param db
     * @return
     */
    public static WeatherState getWeatherState(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_WEATHER_STATE, null, null, null, null, null, null);
        //判断数据是否为空
        if (cursor.moveToFirst()) {
            //temperatureItem1---rainFallItem2---windSpeedItem3---waterLevelItem4
            String temperatureItem1 = cursor.getString(0);
            String rainFallItem2 = cursor.getString(1);
            String windSpeedItem3 = cursor.getString(2);
            String waterLevelItem4 = cursor.getString(3);
            Log.e(TAG, temperatureItem1 + "*********" + rainFallItem2 + "*********"
                    + windSpeedItem3);
            //创建数据---并返回
            WeatherState weatherState = new WeatherState(temperatureItem1, rainFallItem2,
                    windSpeedItem3, waterLevelItem4);
            return weatherState;
        } else {
            return null;
        }
    }

    /**
     * 根据给定的数据库---获取其中的support_struct表的内容
     *
     * @param db
     * @return
     */
    public static SupportStruct getSupportStruct(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_SUPPORT_STRUCT, null, null, null, null, null, null);
        //判断数据是否为空
        if (cursor.moveToFirst()) {
            //qualityItem1---crackItem2---transformItem3---leakItem4
            //slipItem5---pourItem6---otherItem7
            String qualityItem1 = cursor.getString(0);
            String crackItem2 = cursor.getString(1);
            String transformItem3 = cursor.getString(2);
            String leakItem4 = cursor.getString(3);
            String slipItem5 = cursor.getString(4);
            String pourItem6 = cursor.getString(5);
            String otherItem7 = cursor.getString(6);
            Log.e(TAG, qualityItem1 + "*********" + crackItem2 + "*********"
                    + transformItem3);
            //创建数据---并返回
            SupportStruct supportStruct = new SupportStruct(qualityItem1, crackItem2,
                    transformItem3, leakItem4, slipItem5, pourItem6, otherItem7);
            return supportStruct;
        } else {
            return null;
        }
    }

    /**
     * 根据给定的数据库获取construct_state的数据
     *
     * @param db
     * @return
     */
    public static ConstructState getConstructState(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_CONSTRUCT_STATE, null, null, null, null, null, null);
        //判断数据是否为空
        if (cursor.moveToFirst()) {
            //qualityItem1---crackItem2---transformItem3---leakItem4
            //slipItem5---pourItem6---otherItem7
            String soilStateItem1 = cursor.getString(0);
            String lengthWidthItem2 = cursor.getString(1);
            String underWaterItem3 = cursor.getString(2);
            String groundStateItem4 = cursor.getString(3);
            String otherItem5 = cursor.getString(4);
            Log.e(TAG, soilStateItem1 + "*********" + lengthWidthItem2 + "*********"
                    + underWaterItem3);
            //创建数据---并返回
            ConstructState constructState = new ConstructState(soilStateItem1, lengthWidthItem2,
                    underWaterItem3, groundStateItem4, otherItem5);
            return constructState;
        } else {
            return null;
        }
    }

    /**
     * 根据指定的数据库---获取surroundEnv数据
     *
     * @param db
     * @return
     */
    public static SurroundEnv getSurroundEnv(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_SURROUND_ENV, null, null, null, null, null, null);
        //判断数据是否为空
        if (cursor.moveToFirst()) {
            //qualityItem1---crackItem2---transformItem3---leakItem4
            //slipItem5---pourItem6---otherItem7
            String breakStateItem1 = cursor.getString(0);
            String leakItem2 = cursor.getString(1);
            String sinkItem3 = cursor.getString(2);
            String surroundStateItem4 = cursor.getString(3);
            String otherItem5 = cursor.getString(4);
            Log.e(TAG, breakStateItem1 + "*********" + leakItem2 + "*********"
                    + sinkItem3);
            //创建数据---并返回
            SurroundEnv surroundEnv = new SurroundEnv(breakStateItem1, leakItem2,
                    sinkItem3, surroundStateItem4, otherItem5);
            return surroundEnv;
        } else {
            return null;
        }
    }

    /**
     * 根据给定的数据库---获取MonitorFacility数据
     *
     * @param db
     * @return
     */
    public static MonitorFacility getMonitorFacility(SQLiteDatabase db) {
        Cursor cursor = db.query(TABLE_SURROUND_ENV, null, null, null, null, null, null);
        //判断数据是否为空
        if (cursor.moveToFirst()) {
            //qualityItem1---crackItem2---transformItem3---leakItem4
            //slipItem5---pourItem6---otherItem7
            String baseStateItem1 = cursor.getString(0);
            String deviceStateItem2 = cursor.getString(1);
            String workStateItem3 = cursor.getString(2);
            Log.e(TAG, baseStateItem1 + "*********" + deviceStateItem2 + "*********"
                    + workStateItem3);
            //创建数据---并返回
            MonitorFacility monitorFacility = new MonitorFacility(baseStateItem1, deviceStateItem2,
                    workStateItem3);
            return monitorFacility;
        } else {
            return null;
        }
    }

    //---------------判断table是否存在----创建table---清空table的----工具方法------------------------------------

    /**
     * 清空指定表
     *
     * @param db
     * @param tableNname
     */
    public static void clearTable(SQLiteDatabase db, String tableNname) {
        String sql = "DELETE FROM " + tableNname;
        db.execSQL(sql);
    }

    /**
     * 判断数据库中是否有这张表
     *
     * @param db
     * @param tableName
     * @return
     */
    public static boolean isTableExist(SQLiteDatabase db, String tableName) {
        boolean result = false;
        if (tableName == null) {
            return false;
        }
        Cursor cursor = null;
        try {
            String sql = "select count(*) as c from " + "sqlite_master" +
                    " where type ='table' and name ='" + tableName.trim() + "' ";
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
//            Log.e(TAG, e.toString());
        }
        return result;
    }

    /**
     * 根据给定的tablename创建相应的table
     *
     * @param db
     * @param tableName
     */
    public static void createTable(SQLiteDatabase db, String tableName) {
        if (isTableExist(db, tableName) == false) {
            Log.e(TAG, "该表不存在---待我来创建他");
            //获取创建table的Sql语句
            String sqlStr;
            //判断是创建那张表的数据
            if (tableName.equals(TABLE_TOUR_INFO)) {
                //如果是TABLE_TOUR_INFO
                sqlStr = "create table " + tableName + "("
                        + ITEM_TOUR_INFO_PRJ_NAME_ITEM1 + " text,"
                        + ITEM_TOUR_INFO_TOUR_NUMBER_ITEM2 + " integer,"
                        + ITEM_TOUR_INFO_OBSERVER_ITEM3 + " text,"
                        + ITEM_TOUR_INFO_TIME_IN_MILES_ITEM4 + " text,"
                        + ITEM_TOUR_INFO_SUMMARY_ITEM5 + " text"
                        + " )";
            } else if (tableName.equals(TABLE_WEATHER_STATE)) {
                //如果是TABLE_WEATHER_STATE
                sqlStr = "create table " + tableName + "("
                        + ITEM_WEATHER_STATE_TEMPERATURE_ITEM1 + " text,"
                        + ITEM_WEATHER_STATE_RAIN_FALL_ITEM2 + " text,"
                        + ITEM_WEATHER_STATE_WIND_SPEED_ITEM3 + " text,"
                        + ITEM_WEATHER_STATE_WATER_LEVEL_ITEM4 + " text"
                        + " )";
            } else if (tableName.equals(TABLE_SUPPORT_STRUCT)) {
                //如果是TABLE_SUPPORT_STRUCT
                sqlStr = "create table " + tableName + "("
                        + ITEM_SUPPORT_STRUCT_QUALITY_ITEM1 + " text,"
                        + ITEM_SUPPORT_STRUCT_CRACK_ITEM2 + " text,"
                        + ITEM_SUPPORT_STRUCT_TRANSFORM_ITEM3 + " text,"
                        + ITEM_SUPPORT_STRUCT_LEAK_ITEM4 + " text,"
                        + ITEM_SUPPORT_STRUCT_SLIP_ITEM5 + " text,"
                        + ITEM_SUPPORT_STRUCT_POUR_ITEM6 + " text,"
                        + ITEM_SUPPORT_STRUCT_OTHER_ITEM7 + " text"
                        + " )";
            } else if (tableName.equals(TABLE_CONSTRUCT_STATE)) {
                //如果是TABLE_CONSTRUCT_STATE
                sqlStr = "create table " + tableName + "("
                        + ITEM_CONSTRUCT_STATE_SOLID_STATE_ITEM1 + " text,"
                        + ITEM_CONSTRUCT_STATE_LENGTH_WIDTH_ITEM2 + " text,"
                        + ITEM_CONSTRUCT_STATE_UNDER_WATER_ITEM3 + " text,"
                        + ITEM_CONSTRUCT_STATE_GROUND_STATE_ITEM4 + " text,"
                        + ITEM_CONSTRUCT_STATE_OTHER_ITEM5 + " text"
                        + " )";
            } else if (tableName.equals(TABLE_SURROUND_ENV)) {
                //如果是TABLE_SURROUND_ENV
                sqlStr = "create table " + tableName + "("
                        + ITEM_SURROUND_ENV_BREAK_STATE_ITEM1 + " text,"
                        + ITEM_SURROUND_ENV_LEAK_ITEM2 + " text,"
                        + ITEM_SURROUND_ENV_SINK_ITEM3 + " text,"
                        + ITEM_SURROUND_ENV_SURROUND_STATE_ITEM4 + " text,"
                        + ITEM_SURROUND_ENV_OTHER_ITEM5 + " text"
                        + " )";
            } else if (tableName.equals(TABLE_MONITOR_FACILITY)) {
                //如果是TABLE_MONITOR_FACILITY
                sqlStr = "create table " + tableName + "("
                        + ITEM_MONITOR_FACILITY_BASE_STATE_ITEM1 + " text,"
                        + ITEM_MONITOR_FACILITY_DEVICE_STATE_ITEM2 + " text,"
                        + ITEM_MONITOR_FACILITY_WORK_STATE_ITEM3 + " text"
                        + " )";
            } else {
                //如果不是默认的表名---直接返回
                return;
            }
            db.execSQL(sqlStr);
        }
    }
}
