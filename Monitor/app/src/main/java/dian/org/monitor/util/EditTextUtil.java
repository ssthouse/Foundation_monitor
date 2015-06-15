package dian.org.monitor.util;

import android.widget.EditText;

/**
 * Created by ssthouse on 2015/6/14.
 */
public class EditTextUtil {

    /**
     * 判断Edittext是否为空
     * @param et
     * @return
     */
    public static boolean isEmpty(EditText et){
        if(et == null){
            return true;
        }
        if(et.getText().toString().equals("")){
            return true;
        }else{
            return false;
        }
    }
}
