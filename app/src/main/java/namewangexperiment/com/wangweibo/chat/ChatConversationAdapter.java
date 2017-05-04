package namewangexperiment.com.wangweibo.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import namewangexperiment.com.wangweibo.OnlineData.WangUser;
import namewangexperiment.com.wangweibo.R;
import namewangexperiment.com.wangweibo.Utils.MyUpload;

/**
 * Created by Administrator on 2017/4/3.
 */

public class ChatConversationAdapter extends BaseAdapter {
    private ArrayList<ChatConversation> list_data;
    private boolean[] jundge;
    private MyUpload myUpload;
    private LayoutInflater inflater;
    private MyViewHolder wuViewHolder;
    private WangUser mine;
    private Context mcontext;
    public ChatConversationAdapter(Context mcontext, ArrayList<ChatConversation> list_data){
        inflater=LayoutInflater.from(mcontext);
        this.list_data=list_data;
        this.mcontext=mcontext;
        myUpload=new MyUpload(mcontext);
        this.mine=mine;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            wuViewHolder= new MyViewHolder();
            convertView=inflater.inflate(R.layout.listitem_conversation,null);
            wuViewHolder.img_head= (ImageView) convertView.findViewById(R.id.header_headimg);
            wuViewHolder.text_name= (TextView) convertView.findViewById(R.id.listitem2_title);
//            wuViewHolder.text_content= (TextView) convertView.findViewById(R.id.attentions_sign);
//            wuViewHolder.text_count= (TextView) convertView.findViewById(R.id.attentions_jundge);
//            wuViewHolder.text_time= (ImageView) convertView.findViewById(R.id.attentions_sex);
            convertView.setTag(wuViewHolder);
        }else {
            wuViewHolder= (MyViewHolder) convertView.getTag();
        }
//        wuViewHolder.text_name.setText(list_data.get(position).getName());
//        wuViewHolder.text_sign.setText(list_data.get(position).getSign());
//        if(jundge[position]){
//            wuViewHolder.text_jundge.setText("关 注");
//            wuViewHolder.text_jundge.setTextColor( mcontext.getResources().getColor(R.color.white_smoke));
//            wuViewHolder.text_jundge.setBackgroundResource(R.drawable.button_bg);
//        }else {
//            wuViewHolder.text_jundge.setText("已关注");
//            wuViewHolder.text_jundge.setTextColor( mcontext.getResources().getColor(R.color.mp_lblue));
//            wuViewHolder.text_jundge.setBackgroundResource(R.drawable.button_bg_on);
//        }
//        wuViewHolder.text_jundge.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(jundge[position]){
//                    jundge[position]=false;
//                    wuViewHolder.text_jundge.setText("已关注");
//                    wuViewHolder.text_jundge.setTextColor( mcontext.getResources().getColor(R.color.mp_lblue));
//                    wuViewHolder.text_jundge.setBackgroundResource(R.drawable.button_bg_on);
//                }else{
//                    jundge[position]=true;
//                    wuViewHolder.text_jundge.setText("关 注");
//                    wuViewHolder.text_jundge.setTextColor( mcontext.getResources().getColor(R.color.white_smoke));
//                    wuViewHolder.text_jundge.setBackgroundResource(R.drawable.button_bg);
//                }
//            }
//        });
//        if(list_data.get(position).getSex().equals("女"))
//            wuViewHolder.img_sex.setImageResource(R.mipmap.userinfo_icon_female);
//        myUpload.download_asynchronous_head("wangweibodata", "headimg/" + list_data.get(position).getUsername(),wuViewHolder.img_head);
        return convertView;
    }
    private class MyViewHolder{
        private TextView text_name;
        private TextView text_content;
        private ImageView img_head;
        private TextView text_time;
        private TextView text_count;
    }

    public boolean[] getJundge() {
        return jundge;
    }
}
