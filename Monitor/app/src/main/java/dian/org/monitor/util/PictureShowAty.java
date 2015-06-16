package dian.org.monitor.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import dian.org.monitor.R;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * 用于display图片的activity
 * 调用该Activity必须传递一个----"data"----BitmapItem
 * Created by ssthouse on 2015/6/15.
 */
public class PictureShowAty extends Activity {

    private ImageView iv;

    private PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_display_aty);

        //获取数据
        String  picPath = getIntent().getStringExtra("data");
        Bitmap bitmap = BitmapFactory.decodeFile(picPath);

        //
        iv = (ImageView) findViewById(R.id.id_iv);
        iv.setImageBitmap(bitmap);

        photoViewAttacher = new PhotoViewAttacher(iv);
    }
}
