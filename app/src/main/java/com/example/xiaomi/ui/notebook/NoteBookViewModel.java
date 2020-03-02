package com.example.xiaomi.ui.notebook;

import android.provider.ContactsContract;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.xiaomi.model.NoteBook;

import org.litepal.crud.DataSupport;

import java.util.List;

public class NoteBookViewModel extends ViewModel {
    MutableLiveData<List<NoteBook>> noteBooks;
    MutableLiveData<String> chooseNote;
    public NoteBookViewModel() {
        if(noteBooks==null){
            noteBooks = new MutableLiveData<>();noteBooks.setValue(DataSupport.findAll(NoteBook.class));
        }
        if(chooseNote==null){
            chooseNote = new MutableLiveData<>();
            chooseNote.postValue("english");
        }

    }

    public MutableLiveData<String> getChooseNote() {
        return chooseNote;
    }

    public MutableLiveData<List<NoteBook>> getNoteBooks() {
        if(noteBooks==null){
            noteBooks = new MutableLiveData<>(); noteBooks.setValue(DataSupport.findAll(NoteBook.class));
        }

        return noteBooks;
    }

    public void iniNoteBook() {
        if(noteBooks==null){
            noteBooks = new MutableLiveData<>();
        }
        noteBooks.setValue(DataSupport.findAll(NoteBook.class));
    }

    public void saveBook(String toString) {
        NoteBook noteBook  = new NoteBook();
        noteBook.setBookname(toString);
        noteBook.save();
        noteBooks.postValue(DataSupport.findAll(NoteBook.class));
    }
// TODO: Implement the ViewModel
}
