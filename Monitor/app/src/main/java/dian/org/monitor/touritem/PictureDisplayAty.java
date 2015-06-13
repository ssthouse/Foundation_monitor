package dian.org.monitor.touritem;

import android.app.Activity;
import android.os.Bundle;

import dian.org.monitor.Constant;

/**
 * 查看照片的Activity
 * 必须传递一个Intent---带着一个BitmapItem
 * Created by ssthouse on 2015/6/13.
 */
public class PictureDisplayAty extends Activity {

    private BitmapItem bitmapItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取数据
        bitmapItem = getIntent().getParcelableExtra(Constant.INTENT_KEY_DATA_BITMAP_ITEM);
    }
}
