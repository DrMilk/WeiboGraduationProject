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
    private String sign;
    private int fans;
    private int attentions;
    private boolean imgheadstutas;
    private ArrayList<String> list_collect;
    private ArrayList<String> list_mine;
    private ArrayList<String> list_remark;
    private ArrayList<String> list_reward;
    private ArrayList<String> list_fans;
    private ArrayList<String> list_attention;
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
        if(list_mine==null)
            return new ArrayList<String>();
        return list_mine;
    }

    public void setList_mine(ArrayList<String> list_mine) {
        this.list_mine = list_mine;
    }

    public ArrayList<String> getList_remark() {
        if(list_remark==null)
            return new ArrayList<String>();
        return list_remark;
    }

    public void setList_remark(ArrayList<String> list_remark) {
        this.list_remark = list_remark;
    }

    public ArrayList<String> getList_reward() {
        if(list_reward==null)
            return new ArrayList<String>();
        return list_reward;
    }

    public void setList_reward(ArrayList<String> list_reward) {
        this.list_reward = list_reward;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public int getFans() {
        return fans;
    }

    public void setFans(int fans) {
        this.fans = fans;
    }

    public int getAttentions() {
        return attentions;
    }

    public void setAttentions(int attentions) {
        this.attentions = attentions;
    }

    public ArrayList<String> getList_fans() {
        if(list_fans==null)
            return new ArrayList<String>();
        return list_fans;
    }

    public void setList_fans(ArrayList<String> list_fans) {
        this.list_fans = list_fans;
    }

    public ArrayList<String> getList_attention() {
        if(list_attention==null)
            return new ArrayList<String>();
        return list_attention;
    }

    public void setList_attention(ArrayList<String> list_attention) {
        this.list_attention = list_attention;
    }

    public boolean isImgheadstutas() {
        return imgheadstutas;
    }

    public void setImgheadstutas(boolean imgheadstutas) {
        this.imgheadstutas = imgheadstutas;
    }
}
