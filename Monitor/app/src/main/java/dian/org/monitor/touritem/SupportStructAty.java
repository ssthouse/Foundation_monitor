package dian.org.monitor.touritem;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import dian.org.monitor.Constant;
import dian.org.monitor.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_struct_aty);

        //初始化一些数据
        tourItem = (TourItem) getIntent().getSerializableExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM);
        //TODO    file的存储路径要怎么弄??
        picturePath = Constant.PICTURE_PATH + tourItem.getPrjName() + tourItem.getNumber();

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
        //TODO  测试代码
        gvPicture = (GridView) findViewById(R.id.id_gv_picture);
        gvPicture.setAdapter(new GridViewAdapter(this, tourItem));
        for(int i=0; i<gvPicture.getCount(); i++){
            if(gvPicture.getChildAt(i)==null){
                Log.e(TAG, "我的child是空的");
            }
            //判断是否为最后一个
            if(i==(gvPicture.getCount()-1)){

            }
        }
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
        if (requestCode == REQUEST_CODE_PICK_IMAGE) {
            Uri uri = data.getData();
            //to do find the path of pic

        } else if (requestCode == REQUEST_CODE_CAPTURE_CAMEIA) {
            Uri uri = data.getData();
            if (uri == null) {
                //use bundle to get data
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap photo = (Bitmap) bundle.get("data"); //get bitmap
                    //spath :生成图片取个名字和路径包含类型
                    PictureManager.saveImage(photo, picturePath);
                } else {
                    Toast.makeText(getApplicationContext(), "err****", Toast.LENGTH_LONG).show();
                    return;
                }
            } else {
                //to do find the path of pic by uri
            }
        }
    }
}
