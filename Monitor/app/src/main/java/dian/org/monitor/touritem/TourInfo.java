package dian.org.monitor.touritem;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

import dian.org.monitor.Constant;

/**
 * 用于保存----工程名(不可修改)---巡检编号(INTEGER)----检测人(TEXT)---时间(TEXT)---总结(TEXT)
 * Created by ssthouse on 2015/6/14.
 */
@Table(name= Constant.TABLE_NAME_TOUR_INFO)
public class TourInfo extends Model implements Serializable{

    /**
     * 巡查人
     */
    @Column(name = "observer")
    private String observer;
    /**
     * 时间字符串
     */
    @Column(name = "timeInMilesStr")
    private String timeInMilesStr;
    /**
     * 总结
     */
    @Column(name = "summary")
    private String summary;

    /**
     * 构造方法
     * @param observer
     * @param timeInMilesStr
     * @param summary
     */
    public TourInfo(String observer,
                    String timeInMilesStr, String summary) {
        super();
        this.observer = observer;
        this.timeInMilesStr = timeInMilesStr;
        this.summary = summary;
    }

    public TourInfo(){
        super();
    }

    //getter---setter-------------------------------------------------------------------------------
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
