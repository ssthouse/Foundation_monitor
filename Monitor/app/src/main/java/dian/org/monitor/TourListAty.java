package dian.org.monitor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import dian.org.monitor.style.TransparentStyle;

/**
 * Created by ssthouse on 2015/6/10.
 * 展示巡查列表的activity
 */
public class TourListAty extends Activity {
    private static final String TAG = "TourListAty";

    private final int REQUEST_CODE_EDIT = 1001;

    private final int REQUEST_CODE_NEW = 1001;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tour_list_aty);
        //透明顶栏
        TransparentStyle.setAppToTransparentStyle(this, getResources().getColor(R.color.blue_level0));
        initView();
    }

    /**
     * 初始化View
     */
    private void initView() {
        //获取ProjectItem数据
        Intent intent = getIntent();
        projectItem = (ProjectItem) intent.getSerializableExtra(Constant.INTENT_KEY_DATA_PROJECT_ITEM);

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
        lv.setAdapter(new TourLvAdapter(this, projectItem.getTourItemList()));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(TourListAty.this, TourEditAty.class);
                intent.putExtra(Constant.INTENT_KEY_DATA_TOUR_ITEM,
                        projectItem.getTourItemList().get(position));
                startActivityForResult(intent, REQUEST_CODE_EDIT);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
