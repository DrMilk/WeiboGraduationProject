package namewangexperiment.com.wangweibo.chat;

/**
 * Created by Administrator on 2017/4/27.
 */

public class ChatInfo {
    private String text_time;
    private String  text_context;
    private String img_id;
    private int kinds;
    public ChatInfo(){}
    public ChatInfo(String text_time, String text_context, String img_id,int kinds) {
        this.text_time = text_time;
        this.text_context = text_context;
        this.img_id = img_id;
        this.kinds = kinds;
    }

    public String getText_time() {
        return text_time;
    }

    public void setText_time(String text_time) {
        this.text_time = text_time;
    }

    public String getText_context() {
        return text_context;
    }

    public void setText_context(String text_context) {
        this.text_context = text_context;
    }

    public String getImg_id() {
        return img_id;
    }

    public void setImg_id(String img_id) {
        this.img_id = img_id;
    }

    public int getKinds() {
        return kinds;
    }

    public void setKinds(int kinds) {
        this.kinds = kinds;
    }
}
