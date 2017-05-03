package namewangexperiment.com.wangweibo.WuUpload;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import namewangexperiment.com.wangweibo.Utils.L;

/**
 * Created by Administrator on 2017/2/13.
 */

public class MySqliteOpenHelper extends SQLiteOpenHelper{
    private String TAG="MySqliteOpenHelper";
    private String CONVERSATION="conversation.db";
    private String CONTENT="content.db";
    private String MYSQLWUCHAT="wuchat";
    public MySqliteOpenHelper(Context context,String name,int version) {
        super(context,name, null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table content (_id integer primary key autoincrement,writename varchar(20),details text,createtime date)");
        db.execSQL("create table conversation (_id integer primary key autoincrement,writename varchar(20),lookstatus varchar(1),createtime date,chataddress text)");
        L.i(TAG,"数据库创建成功！");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("alter table info add phone varchar(11)");
        L.i(TAG,"数据库更新！");
    }
}
