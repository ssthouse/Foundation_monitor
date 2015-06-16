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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import dian.org.monitor.Constant;
import dian.org.monitor.R;
import dian.org.monitor.style.TransparentStyle;
import dian.org.monitor.util.DialogManager;
import dian.org.monitor.util.EditTextUtil;
import dian.org.monitor.util.PictureManager;
import dian.org.monitor.util.PictureShowAty;

/**
 * 调用该Activity必须传递TourItem
 * Created by ssthouse on 2015/6/11.
 */
public class SupportStructAty extends Activity {
    private static final String TAG = "SupportStructAty****";

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
    private EditText etQualityItem1;
    private EditText etCrackItem2;
    private EditText etTransformItem3;
    private EditText etLeakItem4;
    private EditText etSlipItem5;
    private EditText etPourItem6;
    private EditText etOtherItem7;


    private GridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_struct_aty);

        //初始化数据
        tourItem = (TourItem) getIntent().getSerializableExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM);
        //初始化当前Activity所在的项目的picture路径
        picturePath = PictureManager.PICTURE_PATH + tourItem.getTourInfo().getPrjName() + "/" +
                tourItem.getTourInfo().getTourNumber() + "/"
                + PictureManager.PICTURE_PATH_SUPPORT_STRUCT;
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
                if (EditTextUtil.isEmpty(etQualityItem1) || EditTextUtil.isEmpty(etCrackItem2)
                        || EditTextUtil.isEmpty(etTransformItem3)
                        || EditTextUtil.isEmpty(etLeakItem4)
                        || EditTextUtil.isEmpty(etSlipItem5)
                        || EditTextUtil.isEmpty(etPourItem6)
                        || EditTextUtil.isEmpty(etOtherItem7)) {
                    Toast.makeText(SupportStructAty.this, "不可有空项!", Toast.LENGTH_SHORT).show();
                } else {
                    //填充数据
                    tourItem.getSupportStruct().setQualityItem1(etQualityItem1.getText().toString());
                    tourItem.getSupportStruct().setCrackItem2(etCrackItem2.getText().toString());
                    tourItem.getSupportStruct().setTransformItem3(etTransformItem3.getText().toString());
                    tourItem.getSupportStruct().setLeakItem4(etLeakItem4.getText().toString());
                    tourItem.getSupportStruct().setSlipItem5(etSlipItem5.getText().toString());
                    tourItem.getSupportStruct().setPourItem6(etPourItem6.getText().toString());
                    tourItem.getSupportStruct().setOtherItem7(etOtherItem7.getText().toString());
                    //回调数据
                    Intent intent = getIntent();
                    intent.putExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM, tourItem);
                    setResult(Constant.RESULT_CODE_SAVE, intent);
                    finish();
                }
            }
        });

        //一些Editext
        etQualityItem1 = (EditText) findViewById(R.id.id_et_quality_item1);
        etCrackItem2 = (EditText) findViewById(R.id.id_et_crack_item2);
        etTransformItem3 = (EditText) findViewById(R.id.id_et_transform_item3);
        etLeakItem4 = (EditText) findViewById(R.id.id_et_leak_item4);
        etSlipItem5 = (EditText) findViewById(R.id.id_et_slip_item5);
        etPourItem6 = (EditText) findViewById(R.id.id_et_pour_item6);
        etOtherItem7 = (EditText) findViewById(R.id.id_et_other_item7);
        if (tourItem != null) {
            etQualityItem1.setText(tourItem.getSupportStruct().getQualityItem1());
            etCrackItem2.setText(tourItem.getSupportStruct().getCrackItem2());
            etTransformItem3.setText(tourItem.getSupportStruct().getTransformItem3());
            etLeakItem4.setText(tourItem.getSupportStruct().getLeakItem4());
            etSlipItem5.setText(tourItem.getSupportStruct().getSlipItem5());
            etPourItem6.setText(tourItem.getSupportStruct().getPourItem6());
            etOtherItem7.setText(tourItem.getSupportStruct().getOtherItem7());
        }

        //照片选择的GridView
        gvPicture = (GridView) findViewById(R.id.id_gv_picture);
        gridViewAdapter = new GridViewAdapter(this, tourItem,
                PictureManager.PICTURE_PATH_SUPPORT_STRUCT);
        gvPicture.setAdapter(gridViewAdapter);
        gvPicture.setColumnWidth(GridViewAdapter.gridColumnWidth);
        gvPicture.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
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
                        //在图库中查看图片
//                        PictureManager.viewPictureFromAlbum(SupportStructAty.this,
//                                gridViewAdapter.getBitmapItemList().get(position));
                        if (position < gvPicture.getCount() - 1) {
                            Intent intent = new Intent(getApplicationContext(), PictureShowAty.class);
                            intent.putExtra("data",
                                    gridViewAdapter.getBitmapItemList().get(position).getPath());
                            startActivity(intent);
                        }
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
                        DialogManager.showInVisiableDialog(SupportStructAty.this);
                    }
                });
            } else {
                //启动相机或者图库----获取数据---改变adapter---notify数据变化
                View view = gvPicture.getChildAt(i);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //开启图片来源选择Dialog
                        DialogManager.showAlbumOrCameraDialog(SupportStructAty.this);
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
                    PictureManager.PICTURE_PATH_SUPPORT_STRUCT);
            gvPicture.setAdapter(gridViewAdapter);
            //改变焦点
            DialogManager.showInVisiableDialog(this);
        } else if (requestCode == Constant.RESULT_CODE_CAMERA && null != data) {
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
                            PictureManager.PICTURE_PATH_SUPPORT_STRUCT);
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
                        PictureManager.PICTURE_PATH_SUPPORT_STRUCT);
                gvPicture.setAdapter(gridViewAdapter);
                //改变焦点
                DialogManager.showInVisiableDialog(this);
            }
        }
    }
}
