package dian.org.monitor.gps;

import android.util.Log;

import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;

public class TrackerUtils {

    private static final String LOG_TAG = "TrackerUtils";

    /**
     * 将{@link OneLocationRecord}对象里面的数据转换成{@link String}类型保存，并用于求救时的网络传输。<p>
     * <pre>
     * 		转换方式：
     *
     *        {@link OneLocationRecord}对象中的数据：
     *        {@link OneLocationRecord#getTime()}			long_time
     *        {@link OneLocationRecord#getLongitude()}	double_longitude
     *        {@link OneLocationRecord#getLatitude()}		double_latitude
     *
     * 		转换成：
     *
     *        {@link String}		long_time+double_longitude+double_latitude
     * </pre>
     *
     * @param records
     * @return
     */
    public static String locationRecord2String(ArrayList<OneLocationRecord> records) {
        String ret = "";
        for (OneLocationRecord oneLocationRecord : records) {
            ret += oneLocationRecord.getTime() + ","
                    + oneLocationRecord.getLongitude() + ","
                    + oneLocationRecord.getLatitude() + ";";
        }
        return ret;
    }

    /**
     * 转换方式与{@link #locationRecord2String(ArrayList)}相反.
     * 涉及到网络请求,不要在主线程中调用此方法
     *
     * @param records
     * @return
     * @throws Exception
     * @see #locationRecord2String(ArrayList)
     */
    public static ArrayList<OneLocationRecord> string2LocationRecord(String records) throws Exception {
        ArrayList<OneLocationRecord> retArrayList = new ArrayList<OneLocationRecord>();
        String[] recordStrings = records.split(";");
        for (String string : recordStrings) {
            if (string == null || string.equals("")) {
                continue;
            }
            String[] args = string.split(",");
            OneLocationRecord record = new OneLocationRecord();
            record.setTime(Long.parseLong(args[0]));
            record.setLongitude(Double.parseDouble(args[1]));
            record.setLatitude(Double.parseDouble(args[2]));
            record.setAddrName(getAddressByPoint(Double.parseDouble(args[2]), Double.parseDouble(args[1])));
            retArrayList.add(record);
        }
        return retArrayList;
    }

    /**
     * 根据经纬度得到地址名
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @return 地名
     */
    public static String getAddressByPoint(double longitude, double latitude) {
        //第一步，创建HttpGet对象
        HttpGet httpGet = new HttpGet("http://api.map.baidu.com/geocoder/v2/?ak=QXfGeNhmUMnRjZrjnCpZlEft&location=" + latitude + "," + longitude + "&output=xml&pois=0");
        try {
            //第二步，使用execute方法发送HTTP GET请求，并返回HttpResponse对象
            HttpResponse httpResponse = new DefaultHttpClient().execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                //第三步，使用getEntity方法活得返回结果
                String result = EntityUtils.toString(httpResponse.getEntity());
                return result;
            } else {
                Log.e(LOG_TAG, "获取地址失败");
                return null;
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "获取地址失败");
            return null;
        }
    }

    /**
     * 比较两个位置preLoc和newLoc是不是相近
     *
     * @param preLoc      已经记录下来的位置
     * @param newLoc      新的位置
     * @param minDistance 判断相近的最小距离，单位：米
     * @return 如果两点距离大于 minDistance 则返回true，反之亦然。
     */
    public static boolean isLocANearbyLocB(OneLocationRecord preLoc, OneLocationRecord newLoc, int minDistance) {
        //利用百度地图的sdk计算两点之间的距离，单位：米
        double distance = DistanceUtil.getDistance(new LatLng(preLoc.getLatitude(), preLoc.getLongitude()),
                new LatLng(newLoc.getLatitude(), newLoc.getLongitude()));
        return distance < minDistance;
    }
}
