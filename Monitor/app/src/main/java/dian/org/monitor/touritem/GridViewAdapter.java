package dian.org.monitor.touritem;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.util.List;

import dian.org.monitor.R;
import dian.org.monitor.util.PictureManager;

/**
 * 为图片添加删除的Adapter
 * Created by ssthouse on 2015/6/11.
 */
public class GridViewAdapter extends BaseAdapter {

    private Context context;

    private TourItem tourItem;

    private List<Bitmap> bitmapList;

    /**
     * 构造方法
     *
     * @param context
     * @param tourItem
     */
    public GridViewAdapter(Context context, TourItem tourItem) {
        this.context = context;
        this.tourItem = tourItem;
        //初始化数据---根据TOurItem获取List<Drawable>
        bitmapList = PictureManager.getBitmapList(tourItem);
    }


    @Override
    public int getCount() {
        return bitmapList.size() + 1;
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
        FrameLayout fl = (FrameLayout) LayoutInflater.from(context).
                inflate(R.layout.grid_view_item, null);
        ImageView iv = (ImageView) fl.findViewById(R.id.id_iv_grid_view_picture);
        //判断是不是最后一张---填充数据
        if (position < bitmapList.size()) {
            iv.setImageBitmap(bitmapList.get(position));
        } else {
            iv.setImageDrawable(context.getResources().getDrawable(R.drawable.pic_add));
        }
        return fl;
    }

    class ViewHoler {
        public ImageView iv;
    }
}
