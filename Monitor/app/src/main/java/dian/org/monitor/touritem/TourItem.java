package dian.org.monitor.touritem;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by ssthouse on 2015/6/9.
 * 每一次巡查的数据
 * 需要保存在数据库中
 * TODO 数据格式待定
 */
public class TourItem implements Serializable{
    /**
     * 工程名称
     */
    private String prjName;
    /**
     * 巡检编号
     */
    private int number;
    /**
     * 监测人
     */
    private String observer;

    /**
     * 监测时间
     */
    private Calendar calendar;
    /**
     * 总结
     */
    private String summary;
    /**
     * 自然条件
     */
    private WeatherState weatherState;
    /**
     * 支护结构
     */
    private SupportStruct supportStruct;
    /**
     * 施工工况
     */
    private ConstructState constructState;
    /**
     * 周边环境
     */
    private SurroundEnv surroundEnv;
    /**
     * 监测设施
     */
    private MonitorFacility monitorFacility;

    /**
     * 构造方法
     * 所有的数据有:
     *
     * @param prjName         工程名称
     * @param number          巡检编号
     * @param observer        检测人
     * @param calendar        监测时间
     * @param summary         总结
     * @param weatherState    自然条件
     * @param supportStruct   支护结构
     * @param constructState  施工工况
     * @param surroundEnv     周边环境
     * @param monitorFacility 监测设施
     */
    public TourItem(String prjName, int number,
                    String observer, Calendar calendar,
                    String summary, WeatherState weatherState,
                    SupportStruct supportStruct, ConstructState constructState,
                    SurroundEnv surroundEnv, MonitorFacility monitorFacility) {
        this.prjName = prjName;
        this.number = number;
        this.observer = observer;
        this.calendar = calendar;
        this.summary = summary;
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
    public TourItem() {
        this.prjName = "我是工程名";
        this.number = 1;
        this.observer = "我是观测者";
        this.calendar = Calendar.getInstance();
        this.summary = "我是summary";
        this.weatherState = new WeatherState(35, "小雨", "3级", "300mm");
        this.supportStruct = new SupportStruct("1", "2", "3", "4", "5", "6", "7");
        this.constructState = new ConstructState("1", "2", "3", "4", "5");
        this.surroundEnv = new SurroundEnv("1", "2", "3", "4", "5");
        this.monitorFacility = new MonitorFacility("1", "2", "3");
    }

    //getter*****setter********************************************************
    public String getPrjName() {
        return prjName;
    }
    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public String getObserver() {
        return observer;
    }
    public void setObserver(String observer) {
        this.observer = observer;
    }
    public Calendar getCalendar() {
        return calendar;
    }
    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
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
}
