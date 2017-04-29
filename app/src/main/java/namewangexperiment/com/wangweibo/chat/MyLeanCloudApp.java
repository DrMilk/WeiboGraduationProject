package namewangexperiment.com.wangweibo.chat;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import namewangexperiment.com.wangweibo.Utils.L;

/**
 * Created by Administrator on 2017/4/28.
 */

public class MyLeanCloudApp extends Application{
    private String TAG="MyLeanCloudApp";
    public void onCreate(){
        AVOSCloud.initialize(this,"hTVvDXfB5MoFlEUCLHMJssGm-9Nh9j0Va","KD9tOzBNy3i5I7I9kOAdQiLX");
        //注册默认的消息处理逻辑
       // ReceiveMsgFrom();
    }
    public void ReceiveMsgFrom(){
        //Jerry登录
        AVIMClient jerry = AVIMClient.getInstance("jinqingyu");
        jerry.open(new AVIMClientCallback(){
            @Override
            public void done(AVIMClient client,AVIMException e){
                if(e==null){
                    L.i(TAG,"登录成功！");
                   //登录成功后的逻辑
                }else {
                    L.i(TAG,e.toString());
                 //   e.toString()
                }
            }
        });
    }
}
