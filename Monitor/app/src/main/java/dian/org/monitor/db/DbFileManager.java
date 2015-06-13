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
     * @param prjName
     * @return
     */
    public static int getTourNumber(String prjName) {
        if (prjName == null) {
            return 0;
        }
        File prjPath = new File(DbFileManager.DATABASE_PATH + prjName);
        return prjPath.list().length;
    }

    /**
     * 根据TourItem获取数据库的文件路径
     *
     * @param tourItem
     * @return
     */
    public static String getDbPath(TourItem tourItem) {
        String strPath = DATABASE_PATH + tourItem.getPrjName() +
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
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        //打开数据库
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(path + tourItem.getNumber(), null);
        return database;
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
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(dbPath + fileName, null);
        return database;
    }
}
