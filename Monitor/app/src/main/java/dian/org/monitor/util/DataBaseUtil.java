package dian.org.monitor.util;

import android.util.Log;

import com.activeandroid.query.Select;

import java.util.List;

import dian.org.monitor.touritem.ProjectItem;
import dian.org.monitor.touritem.TourItem;

/**
 * Created by ssthouse on 2015/7/6.
 */
public class DataBaseUtil {

    private static final  String TAG = "DataBaseUtil";

    /**
     * prjName是否已经存在
     *
     * @param prjName
     * @return
     */
    public static boolean isProjectExist(String prjName) {
        List<ProjectItem> list = new Select().from(ProjectItem.class)
                .where("prjName=?", prjName).execute();
        if (list.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取对应prjName的TOurItem的List
     *
     * @param prjName
     * @return
     */
    public static List<TourItem> getTourItemList(String prjName) {
        return new Select().from(TourItem.class)
                .where("prjName=?", prjName)
                .orderBy("tourNumber ASC").execute();
    }

    /**
     * 根据projectItem获取TourItem的List
     *
     * @param projectItem
     * @return
     */
    public static List<TourItem> getTourItemList(ProjectItem projectItem) {
        return new Select().from(TourItem.class)
                .where("prjName=?", projectItem.getPrjName())
                .orderBy("tourNumber ASC").execute();
    }

    /**
     * 获取新建TOurItem的TOurNumber
     *
     * @param projectItem
     * @return
     */
    public static int getNewTourNumber(ProjectItem projectItem) {
        List<TourItem> itemList = new Select().from(TourItem.class)
                .where("prjName=?", projectItem.getPrjName())
                .orderBy("tourNumber DESC").execute();
        if (itemList.size() == 0) {
            return 1;
        } else {
            return itemList.get(0).getTourNumber() + 1;
        }
    }

    /**
     * 判断----想要使用的TourNumber是否已经被用了
     * TODO
     *
     * @param tourNumber
     * @return
     */
    public static boolean isTourNumberAllowed(TourItem tourItem, int tourNumber) {
        List<TourItem> itemList = new Select().from(TourItem.class)
                .where("prjName=?", tourItem.getPrjName())
                .execute();
        if (itemList.size() == 0) {
            return true;
        }
        //只要有一个相同的---就不行
        for(TourItem item : itemList){
            if(item.getTourNumber() == tourNumber){
                return false;
            }
        }
        return true;
    }

    /**
     * 从数据库中获取TourItem
     *
     * @param tourItem
     * @return
     */
    public static TourItem getTourItemInDB(TourItem tourItem) {
        String prjName = tourItem.getPrjName();
        int tourNumber = tourItem.getTourNumber();
        List<TourItem> itemList = new Select().from(TourItem.class)
                .where("prjName=?", prjName)
                .where("tourNumber=?", tourNumber)
                .execute();
        if (itemList.size() == 0) {
            Log.e(TAG, "数据库中tourItem竟然是空的");
            Log.e(TAG, itemList.size()+"");
            return null;
        } else {
            return itemList.get(itemList.size()-1);
        }
    }


    /**
     * 将TOurItem的数据全部保存下来
     *
     * @param tourItem
     */
    public static void saveTourItemAll(TourItem tourItem) {
        tourItem.getTourInfo().save();
        tourItem.getWeatherState().save();
        tourItem.getSupportStruct().save();
        tourItem.getConstructState().save();
        tourItem.getSurroundEnv().save();
        tourItem.getMonitorFacility().save();
        tourItem.save();
    }


    public static void deleteTourItemAll(TourItem tourItem){
        tourItem.getTourInfo().delete();
        tourItem.getWeatherState().delete();
        tourItem.getSupportStruct().delete();
        tourItem.getConstructState().delete();
        tourItem.getSurroundEnv().delete();
        tourItem.getMonitorFacility().delete();
        tourItem.delete();
    }
}
