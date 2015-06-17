package dian.org.monitor.gps;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import dian.org.monitor.R;

/**
 * Created by ssthouse on 2015/6/16.
 */
public class GpsTestAty extends Activity {
    private static final String TAG = "gpsAty";

    private MapView mMapView = null;

    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.gps_text_aty);

        // 设置地图绘制每一帧时的回调接口
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();

        //稻草的记录方法
        LocationTracker.createLocationTracker(this);
        LocationTracker.startWorking();
        HistoryLocationScanner historyLocationScanner =
                LocationTracker.getHistoryLocationScanner(this);
        List<OneLocationRecord> oneLocationRecordList =
                historyLocationScanner.getLocationRecordData();

        List<LatLng> latLngList = new ArrayList<>();
        for(OneLocationRecord oneLocationRecord : oneLocationRecordList){
            latLngList.add(new LatLng(oneLocationRecord.getLatitude(),
                    oneLocationRecord.getLongitude()));
        }
        Log.e(TAG, latLngList.size()+"这是我的记录点的的数目");
        drawLines(latLngList);
    }


     private void drawLines(List<LatLng> latLngList){
         //定义多边形的五个顶点
         LatLng pt1 = new LatLng(30.53923, 114.457428);
         LatLng pt2 = new LatLng(30.63923, 114.657428);
         LatLng pt3 = new LatLng(30.83923, 114.757428);
         LatLng pt4 = new LatLng(30.93923, 114.357428);
         LatLng pt5 = new LatLng(30.43923, 114.457428);
         List<LatLng> pts = new ArrayList<>();
         pts.add(pt1);
         pts.add(pt2);
         pts.add(pt3);
         pts.add(pt4);
         pts.add(pt5);
         OverlayOptions polyLineOption = new PolylineOptions()
                 .color(getResources().getColor(R.color.blue_level0))
                 .points(latLngList);
         mBaiduMap.addOverlay(polyLineOption);

         //构造当前的第一个点
         MapStatus mMapStatus = new MapStatus.Builder()
                 .target(latLngList.get(0))
                 .zoom(15)
                 .build();
         //定义MapStatusUpdate对象，以便描述地图状态将要发生的变化
         MapStatusUpdate mMapStatusUpdate = MapStatusUpdateFactory.newMapStatus(mMapStatus);
         //改变地图状态
         //定位到当前的第一个点
         mBaiduMap.animateMapStatus(mMapStatusUpdate);
     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}
