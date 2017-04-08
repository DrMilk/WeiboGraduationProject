package namewangexperiment.com.wangweibo.mintattentions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;
import namewangexperiment.com.wangweibo.Main.MainActivity;
import namewangexperiment.com.wangweibo.MainInfor.Maintab;
import namewangexperiment.com.wangweibo.OnlineData.WangUser;
import namewangexperiment.com.wangweibo.R;
import namewangexperiment.com.wangweibo.Utils.L;
import namewangexperiment.com.wangweibo.Utils.T;
import namewangexperiment.com.wangweibo.login.LoginActivity;

/**
 * Created by Administrator on 2017/4/3.
 */

public class MainAttentions extends Activity {
    private String TAG="MainAttentions";
    private Context mcontext;
    private ListView listview_attentons;
    private ListAttentionsAdapter adapter;
    private WangUser wangUser;
    private ArrayList<WangUser> list_wangusr=new ArrayList<>();
    private int attentions_length=0;
    private LinearLayout linear_process;
    private ArrayList<String> list_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attentions);
        mcontext=this;
        initView();
    }

    private void initView() {
        listview_attentons= (ListView) findViewById(R.id.list_attentions);
        linear_process= (LinearLayout) findViewById(R.id.tab_context_linear_loading);
        ImageView img_context_loading= (ImageView) findViewById(R.id.tab_context_loading_anim);
        AnimationDrawable anim_context_loading= (AnimationDrawable) img_context_loading.getDrawable();
        anim_context_loading.start();;

    }

    @Override
    protected void onResume() {
        if(checkuser()){
            list_str=new ArrayList<>();
            list_str=wangUser.getList_attention();
            attentions_length=list_str.size();
            for(int i=0;i<list_str.size();i++){
                L.i(TAG,list_str.size()+"");
                seekattentions(list_str.get(i));
            }
        }
        super.onResume();
    }
    private boolean checkuser() {
        wangUser = BmobUser.getCurrentUser(WangUser.class);
        if(wangUser != null){
            //  text_username.setText(name);
            return true;
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            Intent it=new Intent(MainAttentions.this, LoginActivity.class);
            startActivity(it);
            MainAttentions.this.finish();
            return false;
        }
    }
    private void seekattentions(String s){
        BmobQuery<WangUser> query = new BmobQuery<WangUser>();
        query.addWhereEqualTo("username", s);
        query.findObjects(new FindListener<WangUser>() {
            @Override
            public void done(List<WangUser> object, BmobException e) {
                if(e==null){
                    L.i(TAG,"找到一个用户");
                    WangUser bmobUserfind= (WangUser) object.get(0);
                    list_wangusr.add(bmobUserfind);
                    if(list_wangusr.size()==attentions_length){
                        linear_process.setVisibility(View.GONE);
                        adapter=new ListAttentionsAdapter(mcontext,list_wangusr,wangUser);
                        listview_attentons.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                        listview_attentons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent it=new Intent(MainAttentions.this, Maintab.class);
                                Bundle bundle=new Bundle();
                                bundle.putSerializable("wanguesr",list_wangusr.get(position));
                                it.putExtras(bundle);
                                startActivity(it);
                            }
                        });
                    }
                }else{
                    L.i(TAG,"没有该用户"+e.toString());
                    T.showShot(mcontext,"网络异常 个别用户没有找到");
                    linear_process.setVisibility(View.GONE);
                    adapter=new ListAttentionsAdapter(mcontext,list_wangusr,wangUser);
                    listview_attentons.setAdapter(adapter);
                    listview_attentons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent it=new Intent(MainAttentions.this, Maintab.class);
                            Bundle bundle=new Bundle();
                            bundle.putSerializable("wanguesr",list_wangusr.get(position));
                            it.putExtras(bundle);
                            startActivity(it);
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        }

    @Override
    protected void onStop() {boolean[] jundge=adapter.getJundge();
        for(int i=0;i<jundge.length;i++){
            if(jundge[i]){
                for(int q=0;q<list_str.size();q++){
                    if (list_str.equals(list_wangusr.get(i).getUsername())){
                        String s=list_str.get(q);
                        s="没有";
                    }
                }
            }
        }
        int length=list_str.size();
        for(int p=0;p<length;p++){
            if(list_str.get(p).equals("没有")){
                list_str.remove(p);
                length--;
            }
        }
        wangUser.setList_attention(list_str);
        wangUser.update(wangUser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e==null){
                    T.showShot(mcontext,"更新我的关注成功");
                }else {
                    T.showShot(mcontext,"更新我的关注失败");
                }
            }
        });
        super.onStop();
    }
}
