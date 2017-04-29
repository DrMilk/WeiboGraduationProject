package namewangexperiment.com.wangweibo.chat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.bmob.v3.BmobUser;
import namewangexperiment.com.wangweibo.OnlineData.WangUser;
import namewangexperiment.com.wangweibo.R;
import namewangexperiment.com.wangweibo.Utils.HttpUtil;
import namewangexperiment.com.wangweibo.Utils.L;
import namewangexperiment.com.wangweibo.Utils.StringLegalUtil;

/**
 * Created by Administrator on 2017/4/27.
 */

public class Wechat extends Activity implements View.OnClickListener{
    private ListView listview;
    private ChaListtAdapter chatlistadapter;
    private Context context;
    private ImageView button_send;
    private EditText edit_context;
    private String TAG="Wechat";
    private String userid;
    private ArrayList<ChatInfo> listdata;
    private String toid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        context=this;
        recvievdata();
        findViewId();
        initView();
    }

    private void recvievdata() {
        Intent it=getIntent();
        Bundle bundle=it.getExtras();
        toid= (String) bundle.get("toid");
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkuser();
    }

    private void initView() {
        listdata=new ArrayList<>();
        chatlistadapter=new ChaListtAdapter(context,listdata);
        listview.setAdapter(chatlistadapter);
    }

    private void findViewId() {
        listview= (ListView) findViewById(R.id.list_chat);
        edit_context= (EditText) findViewById(R.id.chat_context);
        button_send= (ImageView) findViewById(R.id.chat_send);
        button_send.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.chat_send:
                String strmsg=edit_context.getText().toString().trim();
                if(StringLegalUtil.isHaveLength(strmsg)){
                    sendContext(strmsg);
                }
                L.i(TAG,"点了");break;
        }
    }

    private void sendContext(String strmsg) {
        listdata.add(new ChatInfo(getTime(),strmsg,userid,1));
        chatlistadapter.notifyDataSetChanged();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Map<String,String> map=new HashMap<>();
//                map.put("X-LC-Id","hTVvDXfB5MoFlEUCLHMJssGm-9Nh9j0Va");
//                map.put("X-LC-Key","KD9tOzBNy3i5I7"+"I9kOAdQiLX");
//                map.put("Content-Type","application/json");
//                String s=new String("{\"name\":\"My Private Room\",\"m\": [\"吴振宇\", \"金清宇\"]}，\"data\":\"今天天气不错！\"");
//                try {
//                    HttpUtil.httpPost("https://tab.leancloud.cn/1.1/classes/_Conversation",map,s);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        ChatUtil.sendMessage(strmsg,toid,userid,userid+"&"+toid);
    }
    private void test(){
        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    L.i("saved","success!");
                }
            }
        });
    }
    private boolean checkuser() {
        WangUser bmobUser = BmobUser.getCurrentUser(WangUser.class);
        if(bmobUser != null){
            userid=bmobUser.getUsername();
            ReceiveMsgFrom();
            AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());
            // 允许用户使用应用
            //  String name= (String) BmobUser.getObjectByKey("treename");
            //  text_username.setText(name);
            return true;
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            return false;
        }
    }
    public void ReceiveMsgFrom(){
        //Jerry登录
        AVIMClient jerry = AVIMClient.getInstance(userid);
        jerry.open(new AVIMClientCallback(){
            @Override
            public void done(AVIMClient client,AVIMException e){
                if(e==null){
                    L.i(TAG,"登录成功！"+userid);
                    //登录成功后的逻辑
                }else {
                    L.i(TAG,e.toString());
                    //   e.toString()
                }
            }
        });
    }
    private String changetime(Date time){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String timestr=sdf.format(time);
        return timestr.substring(0,16);
    }
    private String getTime(){
        SimpleDateFormat formatter    =   new    SimpleDateFormat    ("yyyy-MM-dd HH:mm");
        Date curDate    =   new    Date(System.currentTimeMillis());//获取当前时间
        String    str    =    formatter.format(curDate);
        return str.substring(0,16);
    }
    private class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            if(message instanceof AVIMTextMessage){
                listdata.add(new ChatInfo(changetime(conversation.getCreatedAt()),((AVIMTextMessage)message).getText(),toid,2));
                L.d("Tom & Jerry",((AVIMTextMessage)message).getText()+conversation.getCreator()+client.getClientId());
                chatlistadapter.notifyDataSetChanged();
                listview.smoothScrollToPosition(listview.getCount() - 1);
            }
            L.d("Tom & Jerry","aa");
        }

        public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){
            L.d("Tom & Jerry","a");
        }
    }
}
