package namewangexperiment.com.wangweibo.chat;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import namewangexperiment.com.wangweibo.Utils.L;

/**
 * Created by Administrator on 2017/5/3.
 */

public class ChatService extends Service{
    private String TAG="ChatService";

    @Override
    public void onCreate() {
        L.i(TAG,"onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        L.i(TAG,"onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        L.i(TAG,"onCreate");
        return null;
    }
}
