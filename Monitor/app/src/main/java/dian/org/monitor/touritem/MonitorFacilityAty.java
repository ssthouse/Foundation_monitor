package dian.org.monitor.touritem;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import dian.org.monitor.Constant;
import dian.org.monitor.R;
import dian.org.monitor.style.TransparentStyle;
import dian.org.monitor.test.PhotoEditAty;
import dian.org.monitor.util.DialogManager;
import dian.org.monitor.util.EditTextUtil;
import dian.org.monitor.util.PictureManager;

/**
 * Created by ssthouse on 2015/6/11.
 */
public class MonitorFacilityAty extends Activity {
    private static final String TAG = "MonitorFacilityAty****";

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

    //一些EditText
    private EditText etBaseStateItem1;
    private EditText etDeviceStateItem2;
    private EditText etWorkStateItem3;

    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor_facility_aty);
        //初始化数据
        tourItem = (TourItem) getIntent().getSerializableExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM);
        //初始化当前Activity所在的项目的picture路径
        picturePath = PictureManager.PICTURE_PATH + tourItem.getPrjName() + "/" +
                tourItem.getTourNumber() + "/"
                + PictureManager.PICTURE_PATH_MONITOR_FACILITY;
        //透明顶栏
        TransparentStyle.setAppToTransparentStyle(this, getResources().getColor(R.color.blue_level0));
        //初始化View
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
                //确保没有空项
                if (EditTextUtil.isEmpty(etBaseStateItem1) || EditTextUtil.isEmpty(etDeviceStateItem2)
                        || EditTextUtil.isEmpty(etWorkStateItem3)) {
                    Toast.makeText(MonitorFacilityAty.this, "不可有空项!", Toast.LENGTH_SHORT).show();
                } else {
                    //填充数据
                    tourItem.getMonitorFacility().setBaseStateItem1(etBaseStateItem1.getText().toString());
                    tourItem.getMonitorFacility().setDeviceStateItem2(etDeviceStateItem2.getText().toString());
                    tourItem.getMonitorFacility().setWorkStateItem3(etWorkStateItem3.getText().toString());
                    //回调数据
                    Intent intent = getIntent();
                    intent.putExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM, tourItem);
                    setResult(Constant.RESULT_CODE_SAVE, intent);
                    finish();
                }
            }
        });

        //一些Editext
        etBaseStateItem1 = (EditText) findViewById(R.id.id_et_base_state_item1);
        etDeviceStateItem2 = (EditText) findViewById(R.id.id_et_device_state_item2);
        etWorkStateItem3 = (EditText) findViewById(R.id.id_et_work_state_item3);
        if (tourItem != null) {
            etBaseStateItem1.setText(tourItem.getMonitorFacility().getBaseStateItem1());
            etDeviceStateItem2.setText(tourItem.getMonitorFacility().getDeviceStateItem2());
            etWorkStateItem3.setText(tourItem.getMonitorFacility().getWorkStateItem3());
        }

        //照片选择的GridView
        gvPicture = (GridView) findViewById(R.id.id_gv_picture);
        gridViewAdapter = new GridViewAdapter(this, tourItem, PictureManager.PICTURE_PATH_MONITOR_FACILITY);
        gvPicture.setAdapter(gridViewAdapter);
        gvPicture.setColumnWidth(GridViewAdapter.gridColumnWidth);
    }

    /**
     * 初始化gvPicture
     */
    private void initGvPicture() {
//        Log.e(TAG, "我现在还有---个" + gvPicture.getCount());
        for (int i = 0; i < gvPicture.getCount(); i++) {
            final int position = i;
            //判断是否为最后一个
            if (i < (gvPicture.getCount() - 1)) {
                //为图片点击获取查看监听器
                View view = gvPicture.getChildAt(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MonitorFacilityAty.this, PhotoEditAty.class);
                        String picPath = gridViewAdapter.getBitmapItemList().get(position).getPath();
                        intent.putExtra("data", picPath);
                        startActivityForResult(intent, Constant.REQUEST_CODE_EDIT_PICTURE);
                    }
                });
                //为图片删除设置监听事件
                ImageView ivDelete = (ImageView) view.findViewById(R.id.id_iv_grid_view_delete);
                ivDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        gridViewAdapter.deletePicture(position);
                        gvPicture.postInvalidate();
                        //触发焦点变化!!!!!!!!!!
                        DialogManager.showInVisiableDialog(MonitorFacilityAty.this);
                    }
                });
            } else {
                //启动相机或者图库----获取数据---改变adapter---notify数据变化
                View view = gvPicture.getChildAt(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //开启图片来源选择Dialog
                        DialogManager.showAlbumOrCameraDialog(MonitorFacilityAty.this);
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
        //判断是图库---还是照相机
        if (requestCode == Constant.REQUEST_CODE_ALBUM && null != data) {
            //根据uri获取图片路径
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
                    Calendar.getInstance().getTimeInMillis() + ".jpeg");
            //更新界面
            gridViewAdapter = new GridViewAdapter(this, tourItem,
                    PictureManager.PICTURE_PATH_MONITOR_FACILITY);
            gvPicture.setAdapter(gridViewAdapter);
            //改变焦点
            DialogManager.showInVisiableDialog(this);
        } else if (requestCode == Constant.REQUEST_CODE_CAMERA && null != data) {
            Uri uri = data.getData();
            if (uri == null) {
                Log.e(TAG, "拍照的uri是空的!!!");
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                    //直接将Bitmap保存到指定路径
                    PictureManager.saveImage(photo, picturePath +
                            Calendar.getInstance().getTimeInMillis() + ".jpeg");
                    //更新界面
                    gridViewAdapter = new GridViewAdapter(this, tourItem,
                            PictureManager.PICTURE_PATH_MONITOR_FACILITY);
                    gvPicture.setAdapter(gridViewAdapter);
                    //改变焦点
                    DialogManager.showInVisiableDialog(this);
                } else {
                    Toast.makeText(getApplicationContext(), "该照片获取失败!", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                Log.e(TAG, "拍照的uri不是空的");
                //根据uri获取图片路径
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
                        Calendar.getInstance().getTimeInMillis() + ".jpeg");
                //更新界面
                gridViewAdapter = new GridViewAdapter(this, tourItem,
                        PictureManager.PICTURE_PATH_MONITOR_FACILITY);
                gvPicture.setAdapter(gridViewAdapter);
                //改变焦点
                DialogManager.showInVisiableDialog(this);
            }
        }else if(requestCode == Constant.REQUEST_CODE_EDIT_PICTURE) {
//            Log.e(TAG, "我冲编辑界面回来了");
            //更新界面
            gridViewAdapter = new GridViewAdapter(this, tourItem,
                    PictureManager.PICTURE_PATH_MONITOR_FACILITY);
            gvPicture.setAdapter(gridViewAdapter);
            //改变焦点
            DialogManager.showInVisiableDialog(this);
        }
    }
}
