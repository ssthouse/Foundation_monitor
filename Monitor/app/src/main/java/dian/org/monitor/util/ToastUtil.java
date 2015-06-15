package dian.org.monitor.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by ssthouse on 2015/6/14.
 */
public class ToastUtil {

    public static void showToast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
