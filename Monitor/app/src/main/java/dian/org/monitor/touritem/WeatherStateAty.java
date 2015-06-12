package dian.org.monitor.touritem;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import dian.org.monitor.R;
import dian.org.monitor.style.TransparentStyle;

/**
 * 编辑weatherState的Activity
 * Created by ssthouse on 2015/6/11.
 */
public class WeatherStateAty extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_state_aty);


        //透明顶栏
        TransparentStyle.setAppToTransparentStyle(this, getResources().getColor(R.color.blue_level0));
        initView();
    }

    /**
     * 初始化View
     */
    private void initView(){
        //返回
        ImageView ivBack = (ImageView) findViewById(R.id.id_iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDialog();
            }
        });

        //保存
        TextView tvSave = (TextView) findViewById(R.id.id_tv_save);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO---获取EditText数据---保存数据---回调TourEditAty
                finish();
            }
        });

        //智能填充
        TextView tvSmartFill = (TextView) findViewById(R.id.id_tv_smart_fill);
        tvSmartFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 根据当前网络数据----获取当前数据
                //TODO 填充四项数据
            }
        });
    }

    /**
     * 显示确认退出的Dialog
     */
    private void showExitDialog() {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder.withTitle("确认退出?")             //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")
                .withDividerColor("#11000000")
                .withMessage("退出将不会保存当前编辑的数据!")//.withMessage(null)  no Msg
                .withMessageColor("#FFFFFFFF")
                .withDialogColor(getResources().getColor(R.color.dialog_color))
                .withEffect(Effectstype.Slidetop)       //def Effectstype.Slidetop
                .withButton1Text("确认")                 //def gone
                .withButton2Text("取消")                 //def gone
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .withDuration(400)
                .show();
    }
}
