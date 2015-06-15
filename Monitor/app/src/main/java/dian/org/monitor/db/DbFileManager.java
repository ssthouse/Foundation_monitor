package dian.org.monitor.db;

import android.database.sqlite.SQLiteDatabase;

import java.io.File;

import dian.org.monitor.touritem.TourItem;

/**
 * 管理数据库文件层次工具类
 * dbFile
 * |
 * ----prjName1
 * |
 * ----prjName2
 * |
 * ----prjName3
 * |
 * ----tourItem1.db
 * |
 * ----tourItem2.db
 * |
 * ----tourItem3.db
 * |
 * ----table--weather_state
 * |
 * ----table--support_struct
 * |
 * ----table--construct_state
 * |
 * ----table--surround_env
 * |
 * ----table--monitor_facility
 * Created by ssthouse on 2015/6/13.
 */
public class DbFileManager {
    private static final String TAG = "DbFileManager***";

    public static final String TABLE_NAME_WEATHER_STATE = "weather_state";
    public static final String TABLE_NAME_SUPPORT_STRUCT = "support_struct";
    public static final String TABLE_NAME_CONSTRUCT_STATE = "construct_state";
    public static final String TABLE_NAME_SURROUND_ENV = "surround_env";
    public static final String TABLE_NAME_MONITOR_ = "monitor_facility";

    //数据库文件的路径
    public static final String DATABASE_PATH = "/data/data/dian.org.monitor/dbFile/";

    /**
     * 根据prjName获取数据库数量
     *
     * @param prjName
     * @return
     */
    public static int getTourNumber(String prjName) {
        if (prjName == null) {
            return 0;
        }
        File prjPath = new File(DbFileManager.DATABASE_PATH + prjName);
        String fileNames[] = prjPath.list();
        if (fileNames == null) {
            return 0;
        }
        //排除掉文件名中系统生成的数据库
        int fileNumber = 0;
        for (String filename : fileNames) {
            if (!filename.contains("journal")) {
                fileNumber++;
            }
        }
        return fileNumber;
    }

    /**
     * 根据TourItem获取数据库的文件路径
     *
     * @param tourItem
     * @return
     */
    public static String getDbPath(TourItem tourItem) {
        //如果prjName是空的----直接返回null
        if (tourItem.getTourInfo().getPrjName().equals("")) {
            return null;
        }
        String strPath = DATABASE_PATH + tourItem.getTourInfo().getPrjName() +
                "/";
        return strPath;
    }

    /**
     * 获取指定的数据库
     *
     * @param tourItem
     * @return
     */
    public static SQLiteDatabase getDb(TourItem tourItem) {
        //如果文件夹不存在---必须先创建--否则会跪掉
        String path = getDbPath(tourItem);
        if (path == null) {
            return null;
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        //打开数据库
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(path +
                tourItem.getTourInfo().getTourNumber(), null);
        //先判断db的表是否为空
        if (!TourDbHelper.isTableExist(db, TourDbHelper.TABLE_TOUR_INFO)) {
            TourDbHelper.createTable(db, TourDbHelper.TABLE_TOUR_INFO);
        }
        if (!TourDbHelper.isTableExist(db, TourDbHelper.TABLE_WEATHER_STATE)) {
            TourDbHelper.createTable(db, TourDbHelper.TABLE_WEATHER_STATE);
        }
        if (!TourDbHelper.isTableExist(db, TourDbHelper.TABLE_SUPPORT_STRUCT)) {
            TourDbHelper.createTable(db, TourDbHelper.TABLE_SUPPORT_STRUCT);
        }
        if (!TourDbHelper.isTableExist(db, TourDbHelper.TABLE_CONSTRUCT_STATE)) {
            TourDbHelper.createTable(db, TourDbHelper.TABLE_CONSTRUCT_STATE);
        }
        if (!TourDbHelper.isTableExist(db, TourDbHelper.TABLE_SURROUND_ENV)) {
            TourDbHelper.createTable(db, TourDbHelper.TABLE_SURROUND_ENV);
        }
        if (!TourDbHelper.isTableExist(db, TourDbHelper.TABLE_MONITOR_FACILITY)) {
            TourDbHelper.createTable(db, TourDbHelper.TABLE_MONITOR_FACILITY);
        }
        return db;
    }

    /**
     * 根据文件名打开数据库
     *
     * @param dbPath
     * @return
     */
    public static SQLiteDatabase getDb(String dbPath, String fileName) {
        File file = new File(dbPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        //打开数据库
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dbPath + fileName, null);
        //先判断db的表是否为空
        if (!TourDbHelper.isTableExist(db, TourDbHelper.TABLE_TOUR_INFO)) {
            TourDbHelper.createTable(db, TourDbHelper.TABLE_TOUR_INFO);
        }
        if (!TourDbHelper.isTableExist(db, TourDbHelper.TABLE_WEATHER_STATE)) {
            TourDbHelper.createTable(db, TourDbHelper.TABLE_WEATHER_STATE);
        }
        if (!TourDbHelper.isTableExist(db, TourDbHelper.TABLE_SUPPORT_STRUCT)) {
            TourDbHelper.createTable(db, TourDbHelper.TABLE_SUPPORT_STRUCT);
        }
        if (!TourDbHelper.isTableExist(db, TourDbHelper.TABLE_CONSTRUCT_STATE)) {
            TourDbHelper.createTable(db, TourDbHelper.TABLE_CONSTRUCT_STATE);
        }
        if (!TourDbHelper.isTableExist(db, TourDbHelper.TABLE_SURROUND_ENV)) {
            TourDbHelper.createTable(db, TourDbHelper.TABLE_SURROUND_ENV);
        }
        if (!TourDbHelper.isTableExist(db, TourDbHelper.TABLE_MONITOR_FACILITY)) {
            TourDbHelper.createTable(db, TourDbHelper.TABLE_MONITOR_FACILITY);
        }
        return db;
    }
}
