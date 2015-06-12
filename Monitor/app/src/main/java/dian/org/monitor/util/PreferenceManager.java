package dian.org.monitor.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 专门管理Preference的class
 * Created by ssthouse on 2015/6/8.
 */
public class PreferenceManager {
    /**
     * 包名***也是应用的唯一标识
     */
    public static final String PACKAGE_NAME = "dian.org.monitor";

    //是否已经登陆*******************************************************************
    public static final String PREFERENCE_KEY_IS_LOGINED = "isLogined";

    /**
     * 修改登录状态
     *
     * @param context
     * @param loginStatue
     */
    public static void changeLoginState(Context context, boolean loginStatue) {
        getEditor(context).putBoolean(PREFERENCE_KEY_IS_LOGINED, loginStatue);
        getEditor(context).commit();
    }

    /**
     * 获取登陆状态
     *
     * @param context
     * @return
     */
    public static boolean getLoginState(Context context) {
        boolean isLogin = getSharedPerference(context).getBoolean(PREFERENCE_KEY_IS_LOGINED, false);
        return isLogin;
    }


    //当前_用户名*******************************************************************
    public static final String PREFERENCE_KEY_USER_NAME = "user_name_current";
    //当前_密码
    public static final String PREFERENCE_KEY_PASS_WORD = "password_current";

    /**
     * 改变密码
     *
     * @param context
     * @param password
     */
    public static void changePassword(Context context, String password) {
        getEditor(context).putString(PREFERENCE_KEY_PASS_WORD, password);
        getEditor(context).commit();
    }

    /**
     * 获取密码
     *
     * @param context
     * @return
     */
    public static String getPassword(Context context) {
        String str = getSharedPerference(context).getString(PREFERENCE_KEY_PASS_WORD, "");
        return str;
    }

    /**
     * 改变用户名
     *
     * @param context
     * @param userName
     */
    public static void changeUserName(Context context, String userName) {
        getEditor(context).putString(PREFERENCE_KEY_USER_NAME, userName);
        getEditor(context).commit();
    }

    /**
     * 获取用户名
     *
     * @param context
     * @return
     */
    public static String getUserName(Context context) {
        String str = getSharedPerference(context).getString(PREFERENCE_KEY_USER_NAME, "");
        return str;
    }


    /**
     * *******************************************************************
     * 当前服务器名称
     */
    public static final String PREFERENCE_KEY_SERVER_ADDRESS = "server_addredd_current";
    /**
     * 当前服务器的序号
     */
    public static final String PREFERENCE_KEY_SERVER_NUMBER = "server_addredd_number";

    /**
     * 改变服务器地址
     *
     * @param context
     * @param serverAddr
     */
    public static void changeServerAddr(Context context, String serverAddr) {
        getEditor(context).putString(PREFERENCE_KEY_SERVER_ADDRESS, serverAddr);
        getEditor(context).commit();
    }

    /**
     * 获取服务器地址
     *
     * @param context
     * @return
     */
    public static String getServerAddr(Context context) {
        String str = getSharedPerference(context).getString(PREFERENCE_KEY_SERVER_ADDRESS, "");
        return str;
    }

    /**
     * 设置服务器----序号
     *
     * @param context
     * @param number
     */
    public static void changeServerNumber(Context context, int number) {
        getEditor(context).putInt(PREFERENCE_KEY_SERVER_NUMBER, number);
        getEditor(context).commit();
    }

    /**
     * 获取服务器----序号(如果是-1代表没有选中服务器)
     *
     * @param context
     * @return
     */
    public static int getServerNumber(Context context) {
        int number = getSharedPerference(context).getInt(PREFERENCE_KEY_SERVER_NUMBER, -1);
        return number;
    }


    /**
     * ***********************************************************************
     * 唯一的perference
     */
    private static SharedPreferences sharedPreference;
    /**
     * 唯一的Editor
     */
    private static SharedPreferences.Editor editor;

    /**
     * 获取sharedPreference
     *
     * @param context
     * @return
     */
    public static SharedPreferences getSharedPerference(Context context) {
        if (sharedPreference == null || editor == null) {
            sharedPreference = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
            editor = sharedPreference.edit();
        }
        return sharedPreference;
    }

    /**
     * 获取Editor
     *
     * @param context
     * @return
     */
    public static SharedPreferences.Editor getEditor(Context context) {
        if (sharedPreference == null || editor == null) {
            sharedPreference = context.getSharedPreferences(PACKAGE_NAME, Context.MODE_PRIVATE);
            editor = sharedPreference.edit();
        }
        return editor;
    }
}
