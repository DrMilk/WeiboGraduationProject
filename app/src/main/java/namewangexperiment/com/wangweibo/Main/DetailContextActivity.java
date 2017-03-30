package namewangexperiment.com.wangweibo.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import namewangexperiment.com.wangweibo.OnlineData.WangContext;
import namewangexperiment.com.wangweibo.OnlineData.WangRemark;
import namewangexperiment.com.wangweibo.OnlineData.WangUser;
import namewangexperiment.com.wangweibo.R;
import namewangexperiment.com.wangweibo.Utils.L;
import namewangexperiment.com.wangweibo.Utils.MyUpload;
import namewangexperiment.com.wangweibo.Utils.T;
import namewangexperiment.com.wangweibo.login.LoginActivity;

/**
 * Created by Administrator on 2017/3/30.
 */

public class DetailContextActivity extends Activity{
    private String TAG="DetailContextActivity";
    private Button remark_button;
    private EditText remark_edit;
    private ListView listView;
    private RemarkAdapter madapter;
    private Context mcontext;
    private LayoutInflater inflater;
    private WangContext wangcontext;
    private String name;
    private TextView context_text,level,time,writer,bottom_address;
    private ImageView img1,img2,img3;
    private ImageView level_img;
    private LinearLayout linear_img;
    private MyUpload myUpload;
    private ArrayList<WangRemark> listremark=new ArrayList<>();
    private ArrayList<String> list_str=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_context);
        mcontext=this;
        inflater=LayoutInflater.from(mcontext);
        myUpload=new MyUpload(mcontext);
        recivedata();
        initview();
    }

    private void updateview() {
        L.i(TAG,"updateview到这步了~");
            madapter.notifyDataSetChanged();
    }

    private void recivedata() {
        Intent it=getIntent();
        Bundle bundle=it.getExtras();
        wangcontext= (WangContext) bundle.getSerializable("detailcontext");
        list_str=wangcontext.getList_remark();
        L.i(TAG,"detailcontext~"+wangcontext.getContext());
        for(int i=0;i<list_str.size();i++){
            BmobQuery<WangRemark> query = new BmobQuery<WangRemark>();
            query.getObject(list_str.get(i), new QueryListener<WangRemark>() {
                @Override
                public void done(WangRemark remakdata, BmobException e) {
                    if(e==null){
                        listremark.add(remakdata);
                        if(listremark.size()==list_str.size())
                        updateview();
                    }else {

                    }
                }
            });
        }
    }

    private void initview() {
        listView= (ListView) findViewById(R.id.activity_detail_listview);
        View detail_context=inflater.inflate(R.layout.list_context_detail_head,null);
        View detail_remark=inflater.inflate(R.layout.remark_foot,null);
        remark_edit= (EditText) detail_remark.findViewById(R.id.foot_edittext);
        remark_button= (Button) detail_remark.findViewById(R.id.foot_button);
        context_text= (TextView) detail_context.findViewById(R.id.list_context_text);
        img1= (ImageView) detail_context.findViewById(R.id.list_context_image1);
        img2= (ImageView) detail_context.findViewById(R.id.list_context_image2);
        img3= (ImageView) detail_context.findViewById(R.id.list_context_image3);
        level_img= (ImageView) detail_context.findViewById(R.id.list_context_levelpic);
        level= (TextView) detail_context.findViewById(R.id.list_context_level);
        time= (TextView) detail_context.findViewById(R.id.list_context_time);
        writer= (TextView) detail_context.findViewById(R.id.list_context_writer);
        linear_img= (LinearLayout) detail_context.findViewById(R.id.list_context_linearimg);
        bottom_address= (TextView) detail_context.findViewById(R.id.list_context_address);
        context_text.setText(wangcontext.getContext());
        time.setText(wangcontext.getCreatedAt().substring(0,16));
        writer.setText(wangcontext.getWritename());
        bottom_address.setText(wangcontext.getAddress());
        L.i(TAG,wangcontext.getContext()+wangcontext.getCreatedAt().substring(0,16)+wangcontext.getWritename()+wangcontext.getAddress());
        switch (wangcontext.getFakelevel()) {
            case 0:
                level_img.setImageResource(R.mipmap.ic_alert_purple);
                level.setText("狂喜");
                level.setTextColor(mcontext.getResources().getColor(R.color.purple_level));
                context_text.setTextColor(mcontext.getResources().getColor(R.color.purple_level));
                break;   //设置颜色break;
            case 1:
                level_img.setImageResource(R.mipmap.ic_alert_red);
                level.setText("开心");
                level.setTextColor(mcontext.getResources().getColor(R.color.red_level));
                context_text.setTextColor(mcontext.getResources().getColor(R.color.red_level));
                break;
            case 2:
                level_img.setImageResource(R.mipmap.ic_alert_yellow);
                level.setText("一般");
                level.setTextColor(mcontext.getResources().getColor(R.color.yello_level));
                context_text.setTextColor(mcontext.getResources().getColor(R.color.yello_level));
                break;
            case 3:
                level_img.setImageResource(R.mipmap.ic_alert_bule);
                level.setText("难过");
                level.setTextColor(mcontext.getResources().getColor(R.color.blue_level));
                context_text.setTextColor(mcontext.getResources().getColor(R.color.blue_level));
                break;
            case 4:
                level_img.setImageResource(R.mipmap.ic_alert_green);
                level.setText("伤悲");
                level.setTextColor(mcontext.getResources().getColor(R.color.green_level));
                context_text.setTextColor(mcontext.getResources().getColor(R.color.green_level));
                break;
        }
        switch (wangcontext.getNum()) {
            case 0:
                linear_img.setVisibility(View.GONE);
                break;
            case 1:
                img1.setTag(wangcontext.getObjectId()+"_img1");
                myUpload.download_asynchronous("wangweibodata", "context/" + wangcontext.getObjectId() + "/" + "img1",img1);
                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                            Intent it=new Intent(mcontext, About.class);
//                            mcontext.startActivity(it);
                    }
                });
                img2.setVisibility(View.INVISIBLE);
                img3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                img1.setTag(wangcontext.getObjectId()+"_img1");
                img2.setTag(wangcontext.getObjectId()+"_img2");
                myUpload.download_asynchronous("wangweibodata", "context/" + wangcontext.getObjectId() + "/" + "img1", img1);
                myUpload.download_asynchronous("wangweibodata", "context/" + wangcontext.getObjectId() + "/" + "img2", img2);
                img3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                img1.setTag(wangcontext.getObjectId()+"_img1");
                img2.setTag(wangcontext.getObjectId()+"_img2");
                img3.setTag(wangcontext.getObjectId()+"_img3");
                myUpload.download_asynchronous("wangweibodata", "context/" + wangcontext.getObjectId() + "/" + "img1", img1);
                myUpload.download_asynchronous("wangweibodata", "context/" + wangcontext.getObjectId() + "/" + "img2", img2);
                myUpload.download_asynchronous("wangweibodata", "context/" + wangcontext.getObjectId() + "/" + "img3", img3);
                break;
        }
        madapter=new RemarkAdapter(mcontext,listremark);
        listView.setAdapter(madapter);
        listView.addHeaderView(detail_context);
        listView.addFooterView(detail_remark);
        remark_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkuser()){
                    String s=remark_edit.getText().toString().trim();
                    WangRemark remarkdataone=new WangRemark(s,name,0,0);
                    listremark.add(remarkdataone);
                    madapter.notifyDataSetChanged();
                    remarkdataone.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                list_str.add(s);
                                WangContext hoteldata=new WangContext();
                                hoteldata.setList_remark(list_str);
                                hoteldata.update(wangcontext.getObjectId(), new UpdateListener() {
                                    @Override
                                    public void done(BmobException e) {
                                        if(e==null){
                                            T.showShot(mcontext,"上传成功！");
                                        }else
                                            T.showShot(mcontext,"上传失败！");
                                    }
                                });
                            }else {
                                T.showShot(mcontext,"上传出错！");
                            }
                        }
                    });
                }
            }
        });
    }
    private boolean checkuser() {
        WangUser bmobUser = BmobUser.getCurrentUser(WangUser.class);
        if(bmobUser != null){
            // 允许用户使用应用
            name= (String) BmobUser.getObjectByKey("username");
            return true;
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            userrun();
            return false;
        }
    }

    private void userrun() {
        Intent it=new Intent(DetailContextActivity.this, LoginActivity.class);
        startActivity(it);
    }
}
