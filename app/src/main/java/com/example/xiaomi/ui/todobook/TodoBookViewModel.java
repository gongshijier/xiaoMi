package com.example.xiaomi.ui.todobook;

import android.provider.ContactsContract;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.xiaomi.model.TodoBook;

import org.litepal.crud.DataSupport;

import java.util.List;

public class TodoBookViewModel extends ViewModel {
    MutableLiveData<List<TodoBook>> todobooks;
    MutableLiveData<String> choseBook;

    public MutableLiveData<List<TodoBook>> getTodobooks() {
        if(todobooks ==null){
            todobooks = new MutableLiveData<>(); todobooks.setValue(DataSupport.findAll(TodoBook.class));
        }
        return todobooks;
    }

    public TodoBookViewModel() {
        if(todobooks ==null){
            todobooks = new MutableLiveData<>(); todobooks.setValue(DataSupport.findAll(TodoBook.class));
        }
        if(choseBook ==null){
            choseBook = new MutableLiveData<>();
            choseBook.setValue("日常");
        }

    }

    public void saveBook(String s) {

        TodoBook todoBook = new TodoBook();
        todoBook.setBookname(s);
        todoBook.save();
        todobooks.postValue(DataSupport.findAll(TodoBook.class));
    }

    public void setChoseBook(MutableLiveData<String> choseBook) {
        this.choseBook = choseBook;
    }

    public MutableLiveData<String> getChoseBook() {
        return choseBook;
    }
// TODO: Implement the ViewModel
}
