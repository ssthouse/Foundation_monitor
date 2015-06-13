package dian.org.monitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import dian.org.monitor.db.DbFileManager;
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
    private List<ProjectItem> dataList;

    /**
     * 构造方法
     *
     * @param context
     */
    public MainLvAdapter(Context context) {
        this.mContext = context;
        //根据用户名***密码***获取他有的工程信息**然后展示出来
        dataList = getPrjDataList();
    }

    /**
     * 根据用户名获取projectItem数据
     * TODO  从服务器获取project数据***或者从数据库获取数据(这里应该是服务器!!!)
     *
     * @return
     */
    private List<ProjectItem> getPrjDataList() {
        //获取用户名
        String userName = PreferenceManager.getSharedPerference(mContext).
                getString(PreferenceManager.PREFERENCE_KEY_USER_NAME, null);
        //如果用户名为null***返回空
        if (userName == null) {
            return null;
        }
        //初始化数据
        List<ProjectItem> list = new ArrayList<>();
        //根据文件夹获取priName的列表
        File prjDir = new File(DbFileManager.DATABASE_PATH);
        if (!prjDir.exists()) {
            prjDir.mkdirs();
        }
        String prjNames[] = prjDir.list();
        if (prjNames == null) {
            return null;
        } else {
            for (String prjname : prjNames) {
                list.add(new ProjectItem(prjname));
            }
            return list;
        }
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
            holer.tvContent.setText(dataList.get(position).getPrjName());
        } else {
            holer = (ViewHoler) convertView.getTag();
            holer.tvContent.setText(dataList.get(position).getPrjName());
        }
        return convertView;
    }

    class ViewHoler {
        public TextView tvContent;
    }

    @Override
    public int getCount() {
        if (dataList == null) {
            return 0;
        }
        return dataList.size();
    }
    @Override
    public Object getItem(int position) {
        if (dataList == null) {
            return null;
        }
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<ProjectItem> getDataList() {
        return dataList;
    }
}
