package dian.org.monitor.touritem;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

import dian.org.monitor.Constant;

/**
 * Created by ssthouse on 2015/6/9.
 * 支护结构的ItemData
 * 为这个数据添加****一个文件夹**保存照片**里面的文件数目就是照片数目
 * 需要根据具体数据***规定好**数据一对应什么**数据二对应什么!!!
 */
@Table(name = Constant.TABLE_NAME_SUPPORT_STRUCT)
public class SupportStruct  extends Model implements Serializable {

    /**
     * 第1项
     * 支护结构成型质量
     */
    @Column(name = "qualityItem1")
    private String qualityItem1;

    /**
     * 第2项
     * 冠梁、支撑、围檩裂缝
     */
    @Column(name = "crackItem2")
    private String crackItem2;

    /**
     * 第3项
     * 支撑、立柱变形
     */
    @Column(name = "transformItem3")
    private String transformItem3;

    /**
     * 第4项
     * 喷砼开裂、渗漏
     */
    @Column(name = "leakItem4")
    private String leakItem4;

    /**
     * 第5项
     * 墙后土体沉陷、裂缝及滑移
     */
    @Column(name = "slipItem5")
    private String slipItem5;

    /**
     * 第6项
     * 基坑涌土、流沙、管涌
     */
    @Column(name = "pourItem6")
    private String pourItem6;
    /**
     * 第7项
     * 其他
     */
    @Column(name = "otherItem7")
    private String otherItem7;

    /**
     *
     * @param qualityItem1      支护结构成型质量
     * @param crackItem2        冠梁、支撑、围檩裂缝
     * @param transformItem3    支撑、立柱变形
     * @param leakItem4         喷砼开裂、渗漏
     * @param slipItem5         墙后土体沉陷、裂缝及滑移
     * @param pourItem6         基坑涌土、流沙、管涌
     * @param otherItem7        其他
     */
    public SupportStruct(String qualityItem1, String crackItem2, String transformItem3,
                         String leakItem4, String slipItem5,
                         String pourItem6, String otherItem7) {
        super();
        this.qualityItem1 = qualityItem1;
        this.crackItem2 = crackItem2;
        this.transformItem3 = transformItem3;
        this.leakItem4 = leakItem4;
        this.slipItem5 = slipItem5;
        this.pourItem6 = pourItem6;
        this.otherItem7 = otherItem7;
    }

    public SupportStruct(){
        super();
    }

    /**
     * 更新数据---单不是改变引用
     * @param supportStruct
     */
    public void updateData(SupportStruct supportStruct){
        this.setQualityItem1(supportStruct.getQualityItem1());
        this.setCrackItem2(supportStruct.getCrackItem2());
        this.setTransformItem3(supportStruct.getTransformItem3());
        this.setLeakItem4(supportStruct.getLeakItem4());
        this.setSlipItem5(supportStruct.getSlipItem5());
        this.setPourItem6(supportStruct.getPourItem6());
        this.setOtherItem7(supportStruct.getOtherItem7());
    }

    //getter*****setter********************************************************
    public String getQualityItem1() {
        return qualityItem1;
    }
    public void setQualityItem1(String qualityItem1) {
        this.qualityItem1 = qualityItem1;
    }
    public String getCrackItem2() {
        return crackItem2;
    }
    public void setCrackItem2(String crackItem2) {
        this.crackItem2 = crackItem2;
    }
    public String getTransformItem3() {
        return transformItem3;
    }
    public void setTransformItem3(String transformItem3) {
        this.transformItem3 = transformItem3;
    }
    public String getLeakItem4() {
        return leakItem4;
    }
    public void setLeakItem4(String leakItem4) {
        this.leakItem4 = leakItem4;
    }
    public String getSlipItem5() {
        return slipItem5;
    }
    public void setSlipItem5(String slipItem5) {
        this.slipItem5 = slipItem5;
    }
    public String getPourItem6() {
        return pourItem6;
    }
    public void setPourItem6(String pourItem6) {
        this.pourItem6 = pourItem6;
    }
    public String getOtherItem7() {
        return otherItem7;
    }
    public void setOtherItem7(String otherItem7) {
        this.otherItem7 = otherItem7;
    }
}
