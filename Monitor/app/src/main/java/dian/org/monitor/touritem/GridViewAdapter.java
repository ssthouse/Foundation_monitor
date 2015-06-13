package dian.org.monitor.touritem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.File;
import java.util.List;

import dian.org.monitor.R;
import dian.org.monitor.util.PictureManager;
import dian.org.monitor.util.ScreenUtil;

/**
 * 为图片添加删除的Adapter
 * Created by ssthouse on 2015/6/11.
 */
public class GridViewAdapter extends BaseAdapter {
    private static final String TAG = "GridViewAdapter***";

    /**
     * 每一列的padding
     */
    public static final int girdViewColumnPadding = 10;

    /**
     * 每个图片的宽度
     */
    public static int picWidth;

    public static int gridColumnWidth;

    private Context context;

    private TourItem tourItem;

    private List<BitmapItem> bitmapItemList;

    /**
     * 构造方法
     *
     * @param context
     * @param tourItem
     * @param kindpath 想要打开的类型文件的路径
     */
    public GridViewAdapter(Context context, TourItem tourItem, String kindpath) {
        this.context = context;
        this.tourItem = tourItem;
        //初始化数据---根据TOurItem获取List<Drawable>
        bitmapItemList = PictureManager.getBitmapList(tourItem, kindpath);
        gridColumnWidth = (ScreenUtil.getScreenWidth(context)-(girdViewColumnPadding *2)-20)/3;
        picWidth = gridColumnWidth;
    }

    /**
     * 点击小圆圈的时候----调用的删除方法
     * @param position
     */
    public void deletePicture(int position){
        //先把path保存下来
        String path = bitmapItemList.get(position).getPath();
        //先从列表中移除
        bitmapItemList.remove(position);
        //刷新视图
        notifyDataSetChanged();
        //从文件夹中移除
        File file = new File(path);
        file.delete();
    }

    @Override
    public int getCount() {
        return bitmapItemList.size() + 1;
    }


    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Log.e(TAG, "position:"+position);
        //判断是不是最后一张---填充数据
        if (position < bitmapItemList.size()) {
            FrameLayout fl = (FrameLayout) LayoutInflater.from(context).
                    inflate(R.layout.grid_view_item, null);
            ImageView iv = (ImageView) fl.findViewById(R.id.id_iv_grid_view_picture);
            iv.setImageBitmap(bitmapItemList.get(position).getBitmap());
            fl.setLayoutParams(new FrameLayout.LayoutParams(picWidth, picWidth));
            return fl;
        } else {
            ImageView iv = new ImageView(context);
            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.pic_add));
            iv.setLayoutParams(new AbsListView.LayoutParams(picWidth, picWidth));
            return iv;
        }
    }

    public List<BitmapItem> getBitmapItemList() {
        return bitmapItemList;
    }
    public void setBitmapItemList(List<BitmapItem> bitmapItemList) {
        this.bitmapItemList = bitmapItemList;
    }
}
