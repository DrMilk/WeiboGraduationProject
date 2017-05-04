package namewangexperiment.com.wangweibo.chat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

import java.util.ArrayList;

import namewangexperiment.com.wangweibo.Main.About;
import namewangexperiment.com.wangweibo.R;
import namewangexperiment.com.wangweibo.Utils.L;
import namewangexperiment.com.wangweibo.WuUpload.MyInfoDao;

/**
 * Created by Administrator on 2017/5/3.
 */

public class ChatService extends Service{
    private MyInfoDao myinfodao;
    private String TAG="ChatService";
    private String userid;
    private NotificationManager nm;
    private Boolean init_status=false;
    private MyInfoDao infocontext;
    @Override
    public void onCreate() {
        L.i(TAG,"onCreate");
        nm= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.i(TAG,"onStartCommand");
        Bundle bundle=intent.getExtras();
        userid= (String) bundle.get("userid");
        if(!init_status){
            infocontext=new MyInfoDao(getApplicationContext(),1);
            receiveMsgFrom();
            init_status=true;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        L.i(TAG,"onCreate");
        return null;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
    private void receiveMsgFrom() {
        //腾讯登录
        AVIMClient jerry = AVIMClient.getInstance(userid);
        jerry.open(new AVIMClientCallback(){
            @Override
            public void done(AVIMClient client,AVIMException e){
                if(e==null){
                    L.i(TAG,"登录成功！"+userid);
                    //登录成功后的逻辑
                    AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());
                }else {
                    L.i(TAG,e.toString());
                    //   e.toString()
                }
            }
        });
    }
    private class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            if(message instanceof AVIMTextMessage){
                Intent intent=new Intent();
                intent.setClass(ChatService.this, Wechat.class);
                Bundle bundle=new Bundle();
                bundle.putString("toid",conversation.getCreator());
                intent.putExtras(bundle);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(),1,intent,0);
                Notification notifation=new Notification.Builder(getApplicationContext()).setContentTitle("心情微博")
                        .setContentText("你收到了别人的私信")
                        .setContentIntent(pi)
                        .setDefaults(Notification.DEFAULT_SOUND)
                        .setSmallIcon(R.mipmap.upload_icon)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.logo)).build();
                nm.notify(10,notifation);
                L.i(TAG,"收到了一条"+message);
                L.i(TAG,conversation.getCreator()+((AVIMTextMessage) message).getText()+String.valueOf(conversation.getCreatedAt()));
                ContentValues content_values=new ContentValues();
                content_values.put("writename",conversation.getCreator());
                content_values.put("details",((AVIMTextMessage) message).getText());
                content_values.put("createtime", String.valueOf(conversation.getCreatedAt()));
                long id=infocontext.goInsert("content",content_values);
                L.i(TAG,"----------------------"+id);
                ArrayList<ContentValues> list=infocontext.goQuery("conversation","writename=?",new String[]{conversation.getCreator()});
                if(list.size()==0){
                    ContentValues conversation_values=new ContentValues();
                    conversation_values.put("writename",conversation.getCreator());
                    conversation_values.put("lookstatus","1");
                    conversation_values.put("createtime",String.valueOf(conversation.getCreatedAt()));
                    conversation_values.put("chataddress",String.valueOf(id));
                    conversation_values.put("lasttext",((AVIMTextMessage) message).getText());
                    conversation_values.put("textnum",1);
                    infocontext.goInsert("conversation",conversation_values);
                }else {
                    ContentValues conversation_values=list.get(0);
                    conversation_values.put("createtime",String.valueOf(conversation.getCreatedAt()));
                    String chataddress= (String) conversation_values.get("chataddress");
                    StringBuffer sb=new StringBuffer();
                    sb.append(chataddress);
                    L.i(TAG,sb.toString());
                    sb.append(String.valueOf(id)+"|");
                    L.i(TAG,sb.toString());
                    conversation_values.put("chataddress",sb.toString());
                    L.i(TAG,sb.toString());
                    conversation_values.put("lasttext",((AVIMTextMessage) message).getText());
                    int count= Integer.valueOf((String) conversation_values.get("textnum"));
                    count++;
                    conversation_values.put("textnum",count+"");
                    infocontext.goUpdate("conversation",conversation_values,"writename=?",new String[]{conversation.getCreator()});
                }
//                listdata.add(new ChatInfo(changetime(conversation.getCreatedAt()),((AVIMTextMessage)message).getText(),toid,2));
//                L.d("Tom & Jerry",((AVIMTextMessage)message).getText()+conversation.getCreator()+client.getClientId());
//                chatlistadapter.notifyDataSetChanged();
//                listview.smoothScrollToPosition(listview.getCount() - 1);
                infocontext.showall("content");
                infocontext.showall("conversation");
            }
        }

        public void onMessageReceipt(AVIMMessage message,AVIMConversation conversation,AVIMClient client){
            L.d("Tom & Jerry","a");
        }
    }
}
