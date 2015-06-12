package dian.org.monitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
     * @param context
     */
    public MainLvAdapter(Context context){
        this.mContext = context;
        //根据用户名***密码***获取他有的工程信息**然后展示出来
        initProjectItemList();
    }

    /**
     *初始化dataList***projectList
     */
    private void initProjectItemList(){
        dataList = getPrjDataList();
    }

    /**
     * 根据用户名获取projectItem数据
     * TODO  从服务器获取project数据***或者从数据库获取数据(这里应该是服务器!!!)
     * @return
     */
    private List<ProjectItem> getPrjDataList(){
        //获取用户名
        String userName = PreferenceManager.getSharedPerference(mContext).
                getString(PreferenceManager.PREFERENCE_KEY_USER_NAME, null);
        //如果用户名为null***返回空
        if(userName == null){
            return null;
        }
        //暂时****虚拟数据*****
        //TODO
        List<ProjectItem> list = new ArrayList<>();
        list.add(new ProjectItem("我是prj1"));
        list.add(new ProjectItem("我是prj2"));
        list.add(new ProjectItem("我是prj3"));
        list.add(new ProjectItem("我是prj4"));
        list.add(new ProjectItem("我是prj5"));
        return list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoler holer;
        if(convertView == null){
            holer = new ViewHoler();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.main_aty_lv_item, null, false);
            convertView.setTag(holer);
            holer.tvContent = (TextView)convertView.findViewById(R.id.id_tv_content);
            //设置tvContent为名称
            holer.tvContent.setText(dataList.get(position).getPrjName());
        }else {
            holer = (ViewHoler)convertView.getTag();
            holer.tvContent.setText(dataList.get(position).getPrjName());
        }
        return convertView;
    }


    class ViewHoler{
        public TextView tvContent;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }
    @Override
    public Object getItem(int position) {
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
