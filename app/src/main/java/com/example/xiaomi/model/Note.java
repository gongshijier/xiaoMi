package com.example.xiaomi.model;

import org.litepal.crud.DataSupport;

public class Note extends DataSupport {
    private int id;
    private  String content;
    private  String bookname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
}
