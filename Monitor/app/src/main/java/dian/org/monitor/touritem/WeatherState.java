package dian.org.monitor.touritem;

import java.io.Serializable;

/**
 * 自然条件的DataItem
 * Created by ssthouse on 2015/6/9.
 */
public class WeatherState  implements Serializable {

    //气温***雨量***风速***水位
    /**
     * 气温
     * float
     */
    private float temperature;

    /**
     * 雨量
     * String
     */
    private String rainFall;

    /**
     * 风级
     * String
     */
    private String windSpeed;

    /**
     * 水位
     * String
     */
    private String waterLevel;

    /**
     * 构造方法
     * @param temperature   气温
     * @param rainFall      雨量
     * @param windSpeed     风级
     * @param waterLevel    水位
     */
    public WeatherState(float temperature, String rainFall, String windSpeed, String waterLevel) {
        this.temperature = temperature;
        this.rainFall = rainFall;
        this.windSpeed = windSpeed;
        this.waterLevel = waterLevel;
    }
}