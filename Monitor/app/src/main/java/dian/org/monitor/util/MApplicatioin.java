package dian.org.monitor.util;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.thinkland.sdk.android.JuheSDKInitializer;

/**
 * Created by ssthouse on 2015/6/16.
 */
public class MApplicatioin extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        //聚合数据初始化
        JuheSDKInitializer.initialize(getApplicationContext());
    }
}
