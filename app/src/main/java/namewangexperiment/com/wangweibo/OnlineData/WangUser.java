package namewangexperiment.com.wangweibo.OnlineData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2016/11/29.
 */

public class WangUser extends BmobUser implements Serializable {
    private String sex;
    private Date birthday;
    private String name;
    private String qq;
    private String wechat;
    private String address;
    private String passwordshow;
    private ArrayList<String> list_collect;
    private ArrayList<String> list_mine;
    public WangUser(){}

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPasswordshow() {
        return passwordshow;
    }

    public void setPasswordshow(String passwordshow) {
        this.passwordshow = passwordshow;
    }

    public ArrayList<String> getList_collect() {
        return list_collect;
    }

    public void setList_collect(ArrayList<String> list_collect) {
        this.list_collect = list_collect;
    }

    public ArrayList<String> getList_mine() {
        return list_mine;
    }

    public void setList_mine(ArrayList<String> list_mine) {
        this.list_mine = list_mine;
    }
}
