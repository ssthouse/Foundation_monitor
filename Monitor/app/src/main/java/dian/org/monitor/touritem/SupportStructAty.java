package dian.org.monitor.touritem;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import dian.org.monitor.Constant;
import dian.org.monitor.R;
import dian.org.monitor.style.TransparentStyle;
import dian.org.monitor.util.DialogManager;
import dian.org.monitor.util.PictureManager;

/**
 * 调用该Activity必须传递TourItem
 * Created by ssthouse on 2015/6/11.
 */
public class SupportStructAty extends Activity {
    private static final String TAG = "SupportStructAty****";

    //***************requestCode********************************
    private static final int REQUEST_CODE_PICK_IMAGE = 1000;
    private static final int REQUEST_CODE_CAPTURE_CAMEIA = 1001;

    /**
     * 传递的数据
     */
    private TourItem tourItem;

    /**
     * 照片的路径
     */
    private String picturePath;

    /**
     * 展示照片的gridView
     */
    private GridView gvPicture;

    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_struct_aty);


        //初始化一些数据
        tourItem = (TourItem) getIntent().getSerializableExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM);
        //初始化当前Activity所在的项目的picture路径
        picturePath = PictureManager.PICTURE_PATH + tourItem.getPrjName() + "/" +
                tourItem.getNumber() +"/"+ PictureManager.PICTURE_PATH_SUPPORT_STRUCT;

        //透明顶栏
        TransparentStyle.setAppToTransparentStyle(this, getResources().getColor(R.color.blue_level0));
        initView();
    }


    /**
     * 初始化View
     */
    private void initView() {
        //返回
        ImageView ivBack = (ImageView) findViewById(R.id.id_iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //保存
        TextView tvSave = (TextView) findViewById(R.id.id_tv_save);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO  保存数据
            }
        });

        //照片选择的GridView
        gvPicture = (GridView) findViewById(R.id.id_gv_picture);
        gridViewAdapter = new GridViewAdapter(this, tourItem, PictureManager.PICTURE_PATH_SUPPORT_STRUCT);
        gvPicture.setAdapter(gridViewAdapter);
        gvPicture.setColumnWidth(GridViewAdapter.gridColumnWidth);
    }

    /**
     * 初始化gvPicture
     */
    private void initGvPicture(){
        //在这里设置GridView的细节点击事件
        Log.e(TAG, "我现在还有---个"+gvPicture.getCount());
        for (int i = 0; i < gvPicture.getCount(); i++) {
            final int position = i;
            //判断是否为最后一个
            if (i < (gvPicture.getCount() - 1)) {
                View view = gvPicture.getChildAt(i);
                ImageView ivDelete = (ImageView) view.findViewById(R.id.id_iv_grid_view_delete);
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridViewAdapter.deletePicture(position);
                        gvPicture.postInvalidate();
                        //触发焦点变化!!!!!!!!!!
                        DialogManager.showInVisiableDialog(SupportStructAty.this);
                    }
                });
            } else {
                //TODO
                //启动相机或者图库----获取数据---改变adapter---notify数据变化
                View view = gvPicture.getChildAt(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //开启图库
                        PictureManager.getImageFromAlbum(SupportStructAty.this,
                                REQUEST_CODE_PICK_IMAGE);
                    }
                });
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //如果是获取焦点
        if (hasFocus) {
            initGvPicture();
        }
        super.onWindowFocusChanged(hasFocus);
    }

    /**
     * 获取照片的回调方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_PICK_IMAGE && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String srcPath = cursor.getString(columnIndex);
            cursor.close();
            //将照片保存到指定文件夹
            PictureManager.saveImage(srcPath, picturePath +
                    PictureManager.getPictureNumber(picturePath) + ".jpeg");
            //更新界面
            gridViewAdapter = new GridViewAdapter(this, tourItem,
                    PictureManager.PICTURE_PATH_SUPPORT_STRUCT);
            gvPicture.setAdapter(gridViewAdapter);
            DialogManager.showInVisiableDialog(this);
        } else {
            Log.e(TAG, "something is wrong!!!");
        }
//        else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
//            Uri uri = data.getData();
//            if (uri == null) {
//                //use bundle to get data
//                Bundle bundle = data.getExtras();
//                if (bundle != null) {
//                    Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
//                    //spath :生成图片取个名字和路径包含类型
//                    PictureManager.saveImage(photo, picturePath);
//                } else {
//                    Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();
//                    return;
//                }
//            } else {
//                //to do find the path of pic by uri
//            }
//        }
    }
}
