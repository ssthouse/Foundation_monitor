package dian.org.monitor.util;

import android.content.Context;
import android.content.res.Resources;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import dian.org.monitor.R;
import dian.org.monitor.touritem.TourItem;

/**
 * 导出word的工具类
 * 注意:
 * 1.生成的文件是以------"项目名-巡查序号"进行的命名
 * TODO 需要处理一下---要是序号名字改变了怎么办??
 * Created by ssthouse on 2015/7/13.
 */
public class WordGenereteUtil {

    private static final String TAG = "WordGenerateUtil";

    private static final String WORD_FILE_PATH = "/data/data/dian.org.monitor/files/";

    private static final String projectName = "${projectName}";
    private static final String date = "${date}";
    private static final String tourNumber = "${tourNumber}";
    private static final String summary = "${summary}";
    private static final String observer = "${observer}";

    private static final String temperatureItem1 = "${ temperatureItem1}";
    private static final String rainFallItem2 = "${rainFallItem2}";
    private static final String windSpeedItem3 = "${windSpeedItem3}";
    private static final String waterLevelItem4 = "${waterLevelItem4}";

    private static final String qualityItem1 = "${qualityItem1}";
    private static final String crackItem2 = "${crackItem2}";
    private static final String transformItem3 = "${transformItem3}";
    private static final String leakItem4 = "${leakItem4}";
    private static final String slipItem5 = "${slipItem5}";
    private static final String pourItem6 = "${pourItem6}";
    private static final String supportStructOtherItem7 = "${supportStructOtherItem7}";

    private static final String soilStateItem1 = "${soilStateItem1}";
    private static final String lengthWidthItem2 = "${lengthWidthItem2}";
    private static final String underWaterItem3 = "${underWaterItem3}";
    private static final String groundStateItem4 = "${groundStateItem4}";
    private static final String constructStateotherItem5 = "${constructStateotherItem5}";

    private static final String breakStateItem1 = "${breakStateItem1}";
    private static final String leakItem2 = "${leakItem2}";
    private static final String sinkItem3 = "${sinkItem3}";
    private static final String surroundStateItem4 = "${surroundStateItem4}";
    private static final String surroundEnvOtherItem5 = "${surroundEnvOtherItem5}";

    private static final String baseStateItem1 = "${baseStateItem1}";
    private static final String deviceStateItem2 = "${deviceStateItem2}";
    private static final String workStateItem3 = "${workStateItem3}";

    /**
     * 获取数据的映射表
     *
     * @param tourItem
     * @return
     */
    private static Map<String, String> getDataMap(TourItem tourItem) {
        Map<String, String> dataMap = new HashMap<>();

        dataMap.put(projectName, tourItem.getPrjName());
        dataMap.put(date, StringUtil.getFormatDate(tourItem.getTourInfo().getTimeInMilesStr()));
        dataMap.put(tourNumber, tourItem.getTourNumber() + "");
        dataMap.put(summary, tourItem.getTourInfo().getSummary());
        dataMap.put(observer, tourItem.getTourInfo().getObserver());

        dataMap.put(temperatureItem1, tourItem.getWeatherState().getTemperatureItem1());
        dataMap.put(rainFallItem2, tourItem.getWeatherState().getRainFallItem2());
        dataMap.put(windSpeedItem3, tourItem.getWeatherState().getWindSpeedItem3());
        dataMap.put(waterLevelItem4, tourItem.getWeatherState().getWaterLevelItem4());

        dataMap.put(qualityItem1, tourItem.getSupportStruct().getQualityItem1());
        dataMap.put(crackItem2, tourItem.getSupportStruct().getCrackItem2());
        dataMap.put(transformItem3, tourItem.getSupportStruct().getTransformItem3());
        dataMap.put(leakItem4, tourItem.getSupportStruct().getLeakItem4());
        dataMap.put(slipItem5, tourItem.getSupportStruct().getSlipItem5());
        dataMap.put(pourItem6, tourItem.getSupportStruct().getPourItem6());
        dataMap.put(supportStructOtherItem7, tourItem.getSupportStruct().getOtherItem7());

        dataMap.put(soilStateItem1, tourItem.getConstructState().getSoilStateItem1());
        dataMap.put(lengthWidthItem2, tourItem.getConstructState().getLengthWidthItem2());
        dataMap.put(underWaterItem3, tourItem.getConstructState().getUnderWaterItem3());
        dataMap.put(groundStateItem4, tourItem.getConstructState().getGroundStateItem4());
        dataMap.put(constructStateotherItem5, tourItem.getConstructState().getOtherItem5());

        dataMap.put(breakStateItem1, tourItem.getSurroundEnv().getBreakStateItem1());
        dataMap.put(leakItem2, tourItem.getSurroundEnv().getLeakItem2());
        dataMap.put(sinkItem3, tourItem.getSurroundEnv().getSinkItem3());
        dataMap.put(surroundStateItem4, tourItem.getSurroundEnv().getSurroundStateItem4());
        dataMap.put(surroundEnvOtherItem5, tourItem.getSurroundEnv().getOtherItem5());

        dataMap.put(baseStateItem1, tourItem.getMonitorFacility().getBaseStateItem1());
        dataMap.put(deviceStateItem2, tourItem.getMonitorFacility().getDeviceStateItem2());
        dataMap.put(workStateItem3, tourItem.getMonitorFacility().getWorkStateItem3());

        return dataMap;
    }

    /**
     * 生成word文档的方法
     * @param context
     * @param tourItem
     */
    public static void generateWordFile(Context context, TourItem tourItem) {
        Map<String, String> mapData = getDataMap(tourItem);
        try {
            //读取word模板
            Resources res = context.getResources();
            InputStream in = res.openRawResource(R.raw.model1);
            HWPFDocument doc = new HWPFDocument(in);
            //读取word文本操作模块
            Range range = doc.getRange();
            //替换文本内容
            for (Map.Entry<String, String> oneData : mapData.entrySet()) {
                range.replaceText(oneData.getKey(), oneData.getValue());
            }
            //以工程名+"-"+touNumebr作为word的文件名
            String fileName = tourItem.getPrjName()+"-"+tourItem.getTourNumber()+".doc";
            FileOutputStream out = new FileOutputStream(WORD_FILE_PATH + fileName);
            doc.write(out);
            //处理流
            out.flush();
            out.close();
            in.close();
            //设置文件可以被自带的应用打开
            File file = new File(WORD_FILE_PATH + fileName);
            file.setReadable(true, false);
            file.setWritable(true, false);
            file.setExecutable(true, false);
            //Toasts
            ToastUtil.showToast(context, "Word文件生成成功!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
