package namewangexperiment.com.wangweibo.OnlineData;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/31.
 */

public class WangSign extends BmobObject{
    public String value;
    public String precent;

    public WangSign(String value, String precent) {
        this.value = value;
        this.precent = precent;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getPrecent() {
        return precent;
    }

    public void setPrecent(String precent) {
        this.precent = precent;
    }
}
