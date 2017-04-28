package namewangexperiment.com.wangweibo.chat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import namewangexperiment.com.wangweibo.R;
import namewangexperiment.com.wangweibo.Utils.HttpUtil;
import namewangexperiment.com.wangweibo.Utils.L;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        context=this;
        findViewId();
        initView();
    }

    private void initView() {
        ArrayList<ChatInfo> listdata=new ArrayList<>();
        listdata.add(new ChatInfo("4月10日","哈喽？","242",1));
        listdata.add(new ChatInfo("4月11日","哈喽!","242",2));
        listdata.add(new ChatInfo("4月12日","哈喽？","242",1));
        listdata.add(new ChatInfo("4月13日","哈喽a!哈喽a!哈喽a!哈喽a!哈喽a!哈喽a!哈喽a!哈喽a!哈喽a!哈喽a!哈喽a!哈喽a!v哈喽a!","242",2));
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
            case R.id.chat_send:sendContext();
                L.i(TAG,"点了");break;
        }
    }

    private void sendContext() {
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
}
