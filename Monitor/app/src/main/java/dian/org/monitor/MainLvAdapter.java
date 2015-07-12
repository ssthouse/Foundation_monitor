package dian.org.monitor;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.activeandroid.query.Select;

import java.util.List;

import dian.org.monitor.touritem.ProjectItem;
import dian.org.monitor.util.PreferenceManager;

/**
 * 根据当前的Perference里面保存的username获取数据***填充到ListView中
 * Created by ssthouse on 2015/6/9.
 */
public class MainLvAdapter extends BaseAdapter {
    private static final String TAG = "MainAdapter*****";

    private Context mContext;

    /**
     * 存储所有数据的List
     */
    private List<ProjectItem> projectItemList;

    /**
     * 构造方法
     *
     * @param context
     */
    public MainLvAdapter(Context context) {
        this.mContext = context;
        //根据用户名***密码***获取他有的工程信息**然后展示出来
        projectItemList = getPrjDataList();
    }

    @Override
    public void notifyDataSetChanged() {
        getPrjDataList();
        super.notifyDataSetChanged();
    }

    /**
     * 根据用户名获取projectItem数据
     * TODO  从服务器获取project数据***或者从数据库获取数据(这里应该是服务器!!!)
     *
     * @return
     */
    private List<ProjectItem> getPrjDataList() {
//        Log.e(TAG, "我开始获取----prj文件的列表");
        //获取用户名
        String userName = PreferenceManager.getSharedPerference(mContext).
                getString(PreferenceManager.PREFERENCE_KEY_USER_NAME, null);
        //如果用户名为null***返回空
        if (userName == null) {
            Log.e(TAG, "用户名为空!!!!!!!!!!");
            return null;
        }

        //初始化数据---从数据库
        List<ProjectItem> list = new Select().from(ProjectItem.class).execute();
        return list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler holer;
        if (convertView == null) {
            holer = new ViewHoler();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_aty_lv_item, null, false);
            convertView.setTag(holer);
            holer.tvContent = (TextView) convertView.findViewById(R.id.id_tv_content);
            //设置tvContent为名称
            holer.tvContent.setText(projectItemList.get(position).getPrjName());
        } else {
            holer = (ViewHoler) convertView.getTag();
            holer.tvContent.setText(projectItemList.get(position).getPrjName());
        }
        return convertView;
    }

    class ViewHoler {
        public TextView tvContent;
    }

    @Override
    public int getCount() {
        if (projectItemList == null) {
            return 0;
        }
        return projectItemList.size();
    }
    @Override
    public Object getItem(int position) {
        if (projectItemList == null) {
            return null;
        }
        return projectItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<ProjectItem> getProjectItemList() {
        return projectItemList;
    }
}
