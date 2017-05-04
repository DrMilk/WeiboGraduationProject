package namewangexperiment.com.wangweibo.WuUpload;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import namewangexperiment.com.wangweibo.OnlineData.Other;
import namewangexperiment.com.wangweibo.Utils.L;
import namewangexperiment.com.wangweibo.chat.ChatInfo;


/**
 * Created by Administrator on 2017/2/19.
 */

public class MyInfoDao {
    private String TAG="MyInfoDao";
    MySqliteOpenHelper msoh;
    public  MyInfoDao(){}
    public  MyInfoDao(Context context,int version){
        msoh=new MySqliteOpenHelper(context,"mychat.db",version);
    }
    public long goInsert(String tablename, ContentValues value){
        SQLiteDatabase sqld=msoh.getReadableDatabase();
        long result=sqld.insert(tablename,null,value);
       // sqld.execSQL("insert into info(name,phone) values(?,?);",new Object[]{other.getId(),"123456789"});
        sqld.close();
        return result;
    }
    public int goDelete(String tablename,String whereClause,String str[]){
        SQLiteDatabase sqld=msoh.getReadableDatabase();
      //  sqld.execSQL("delete from info where name=?;",new Object[]{other.getId()});
        int result=sqld.delete(tablename,whereClause,str);
        sqld.close();
        return result;
    }
    public int goUpdate(String tablename, ContentValues value, String whereClause, String str[]){
        SQLiteDatabase sqld=msoh.getReadableDatabase();
     //   sqLiteDatabase.execSQL("updata info set phone=? where name=?;",new Object[]{"ef","ef"});
        int result=sqld.update(tablename,value,whereClause,str);
        sqld.close();
        return result;
    }
    public ArrayList<ContentValues> goQuery(String tablename, String whereClause, String str[]){
        SQLiteDatabase sqld=msoh.getReadableDatabase();
        //table:表名，columns:查询的列名，如果null代表查询所有列，selection：查询条件，selectionArgs：条件占位符的参数值，
        // groupBy：按什么字段分组，having：分组条件，orderBy：按什么字段排序
        Cursor cursor=sqld.query(tablename,null,whereClause,str,null,null,null);
//        Cursor cursor=sqLiteDatabase.rawQuery("select name,phone from info where name=?;",new String[]{"ef"});
        //获取cursor内容
        ArrayList<ContentValues> list=new ArrayList<>();
        if(cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){
                ContentValues value=new ContentValues();
                for(int i=0;i<cursor.getColumnCount();i++){
                    value.put(cursor.getColumnName(i),cursor.getString(i));
                }
                list.add(value);
            }
            cursor.close();
        }
        sqld.close();
        return list;
    }
    public void showall(String tablename){
        L.i(TAG,"showall");
        SQLiteDatabase sqld=msoh.getReadableDatabase();
        Cursor cursor = sqld.rawQuery("select * from "+tablename, null);
     //   ArrayList<ContentValues> list=new ArrayList<>();
        if(cursor!=null && cursor.getCount()>0){
            while (cursor.moveToNext()){
            //    ContentValues value=new ContentValues();
                for(int i=0;i<cursor.getColumnCount();i++){
                    L.i(TAG,cursor.getColumnName(i)+"----"+cursor.getString(i));
                    //value.put(cursor.getColumnName(i),cursor.getString(i));
                }
          //      list.add(value);
            }
            cursor.close();
        }
        sqld.close();
    }
}
