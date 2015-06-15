package dian.org.monitor.touritem;

import java.io.Serializable;

/**
 * 用于保存----工程名(不可修改)---巡检编号(INTEGER)----检测人(TEXT)---时间(TEXT)---总结(TEXT)
 * Created by ssthouse on 2015/6/14.
 */
public class TourInfo implements Serializable{

    /**
     * 工程名
     */
    private String prjName;
    /**
     * 巡查编号
     */
    private int tourNumber;
    /**
     * 巡查人
     */
    private String observer;
    /**
     * 时间字符串
     */
    private String timeInMilesStr;
    /**
     * 总结
     */
    private String summary;

    /**
     * 构造方法
     * @param prjName
     * @param tourNumber
     * @param observer
     * @param timeInMilesStr
     * @param summary
     */
    public TourInfo(String prjName, int tourNumber, String observer,
                    String timeInMilesStr, String summary) {
        this.prjName = prjName;
        this.tourNumber = tourNumber;
        this.observer = observer;
        this.timeInMilesStr = timeInMilesStr;
        this.summary = summary;
    }

    //getter---setter-------------------------------------------------------------------------------
    public String getPrjName() {
        return prjName;
    }
    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }
    public int getTourNumber() {
        return tourNumber;
    }
    public void setTourNumber(int tourNumber) {
        this.tourNumber = tourNumber;
    }
    public String getObserver() {
        return observer;
    }
    public void setObserver(String observer) {
        this.observer = observer;
    }
    public String getTimeInMilesStr() {
        return timeInMilesStr;
    }
    public void setTimeInMilesStr(String timeInMilesStr) {
        this.timeInMilesStr = timeInMilesStr;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
}
