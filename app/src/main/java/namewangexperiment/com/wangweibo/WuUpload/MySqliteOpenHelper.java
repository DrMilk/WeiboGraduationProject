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
    String CONVERSATION="conversation";
    String CONTENT="content";
    String MYCHAT="mychat.db";
    public MySqliteOpenHelper(Context context,String name,int version) {
        super(context,name, null,version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // _id wrtiename details createtime
        db.execSQL("create table content (_id integer primary key autoincrement,writename varchar(20),details text,createtime date)");
        // _id writename lookstatus createtime chataddress lasttext textnum
        db.execSQL("create table conversation (_id integer primary key autoincrement,writename varchar(20),lookstatus varchar(1),createtime text,chataddress text,lasttext text,textnum varchar(4))");
        L.i(TAG,"数据库创建成功！");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("alter table info add phone varchar(11)");
        L.i(TAG,"数据库更新！");
    }
}
