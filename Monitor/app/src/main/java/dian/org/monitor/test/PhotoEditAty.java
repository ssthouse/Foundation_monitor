package dian.org.monitor.test;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import dian.org.monitor.R;

/**
 * 开启当前Activity必须传递一个Bitmap
 * Created by ssthouse on 2015/7/9.
 */
public class PhotoEditAty extends Activity {

    private PhotoEditView photoEditView;

    private Bitmap bitmap;

    private String path;

    private Button btnCancel, btnAddText, btnComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_photo_aty);

        //获取数据
         path = getIntent().getStringExtra("data");
        bitmap = BitmapFactory.decodeFile(path);

        initView();
    }

    private void initView() {
        photoEditView = (PhotoEditView) findViewById(R.id.id_sv);
        photoEditView.setBitmap(bitmap, path);

        btnCancel = (Button) findViewById(R.id.id_btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddText = (Button) findViewById(R.id.id_btn_add_text);
        btnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //出现Dialog----输入要添加的文字
                showTextInputDialog();
            }
        });

        btnComplete = (Button) findViewById(R.id.id_btn_complete);
        btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //保存bitmap
                photoEditView.saveBitmap();
            }
        });

        //关闭缓冲条
        findViewById(R.id.id_pb_empty).setVisibility(View.INVISIBLE);
    }


    /**
     * 显示文字输入Dialog
     */
    private void showTextInputDialog() {
        //新建dialog
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        //确定监听器
        View.OnClickListener sureListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改数据
                EditText et = (EditText) dialogBuilder.
                        findViewById(R.id.id_et);
                String text = et.getText().toString();
                //更新界面
                if (!text.equals("")) {
                    //更新---surfaceView中画的数据
                    photoEditView.setText(text);
                    dialogBuilder.dismiss();
                }
            }
        };
        View.OnClickListener cancleListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        };
        dialogBuilder.withTitle("请输入文字")             //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")
                .withDividerColor("#11000000")
                .withMessage(null)                      //.withMessage(null)  no Msg
                .withDialogColor(getResources().getColor(R.color.dialog_color))
                .withEffect(Effectstype.Slidetop)       //def Effectstype.Slidetop
                .withButton1Text("确认")                 //def gone
                .withButton2Text("取消")                 //def gone
                .isCancelableOnTouchOutside(false)
                .setButton1Click(sureListener)
                .setButton2Click(cancleListener)
                .withDuration(400)
                .setCustomView(R.layout.photo_text_input, this)
                .show();
    }

}
