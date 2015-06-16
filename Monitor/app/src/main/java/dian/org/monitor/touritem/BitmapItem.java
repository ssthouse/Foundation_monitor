package dian.org.monitor.touritem;

import android.graphics.Bitmap;

/**
 * 存放一个bitmap和一个path的item
 * Created by ssthouse on 2015/6/12.
 */
public class BitmapItem{

    private Bitmap bitmap;

    private String path;

    /**
     * 就这一个构造方法
     * @param bitmap
     * @param path
     */
    public BitmapItem(Bitmap bitmap, String path){
        this.bitmap = bitmap;
        this.path = path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
