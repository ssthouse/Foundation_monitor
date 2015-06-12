package dian.org.monitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dian.org.monitor.touritem.TourItem;

/**
 * Created by ssthouse on 2015/6/9.
 * 一个工程的所有数据
 */
public class ProjectItem implements Serializable{

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
        //暂时默认为3
        prjTourNum = 3;
        //初始化TourItemList的数据***根据工程名字!!!
        //TODO
        initTourItemList();
    }


    /**
     * 初始化TourItemList
     * TODO
     */
    private void initTourItemList() {
        tourItemList = new ArrayList<>();
        for (int i = 0; i < prjTourNum; i++) {
            //这里应该是根据名字获取每一个TourNum
            //或者根据名字从数据库获取每一个TourItem
            TourItem tourItem = new TourItem();
            //将每一个都添加到List中
            tourItemList.add(tourItem);
        }
    }

    public String getPrjName() {
        if(prjName == null){
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