package dian.org.monitor.touritem;

import android.app.Activity;
import android.os.Bundle;

import dian.org.monitor.R;
import dian.org.monitor.style.TransparentStyle;

/**
 * Created by ssthouse on 2015/6/11.
 */
public class MonitorFacilityAty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //透明顶栏
        TransparentStyle.setAppToTransparentStyle(this, getResources().getColor(R.color.blue_level0));
    }
}
