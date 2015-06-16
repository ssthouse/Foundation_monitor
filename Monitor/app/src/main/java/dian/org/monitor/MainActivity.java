package dian.org.monitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.io.File;

import dian.org.monitor.db.DbFileManager;
import dian.org.monitor.style.TransparentStyle;

/**
 * 展示项目列表的主界面
 * Created by ssthouse on 2015/6/8.
 */
public class MainActivity extends Activity {
    private static final String TAG = "MainActivity";

    private static final int REQUEST_CODE = 1000;

    /**
     * 主界面的lvAdapter
     */
    private MainLvAdapter lvAdapter;

    /**
     * 设置按钮
     */
    private ImageView ivSetting;
    /**
     * 主视图的ListView
     */
    private ListView lv;

    /**
     * 添加project的textView
     */
    private TextView tvNewProject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
//        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.main_aty);
        //透明顶栏
        TransparentStyle.setAppToTransparentStyle(this, getResources().getColor(R.color.blue_level0));
        //初始化View
        initView();

        //TODO
//        startActivity(new Intent(this, GpsTestAty.class));
    }

    /**
     * 初始化View
     */
    private void initView() {
        //设置按钮
        ivSetting = (ImageView) findViewById(R.id.id_iv_setting);
        ivSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingAty.class);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });

        //初始化主界面列表
        lv = (ListView) findViewById(R.id.id_lv_main);
        lvAdapter = new MainLvAdapter(this);
        lv.setAdapter(lvAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //根据lvAdapter获取数据***传递给详细页面的
                ProjectItem projectItem = lvAdapter.getDataList().get(position);
                Intent intent = new Intent(MainActivity.this, TourListAty.class);
                intent.putExtra(Constant.INTENT_KEY_DATA_PROJECT_ITEM, projectItem);
                //将ProjectItem传递给TourListAty
                startActivity(intent);
            }
        });

        //新建工程
        tvNewProject = (TextView) findViewById(R.id.id_tv_new_project);
        tvNewProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LinearLayout llPrjName = (LinearLayout) LayoutInflater.from(MainActivity.this).
                        inflate(R.layout.prj_name_edit_dialog, null);
                final EditText etPrjName = (EditText) llPrjName.findViewById(R.id.id_et_prj_name_dialog);
                final NiftyDialogBuilder dialogBuilder = NiftyDialogBuilder.getInstance(MainActivity.this);
                dialogBuilder.withTitle("请填写工程名")             //.withTitle(null)  no title
                        .withTitleColor("#FFFFFF")
                        .withDividerColor("#11000000")
                        .withMessage(null)//.withMessage(null)  no Msg
                        .withMessageColor("#FFFFFFFF")
                        .withDialogColor(getResources().getColor(R.color.dialog_color))
                        .withEffect(Effectstype.Slidetop)       //def Effectstype.Slidetop
                        .setCustomView(llPrjName, MainActivity.this)
                        .withButton1Text("确认")                 //def gone
                        .withButton2Text("取消")                 //def gone
                        .isCancelableOnTouchOutside(false)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //TODO
                                if (etPrjName.getText().toString().equals("")) {
                                    Toast.makeText(MainActivity.this, "工程名不可为空",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    //TODO
                                    //新建工程---只用新建它的路径就行(注意是否工程名重复)
                                    File prjFile = new File(DbFileManager.DATABASE_PATH + etPrjName.getText());
                                    if (prjFile.exists()) {
                                        Toast.makeText(MainActivity.this, "该工程已经存在!",
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        prjFile.mkdirs();
                                        //重新加载工程视图
                                        lvAdapter = new MainLvAdapter(MainActivity.this);
                                        lv.setAdapter(lvAdapter);
                                        dialogBuilder.dismiss();
                                    }
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
                        .show();

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

}