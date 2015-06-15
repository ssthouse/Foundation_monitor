package dian.org.monitor.touritem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import dian.org.monitor.Constant;
import dian.org.monitor.R;
import dian.org.monitor.style.TransparentStyle;
import dian.org.monitor.util.EditTextUtil;

/**
 * 编辑weatherState的Activity
 * Created by ssthouse on 2015/6/11.
 */
public class WeatherStateAty extends Activity {
    private static final String TAG = "WeatherStateASty";

    /**
     * 该Activity编辑的数据
     */
    private WeatherState weatherState;

    private EditText etTemperature;

    private EditText etRainFall;

    private EditText etWindSpeed;

    private EditText etWaterLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_state_aty);
        //透明顶栏
        TransparentStyle.setAppToTransparentStyle(this, getResources().getColor(R.color.blue_level0));
        //获取数据
        weatherState = (WeatherState) getIntent().getSerializableExtra("data");
        if (weatherState == null) {
            Log.e(TAG, "天哪...我收到的weatherState竟然是空的!!!");
        }
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
                showExitDialog();
            }
        });

        //保存
        TextView tvSave = (TextView) findViewById(R.id.id_tv_save);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断数据是否有空的
                if (EditTextUtil.isEmpty(etTemperature) ||
                        EditTextUtil.isEmpty(etRainFall) ||
                        EditTextUtil.isEmpty(etWindSpeed) ||
                        EditTextUtil.isEmpty(etWaterLevel)) {
//                    ToastUtil.showToast(WeatherStateAty.this, "数据不可为空!");
                } else {
//                    ToastUtil.showToast(WeatherStateAty.this, "数据通过!");
//                    Log.e(TAG, "我即将回调tourEdit");
                    /*
                    将数据回调给调用的Activity
                     */
                    //先将改变的数据填充
                    weatherState.setTemperatureItem1(etTemperature.getText().toString());
                    weatherState.setRainFallItem2(etRainFall.getText().toString());
                    weatherState.setWindSpeedItem3(etWindSpeed.getText().toString());
                    weatherState.setWaterLevelItem4(etWaterLevel.getText().toString());
                    //将改好的数据放进Intent---并传回
                    Intent resultIntent = getIntent();
//                    Log.e(TAG, "我的第一项数据是:" + weatherState.getTemperatureItem1());
                    resultIntent.putExtra("data", weatherState);
                    setResult(Constant.RESULT_CODE_SAVE, resultIntent);
                    finish();
                }
            }
        });

        //四个数据的Edittext
        etTemperature = (EditText) findViewById(R.id.id_et_temperature);
        etRainFall = (EditText) findViewById(R.id.id_et_rainFall);
        etWindSpeed = (EditText) findViewById(R.id.id_et_windSpeed);
        etWaterLevel = (EditText) findViewById(R.id.id_et_waterLevel);

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
                        dialogBuilder.dismiss();
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

    @Override
    public void onBackPressed() {
        showExitDialog();
        super.onBackPressed();
    }
}
