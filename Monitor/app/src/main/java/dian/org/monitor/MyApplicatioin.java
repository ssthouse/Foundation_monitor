package dian.org.monitor;

import android.app.Application;

import com.activeandroid.ActiveAndroid;
import com.baidu.mapapi.SDKInitializer;
import com.thinkland.sdk.android.JuheSDKInitializer;

/**
 * Created by ssthouse on 2015/6/16.
 */
public class MyApplicatioin extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //ORM数据库框架初始化
        ActiveAndroid.initialize(this);
        //百度Sdk初始化
        SDKInitializer.initialize(getApplicationContext());
        //聚合数据初始化
        JuheSDKInitializer.initialize(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ActiveAndroid.dispose();
    }
}
