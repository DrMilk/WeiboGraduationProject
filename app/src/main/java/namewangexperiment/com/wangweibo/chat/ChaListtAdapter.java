package namewangexperiment.com.wangweibo.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import namewangexperiment.com.wangweibo.R;

/**
 * Created by Administrator on 2017/4/27.
 */

public class ChaListtAdapter extends BaseAdapter{
    private LayoutInflater minflater;
    private ArrayList<ChatInfo> mlist;
    private WuViewHolder viewHolder;
    private ChatInfo charinfo;
    public ChaListtAdapter(Context context, ArrayList<ChatInfo> mlist){
        minflater=LayoutInflater.from(context);
        this.mlist=mlist;
    }
    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        charinfo=mlist.get(position);
        if(convertView==null){
            if(charinfo.getKinds()==1){
                convertView=minflater.inflate(R.layout.listitme_right,null);
            }else if(charinfo.getKinds()==2){
                convertView=minflater.inflate(R.layout.listitme_left,null);
            }
            viewHolder=new WuViewHolder();
            viewHolder.img_head= (ImageView) convertView.findViewById(R.id.header_headimg);
            viewHolder.text_time= (TextView) convertView.findViewById(R.id.char_text_time);
            viewHolder.text_content= (TextView) convertView.findViewById(R.id.char_text_content);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(WuViewHolder) convertView.getTag();
        }
        viewHolder.text_time.setText(charinfo.getText_time());
        viewHolder.text_content.setText(charinfo.getText_context());
       // viewHolder.img_head.setImageResource(charinfo.getImg_id());
        return convertView;
    }
    private class WuViewHolder{
        private TextView text_time;
        private TextView text_content;
        private ImageView img_head;
    }
}
