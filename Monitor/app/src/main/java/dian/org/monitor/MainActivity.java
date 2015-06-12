package dian.org.monitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_aty);
        //透明顶栏
        TransparentStyle.setAppToTransparentStyle(this, getResources().getColor(R.color.blue_level0));

        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
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
    }
}
