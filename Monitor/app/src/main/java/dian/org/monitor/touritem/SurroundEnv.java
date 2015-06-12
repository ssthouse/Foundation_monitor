package dian.org.monitor.touritem;

import java.io.Serializable;

/**
 * Created by ssthouse on 2015/6/9.
 * 周边环境的ItemData
 */
public class SurroundEnv implements Serializable {
    /**
     * 第1项
     * 管道破损、泄露情况
     */
    private String breakStateItem1;
    /**
     * 第2项
     * 周边建筑裂缝
     */
    private String leakItem2;
    /**
     * 第3项
     * 周边道路裂缝、沉陷
     */
    private String sinkItem3;
    /**
     * 第4项
     * 邻近施工情况
     */
    private String surroundStateItem4;
    /**
     * 第5项
     * 其他
     */
    private String otherItem5;

    /**
     *
     * @param breakStateItem1       管道破损、泄露情况
     * @param leakItem2             周边建筑裂缝
     * @param sinkItem3             周边道路裂缝、沉陷
     * @param surroundStateItem4    邻近施工情况
     * @param otherItem5            其他
     */
    public SurroundEnv(String breakStateItem1, String leakItem2, String sinkItem3,
                       String surroundStateItem4, String otherItem5) {
        this.breakStateItem1 = breakStateItem1;
        this.leakItem2 = leakItem2;
        this.sinkItem3 = sinkItem3;
        this.surroundStateItem4 = surroundStateItem4;
        this.otherItem5 = otherItem5;
    }

    //getter*****setter********************************************************
    public String getBreakStateItem1() {
        return breakStateItem1;
    }
    public void setBreakStateItem1(String breakStateItem1) {
        this.breakStateItem1 = breakStateItem1;
    }
    public String getLeakItem2() {
        return leakItem2;
    }
    public void setLeakItem2(String leakItem2) {
        this.leakItem2 = leakItem2;
    }
    public String getSinkItem3() {
        return sinkItem3;
    }
    public void setSinkItem3(String sinkItem3) {
        this.sinkItem3 = sinkItem3;
    }
    public String getSurroundStateItem4() {
        return surroundStateItem4;
    }
    public void setSurroundStateItem4(String surroundStateItem4) {
        this.surroundStateItem4 = surroundStateItem4;
    }
    public String getOtherItem5() {
        return otherItem5;
    }
    public void setOtherItem5(String otherItem5) {
        this.otherItem5 = otherItem5;
    }
}
