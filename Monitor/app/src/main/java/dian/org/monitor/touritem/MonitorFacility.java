package dian.org.monitor.touritem;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

import dian.org.monitor.Constant;

/**
 * Created by ssthouse on 2015/6/9.
 * 监测设施的ItemData
 */
@Table(name = Constant.TABLE_NAME_MONITOR_FACILITY)
public class MonitorFacility extends Model implements Serializable {

    /**
     * 第1项
     * 基准点、测点完好状况
     */
    @Column(name = "baseStateItem1")
    private String baseStateItem1;

    /**
     * 第2项
     * 监测元件完好情况
     */
    @Column(name = "deviceStateItem2")
    private String deviceStateItem2;

    /**
     * 第3项
     * 观测工作条件
     */
    @Column(name = "workStateItem3")
    private String workStateItem3;

    /**
     * @param baseStateItem1   基准点、测点完好状况
     * @param deviceStateItem2 监测元件完好情况
     * @param workStateItem3   观测工作条件
     */
    public MonitorFacility(String baseStateItem1, String deviceStateItem2, String workStateItem3) {
        super();
        this.baseStateItem1 = baseStateItem1;
        this.deviceStateItem2 = deviceStateItem2;
        this.workStateItem3 = workStateItem3;
    }

    public MonitorFacility(){
        super();
    }

    /**
     * 更新数据---单不是改变引用
     * @param monitorFacility
     */
    public void updateData(MonitorFacility monitorFacility){
        this.setBaseStateItem1(monitorFacility.getBaseStateItem1());
        this.setDeviceStateItem2(monitorFacility.getDeviceStateItem2());
        this.setWorkStateItem3(monitorFacility.getWorkStateItem3());
    }

    //getter*****setter********************************************************
    public String getBaseStateItem1() {
        return baseStateItem1;
    }

    public void setBaseStateItem1(String baseStateItem1) {
        this.baseStateItem1 = baseStateItem1;
    }

    public String getDeviceStateItem2() {
        return deviceStateItem2;
    }

    public void setDeviceStateItem2(String deviceStateItem2) {
        this.deviceStateItem2 = deviceStateItem2;
    }

    public String getWorkStateItem3() {
        return workStateItem3;
    }

    public void setWorkStateItem3(String workStateItem3) {
        this.workStateItem3 = workStateItem3;
    }
}
