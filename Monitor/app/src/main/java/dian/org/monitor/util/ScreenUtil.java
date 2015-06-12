package dian.org.monitor.util;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by ssthouse on 2015/6/12.
 */
public class ScreenUtil {

    /**
     * 获取屏幕宽度
     * @param context
     * @return
     */
    public static final int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int width = wm.getDefaultDisplay().getWidth();
        return width;
    }
}
