package dian.org.monitor.gps;

/**
 * 一个单独的location的数据
 */
public class OneLocationRecord {

    /**
     * 表示生成位置时的时间点。
     */
    private long time;

    /**
     * 经度
     */
    private double longitude;

    /**
     * 纬度
     */
    private double latitude;

    /**
     * 位置对应的地址名称
     */
    private String addrName;

    /**
     * 是否是时间线的第一个点
     */
    private boolean is_first_of_line;

    /**
     * 位置保持的时间
     */
    private long duration;


    //getter---and---setter----------------------------------------------------
    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAddrName() {
        return addrName;
    }

    public void setAddrName(String addrName) {
        this.addrName = addrName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public boolean is_first_of_line() {
        return is_first_of_line;
    }

    public void set_first_of_line(boolean is_first_of_line) {
        this.is_first_of_line = is_first_of_line;
    }
}
