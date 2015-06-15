package dian.org.monitor;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.Calendar;

import dian.org.monitor.db.DbFileManager;
import dian.org.monitor.db.TourDbHelper;
import dian.org.monitor.style.TransparentStyle;
import dian.org.monitor.touritem.ConstructStateAty;
import dian.org.monitor.touritem.MonitorFacilityAty;
import dian.org.monitor.touritem.SupportStructAty;
import dian.org.monitor.touritem.SurroundEnvAty;
import dian.org.monitor.touritem.TourItem;
import dian.org.monitor.touritem.WeatherState;
import dian.org.monitor.touritem.WeatherStateAty;
import dian.org.monitor.util.StringUtil;

/**
 * Created by ssthouse on 2015/6/10.
 * 开启该Activity需要---传入一个TourItem
 */
public class TourEditAty extends Activity {
    private static final String TAG = "TourEditAty*******";

    //requestcode**************************
    private static final int REQUEST_CODE_WEATHER_STATE = 1001;

    private static final int REQUEST_CODE_SUPPORT_STRUCT = 1002;

    private static final int REQUEST_CODE_CONSTRUCT_STATE = 1003;

    private static final int REQUEST_CODE_SURROUND_ENV = 1004;

    private static final int REQUEST_CODE_MONITOR_FACILITY = 1005;
    //*********************************************

    /**
     * 修改的数据
     */
    private TourItem tourItem;

    private TextView tvNumber;

    private TextView tvObserver;

    private TextView tvDate;

    private TextView tvWeatherState;

    private TextView tvSupportStruct;

    private TextView tvConstructState;

    private TextView tvSurroundEnv;

    private TextView tvMonitorFacility;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_edit_aty);
        //透明顶栏
        TransparentStyle.setAppToTransparentStyle(this, getResources().getColor(R.color.blue_level0));
        //获取TourItem数据
        Intent intent = getIntent();
        tourItem = (TourItem) intent.getSerializableExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM);
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        //返回按钮
        ImageView ivBack = (ImageView) findViewById(R.id.id_iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提示对话框
                showExitDialog();
            }
        });

        //ActionBar工程名
        TextView tvAbPrjName = (TextView) findViewById(R.id.id_tv_ab_prjName);
        tvAbPrjName.setText(tourItem.getTourInfo().getPrjName());

        //保存TextView
        TextView tvSave = (TextView) findViewById(R.id.id_tv_save);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e(TAG, "我点击了保存");
                //打开对应数据库
                SQLiteDatabase db = DbFileManager.getDb(tourItem);
                TourDbHelper.setTourItem(db, tourItem);
                //保存数据
                finish();
            }
        });

        //工程名
        TextView tvPrjName = (TextView) findViewById(R.id.id_tv_prjName);
        tvPrjName.setText(tourItem.getTourInfo().getPrjName());

        //观测次数
        tvNumber = (TextView) findViewById(R.id.id_tv_number);
        tvNumber.setText("第 "+tourItem.getTourInfo().getTourNumber()+" 次");
        LinearLayout llNumber = (LinearLayout) findViewById(R.id.id_ll_number);
        llNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNumberEditDialog();
            }
        });

        //观测者
        tvObserver = (TextView) findViewById(R.id.id_tv_observer);
        tvObserver.setText(tourItem.getTourInfo().getObserver());
        LinearLayout llObserver = (LinearLayout) findViewById(R.id.id_ll_observer);
        llObserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showObserverEditDialog();
            }
        });

        //观测日期
        tvDate = (TextView) findViewById(R.id.id_tv_date);
        tvDate.setText(StringUtil.getFormatDate(StringUtil.getCalendarFromTimeInMiles(
                tourItem.getTourInfo().getTimeInMilesStr())));
        LinearLayout llDate = (LinearLayout) findViewById(R.id.id_ll_date);
        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        //自然条件
        tvWeatherState = (TextView) findViewById(R.id.id_tv_weather_state);
        LinearLayout llWeatherState = (LinearLayout) findViewById(R.id.id_ll_weather_state);
        llWeatherState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourEditAty.this, WeatherStateAty.class);
                intent.putExtra("data", tourItem.getWeatherState());
                if(tourItem.getWeatherState()==null){
                    Log.e(TAG, "天啊...我传递给WeatherStateAty的数据竟然是空的!!!");
                }
                startActivityForResult(intent, REQUEST_CODE_WEATHER_STATE);
            }
        });

        //支护结构
        tvSupportStruct = (TextView) findViewById(R.id.id_tv_support_struct);
        LinearLayout llSupportStruct = (LinearLayout) findViewById(R.id.id_ll_support_struct);
        llSupportStruct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourEditAty.this, SupportStructAty.class);
                intent.putExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM, tourItem);
                startActivityForResult(intent, REQUEST_CODE_SUPPORT_STRUCT);
            }
        });

        //施工工况
        tvConstructState = (TextView) findViewById(R.id.id_tv_construct_state);
        LinearLayout llConstructState = (LinearLayout) findViewById(R.id.id_ll_construct_state);
        llConstructState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourEditAty.this, ConstructStateAty.class);
                intent.putExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM, tourItem);
                startActivityForResult(intent, REQUEST_CODE_CONSTRUCT_STATE);
            }
        });

        //周边情况
        tvSurroundEnv = (TextView) findViewById(R.id.id_tv_surround_env);
        LinearLayout llSurroundEnv = (LinearLayout) findViewById(R.id.id_ll_surround_env);
        llSurroundEnv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourEditAty.this, SurroundEnvAty.class);
                intent.putExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM, tourItem);
                startActivityForResult(intent, REQUEST_CODE_SURROUND_ENV);
            }
        });

        //监测设施
        tvMonitorFacility = (TextView) findViewById(R.id.id_tv_monitor_facility);
        LinearLayout llMonitorFacility = (LinearLayout) findViewById(R.id.id_ll_monitor_facility);
        llMonitorFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourEditAty.this, MonitorFacilityAty.class);
                intent.putExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM, tourItem);
                startActivityForResult(intent, REQUEST_CODE_MONITOR_FACILITY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        Log.e(TAG, "我回调了----tourEditAty");
        switch (requestCode) {
            case REQUEST_CODE_WEATHER_STATE:
                Log.e(TAG, "我回调了---TourEditActivity----weatherState");
                if (resultCode == Constant.RESULT_CODE_SAVE) {
                    //更新weatherState数据
                    WeatherState weatherState = (WeatherState) data.getSerializableExtra("data");
                    tourItem.setWeatherState(weatherState);
                    Log.e(TAG, "我改变了weatherState");
                } else if (resultCode == Constant.RESULT_CODE_CANCEL) {
                    //do nothing
                }
                break;
            case REQUEST_CODE_SUPPORT_STRUCT:
                if (resultCode == Constant.RESULT_CODE_SAVE) {
                    //更新数据---如果不为空的话
                    TourItem tourItem = (TourItem) data.getSerializableExtra(
                            Constant.INTENT_KEY_DATA_TOUR_ITEM);
                    if(tourItem != null){
                        this.tourItem = tourItem;
                    }
                    Log.e(TAG, "我改变了SupportStruct");
                }
                break;
            case REQUEST_CODE_CONSTRUCT_STATE:
                if (resultCode == Constant.RESULT_CODE_SAVE) {
                    //更新数据---如果不为空的话
                    TourItem tourItem = (TourItem) data.getSerializableExtra(
                            Constant.INTENT_KEY_DATA_TOUR_ITEM);
                    if(tourItem != null){
                        this.tourItem = tourItem;
                    }
                    Log.e(TAG, "我改变了construct_state");
                }
                break;
            case REQUEST_CODE_SURROUND_ENV:
                if (resultCode == Constant.RESULT_CODE_SAVE) {
                    //更新数据---如果不为空的话
                    TourItem tourItem = (TourItem) data.getSerializableExtra(
                            Constant.INTENT_KEY_DATA_TOUR_ITEM);
                    if(tourItem != null){
                        this.tourItem = tourItem;
                    }
                    Log.e(TAG, "我改变了surround_env");
                }
                break;
            case REQUEST_CODE_MONITOR_FACILITY:
                if (resultCode == Constant.RESULT_CODE_SAVE) {
                    //更新数据---如果不为空的话
                    TourItem tourItem = (TourItem) data.getSerializableExtra(
                            Constant.INTENT_KEY_DATA_TOUR_ITEM);
                    if(tourItem != null){
                        this.tourItem = tourItem;
                    }
                    Log.e(TAG, "我改变了monitor_facility");
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        showExitDialog();
    }

    //Dialog****************************************************************************************

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
     * 显示巡视次数选择Dialog
     */
    private void showNumberEditDialog() {
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        dialogBuilder.withTitle("观测次数")             //.withTitle(null)  no title
                .withTitleColor("#FFFFFF")
                .withDividerColor("#11000000")
                .withMessage(null)                      //.withMessage(null)  no Msg
                .withDialogColor(getResources().getColor(R.color.dialog_color))
                .withEffect(Effectstype.Slidetop)       //def Effectstype.Slidetop
                .withButton1Text("确认")                 //def gone
                .withButton2Text("取消")                 //def gone
                .isCancelableOnTouchOutside(false)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //修改数据
                        EditText etNumber = (EditText) dialogBuilder.
                                findViewById(R.id.id_et_number_dialog);
                        //判断是否为空
                        if (!etNumber.getText().toString().equals("")) {
                            int number = Integer.parseInt(etNumber.getText().toString());
                            if (number > 0) {
                                //更新数据
                                tourItem.getTourInfo().setTourNumber(number);
                                //更新界面
                                tvNumber.setText("第 " + tourItem.getTourInfo().getTourNumber() + " 次");
                            }
                            dialogBuilder.dismiss();
                        }
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .withDuration(400)
                .setCustomView(R.layout.tour_number_edit_dialog, this)
                .show();
    }

    /**
     * 显示观测者修改Dialog
     */
    private void showObserverEditDialog() {
        //新建dialog
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        //确定监听器
        View.OnClickListener sureListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //修改数据
                EditText etObserver = (EditText) dialogBuilder.
                        findViewById(R.id.id_et_observer_dialog);
                String strObserver = etObserver.getText().toString();
                //更新界面
                if (!strObserver.equals("")) {
                    if (strObserver.contains(" ")) {
                        Toast.makeText(TourEditAty.this, "观测人不可有空格!", Toast.LENGTH_SHORT).
                                show();
                    } else {
                        //更新数据
                        tourItem.getTourInfo().setObserver(strObserver);
                        //更新界面
                        tvObserver.setText(strObserver);
                        dialogBuilder.dismiss();
                    }
                }
            }
        };
        View.OnClickListener cancleListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        };
        dialogBuilder.withTitle("观测者")             //.withTitle(null)  no title
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
                .setCustomView(R.layout.tour_observer_edit_dialog, this)
                .show();
    }

    /**
     * 显示Date选择Dialog
     */
    private void showDatePickerDialog() {
        //新建dialog
        final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(this);
        //确定监听器
        View.OnClickListener sureListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取时间
                CalendarView calendarView = (CalendarView) dialogBuilder.
                        findViewById(R.id.id_cv_date_picker);
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(calendarView.getDate());
                //更新界面
                String strDate = StringUtil.getFormatDate(calendar);
                tvDate.setText(strDate);
                //更新数据
                tourItem.getTourInfo().setTimeInMilesStr(calendar.getTimeInMillis()+"");
                dialogBuilder.dismiss();
            }
        };
        View.OnClickListener cancleListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogBuilder.dismiss();
            }
        };
        dialogBuilder.withTitle("观测日期")             //.withTitle(null)  no title
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
                .setCustomView(R.layout.tour_date_picker_dialog, this)
                .show();
        //初始化dialogBuilder界面
        CalendarView calendarView = (CalendarView) dialogBuilder.findViewById(R.id.id_cv_date_picker);
        calendarView.setDate(Long.parseLong(tourItem.getTourInfo().getTimeInMilesStr()));
    }
}
