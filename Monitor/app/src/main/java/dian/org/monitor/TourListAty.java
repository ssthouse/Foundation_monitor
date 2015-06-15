package dian.org.monitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Calendar;

import dian.org.monitor.db.DbFileManager;
import dian.org.monitor.style.TransparentStyle;
import dian.org.monitor.touritem.TourInfo;
import dian.org.monitor.touritem.TourItem;

/**
 * Created by ssthouse on 2015/6/10.
 * 展示巡查列表的activity
 */
public class TourListAty extends Activity {
    private static final String TAG = "TourListAty";

    //
    private final int REQUEST_CODE_EDIT = 1001;

    private final int REQUEST_CODE_NEW = 1002;

    /**
     * 所有的数据
     */
    private ProjectItem projectItem;

    /**
     * 返回按钮
     */
    private ImageView ivBack;
    /**
     * 项目名称
     */
    private TextView tvTitle;
    /**
     * 巡视列表
     */
    private ListView lv;
    /**
     * 新建巡视
     */
    private TextView tvNewTour;

    private TourLvAdapter lvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_list_aty);
        //透明顶栏
        TransparentStyle.setAppToTransparentStyle(this, getResources().getColor(R.color.blue_level0));
        //获取ProjectItem数据
        Intent intent = getIntent();
        projectItem = (ProjectItem) intent.getSerializableExtra(Constant.INTENT_KEY_DATA_PROJECT_ITEM);
        //初始化View
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        //标题
        tvTitle = (TextView) findViewById(R.id.id_tv_title);
        tvTitle.setText(projectItem.getPrjName());

        //返回键
        ivBack = (ImageView) findViewById(R.id.id_iv_back);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //主视图列表
        lv = (ListView) findViewById(R.id.id_lv_tour_item);
        //TODO  获取tourList的数据---从数据库会哦去
        lvAdapter = new TourLvAdapter(this, projectItem.getTourItemList());
        lv.setAdapter(lvAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TourListAty.this, TourEditAty.class);
                intent.putExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM,
                        projectItem.getTourItemList().get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });

        //新建TourItem
        tvNewTour = (TextView) findViewById(R.id.id_tv_new_tour_item);
        tvNewTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TourListAty.this, TourEditAty.class);
                TourItem tourItem = new TourItem(true);
                tourItem.setTourInfo(new TourInfo(projectItem.getPrjName(),
                        DbFileManager.getTourNumber(projectItem.getPrjName())+1,"",
                        Calendar.getInstance().getTimeInMillis()+"", ""));
                intent.putExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM,tourItem);
                startActivityForResult(intent, REQUEST_CODE_NEW);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //TODO----回调的处理
        switch (requestCode) {
            case REQUEST_CODE_EDIT:
                //刷新prjItem数据
                projectItem = new ProjectItem(projectItem.getPrjName());
                //刷新listVierw的数据
                lvAdapter = new TourLvAdapter(this, projectItem.getTourItemList());
                lv.setAdapter(lvAdapter);
                break;
            case REQUEST_CODE_NEW:
                //刷新prjItem数据
                projectItem = new ProjectItem(projectItem.getPrjName());
                //刷新listVierw的数据
                lvAdapter = new TourLvAdapter(this, projectItem.getTourItemList());
                lv.setAdapter(lvAdapter);
                break;
        }
        Log.e(TAG, "我回调了----TourListAty的OnActitityResult方法");
        super.onActivityResult(requestCode, resultCode, data);
    }
}
