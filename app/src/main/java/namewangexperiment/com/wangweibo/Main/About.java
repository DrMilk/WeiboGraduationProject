package namewangexperiment.com.wangweibo.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import namewangexperiment.com.wangweibo.R;
import namewangexperiment.com.wangweibo.chat.Wechat;

/**
 * Created by Administrator on 2017/4/1.
 */

public class About extends Activity{
    private ImageView imghead;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        imghead= (ImageView) findViewById(R.id.imageView2);
//        imghead.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent it=new Intent(About.this, Wechat.class);
//                startActivity(it);
//            }
//        });
    }
}
