package namewangexperiment.com.wangweibo.mintattentions;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import namewangexperiment.com.wangweibo.MainInfor.WangContextRecyclerViewAdapter;
import namewangexperiment.com.wangweibo.OnlineData.WangUser;
import namewangexperiment.com.wangweibo.R;
import namewangexperiment.com.wangweibo.Utils.MyUpload;

/**
 * Created by Administrator on 2017/4/3.
 */

public class ListAttentionsAdapter extends BaseAdapter {
    private ArrayList<WangUser> list_data;
    private boolean[] jundge;
    private ArrayList<Boolean> list_jundge;
    private MyUpload myUpload;
    private LayoutInflater inflater;
    private MyViewHolder wuViewHolder;
    public ListAttentionsAdapter(Context mcontext,ArrayList<WangUser> list_data){
        inflater=LayoutInflater.from(mcontext);
        this.list_data=list_data;
        jundge=new boolean[list_data.size()];
        myUpload=new MyUpload(mcontext);
    }
    @Override
    public int getCount() {
        return list_data.size();
    }

    @Override
    public Object getItem(int position) {
        return list_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            wuViewHolder= new MyViewHolder();
            convertView=inflater.inflate(R.layout.listitem_attentions,null);
            wuViewHolder.img_head= (ImageView) convertView.findViewById(R.id.attentions_headimg);
            wuViewHolder.text_name= (TextView) convertView.findViewById(R.id.attentions_name);
            wuViewHolder.text_sign= (TextView) convertView.findViewById(R.id.attentions_sign);
            wuViewHolder.text_jundge= (TextView) convertView.findViewById(R.id.attentions_jundge);
            wuViewHolder.img_sex= (ImageView) convertView.findViewById(R.id.attentions_sex);
            convertView.setTag(wuViewHolder);
        }else {
            wuViewHolder= (MyViewHolder) convertView.getTag();
        }
        wuViewHolder.text_name.setText(list_data.get(position).getName());
        wuViewHolder.text_sign.setText(list_data.get(position).getSign());
        if(jundge[position]){
            wuViewHolder.text_jundge.setText("关注");
        }
        if(list_data.get(position).getSex().equals("女"))
            wuViewHolder.img_sex.setImageResource(R.mipmap.userinfo_icon_female);
        myUpload.download_asynchronous_head("wangweibodata", "headimg/" + list_data.get(position).getUsername(),wuViewHolder.img_head);
        return convertView;
    }
    private class MyViewHolder{
        private TextView text_name;
        private TextView text_sign;
        private ImageView img_head;
        private TextView text_jundge;
        private ImageView img_sex;
    }
}
