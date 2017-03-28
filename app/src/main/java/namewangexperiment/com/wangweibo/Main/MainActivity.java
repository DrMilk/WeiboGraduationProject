package namewangexperiment.com.wangweibo.Main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;
import cn.bmob.v3.BmobUser;
import namewangexperiment.com.wangweibo.KeySearch.WangSearch;
import namewangexperiment.com.wangweibo.OnlineData.WangUser;
import namewangexperiment.com.wangweibo.R;
import namewangexperiment.com.wangweibo.Utils.L;
import namewangexperiment.com.wangweibo.Utils.MyUpload;
import namewangexperiment.com.wangweibo.Utils.SharePreferenceUtil;
import namewangexperiment.com.wangweibo.login.LoginActivity;
import namewangexperiment.com.wangweibo.write.Writetreememory;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener{
    private String TAG="MainActivity";
    private Context mcontext;
    private RelativeLayout head_rl;
    private WangUser wangUser;
    private ImageView image_head;
    private TextView tv_name;
    private TextView tv_sign;
    private MyUpload myUpload;
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

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        image_head.setImageResource(R.mipmap.ic_alert_green);
        head_rl.setOnClickListener(this);
        image_head.setOnClickListener(this);
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
        } else if (id == R.id.nav_search) {
            Intent it=new Intent(MainActivity.this, WangSearch.class);
            startActivity(it);
        } else if (id == R.id.nav_minetab) {
            Intent it=new Intent(MainActivity.this, SettingActivity.class);
            startActivity(it);
        } else if (id == R.id.nav_manage) {
            Intent it1=new Intent(MainActivity.this,PersonaldActivity.class);startActivity(it1);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

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
        if(checkuser()){
            tv_name.setText(wangUser.getName());
            String sign=wangUser.getSign();
            if(sign!=null){
                tv_sign.setText(sign);
            }
            if(wangUser.isImgheadstutas()){
                myUpload.download_asynchronous("wangweibodata", "headimg/" + wangUser.getUsername(),image_head);
                L.i(TAG,"不会没更新投降吧！");
            }
        }

    }
}
