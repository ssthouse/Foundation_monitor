package dian.org.monitor.gps;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import java.util.ArrayList;
import java.util.List;

import dian.org.monitor.R;

/**
 * Created by ssthouse on 2015/6/16.
 */

/**
 * 尚需 不保存删除数据库
 * 插标志
 * 传工程名称
 */
public class GpsTestAty extends Activity {
    private static final String TAG = "gpsAty";
    private String patrol_name="123";//传入巡检项目工程名，用于查询该工程的路径
    private LatLng lastlines;
    private boolean isFirstLoc = true;// 是否首次定位
    private LocationClient mLocClient;
    private InfoWindow mInfoWindow;
    public MyLocationListenner myListener = new MyLocationListenner();
    public MapView mMapView = null;
    public BaiduMap mBaiduMap;
    public BDLocation mylocation;
    private static int  MIN_DISTANCE=10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.gps_text_aty);

        // 设置地图绘制每一帧时的回调接口
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        //稻草的记录方法
        final HistoryLocationScanner historyLocationScanner =
                LocationTracker.getHistoryLocationScanner(this);
        List<OneLocationRecord> oneLocationRecordList =
                historyLocationScanner.getLocationRecordData(patrol_name);

        List<LatLng> latLngList = new ArrayList<>();
        for (OneLocationRecord oneLocationRecord : oneLocationRecordList) {
            latLngList.add(new LatLng(oneLocationRecord.getLatitude(),
                    oneLocationRecord.getLongitude()));
        }
        Log.e(TAG, latLngList.size() + "这是我的记录点的的数目");
        for(int i = 0;i < latLngList.size(); i ++) {
            Log.i(TAG, "经度:" + latLngList.get(i).longitude);
            Log.i(TAG, "纬度:" + latLngList.get(i).latitude);
            Drawlines(latLngList.get(i));
        }

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(5000);
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        mLocClient.setLocOption(option);
        mLocClient.start();
        Log.i(TAG, "定位初始化");
    }

    /**
     * 设置起点标志
     * @param lines
     */
    private void initOverlay(LatLng lines){
        MyOverlay myOverlay=new MyOverlay(this,mBaiduMap,lines.longitude,lines.latitude);
        myOverlay.showView();
        LatLng ll =myOverlay.mMarker.getPosition();
        Button button = new Button(getApplicationContext());
        button.setBackgroundResource(R.drawable.popup);
        button.setText("起点");
        InfoWindow mInfoWindow;
        mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), ll, -47, null);
        mBaiduMap.showInfoWindow(mInfoWindow);
    }

    /**
     * 路径绘制
     * @param lines
     */
    private void Drawlines(LatLng lines){
        if(lastlines==null){//初始化起点
            lastlines=lines;
            initOverlay(lines);
        }
        else{
            List<LatLng> points = new ArrayList<LatLng>();
            points.add(lastlines);
            points.add(lines);
            OverlayOptions ooPolyline = new PolylineOptions().width(10)
                    .color(0xAAFF0000).points(points);
            mBaiduMap.addOverlay(ooPolyline);
            lastlines=lines;
        }
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            mylocation=location;
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            Log.i(TAG,"我更新了位置");
            mBaiduMap.setMyLocationData(locData);
            if(lastlines!=null) {
                if (Distance(lastlines.longitude, lastlines.latitude,
                        location.getLongitude(), location.getLatitude())
                        > MIN_DISTANCE)
                    Drawlines(new LatLng(location.getLatitude(), location.getLongitude()));
            }else{
                lastlines=new LatLng(location.getLatitude(), location.getLongitude());
            }
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }
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
        double distance= DistanceUtil.getDistance(new LatLng(latitude, longitude), new LatLng(nextlatitude, nextlongitude));
        return distance;
    }
    @Override
    protected void onDestroy() {
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        // 退出时销毁定位
        mLocClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
