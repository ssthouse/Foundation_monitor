package dian.org.monitor;

/**
 * Created by ssthouse on 2015/6/8.
 * 保存一些常量
 */
public class Constant {

    /**
     * 标准的图片保存格式
     */
    public static final String PIC_FORMAT = ".jpeg";

    //暂时用来充当用户名和密码的常量
    public static final String USER_NAME = "123";
    public static final String PASS_WORD = "123";


    //Intent之间传递数据的Key
    //五种数据之间---是直接用"data
    public static final String INTENT_KEY_DATA_PROJECT_ITEM = "intent_key_data_project_item";

    public static final String INTENT_KEY_DATA_TOUR_ITEM = "intent_key_data_tour_item";

    public static final String INTENT_KEY_DATA_BITMAP_ITEM = "intent_key_data_bitmap_item";

    //公用的requestCode和resutCode
    //相机还是相册
    public static final int REQUEST_CODE_ALBUM = 10001;
    public static final int RESULT_CODE_CAMERA = 10002;
    //保存还是取消
    public static final int RESULT_CODE_SAVE = 20001;
    public static final int RESULT_CODE_CANCEL = 20002;
}
