package dian.org.monitor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;

import dian.org.monitor.touritem.TourItem;
import dian.org.monitor.util.StringUtil;

/**
 * 巡视列表的Adapter
 * Created by ssthouse on 2015/6/10.
 */
public class TourLvAdapter extends BaseAdapter {
    private static final String TAG = "TourLvAdapter*****";

    /**
     * 保存所有数据的List
     */
    private List<TourItem> dataList;

    private Context mContext;

    private static final String strTvNumber = "巡检标号 : ";

    /**
     * 构造方法
     */
    public TourLvAdapter(Context mContext, List<TourItem> dataList) {
        this.mContext = mContext;
        this.dataList = dataList;
    }


    @Override
    public int getCount() {
//        Log.e(TAG, "我有"+dataList.size()+"个数据");
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tour_lv_item, null);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.id_tv_date);
            viewHolder.tvNumber = (TextView) convertView.findViewById(R.id.id_tv_number);
            //填充数据
            viewHolder.tvNumber.setText(strTvNumber + dataList.get(position)
                    .getTourInfo().getTourNumber());
            Calendar calendar = StringUtil.getCalendarFromTimeInMiles(dataList.get(position)
                    .getTourInfo().getTimeInMilesStr());
            String strDate = StringUtil.getFormatDate(calendar);
            viewHolder.tvDate.setText(strDate);
            //添加标签
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            //填充数据
            viewHolder.tvNumber.setText(strTvNumber + dataList.get(position)
                    .getTourInfo().getTourNumber());
            Calendar calendar = StringUtil.getCalendarFromTimeInMiles(
                    dataList.get(position).getTourInfo().getTimeInMilesStr());
            String strDate = StringUtil.getFormatDate(calendar);
            viewHolder.tvDate.setText(strDate);
        }
        return convertView;
    }

    class ViewHolder {
        public TextView tvNumber;
        public TextView tvDate;
    }

    //getter******setter*******************************************************
    public List<TourItem> getDataList() {
        return dataList;
    }

    public void setDataList(List<TourItem> dataList) {
        this.dataList = dataList;
    }
}
