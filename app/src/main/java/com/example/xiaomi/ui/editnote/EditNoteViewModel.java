package com.example.xiaomi.ui.editnote;

import androidx.lifecycle.ViewModel;

import com.example.xiaomi.model.Note;
import com.example.xiaomi.model.NoteBook;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditNoteViewModel extends ViewModel {
    List<String> list;
    public List<String> getNoteBook() {
       if(list==null){
           list = new ArrayList<String>();
       }
       list.clear();
       List<NoteBook> noteBooks = DataSupport.findAll(NoteBook.class);
       for(NoteBook noteBook:noteBooks){
           list.add(noteBook.getBookname());
       }
        return list;
    }

    public void saveNote(String toString, String toString1) {

        Note note = new Note();
        note.setBookname(toString1);
        note.setContent(toString);
        note.save();

    }

    public void updateNote(String string, String toString, int noteid) {
        DataSupport.delete(Note.class,noteid);
        Note note = new Note();
        note.setContent(string);
        note.setBookname(toString);
        note.save();

    }


    // TODO: Implement the ViewModel
}
