package com.example.xiaomi.model;

import org.litepal.crud.DataSupport;

public class TodoBook extends DataSupport {
    private int id;
    private String bookname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }
}
