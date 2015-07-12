package dian.org.monitor.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import dian.org.monitor.util.ToastUtil;

/**
 * Created by ssthouse on 2015/7/9.
 * <p/>
 * 加在图片上面的文字-----大的是10/1---中的是1/20---小的是1/30
 */
public class PhotoEditView extends SurfaceView implements View.OnTouchListener,
        SurfaceHolder.Callback, Runnable {

    private static final String TAG = "PhotoEditView";

    private static int TEXT_SCALE_SMALL = 30;
    private static int TEXT_SCALE_MEDIUM = 20;
    private static int TEXT_SCALE_BIG = 10;

    /**
     * 当前SurfaceView是否处在编辑状态
     */
    private boolean editAble = false;

    private SurfaceHolder mHolder;

    private Canvas mCanvas;

    /**
     * 用于绘制的线程
     */
    private Thread t;
    /**
     * 判断线程是否在运行
     */
    private boolean isRunning;


    //图片
    private Bitmap bitmap;
    //图片的路径
    private String path;
    //图片的缩放比例
    private float scale;
    //图片的外框
    private Rect bitmapRect;

    //文字
    private String text = "";
    //文字的位置
    private Point textPoint;
    //文字的画笔
    private Paint paintText;
    //文字画笔的大小
    private int textSize;


    public PhotoEditView(Context context) {
        this(context, null);
    }

    public PhotoEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhotoEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //真正初始化的地方
        mHolder = getHolder();
        mHolder.addCallback(this);

        //可获取焦点
        setFocusable(true);
        setKeepScreenOn(true);
        setFocusableInTouchMode(true);
    }

    /**
     * 按下时的X,Y轴的坐标
     */
    private int touchDownX, touchDownY;
    private Point tempPoint = new Point();

    /**
     * 触摸监听事件
     *
     * @param v
     * @param event
     * @return
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //判断editAble---如果不可编辑--这不用判断触摸事件
        if (!editAble) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    //将当前文字位置保存
                    tempPoint.set(textPoint.x, textPoint.y);
                    //获取按下的位置
                    touchDownX = (int) event.getX();
                    touchDownY = (int) event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    //只有在方框内---才可以
                    textPoint.set(tempPoint.x + ((int) event.getX() - touchDownX),
                            tempPoint.y + ((int) event.getY() - touchDownY));
                    // Log.e(TAG, "i am moving");
                    break;
                case MotionEvent.ACTION_UP:
                    //判断是不是---整个文字---都在图片外面
                    if (textPoint.x + paintText.measureText(text) < bitmapRect.left ||
                            textPoint.y < bitmapRect.top ||
                            textPoint.x > bitmapRect.right ||
                            textPoint.y - paintText.measureText("字") > bitmapRect.bottom) {
                        textPoint = new Point(getWidth() / 2, getHeight() / 2);
                    }
                    break;
            }
        }
        return true;
    }


    /**
     * 真正画图的地方
     *
     * @param holder
     */
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //初始化一些数据

        //文字的位置---大小
        textPoint = new Point(getWidth() / 2, getHeight() / 2);
        textSize = getWidth() / TEXT_SCALE_MEDIUM;

        //初始化文字画笔
        paintText = new Paint();
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(textSize);
        paintText.setFlags(Paint.ANTI_ALIAS_FLAG);

        //初始化监听事件
        this.setOnTouchListener(this);
        //开启线程
        isRunning = true;
        t = new Thread(this);
        t.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
    }

    /**
     * 绘图线程的实现
     */
    @Override
    public void run() {
        //只要SurfaceView还在---就不断的绘制
        while (isRunning) {
            long start = System.currentTimeMillis();
            //最重要的方法
            draw();
            long end = System.currentTimeMillis();
            //防止刷新频率过快
            if ((end - start) < 50) {
                try {
                    Thread.sleep(50 - (end - start));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 真正画图的地方
     */
    private void draw() {
        try {
            mCanvas = mHolder.lockCanvas();
            if (mCanvas != null) {
                //draw something
                drawBitmap();

                drawText();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (mCanvas != null) {
                mHolder.unlockCanvasAndPost(mCanvas);
            }
        }
    }


    /**
     * 根据图片大小画出---当前编辑的图片
     */
    private void drawBitmap() {
        mCanvas.drawColor(Color.BLACK);
        if (bitmap != null) {
            //屏幕宽高
            float width = getWidth();
            float height = getHeight();
            float bitmapWidth = bitmap.getWidth();
            float bitmapHeight = bitmap.getHeight();
            //如果是宽度--比较大
            if (bitmapWidth / width > bitmapHeight / height) {
                scale = bitmapWidth / width;
                //图片方框的---长宽
                float bitmapRectWidth =  (bitmapWidth / scale);
                float bitmapRectHeight = (bitmapHeight / scale);
                bitmapRect = new Rect(0, (int)(height - bitmapRectHeight) / 2,
                        (int)width, (int)((height - bitmapRectHeight) / 2 + bitmapRectHeight));
                mCanvas.drawBitmap(bitmap, null
                        , bitmapRect
                        , new Paint());
            } else {
                scale = bitmapHeight / height;
                float bitmapRectWidth =  (bitmapWidth / scale);
                float bitmapRectHeight = (bitmapHeight / scale);
                bitmapRect = new Rect((int)(width- bitmapRectWidth)/2, 0,
                        (int)((width- bitmapRectWidth)/2+bitmapRectWidth), (int)height);
                mCanvas.drawBitmap(bitmap, null
                        , bitmapRect
                        , new Paint());
            }
        }
    }

    /**
     * 画出文字
     */
    private void drawText() {
//        Log.e(TAG, textPoint.x+"  "+textPoint.y);
        mCanvas.drawText(text, textPoint.x, textPoint.y, paintText);
    }

    /**
     * 根据文件大小判断----如何存储bitmap
     */
    public void saveBitmap(){
        if(bitmap.getWidth()*bitmap.getHeight()>3000000){
            Log.e(TAG, "i save the small bitmap");
//            saveSmallBitmap();
        }else{
            Log.e(TAG, "i save the big bitmap ");
//            saveBigBitmap();
        }
    }

    /**
     * 将编辑完成的图片保存下来
     */
    public void saveBigBitmap() {
        //创建新的Bitmap---获取对应的画布
        Bitmap saveBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight()
                , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(saveBitmap);
        //大小自动适配的Paint
        Paint textPaint = new Paint();
        textPaint.setTextSize(textSize * scale);
        textPaint.setColor(Color.WHITE);
        //画出背景---画出文字
        canvas.drawBitmap(bitmap, 0, 0, new Paint());
        canvas.drawText(text,
                (textPoint.x - (getWidth() - bitmapRect.width()) / 2) * scale,
                (textPoint.y - (getHeight() - bitmapRect.height()) / 2) * scale,
                textPaint);
        //存数图片---为了不阻塞主界面
        new SaveFileTask().execute(saveBitmap);
    }

    public void saveSmallBitmap(){
        //创建新的Bitmap---获取对应的画布
        Bitmap saveBitmap = Bitmap.createBitmap(bitmapRect.width(), bitmapRect.height()
                , Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(saveBitmap);
        //大小自动适配的Paint
        Paint textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setColor(Color.WHITE);
        //画出背景---画出文字
//        Log.e(TAG, bitmapRect.width() + "---" + bitmapRect.height());
//        Log.e(TAG, bitmap.getWidth()+ "  "+bitmap.getHeight());
        Rect drawRect = new Rect(0, 0, bitmapRect.width(), bitmapRect.height());
        canvas.drawBitmap(bitmap, null, drawRect, new Paint());
        canvas.drawText(text,
                (textPoint.x - (getWidth() - bitmapRect.width()) / 2),
                (textPoint.y - (getHeight() - bitmapRect.height()) / 2),
                textPaint);
        //存数图片---为了不阻塞主界面
        new SaveFileTask().execute(saveBitmap);
    }

    /**
     * 存储图片的新线程---将新生成的图片覆盖原来的图片
     */
    class SaveFileTask extends AsyncTask<Bitmap, Void, Void>{
        @Override
        protected Void doInBackground(Bitmap... params) {
            //保存图片
            try {
                File file = new File(path);
                FileOutputStream fos;
                fos = new FileOutputStream(file);
                params[0].compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            ToastUtil.showToast(getContext(), "图片修改已保存!");
        }
    }

    //Getter-------and-----setter--------------------------------

    public boolean isEditAble() {
        return editAble;
    }

    public void setEditAble(boolean editAble) {
        this.editAble = editAble;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap, String path) {
        this.bitmap = bitmap;
        this.path = path;
    }

}
