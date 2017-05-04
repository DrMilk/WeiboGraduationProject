package namewangexperiment.com.wangweibo.chat;

/**
 * Created by Administrator on 2017/5/4.
 */

public class ConversationInfo {
    private String name;
    private String text_content;
    private String text_num;
    private String time;

    public ConversationInfo(String name, String text_content, String text_num, String time) {
        this.name = name;
        this.text_content = text_content;
        this.text_num = text_num;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText_content() {
        return text_content;
    }

    public void setText_content(String text_content) {
        this.text_content = text_content;
    }

    public String getText_num() {
        return text_num;
    }

    public void setText_num(String text_num) {
        this.text_num = text_num;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
