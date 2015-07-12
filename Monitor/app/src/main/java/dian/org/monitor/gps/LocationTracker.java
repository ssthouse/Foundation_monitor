package dian.org.monitor.gps;

import android.content.Context;

import com.baidu.mapapi.map.BaiduMap;

import dian.org.monitor.touritem.TourItem;

/**
 * 这个是位置跟踪器类，是这个包的核心类。
 * 使用它能够利用{@link LocationMaker}定时记录下地理位置，地理位置的信息见{@link OneLocationRecord}.
 * 生成的最新{@link OneLocationRecord}会通过{@link LocationDB#recordLocation(OneLocationRecord)}进行保存。
 *
 * @author ChameleonChen
 */
public class LocationTracker {
    /**
     * 单个的实例
     */
    private static LocationTracker instance;

    /**
     *
     */
    private static HistoryLocationScanner mHistoryLocationScanner;
    /**
     * 记录时间的间隔
     */
    private static int unitTrackingTime = 5000;
    private LocationMaker mLocationMaker;
    private LocationDB mLocationDB;
    private static String patrol_name;//传入工程的名字
    private LocationTracker(Context context) {
        //创建一个数据库用来存储---位置数据
        mLocationDB = LocationDB.getInstance(context);
        //创建一个locationMaker用来创建---位置数据
        mLocationMaker = new LocationMaker(context, unitTrackingTime, patrol_name, new LocationMaker.LocationCreatedListener() {
            @Override
            public void onCreated(OneLocationRecord record) {
                onLocationCreated(record);
            }
        });
        if (mHistoryLocationScanner == null) {
            mHistoryLocationScanner = new HistoryLocationScanner(context);
        }
    }

    /**
     * 创建一个可以工作的 {@link LocationTracker}
     *
     * @param context
     */
    public static void createLocationTracker(Context context,String name) {
        if (instance == null) {
            patrol_name=name;
            instance = new LocationTracker(context);
        }
    }

    /**
     * 开始工作
     * 调用{@link #stopWorking()}可以结束工作。
     */
    public static void startWorking() {
        instance.start();

    }

    /**
     * 结束工作。
     * 结束工作之后，如果想再次开启工作，需要调用{@link #createLocationTracker(Context)}
     */
    public static void stopWorking() {
        if(instance!=null) {
            instance.stop();
            instance.release();
            instance = null;
        }
    }

    ////////////////////////////////////////////////////////
    /// 对外的函数
    ///////////////////////////////////////////////////////

    /**
     * 获取地理位置数据的浏览器
     *
     * @return
     */
    public static HistoryLocationScanner getHistoryLocationScanner(Context context) {
        if (mHistoryLocationScanner == null) {
            mHistoryLocationScanner = new HistoryLocationScanner(context);
        }
        return mHistoryLocationScanner;
    }

    private void start() {
        mLocationMaker.start();     // 开启位置生成器
    }

    private void stop() {
        mLocationMaker.stop();      // 让位置生成器不工作
    }

    private void release() {
        mLocationMaker = null;
        mLocationDB = null;
    }

    /**
     * 当新的地理位置产生的时候，这个函数会被执行
     * 在数据库中添加一条数据
     *
     * @param record
     */
    private void onLocationCreated(OneLocationRecord record) {
        mLocationDB.recordLocation(record);     // 将位置交给地图数据库处理
    }

}
