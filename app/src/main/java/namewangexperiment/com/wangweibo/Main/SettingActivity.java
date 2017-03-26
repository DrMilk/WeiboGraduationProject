package namewangexperiment.com.wangweibo.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import namewangexperiment.com.wangweibo.R;
import namewangexperiment.com.wangweibo.Utils.SharePreferenceUtil;


/**
 * Created by Administrator on 2017/3/18.
 */

public class SettingActivity extends Activity implements View.OnClickListener{
    private TextView textView;
    private Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext=this;
        setContentView(R.layout.acticity_setting);
        initView();
    }

    private void initView() {
//        textView= (TextView) findViewById(R.id._setting_logout);
//        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.mine_setting_logout:
//                SharePreferenceUtil.putSettingDataBoolean(mcontext,SharePreferenceUtil.AUTOLOGIN,false);
//                Intent it=new Intent(SettingActivity.this, LoginActivity.class);startActivity(it);SettingActivity.this.finish();break;
        }
    }
}
