package dian.org.monitor.touritem;

import java.io.Serializable;

/**
 * Created by ssthouse on 2015/6/9.
 * 监测设施的ItemData
 */
public class MonitorFacility implements Serializable {

    /**
     * 第1项
     * 基准点、测点完好状况
     */
    private String baseStateItem1;

    /**
     * 第2项
     * 监测元件完好情况
     */
    private String deviceStateItem2;

    /**
     * 第3项
     * 观测工作条件
     */
    private String workStateItem3;

    /**
     * @param baseStateItem1   基准点、测点完好状况
     * @param deviceStateItem2 监测元件完好情况
     * @param workStateItem3   观测工作条件
     */
    public MonitorFacility(String baseStateItem1, String deviceStateItem2, String workStateItem3) {
        this.baseStateItem1 = baseStateItem1;
        this.deviceStateItem2 = deviceStateItem2;
        this.workStateItem3 = workStateItem3;
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
