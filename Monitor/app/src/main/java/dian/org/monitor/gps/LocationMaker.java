package dian.org.monitor.gps;

import android.content.Context;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 这是位置生成器，能够在规定的时间间隔内生成地理位置信息
 * 调用其{@link #start()}能够开启定位，调用{@link #stop()}能够结束定位。
 * 调用了{@link #stop()}之后能够调用{@link #start()}来开启定位。
 */
public class LocationMaker implements BDLocationListener {
    private static final String LOG_TAG = "LocationMaker";

    private LocationClient mLocationClient;

    private Context context;
    /**
     * 生成位置的时间间隔,单位是 ms
     */
    private int intervalTime;
    /**
     * 生成位置之后的回调接口
     */
    private LocationCreatedListener mLocationCreatedListener;

    /**
     * @param intervalTime 记录位置的时间间隔
     * @param listener     位置生成后的回调
     */
    public LocationMaker(Context context, int intervalTime, LocationCreatedListener listener) {
        this.intervalTime = intervalTime;
        this.context = context;
        if (listener == null)
            throw new NullPointerException();
        mLocationCreatedListener = listener;

    }

    /**
     * 开始定时记录位置
     * 这个方法不能够做耗时工作
     */
    public void start() {
        if (mLocationClient == null) {
            mLocationClient = new LocationClient(context);
            mLocationClient.registerLocationListener(this);
            //设置定位功能选项
            LocationClientOption option = new LocationClientOption();
            option.setOpenGps(true);
            option.setCoorType("bd09ll");
            option.setScanSpan(intervalTime);
            mLocationClient.setLocOption(option);
        }
        if (mLocationClient.isStarted()) {
            return;
        }
        mLocationClient.start();

        Log.e(LOG_TAG, "记录历史位置开启百度定位");
    }

    /**
     * 结束定时记录位置
     * 这个方法不能够做耗时工作
     */
    public void stop() {
        Log.e(LOG_TAG, "记录历史位置关闭百度定位");
        if (mLocationClient == null) {
            return;
        }
        mLocationClient.stop();
        mLocationClient = null;
    }

    /**
     * 生成位置之后，调用此方法
     */
    private void makeOneLocationRecord(OneLocationRecord record) {
        //TODO 实现这个方法
        mLocationCreatedListener.onCreated(record);
    }

    /**
     * 重载方法
     */
    @Override
    public void onReceiveLocation(BDLocation location) {
        if (location == null) return;
        float radius = location.getRadius();
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();
        int locType = location.getLocType();
        if (locType == BDLocation.TypeNone
                || locType == BDLocation.TypeNetWorkException
                || locType == BDLocation.TypeOffLineLocation
                || locType == BDLocation.TypeOffLineLocationFail
                || locType == BDLocation.TypeOffLineLocationNetworkFail
                || locType == BDLocation.TypeServerError) {
            Log.e(LOG_TAG, "无效定位结果！");
        }
        OneLocationRecord record = new OneLocationRecord();
        record.setLatitude(latitude);
        record.setLongitude(longitude);
        record.setTime(System.currentTimeMillis());
        //保存数据
        makeOneLocationRecord(record);
        Log.e(LOG_TAG, "经纬度");
        Log.e(LOG_TAG, location.getLongitude()+"");
        Log.e(LOG_TAG, location.getLatitude()+"");
        Log.e(LOG_TAG, location.getLocType()+"");
    }


    /**
     * 位置信息创建的回调接口
     */
    public interface LocationCreatedListener {
        void onCreated(OneLocationRecord record);
    }
}
