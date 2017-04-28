package namewangexperiment.com.wangweibo.chat;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;

/**
 * Created by Administrator on 2017/4/28.
 */

public class MyLeanCloudApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"hTVvDXfB5MoFlEUCLHMJssGm-9Nh9j0Va","KD9tOzBNy3i5I7I9kOAdQiLX");
    }
}
