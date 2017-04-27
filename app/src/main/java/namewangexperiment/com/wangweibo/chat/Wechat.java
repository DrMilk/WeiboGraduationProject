package namewangexperiment.com.wangweibo.chat;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import namewangexperiment.com.wangweibo.R;

/**
 * Created by Administrator on 2017/4/27.
 */

public class Wechat extends Activity implements View.OnClickListener{
    private ListView listview;
    private ChaListtAdapter chatlistadapter;
    private Context context;
    private ImageView button_send;
    private EditText edit_context;
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
            case R.id.chat_send:sendContext();break;
        }
    }

    private void sendContext() {
    }
}
