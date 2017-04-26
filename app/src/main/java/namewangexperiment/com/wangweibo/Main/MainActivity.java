package namewangexperiment.com.wangweibo.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import namewangexperiment.com.wangweibo.KeySearch.WangSearch;
import namewangexperiment.com.wangweibo.MainInfor.Maintab;
import namewangexperiment.com.wangweibo.MainInfor.WangContextRecyclerViewAdapter;
import namewangexperiment.com.wangweibo.OnlineData.WangContext;
import namewangexperiment.com.wangweibo.OnlineData.WangUser;
import namewangexperiment.com.wangweibo.R;
import namewangexperiment.com.wangweibo.Utils.AppUtil;
import namewangexperiment.com.wangweibo.Utils.L;
import namewangexperiment.com.wangweibo.Utils.MyUpload;
import namewangexperiment.com.wangweibo.Utils.SharePreferenceUtil;
import namewangexperiment.com.wangweibo.Utils.T;
import namewangexperiment.com.wangweibo.login.ChangePasswordActivity;
import namewangexperiment.com.wangweibo.login.LoginActivity;
import namewangexperiment.com.wangweibo.mintattentions.MainAttentions;
import namewangexperiment.com.wangweibo.write.Writetreememory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener,SwipeRefreshLayout.OnRefreshListener{
    private String TAG="MainActivity";
    private Context mcontext;
    private RelativeLayout head_rl;
    private WangUser wangUser;
    private ImageView image_head;
    private TextView tv_name;
    private TextView tv_sign;
    private MyUpload myUpload;
    private ArrayList<WangContext> list_context=new ArrayList<>();
    private BmobQuery<WangContext> query;
    private int allcontextnum=0;
    private RecyclerView recyclerView_context;
    private WangContextRecyclerViewAdapter mcontextAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout linear_no;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext=this;
        myUpload=new MyUpload(mcontext);
        initView();
     // mbmobinitdata();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view_head= navigationView.getHeaderView(0);
        head_rl= (RelativeLayout) view_head.findViewById(R.id.header_headimg_main_bg);
        image_head= (ImageView) view_head.findViewById(R.id.header_headimg);
        tv_name= (TextView) view_head.findViewById(R.id.header_name);
        tv_sign= (TextView) view_head.findViewById(R.id.header_sign);
        head_rl.setOnClickListener(this);
        image_head.setOnClickListener(this);
        recyclerView_context= (RecyclerView) findViewById(R.id.list_context_main);
        linear_no= (LinearLayout) findViewById(R.id.tab_context_linear_no);
        linear_no.setVisibility(View.GONE);
        swipeRefreshLayout= (SwipeRefreshLayout) findViewById(R.id.main_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.purple_level,R.color.green_level,
                R.color.blue_level, R.color.orange_level);
        swipeRefreshLayout.setDistanceToTriggerSync(400);// 设置手指在屏幕下拉多少距离会触发下拉刷新
        //swipeRefreshLayout.setProgressBackgroundColor(R.color.red); // 设定下拉圆圈的背景
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT); // 设置圆圈的大小
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            SharePreferenceUtil.putSettingDataBoolean(mcontext,SharePreferenceUtil.AUTOLOGIN,false);
            BmobUser.logOut();
            Intent it=new Intent(MainActivity.this, LoginActivity.class);startActivity(it);MainActivity.this.finish();
            return true;
        }else if(id==R.id.action_changerpassword){
            Intent it=new Intent(MainActivity.this, ChangePasswordActivity.class);startActivity(it);MainActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_write) {
            // Handle the camera action
            Intent it=new Intent(MainActivity.this, Writetreememory.class);
            startActivity(it);
            MainActivity.this.finish();
        } else if (id == R.id.nav_search) {
            Intent it=new Intent(MainActivity.this, WangSearch.class);
            startActivity(it);
        } else if (id == R.id.nav_minetab) {
            Intent it=new Intent(MainActivity.this, Maintab.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("wanguesr",wangUser);
            it.putExtras(bundle);
            startActivity(it);
        } else if (id == R.id.nav_manage) {
            Intent it1=new Intent(MainActivity.this,SettingsActivity.class);startActivity(it1);
        } else if (id == R.id.nav_share) {
            startActivity(Intent.createChooser(AppUtil.getShareMsgIntentTwo("快去下载《心情》app，关注我哦~"+"   id"+wangUser.getUsername()),"分享到"));
        } else if (id == R.id.nav_send) {
            Intent it1=new Intent(MainActivity.this,About.class);startActivity(it1);
        }else if(id==R.id.nav_mineatttions){
            Intent it1=new Intent(MainActivity.this, MainAttentions.class);
            startActivity(it1);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void mbmobinitdata() {
        Bmob.initialize(this, "99dd404fe87588c447057b2a1d533eee");
        //第二：自v3.4.7版本开始,设置BmobConfig,允许设置请求超时时间、文件分片上传时每片的大小、文件的过期时间(单位为秒)，
        BmobConfig config =new BmobConfig.Builder(this)
                //设置appkey
                .setApplicationId("99dd404fe87588c447057b2a1d533eee")
                //请求超时时间（单位为秒）：默认15s
                .setConnectTimeout(30)
                //文件分片上传时每片的大小（单位字节），默认512*1024
                .setUploadBlockSize(1024*1024)
                //文件的过期时间(单位为秒)：默认1800s
                .setFileExpiration(2500)
                .build();
        Bmob.initialize(config);
    }
    private boolean checkuser() {
        WangUser bmobUser = BmobUser.getCurrentUser(WangUser.class);
        if(bmobUser != null){
            wangUser=bmobUser;
            //  text_username.setText(name);
            return true;
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            Intent it=new Intent(MainActivity.this, LoginActivity.class);
            startActivity(it);
            MainActivity.this.finish();
            return false;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.header_headimg_main_bg:
                Intent it=new Intent(MainActivity.this,PersonaldActivity.class);startActivity(it);
                break;
            case R.id.header_headimg:
                L.i(TAG,"点了吗！");
                Intent it1=new Intent(MainActivity.this,PersonaldActivity.class);startActivity(it1);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
      //  this.onRefresh();
        if(list_context.size()==0){
            swipeRefreshLayout.post(new Runnable(){
                @Override
                public void run() {
                    swipeRefreshLayout.setRefreshing(true);
                }
            });
            if(checkuser()){
                tv_name.setText(wangUser.getName());
                String sign=wangUser.getSign();
                if(sign!=null){
                    tv_sign.setText(sign);
                }
                    myUpload.download_asynchronous_head("wangweibodata", "headimg/" + wangUser.getUsername(),image_head);
            }
            ArrayList<String> list_context_look=wangUser.getList_attention();
            L.i(TAG,list_context_look.size()+"我关注的用户数");
            for(int i=0;i<list_context_look.size();i++){
                L.i(TAG,list_context_look.size()+"");
                seekattentions(list_context_look.get(i));
            }
            ArrayList<String> list_minecontext_id=wangUser.getList_mine();
            allcontextnum+=list_minecontext_id.size();
            for(int q=0;q<list_minecontext_id.size();q++){
                findContext(list_minecontext_id.get(q),q);
            }
            if(list_minecontext_id.size()==0&&list_context_look.size()==0){
                swipeRefreshLayout.post(new Runnable(){
                    @Override
                    public void run() {
                        linear_no.setVisibility(View.VISIBLE);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
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
                    ArrayList<String> list_contextid=bmobUserfind.getList_mine();
                    L.i(TAG,"找到一个用户的文章数"+list_contextid.size());
                    allcontextnum+=list_contextid.size();
                        for(int i=0;i<list_contextid.size();i++){
                            findContext(list_contextid.get(i),i);
                        }
                }else{
                    swipeRefreshLayout.setRefreshing(false);
                    L.i(TAG,"没有该用户"+e.toString());
                    T.showShot(mcontext,"没找到该用户");
                }
            }
        });
    }
    private void findContext(String str_objectId,int i){
        query = new BmobQuery<WangContext>();
        query.getObject(str_objectId, new QueryListener<WangContext>() {

            @Override
            public void done(WangContext object, BmobException e) {
                if(e==null){
                    list_context.add(object);
                    L.i(TAG,"找到一个文章"+list_context.size()+"allcontextnum"+allcontextnum);
                    if(list_context.size()==allcontextnum){
                        L.i(TAG,"执行更新了");
                        if(updataContext()){
                            msetlistAdatper();
                        }
                    }
                }else{
                    T.showShot(mcontext,"服务器异常 个别文章加载失败");
                    swipeRefreshLayout.setRefreshing(false);
                    if(updataContext()){
                        msetlistAdatper();
                    }
                }
            }

        });
    }
    private boolean updataContext() {
        L.i(TAG,"排序");
        final SimpleDateFormat sdf=new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        final Date[] data1 = {null};
        final Date[] data2 = {null};
        for (int i=0;i<list_context.size();i++){
            Log.i(TAG,list_context.get(i).getCreatedAt()+"日期");
        }
        Comparator<WangContext> comparator = new Comparator<WangContext>(){
            public int compare(WangContext s1, WangContext s2) {
                //排序日期
                try {
                    data1[0] =sdf.parse(s1.getCreatedAt());
                    data2[0] =sdf.parse(s2.getCreatedAt());
                } catch (ParseException e) {
                    Log.i(TAG,"wenti");
                    e.printStackTrace();
                }
                if(data1[0].getTime()> data2[0].getTime()){
                    return -1;
                }else {
                    return 1;
                }
            }
        };
        if(list_context.size()>1){
            Collections.sort(list_context,comparator);
        }
        return true;
    }
    private void msetlistAdatper() {
        L.i(TAG,"更新recycleview");
        swipeRefreshLayout.setRefreshing(false);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView_context.setLayoutManager(staggeredGridLayoutManager);
        mcontextAdapter=new WangContextRecyclerViewAdapter(this,list_context);
        mcontextAdapter.setItemContextOnclickListenner(new WangContextRecyclerViewAdapter.ItemContextnclickListenner() {
            @Override
            public void onitemclickcontext(WangContextRecyclerViewAdapter.MyViewHolder viewHolder, int postion) {
                Intent it=new Intent(MainActivity.this,DetailContextActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable("detailcontext",list_context.get(postion));
                it.putExtras(bundle);
                L.i(TAG,"到这步了~");
                startActivity(it);
            }
        });
        recyclerView_context.setAdapter(mcontextAdapter);
//        listview_context.setAdapter(new Maincontext_Adapter(this,alist_context,alist_time,alist_level,alist_writer,alist_num,alist_numURL));
     //   context_loading_linear.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRefresh() {
        L.i(TAG,"onRefresh了");
        L.i(TAG,"onRefresh了"+"到这步1");
       //list_context=new ArrayList<>();
        if(list_context.size()==0){
            if(checkuser()){
                tv_name.setText(wangUser.getName());
                String sign=wangUser.getSign();
                if(sign!=null){
                    tv_sign.setText(sign);
                }
                myUpload.download_asynchronous_head("wangweibodata", "headimg/" + wangUser.getUsername(),image_head);
            }
            L.i(TAG,"onRefresh了"+"到这步1");
            ArrayList<String> list_context_look=wangUser.getList_attention();
            L.i(TAG,list_context_look.size()+"我关注的用户数");
            for(int i=0;i<list_context_look.size();i++){
                L.i(TAG,list_context_look.size()+"");
                seekattentions(list_context_look.get(i));
                L.i(TAG,"onRefresh了"+"到这步2");
            }
            ArrayList<String> list_minecontext_id=wangUser.getList_mine();
            allcontextnum+=list_minecontext_id.size();
            for(int q=0;q<list_minecontext_id.size();q++){
                findContext(list_minecontext_id.get(q),q);
                L.i(TAG,"onRefresh了"+"到这步3");
            }
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
                T.showShot(mcontext,"刷新完成!");
            }
        },5000);
    }
}
