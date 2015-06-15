package dian.org.monitor.touritem;

import java.io.Serializable;

/**
 * 自然条件的DataItem
 * Created by ssthouse on 2015/6/9.
 */
public class WeatherState implements Serializable {

    //气温***雨量***风速***水位
    /**
     * 气温
     * float
     */
    private String temperatureItem1;

    /**
     * 雨量
     * String
     */
    private String rainFallItem2;

    /**
     * 风级
     * String
     */
    private String windSpeedItem3;

    /**
     * 水位
     * String
     */
    private String waterLevelItem4;

    /**
     * 构造方法
     *
     * @param temperatureItem1 气温
     * @param rainFallItem2    雨量
     * @param windSpeedItem3   风级
     * @param waterLevelItem4  水位
     */
    public WeatherState(String temperatureItem1, String rainFallItem2, String windSpeedItem3, String waterLevelItem4) {
        this.temperatureItem1 = temperatureItem1;
        this.rainFallItem2 = rainFallItem2;
        this.windSpeedItem3 = windSpeedItem3;
        this.waterLevelItem4 = waterLevelItem4;
    }


    public String getTemperatureItem1() {
        return temperatureItem1;
    }

    public void setTemperatureItem1(String temperatureItem1) {
        this.temperatureItem1 = temperatureItem1;
    }

    public String getRainFallItem2() {
        return rainFallItem2;
    }

    public void setRainFallItem2(String rainFallItem2) {
        this.rainFallItem2 = rainFallItem2;
    }

    public String getWindSpeedItem3() {
        return windSpeedItem3;
    }

    public void setWindSpeedItem3(String windSpeedItem3) {
        this.windSpeedItem3 = windSpeedItem3;
    }

    public String getWaterLevelItem4() {
        return waterLevelItem4;
    }

    public void setWaterLevelItem4(String waterLevelItem4) {
        this.waterLevelItem4 = waterLevelItem4;
    }
}