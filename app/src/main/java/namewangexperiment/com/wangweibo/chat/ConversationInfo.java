package namewangexperiment.com.wangweibo.chat;

/**
 * Created by Administrator on 2017/5/4.
 */

public class ConversationInfo {
    private String text_title;
    private String text_content;
    private String text_num;
    private String time;
    private String text_writername;

    public ConversationInfo(String text_title, String text_content, String text_num, String time, String text_writername) {
        this.text_title = text_title;
        this.text_content = text_content;
        this.text_num = text_num;
        this.time = time;
        this.text_writername = text_writername;
    }

    public String getText_title() {
        return text_title;
    }

    public void setText_title(String text_title) {
        this.text_title = text_title;
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

    public String getText_writername() {
        return text_writername;
    }

    public void setText_writername(String text_writername) {
        this.text_writername = text_writername;
    }
}
