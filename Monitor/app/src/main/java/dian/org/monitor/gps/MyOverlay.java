package dian.org.monitor.gps;

import android.content.Context;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import dian.org.monitor.R;


public class MyOverlay {
    BaiduMap mBaiduMap;
    private Marker mMarker;
    BitmapDescriptor bdA = BitmapDescriptorFactory
            .fromResource(R.drawable.back);
    double latitude;
    double longtitude;
    LatLng lastLatLng;//记录上一次的地理
    Context context;
    InfoWindow mInfoWindow;//即覆盖物上的显示框
    List<LatLng> points = new ArrayList<LatLng>();
    boolean isFirst = true;

    /**
     * 添加覆盖物
     *
     * @param context   是 getApplicationContext()
     * @param mBaiduMap
     * @param latitude
     * @param longitude
     */
    public MyOverlay(Context context, BaiduMap mBaiduMap, double latitude, double longitude) {
        this.context = context;
        this.mBaiduMap = mBaiduMap;
        //存储地理位置
        LatLng llA = new LatLng(latitude, longitude);
        //设置覆盖物的选项
        OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA)
                .zIndex(9);
        //将覆盖物加到地图上
        mMarker = (Marker) (mBaiduMap.addOverlay(ooA));

    }

    /**
     * 将地图移动到使此覆盖物显示在地图中间
     * 显示文本框
     * 画路线
     */
    public void showView() {

        //将地图移动到使此覆盖物显示在地图中间
        MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(mMarker.getPosition());
        mBaiduMap.animateMapStatus(update);

        //////////////////////////////
        //////////////用于显示路径和覆盖物上标题的代码
        //////////////////////////
//	//显示文本框
//	Button button = new Button(context);
//	button.setBackgroundResource(R.drawable.popup);
//	button.setText("啦啦啦");
//	mInfoWindow = new InfoWindow(BitmapDescriptorFactory.fromView(button), mMarker.getPosition(), -47, null);
//	mBaiduMap.showInfoWindow(mInfoWindow);
//	points.add(mMarker.getPosition());
//	if(!isFirst){
//		//画线
//		points.add(0,lastLatLng);
//		points.add(1,mMarker.getPosition());
//	OverlayOptions ooPolyline = new PolylineOptions().width(10)
//			.color(0xAAFF0000).points(points);
//	mBaiduMap.addOverlay(ooPolyline);
//	//储存上次地址
//	lastLatLng = mMarker.getPosition();
//	
//	
//	
//	//iter.remove();
//	}else {
//		isFirst = false;
//		lastLatLng = mMarker.getPosition();
//	}
    }

    /**
     * 设置地址
     *
     * @param latitude
     * @param longitude
     */
    public void setPosition(double latitude, double longitude) {
        mMarker.setPosition(new LatLng(latitude, longitude));
    }
}
