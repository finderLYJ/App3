package com.example.app3.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.app3.bean.UserInfo;
import com.example.app3.bean.Word;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyDBhelper extends SQLiteOpenHelper {
    private SQLiteDatabase db;
    public MyDBhelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //存储用户信息
        sqLiteDatabase.execSQL("create table user(id integer primary key autoincrement,username varchar(10),password varchar(10))");
        //存储常用文字的数据库
        sqLiteDatabase.execSQL("create table word(id integer primary key autoincrement,content text,add_time text)");
        //存储声音转换成文字记录
        sqLiteDatabase.execSQL("create table trahistory(id integer primary key autoincrement,content text,add_time text)");
    }
    public boolean insertTraHistory(String content){
        //获取系统当前时间
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月d日 hh:mm:ss");
        String finalDate=sdf.format(date);

        ContentValues contentValues=new ContentValues();
        contentValues.put("add_time",finalDate);
        contentValues.put("content",content);
        long i=db.insert("trahistory",null,contentValues);
        return i>0;
    }
    public List<Word> queryTraHistory(){
        List<Word> list=new ArrayList<>();
        Cursor cursor=db.query("trahistory",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            Word word=new Word();
            word.setId(String.valueOf(cursor.getInt(0)));
            word.setWord(cursor.getString(1));
            word.setAdd_time(cursor.getString(2));
            list.add(word);
        }
        cursor.close();
        return list;
    }
    public boolean insertWord(String content){
        //获取系统当前时间
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月d日 hh:mm:ss");
        String finalDate=sdf.format(date);

        ContentValues contentValues=new ContentValues();
        contentValues.put("add_time",finalDate);
        contentValues.put("content",content);
        long i=db.insert("word",null,contentValues);
        return i>0;
    }
    public boolean insertUser(String username,String password){
        ContentValues contentValues=new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        long i=db.insert("user",null,contentValues);
        return i>0;
    }
    public boolean updateUser(String username,String password){
        ContentValues contentValues=new ContentValues();
        contentValues.put("password",password);
        int i=db.update("user",contentValues,"username=?",new String[]{username});
        return i>0;
    }
    public boolean updateWord(String updateId,String updateWord){
        //将需要更新的内容存入contentValues中
        ContentValues contentValues=new ContentValues();
        //获取系统当前时间
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月d日 hh:mm:ss");
        String finalDate=sdf.format(date);
        contentValues.put("content",updateWord);
        contentValues.put("add_time",finalDate);
        int i=db.update("word",contentValues,"id=?",new String[]{updateId});
        return i>0;
    }
    public List<Word> queryWord(){
        List<Word> list=new ArrayList<>();
        Cursor cursor=db.query("word",null,null,null,null,null,null);
        while(cursor.moveToNext()){
            Word word=new Word();
            word.setId(String.valueOf(cursor.getInt(0)));
            word.setWord(cursor.getString(1));
            word.setAdd_time(cursor.getString(2));
            list.add(word);
        }
        cursor.close();
        return list;
    }
    public boolean deleteWord(String deleteId){
        int i=db.delete("word","id=?",new String[]{deleteId});
        return i > 0;
    }
    public void clear(){
        Cursor cursor=db.query("word",null,null,null,null,null,null);
        while(cursor.moveToFirst()){
            String deleteId= String.valueOf(cursor.getInt(0));
            db.delete("word","id=?",new String[]{deleteId});
        }
        cursor.close();
    }
    public UserInfo query(String username){
        UserInfo user=new UserInfo();
        Cursor cursor=db.query("user",null,"username=?",new String[]{username},null,null,null);
        if(cursor.getCount()==1){
            cursor.moveToFirst();
            int id=cursor.getInt(0);
            String username1=cursor.getString(1);
            String password=cursor.getString(2);
            user.setId(id);
            user.setUsername(username1);
            user.setPassword(password);
            cursor.close();
            return user;
        }else{
            return null;
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
