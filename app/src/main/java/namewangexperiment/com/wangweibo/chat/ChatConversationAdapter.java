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
    private ArrayList<ConversationInfo> list_data;
    private boolean[] jundge;
    private MyUpload myUpload;
    private LayoutInflater inflater;
    private MyViewHolder wuViewHolder;
    private WangUser mine;
    private Context mcontext;
    public ChatConversationAdapter(Context mcontext, ArrayList<ConversationInfo> list_data){
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
            wuViewHolder.text_time= (TextView) convertView.findViewById(R.id.listitem2_time);
            wuViewHolder.text_content= (TextView) convertView.findViewById(R.id.listitem2_context);
            wuViewHolder.text_count= (TextView) convertView.findViewById(R.id.listitem2_count);
//            wuViewHolder.text_content= (TextView) convertView.findViewById(R.id.attentions_sign);
//            wuViewHolder.text_count= (TextView) convertView.findViewById(R.id.attentions_jundge);
//            wuViewHolder.text_time= (ImageView) convertView.findViewById(R.id.attentions_sex);
            convertView.setTag(wuViewHolder);
        }else {
            wuViewHolder= (MyViewHolder) convertView.getTag();
        }

        myUpload.download_asynchronous_head("wangweibodata", "headimg/" +list_data.get(position).getText_writername(),wuViewHolder.img_head);
        wuViewHolder.text_name.setText(list_data.get(position).getText_title());
        wuViewHolder.text_content.setText(list_data.get(position).getText_content());
        wuViewHolder.text_time.setText(list_data.get(position).getTime());
        wuViewHolder.text_count.setText(list_data.get(position).getText_num());
        if(list_data.get(position).getText_num().equals("0")){
            wuViewHolder.text_count.setVisibility(View.INVISIBLE);
        }
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
