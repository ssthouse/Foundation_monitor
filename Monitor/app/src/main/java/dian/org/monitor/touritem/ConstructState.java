package dian.org.monitor.touritem;

import java.io.Serializable;

/**
 * Created by ssthouse on 2015/6/9.
 * 5项
 * 施工情况的ItemData
 */
public class ConstructState implements Serializable {

    /**
     * 第1项
     * 土质情况
     */
    private String soilStateItem1;
    /**
     * 第2项
     * 基坑开挖分段长度及分层厚度
     */
    private String lengthWidthItem2;
    /**
     * 第3项
     * 地表水、地下水状况
     */
    private String underWaterItem3;
    /**
     * 第4项
     * 基坑周边地面堆载情况
     */
    private String groundStateItem4;
    /**
     * 第5项
     * 其他
     */
    private String otherItem5;

    /**
     * @param soilStateItem1   土质情况
     * @param lengthWidthItem2 基坑开挖分段长度及分层厚度
     * @param underWaterItem3  地表水、地下水状况
     * @param groundStateItem4 基坑周边地面堆载情况
     * @param otherItem5       其他
     */
    public ConstructState(String soilStateItem1, String lengthWidthItem2,
                          String underWaterItem3, String groundStateItem4, String otherItem5) {
        this.soilStateItem1 = soilStateItem1;
        this.lengthWidthItem2 = lengthWidthItem2;
        this.underWaterItem3 = underWaterItem3;
        this.groundStateItem4 = groundStateItem4;
        this.otherItem5 = otherItem5;
    }

    //getter*****setter********************************************************
    public String getSoilStateItem1() {
        return soilStateItem1;
    }
    public void setSoilStateItem1(String soilStateItem1) {
        this.soilStateItem1 = soilStateItem1;
    }
    public String getLengthWidthItem2() {
        return lengthWidthItem2;
    }
    public void setLengthWidthItem2(String lengthWidthItem2) {
        this.lengthWidthItem2 = lengthWidthItem2;
    }
    public String getUnderWaterItem3() {
        return underWaterItem3;
    }
    public void setUnderWaterItem3(String underWaterItem3) {
        this.underWaterItem3 = underWaterItem3;
    }
    public String getGroundStateItem4() {
        return groundStateItem4;
    }
    public void setGroundStateItem4(String groundStateItem4) {
        this.groundStateItem4 = groundStateItem4;
    }
    public String getOtherItem5() {
        return otherItem5;
    }
    public void setOtherItem5(String otherItem5) {
        this.otherItem5 = otherItem5;
    }
}
