package dian.org.monitor.touritem;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import dian.org.monitor.Constant;
import dian.org.monitor.R;
import dian.org.monitor.style.TransparentStyle;
import dian.org.monitor.util.EditTextUtil;
import dian.org.monitor.util.NetWorkUtil;
import dian.org.monitor.util.ToastUtil;

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
                    ToastUtil.showToast(WeatherStateAty.this, "数据不可为空!");
                } else {
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
        if (weatherState != null) {
            etTemperature.setText(weatherState.getTemperatureItem1());
            etRainFall.setText(weatherState.getRainFallItem2());
            etWindSpeed.setText(weatherState.getWindSpeedItem3());
            etWaterLevel.setText(weatherState.getWaterLevelItem4());
        }

        //智能填充
        TextView tvSmartFill = (TextView) findViewById(R.id.id_tv_smart_fill);
        tvSmartFill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //根据当前网络数据----获取当前数据
                smartFill();
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

    /**
     * 智能填充
     */
    private void smartFill(){
        //监测网络是否可用
        if(!NetWorkUtil.isNetworkAvailable(this)){
            Toast.makeText(this, "当前网络不可用", Toast.LENGTH_SHORT).show();
            return;
        }
        //&lon=116.39277&lat=39.933748
        //TODO 获取经纬度
        float lon = 116.39277f;
        float lat = 39.933748f;
        //获取完整的请求字符串
        String urlAll = "http://v.juhe.cn/weather/geo?format=2&key=2924914fd4108220c3470612914aa336" +
                "&lon="+lon+"&lat="+lat;
        String charSet = "UTF-8";
        //开启任务线程
        new AsyncTask<String, String, String>(){
            @Override
            protected String doInBackground(String... params) {
                String result = getJsonString(params[0], params[1]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                try {
                    //创建JSONBoject
                    JSONObject jsonObject = new JSONObject(s);
                    Log.e(TAG, jsonObject.toString());
                    //获取result的Object
                    JSONObject resultObject = (JSONObject)jsonObject.get("result");
                    Log.e(TAG, resultObject.toString());
                    JSONObject skObject = (JSONObject) resultObject.get("sk");
                    //获取数据
                    String temp = (String) skObject.get("temp");
                    String wind = (String)skObject.get("wind_direction")+
                            skObject.get("wind_strength");
                    Log.e(TAG, "温度:"+temp+"风速"+wind);

                    //创建返回数据
                    weatherState.setTemperatureItem1(temp);
                    weatherState.setWindSpeedItem3(wind);

                    //更新界面
                    etTemperature.setText(weatherState.getTemperatureItem1());
                    etWindSpeed.setText(weatherState.getWindSpeedItem3());
                    Toast.makeText(WeatherStateAty.this, "填充完成", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(TAG, "something is wrong");
                }
            }
        }.execute(urlAll, charSet);
//        Log.e(TAG, weatherState.getTemperatureItem1() + weatherState.getWindSpeedItem3());
    }

    /**
     * @param urlAll:请求接口
     * @param charset:字符编码
     * @return 返回json结果
     */
    public static String getJsonString(String urlAll, String charset) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL(urlAll);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(30000);
            connection.setConnectTimeout(30000);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(
                    is, charset));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
            }
            reader.close();
            result = sbf.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }
}
