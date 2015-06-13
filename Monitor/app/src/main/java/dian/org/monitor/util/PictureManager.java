package dian.org.monitor.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dian.org.monitor.Constant;
import dian.org.monitor.touritem.BitmapItem;
import dian.org.monitor.touritem.TourItem;

/**
 * 获取图片的工具类
 * Created by ssthouse on 2015/6/11.
 */
public class PictureManager {
    private static final String TAG = "PictureManager";

    //图片文件的路径
    public static final String PICTURE_PATH = "/data/data/dian.org.monitor/picture/";
    //每个细节的图片的路径
    public static final String PICTURE_PATH_WEATHER_STATE = "WeatherState/";
    public static final String PICTURE_PATH_SUPPORT_STRUCT = "SupportStruct/";
    public static final String PICTURE_PATH_CONSTRUCT_STATE = "ConstructState/";
    public static final String PICTURE_PATH_SURROUND_ENV = "SurroundEnv/";
    public static final String PICTURE_PATH_MONITOR_FACILITY = "MonitorFacility/";


    /**
     * 将图片保存到指定的目录
     * @param photo
     * @param spath
     * @return
     */
    public static boolean saveImage(Bitmap photo, String spath) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(
                    new FileOutputStream(spath, false));
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
     * 将图片从指定文件夹---复制到目标文件夹
     *
     * @param srcPath
     * @param targetPath
     * @return
     */
    public static boolean saveImage(String srcPath, String targetPath) {
        //TODO
        Log.e(TAG, "我是源文件" + srcPath);
        Log.e(TAG, "我是目标文件" + targetPath);
        //判断路径是否为空
        if (srcPath == null) {
            return false;
        }
        try {
            //判断源文件是否存在
            File srcFile = new File(srcPath);
            if (srcFile.exists() == false) {
                return false;
            }
            //如果目标文件不存在---创建
            File targetFile = new File(targetPath);
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            //设置目标文件权限
            targetFile.setReadable(true);
            targetFile.setWritable(true);
            targetFile.setExecutable(true);
            //复制文件
            FileInputStream fosfrom = new java.io.FileInputStream(srcFile);
            FileOutputStream fosto = new FileOutputStream(targetFile);
            byte bt[] = new byte[1024];
            int c;
            while ((c = fosfrom.read(bt)) > 0) {
                fosto.write(bt, 0, c); //将内容写到新文件当中
            }
            fosfrom.close();
            fosto.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 获取指定文件夹图片的数量
     *
     * @param path
     * @return
     */
    public static int getPictureNumber(String path) {
        File file = new File(path);
        File[] fileList = file.listFiles();
        if (fileList == null) {
            return 0;
        } else {
            return fileList.length;
        }
    }

    /**
     * 根据给定的TourItem---获取图片文件---获取bitmap的List
     *
     * @param tourItem
     * @param kindPath 五个项目中的哪一个
     * @return Bitmap的List
     */
    public static List<BitmapItem> getBitmapList(TourItem tourItem, String kindPath) {
        //要返回的数据
        List<BitmapItem> bitmapList = new ArrayList<>();
        //列出picture文件
        File[] fileList;
        File dir = new File(PICTURE_PATH + tourItem.getPrjName() + "/" + tourItem.getNumber() +
                "/" + kindPath);
        if (dir.exists()) {
            fileList = dir.listFiles();
        } else {
            dir.mkdirs();
            fileList = dir.listFiles();
        }
        //将每个文件转化为bitmap
        for (File file : fileList) {
            //获取缩略图
            Bitmap bitmap = getSmallBitmap(file.getAbsolutePath(), 70, 70);
            bitmapList.add(new BitmapItem(bitmap, file.getAbsolutePath()));
        }
        return bitmapList;
    }

    /**
     * 获取压缩后的bitmap
     *
     * @param imagePath
     * @param width
     * @param height
     * @return
     */
    public static Bitmap getSmallBitmap(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

    /**
     * 在图库中查看picture
     * @param context
     * @param bitmapItem
     */
    public static void viewPictureFromAlbum(Context context, BitmapItem bitmapItem) {
        //使用Intent
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //Uri mUri = Uri.parse("file://" + picFile.getPath());Android3.0以后最好不要通过该方法，存在一些小Bug
        intent.setDataAndType(Uri.fromFile(new File(bitmapItem.getPath())), "image/*");
        context.startActivity(intent);
    }

    /**
     * 开启图库获取照片
     *
     * @param activity
     */
    public static void getPictureFromAlbum(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        activity.startActivityForResult(intent, Constant.REQUEST_CODE_ALBUM);
    }

    /**
     * 开启照相机获取照片
     *
     * @param activity
     */
    public static void getPictureFromCamera(Activity activity) {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            Intent getImageByCamera = new Intent("android.media.action.IMAGE_CAPTURE");
            activity.startActivityForResult(getImageByCamera, Constant.RESULT_CODE_CAMERA);
        } else {
            Toast.makeText(activity, "请确认已经插入SD卡", Toast.LENGTH_SHORT).show();
        }
    }
}
