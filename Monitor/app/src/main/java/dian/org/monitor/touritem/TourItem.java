package dian.org.monitor.touritem;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

import dian.org.monitor.Constant;

/**
 * Created by ssthouse on 2015/6/9.
 * 每一次巡查的数据
 * 需要保存在数据库中
 */
@Table(name = Constant.TABLE_NAME_TOUR_ITEM)
public class TourItem extends Model implements Serializable {

    @Column(name = "prjName")
    private String prjName;

    @Column(name = "tourNumber")
    private int tourNumber;

    @Column(name = "TourInfo")
    private TourInfo tourInfo;

    @Column(name = "WeatherState")
    private WeatherState weatherState;


    @Column(name = "SupportStruct")
    private SupportStruct supportStruct;


    @Column(name = "ConstructState")
    private ConstructState constructState;


    @Column(name = "SurroundEnv")
    private SurroundEnv surroundEnv;


    @Column(name = "MonitorFacility")
    private MonitorFacility monitorFacility;

    /**
     * 这个方法一定要有
     */
    public TourItem() {
        super();
    }

    /**
     * 构造方法
     * 所有的数据有:
     *
     * @param weatherState    自然条件
     * @param supportStruct   支护结构
     * @param constructState  施工工况
     * @param surroundEnv     周边环境
     * @param monitorFacility 监测设施
     */
    public TourItem(String prjName, int tourNumber, TourInfo tourInfo, WeatherState weatherState,
                    SupportStruct supportStruct, ConstructState constructState,
                    SurroundEnv surroundEnv, MonitorFacility monitorFacility) {
        super();
        this.prjName = prjName;
        this.tourInfo = tourInfo;

        this.tourInfo = tourInfo;
        this.weatherState = weatherState;
        this.supportStruct = supportStruct;
        this.constructState = constructState;
        this.surroundEnv = surroundEnv;
        this.monitorFacility = monitorFacility;
    }

    /**
     * TODO
     * 用于测试的构造方法***为了直接获取数据
     */
    public TourItem(String prjName, int tourNumber, boolean isEmpty) {
        super();
        if (isEmpty == true) {
            this.prjName = prjName;
            this.tourNumber = tourNumber;
            this.tourInfo = new TourInfo("", "", "");
            this.weatherState = new WeatherState("", "", "", "");
            this.supportStruct = new SupportStruct("", "", "", "", "", "", "");
            this.constructState = new ConstructState("", "", "", "", "");
            this.surroundEnv = new SurroundEnv("", "", "", "", "");
            this.monitorFacility = new MonitorFacility("", "", "");
        } else {
            this.prjName = prjName;
            this.tourNumber = tourNumber;
            this.tourInfo = new TourInfo("ssthouse", "1427678030287", "还行");
            this.weatherState = new WeatherState("35", "小雨", "3级", "300mm");
            this.supportStruct = new SupportStruct("1", "2", "3", "4", "5", "6", "7");
            this.constructState = new ConstructState("1", "2", "3", "4", "5");
            this.surroundEnv = new SurroundEnv("1", "2", "3", "4", "5");
            this.monitorFacility = new MonitorFacility("1", "2", "3");
        }
    }

    //getter*****setter********************************************************
    public WeatherState getWeatherState() {
        return weatherState;
    }
    public void setWeatherState(WeatherState weatherState) {
        this.weatherState = weatherState;
    }
    public SupportStruct getSupportStruct() {
        return supportStruct;
    }
    public void setSupportStruct(SupportStruct supportStruct) {
        this.supportStruct = supportStruct;
    }
    public ConstructState getConstructState() {
        return constructState;
    }
    public void setConstructState(ConstructState constructState) {
        this.constructState = constructState;
    }
    public SurroundEnv getSurroundEnv() {
        return surroundEnv;
    }
    public void setSurroundEnv(SurroundEnv surroundEnv) {
        this.surroundEnv = surroundEnv;
    }
    public MonitorFacility getMonitorFacility() {
        return monitorFacility;
    }
    public void setMonitorFacility(MonitorFacility monitorFacility) {
        this.monitorFacility = monitorFacility;
    }
    public TourInfo getTourInfo() {
        return tourInfo;
    }
    public void setTourInfo(TourInfo tourInfo) {
        this.tourInfo = tourInfo;
    }
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
}
