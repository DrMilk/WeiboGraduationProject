package namewangexperiment.com.wangweibo.chat;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import namewangexperiment.com.wangweibo.MainInfor.Maintab;
import namewangexperiment.com.wangweibo.R;
import namewangexperiment.com.wangweibo.WuUpload.MyInfoDao;
import namewangexperiment.com.wangweibo.mintattentions.MainAttentions;

/**
 * Created by Administrator on 2017/5/4.
 */

public class ChatConversation extends Activity{
    private String TAG="ChatConversation";
    private Context mcontext;
    private ListView listView;
    private ArrayList<ConversationInfo> list_data;
    private ChatConversationAdapter adatpter;
    private MyInfoDao myinfodao;
    private Handler mhandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_conversation);
        mcontext=this;
        myinfodao=new MyInfoDao(mcontext,1);
        initView();
    }

    private void initView() {
        list_data=new ArrayList<>();
        listView= (ListView) findViewById(R.id.chat_listview);
        adatpter=new ChatConversationAdapter(mcontext,list_data);
        listView.setAdapter(adatpter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it1=new Intent(ChatConversation.this, Wechat.class);
                Bundle bundle=new Bundle();
                bundle.putString("toid",list_data.get(position).getText_writername());
                it1.putExtras(bundle);
                startActivity(it1);
                updatesqldata(list_data.get(position).getText_writername());
            }
        });
    }

    private void updatesqldata(String s) {
        ArrayList<ContentValues> list_result=myinfodao.goQuery(myinfodao.CONVERSATION,"writename=?",new String[]{s});
        ContentValues result=list_result.get(0);
        result.put("textnum","0");
        myinfodao.goUpdate(myinfodao.CONVERSATION,result,"writename=?",new String[]{s});
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(list_data.size()==0){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ArrayList<ContentValues> list_update=myinfodao.showall("conversation");
                    for (int i=0;i<list_update.size();i++){
                        list_data.add(new ConversationInfo((String)list_update.get(i).get("writename"),(String)list_update.get(i).get("lasttext"),
                                (String)list_update.get(i).get("textnum"),(String)list_update.get(i).get("createtime"),
                                (String)list_update.get(i).get("writename")));
                    }
                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {
                            adatpter.notifyDataSetChanged();
                        }
                    });
                }
            }).start();
        }
    }
}
