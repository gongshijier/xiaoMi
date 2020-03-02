package com.example.xiaomi.ui.todolist;

import android.provider.ContactsContract;
import android.service.autofill.Dataset;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.xiaomi.model.DoneThing;
import com.example.xiaomi.model.TodoBook;
import com.example.xiaomi.model.TodoThing;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class TodoViewModel extends ViewModel {
    MutableLiveData<List<TodoThing>> todoLive;
    MutableLiveData<List<DoneThing>> donList;

    public TodoViewModel() {
        if(todoLive==null){
            todoLive = new MutableLiveData<>();
            todoLive.postValue(DataSupport.where("bookName=?","日常").find(TodoThing.class));
        }
        if(donList==null){
            donList = new MutableLiveData<>();
            donList.postValue(DataSupport.where("bookName=?","日常").find(DoneThing.class));
        }
    }

    public MutableLiveData<List<TodoThing>> getTodoLive() {
        if(todoLive==null){
            todoLive = new MutableLiveData<>();
            todoLive.postValue(DataSupport.where("bookName=?","日常").find(TodoThing.class));
        }
        return todoLive;
    }

    public void setTodoLive(MutableLiveData<List<TodoThing>> todoLive) {
        this.todoLive = todoLive;
    }

    public MutableLiveData<List<DoneThing>> getDonList() {
        if(donList==null){
           donList = new MutableLiveData<>();
            donList.postValue(DataSupport.where("bookName=?","日常").find(DoneThing.class));
        }
        return donList;
    }

    public void setDonList(MutableLiveData<List<DoneThing>> donList) {
        this.donList = donList;
    }

    public List<String> getTodoBook() {
        List<TodoBook> todoBooks=  DataSupport.findAll(TodoBook.class);
        List<String> strings =new ArrayList<String>();
        for(TodoBook todoBook:todoBooks){
            strings.add(todoBook.getBookname());
        }
    return strings;
    }

    public void saveTodo(String s, String toString) {
        TodoThing todoThing = new TodoThing();
        todoThing.setBookName(s);
        todoThing.setContent(toString);
        todoThing.save();
        todoLive.postValue(DataSupport.where("bookName=?",s).find(TodoThing.class));
    }

    public void iniTodo() {
        todoLive.postValue(DataSupport.where("bookName=?","日常").find(TodoThing.class));
    }

    public void updateTodo(String toString) {
        todoLive.postValue(DataSupport.where("bookName=?",toString).find(TodoThing.class));
    }

    public void updateDone(String toString) {
        donList.postValue(DataSupport.where("bookName=?",toString).find(DoneThing.class));
    }
}
