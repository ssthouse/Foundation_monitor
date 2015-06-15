package dian.org.monitor;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dian.org.monitor.db.DbFileManager;
import dian.org.monitor.db.TourDbHelper;
import dian.org.monitor.touritem.TourItem;

/**
 * Created by ssthouse on 2015/6/9.
 * 一个工程的所有数据
 */
public class ProjectItem implements Serializable {

    /**
     * 项目名称
     */
    private String prjName;

    /**
     * 项目的巡视次数
     */
    private int prjTourNum;

    /**
     * 每一次巡查的数据List
     */
    private List<TourItem> tourItemList;

    /**
     * 构造方法
     * 根据项目的名称***向服务器请求该工程的数据
     */
    public ProjectItem(String prjName) {
        this.prjName = prjName;
        //根据名字获取各种数据
        prjTourNum = DbFileManager.getTourNumber(prjName);
        //初始化TourItemList
        initTourItemList();
    }


    /**
     * 初始化TourItemList
     * TODO
     */
    private void initTourItemList() {
        //工程名不能为空
        if (prjName == null || prjName.equals("")) {
            return;
        }
        //初始化TourList数据
        tourItemList = new ArrayList<>();
        //获取所有数据库---通过文件名
        String fileNames[] = new File(DbFileManager.DATABASE_PATH + prjName).list();
        for (String fileName :fileNames) {
            //这里应该是根据名字获取每一个TourNum---排除系统自己产生的数据库
            if(!fileName.contains("journal")) {
                TourItem tourItem = TourDbHelper.getTourItem(DbFileManager.
                        getDb(DbFileManager.DATABASE_PATH + prjName + "/", fileName));
                //将每一个都添加到List中
                tourItemList.add(tourItem);
            }
        }
    }

    public String getPrjName() {
        if (prjName == null) {
            return "";
        }
        return prjName;
    }

    public int getPrjTourNum() {
        return prjTourNum;
    }

    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }

    public void setPrjTourNum(int prjTourNum) {
        this.prjTourNum = prjTourNum;
    }

    public List<TourItem> getTourItemList() {
        return tourItemList;
    }

    public void setTourItemList(List<TourItem> tourItemList) {
        this.tourItemList = tourItemList;
    }
}
