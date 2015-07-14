package dian.org.monitor;

import android.app.Activity;
import android.content.Intent;
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

import dian.org.monitor.gps.GpsTestAty;
import dian.org.monitor.gps.LocationTracker;
import dian.org.monitor.style.TransparentStyle;
import dian.org.monitor.touritem.ConstructStateAty;
import dian.org.monitor.touritem.MonitorFacilityAty;
import dian.org.monitor.touritem.SupportStructAty;
import dian.org.monitor.touritem.SurroundEnvAty;
import dian.org.monitor.touritem.TourItem;
import dian.org.monitor.touritem.WeatherState;
import dian.org.monitor.touritem.WeatherStateAty;
import dian.org.monitor.util.DataBaseUtil;
import dian.org.monitor.util.PictureManager;
import dian.org.monitor.util.StringUtil;
import dian.org.monitor.util.ToastUtil;
import dian.org.monitor.util.WordGenereteUtil;

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

    private TextView tvGps;

    private LinearLayout llShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_edit_aty);
        //透明顶栏
        TransparentStyle.setAppToTransparentStyle(this, getResources().getColor(R.color.blue_level0));
        //获取TourItem数据
        Intent intent = getIntent();
        tourItem = (TourItem) intent.getSerializableExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM);
        Log.e(TAG, tourItem.getPrjName()+":"+ tourItem.getTourNumber());
        //如果是新建的TourItem----应该是从数据库获取数据
        tourItem = DataBaseUtil.getTourItemInDB(tourItem);
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
        tvAbPrjName.setText(tourItem.getPrjName());

        //保存TextView
        TextView tvSave = (TextView) findViewById(R.id.id_tv_save);
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e(TAG, "我点击了保存");
                //保存数据
                DataBaseUtil.saveTourItemAll(tourItem);
                finish();
            }
        });

        //工程名
        TextView tvPrjName = (TextView) findViewById(R.id.id_tv_prjName);
        tvPrjName.setText(tourItem.getPrjName());
        //观测次数
        tvNumber = (TextView) findViewById(R.id.id_tv_number);
        tvNumber.setText("第 " + tourItem.getTourNumber() + " 次");
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
        if (tourItem.getWeatherState().getTemperatureItem1().equals("")) {
            tvWeatherState.setText("未填写");
        } else {
            tvWeatherState.setText("");
        }
        LinearLayout llWeatherState = (LinearLayout) findViewById(R.id.id_ll_weather_state);
        llWeatherState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourEditAty.this, WeatherStateAty.class);
                intent.putExtra("data", tourItem.getWeatherState());
                if (tourItem.getWeatherState() == null) {
                    Log.e(TAG, "天啊...我传递给WeatherStateAty的数据竟然是空的!!!");
                }
                startActivityForResult(intent, REQUEST_CODE_WEATHER_STATE);
            }
        });

        //支护结构
        tvSupportStruct = (TextView) findViewById(R.id.id_tv_support_struct);
        if (tourItem.getSupportStruct().getQualityItem1().equals("")) {
            tvSupportStruct.setText("未填写");
        } else {
            tvSupportStruct.setText("");
        }
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
        if (tourItem.getConstructState().getSoilStateItem1().equals("")) {
            tvConstructState.setText("未填写");
        } else {
            tvConstructState.setText("");
        }
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
        if (tourItem.getSurroundEnv().getBreakStateItem1().equals("")) {
            tvSurroundEnv.setText("未填写");
        } else {
            tvSurroundEnv.setText("");
        }
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
        if (tourItem.getMonitorFacility().getBaseStateItem1().equals("")) {
            tvMonitorFacility.setText("未填写");
        } else {
            tvMonitorFacility.setText("");
        }
        LinearLayout llMonitorFacility = (LinearLayout) findViewById(R.id.id_ll_monitor_facility);
        llMonitorFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourEditAty.this, MonitorFacilityAty.class);
                intent.putExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM, tourItem);
                startActivityForResult(intent, REQUEST_CODE_MONITOR_FACILITY);
            }
        });

        //GPS记录数据
        //TODO ---需要监测---传回来额数据里面有没有GPS的db数据
        tvGps = (TextView) findViewById(R.id.id_tv_gps_state);
//        if (tourItem.getGps() == null) {
//        } else {
//            tvGps.setText("");
//        }
        LinearLayout llGps = (LinearLayout) findViewById(R.id.id_ll_gps);
        llGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                //如果有数据---打开Activity---显示GPS数据
                Intent intent = new Intent(TourEditAty.this, GpsTestAty.class);
                intent.putExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM, tourItem);
                startActivityForResult(intent, REQUEST_CODE_MONITOR_FACILITY);
                //否则---显示Dialog---没有数据
//                NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(TourEditAty.this);
//                dialogBuilder.withTitle("提醒")             //.withTitle(null)  no title
//                        .withTitleColor("#FFFFFF")
//                        .withDividerColor("#11000000")
//                        .withMessage("该次巡查没记录GPS数据")//.withMessage(null)  no Msg
//                        .withMessageColor("#FFFFFFFF")
//                        .withDialogColor(getResources().getColor(R.color.dialog_color))
//                        .withEffect(Effectstype.Slidetop)       //def Effectstype.Slidetop
//                        .isCancelableOnTouchOutside(true)
//                        .withDuration(400)
//                        .show();
            }
        });

        llShare = (LinearLayout) findViewById(R.id.id_ll_share);
        llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WordGenereteUtil.generateWordFile(TourEditAty.this, tourItem);
                //TODO ----分享
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
                    tourItem.getWeatherState().updateData(weatherState);
                    Log.e(TAG, "我改变了weatherState");
                    //刷新界面
                    tvWeatherState.setText("");
                }
                break;
            case REQUEST_CODE_SUPPORT_STRUCT:
                if (resultCode == Constant.RESULT_CODE_SAVE) {
                    //更新数据---如果不为空的话
                    TourItem tourItem = (TourItem) data.getSerializableExtra(
                            Constant.INTENT_KEY_DATA_TOUR_ITEM);
                    if (tourItem != null) {
                        this.tourItem.getSupportStruct().updateData(tourItem.getSupportStruct());
                    }
                    Log.e(TAG, "我改变了SupportStruct");
                    //刷新界面
                    tvSupportStruct.setText("");
                }
                break;
            case REQUEST_CODE_CONSTRUCT_STATE:
                if (resultCode == Constant.RESULT_CODE_SAVE) {
                    //更新数据---如果不为空的话
                    TourItem tourItem = (TourItem) data.getSerializableExtra(
                            Constant.INTENT_KEY_DATA_TOUR_ITEM);
                    if (tourItem != null) {
                        this.tourItem.getConstructState().updateData(tourItem.getConstructState());
                    }
                    Log.e(TAG, "我改变了construct_state");
                    //刷新界面
                    tvConstructState.setText("");
                }
                break;
            case REQUEST_CODE_SURROUND_ENV:
                if (resultCode == Constant.RESULT_CODE_SAVE) {
                    //更新数据---如果不为空的话
                    TourItem tourItem = (TourItem) data.getSerializableExtra(
                            Constant.INTENT_KEY_DATA_TOUR_ITEM);
                    if (tourItem != null) {
                        this.tourItem.getSurroundEnv().updateData(tourItem.getSurroundEnv());
                    }
                    Log.e(TAG, "我改变了surround_env");
                    //刷新界面
                    tvSurroundEnv.setText("");
                }
                break;
            case REQUEST_CODE_MONITOR_FACILITY:
                if (resultCode == Constant.RESULT_CODE_SAVE) {
                    //更新数据---如果不为空的话
                    TourItem tourItem = (TourItem) data.getSerializableExtra(
                            Constant.INTENT_KEY_DATA_TOUR_ITEM);
                    if (tourItem != null) {
                        this.tourItem.getMonitorFacility().updateData(tourItem.getMonitorFacility());
                    }
                    Log.e(TAG, "我改变了monitor_facility");
                    //刷新界面
                    tvMonitorFacility.setText("");
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
                        //如果是新建的---且没有保存--直接删除
                        if (getIntent().getIntExtra(Constant.INTENT_KEY_REQUEST_CODE, 0) ==
                                TourListAty.REQUEST_CODE_NEW) {
                            DataBaseUtil.deleteTourItemAll(tourItem);
                        }
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
                                //判断当前数字的数据库是否存在
                                if (!DataBaseUtil.isTourNumberAllowed(tourItem, number)) {
                                    ToastUtil.showToast(TourEditAty.this, "该观测次数意存在,不可重复!");
                                } else {
                                    //TODO---更改---图片文件---
                                    PictureManager.changeFileName(PictureManager.PICTURE_PATH +
                                                    tourItem.getPrjName() + "/",
                                            tourItem.getTourNumber() + "", number + "");
                                    //更新界面
                                    tvNumber.setText("第 " + number + " 次");
                                    //更新数据
                                    tourItem.setTourNumber(number);
                                    dialogBuilder.dismiss();
                                }
                            } else {
                                ToastUtil.showToast(TourEditAty.this, "观测次数不可为0!");
                            }
                        } else {
                            ToastUtil.showToast(TourEditAty.this, "观测次数不可为空!");
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
                tourItem.getTourInfo().setTimeInMilesStr(calendar.getTimeInMillis() + "");
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        LocationTracker.stopWorking();
    }
}
