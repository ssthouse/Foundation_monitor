package dian.org.monitor.touritem;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

import dian.org.monitor.Constant;

/**
 * 自然条件的DataItem
 * Created by ssthouse on 2015/6/9.
 */
@Table(name = Constant.TABLE_NAME_WEATHER_STATE)
public class WeatherState extends Model implements Serializable {

    //气温***雨量***风速***水位
    /**
     * 气温
     * float
     */
    @Column(name = "temperatureItem1")
    private String temperatureItem1;

    /**
     * 雨量
     * String
     */
    @Column(name = "rainFallItem2")
    private String rainFallItem2;

    /**
     * 风级
     * String
     */
    @Column(name = "windSpeedItem3")
    private String windSpeedItem3;

    /**
     * 水位
     * String
     */
    @Column(name = "waterLevelItem4")
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
        super();
        this.temperatureItem1 = temperatureItem1;
        this.rainFallItem2 = rainFallItem2;
        this.windSpeedItem3 = windSpeedItem3;
        this.waterLevelItem4 = waterLevelItem4;
    }

    public WeatherState(){
        super();
    }

    /**
     * 更新数据---单不是改变引用
     * @param weatherState
     */
    public void updateData(WeatherState weatherState){
        this.setTemperatureItem1(weatherState.getTemperatureItem1());
        this.setRainFallItem2(weatherState.getRainFallItem2());
        this.setWindSpeedItem3(weatherState.getWindSpeedItem3());
        this.setWaterLevelItem4(weatherState.getWaterLevelItem4());
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