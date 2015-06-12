package dian.org.monitor.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import dian.org.monitor.Constant;
import dian.org.monitor.touritem.TourItem;

/**
 * 获取图片的工具类
 * Created by ssthouse on 2015/6/11.
 */
public class PictureManager {

    /**
     * 开启图库获取照片
     * @param activity
     * @param requestCode
     */
    protected static void getImageFromAlbum(Activity activity, int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 开启照相机获取照片
     * @param activity
     * @param requestCode
     */
    protected void getImageFromCamera(Activity activity, int requestCode) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            activity.startActivityForResult(getImageByCamera, requestCode);
        }
        else {
            Toast.makeText(activity, "请确认已经插入SD卡", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 保存图片
     * @param photo
     * @param path
     */
    public static boolean saveImage(Bitmap photo, String path) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(path, false));
            photo.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 根据给定的TourItem---获取图片文件---获取bitmap的List
     * @param tourItem
     * @return
     */
    public static List<Bitmap> getBitmapList(TourItem tourItem){
        //要返回的数据
        List<Bitmap> bitmapList = new ArrayList<>();
        //列出picture文件
        String pathStr = Constant.PICTURE_PATH+ tourItem.getPrjName()+tourItem.getNumber();
        File dir = new File(pathStr);
        File[] fileList;
        if(dir.exists()){
             fileList = dir.listFiles();
        }else{
            dir.mkdirs();
            fileList = dir.listFiles();
        }
        //将每个文件转化为bitmap
        for(File file : fileList){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            bitmapList.add(bitmap);
        }
        return bitmapList;
    }
}
