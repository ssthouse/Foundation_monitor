package dian.org.monitor.touritem;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.io.Serializable;
import java.util.List;

import dian.org.monitor.Constant;

/**
 * Created by ssthouse on 2015/7/6.
 */
@Table(name = Constant.TABLE_NAME_PROJECT)
public class ProjectItem extends Model implements Serializable{

    @Column(name = "prjName")
    private String prjName;

    public ProjectItem(){
        super();
    }

    public ProjectItem(String prjName){
        super();
        this.prjName = prjName;
    }

    //TODO

    /**
     * 根据prjName找到对应的TOurItems表中的对应数据
     * 每次调用都是获取的最新的数据
     * @return
     */
    public List<TourItem> getTourItemList(){
        List<TourItem> itemList;
        itemList = new Select().from(TourItem.class).where("prjName=?", prjName).execute();
        return itemList;
    }

    //getter----and----setter----------------------
    public String getPrjName() {
        return prjName;
    }
    public void setPrjName(String prjName) {
        this.prjName = prjName;
    }
}
