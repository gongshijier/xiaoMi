package com.example.xiaomi.model;

import android.provider.ContactsContract;
import android.util.Log;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.util.List;

public class Dbservice {
    private static final Dbservice ourInstance = new Dbservice();

    public static Dbservice getInstance() {
        return ourInstance;
    }

    private Dbservice() {

        if(DataSupport.count(UserInfo.class)==0){

            Log.i("gong","创建数据库");
            LitePal.getDatabase();


            NoteBook noteBook = new NoteBook();
            noteBook.setBookname("笔记");
            noteBook.save();


            TodoBook todoBook = new TodoBook();
            todoBook.setBookname("日常");
            todoBook.save();


            Note note3 = new Note();
            note3.setBookname("笔记");
            note3.setContent("TODO完成项 点击右上角查看每日Log！");
            note3.save();

            Note note = new Note();
            note.setBookname("笔记");
            note.setContent("修改+删除尝试长按");
            note.save();


            Note note1 = new Note();
            note1.setBookname("笔记");
            note1.setContent("长按信息对应项——>修改签名昵称");
            note1.save();


            UserInfo userInfo = new UserInfo();
            userInfo.setSaying("@_@");
            userInfo.setName("Gong S.J");
            userInfo.setProfile("1");
            userInfo.save();

        }
        else {
            LitePal.getDatabase();
        }
    }}
