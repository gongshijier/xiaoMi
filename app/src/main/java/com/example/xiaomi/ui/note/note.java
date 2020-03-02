package com.example.xiaomi.ui.note;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaomi.R;
import com.example.xiaomi.model.Note;
import com.example.xiaomi.ui.adapter.NoteAdapter;
import com.example.xiaomi.ui.notebook.NoteBookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.Observable;

public class note extends Fragment {
    private ArrayAdapter<String> arrayAdapter;
    Spinner spinner;
    private NoteViewModel mViewModel;
    FloatingActionButton addNote;
    View view;
    List<Note> noteList = null;
    NoteAdapter noteAdapter;


    NoteBookViewModel noteBookViewModel;
    RecyclerView recyclerView;

    public static note newInstance() {
        return new note();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);
        noteBookViewModel = new ViewModelProvider(requireActivity()).get(NoteBookViewModel.class);
        view = inflater.inflate(R.layout.note_fragment, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

Log.i("gong","调用了创建的方法");
        //添加便签按钮
        addNote = view.findViewById(R.id.addNote);
        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_note_to_editNote);
            }
        });


        //实现spinner
        spinner = view.findViewById(R.id.NoteSpinner);
        if(arrayAdapter ==null ){

            arrayAdapter = new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_spinner_item,
                    mViewModel.getNoteBook());

        }        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        //切换条目的时候重置notelist值
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.updateNote(spinner.getSelectedItem().toString());
                mViewModel.chooseBook.postValue(spinner.getSelectedItem().toString());
                noteBookViewModel.getChooseNote().postValue(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        //实现recyclerView
        if (noteAdapter == null) {
            noteAdapter = new NoteAdapter(mViewModel.getNotelist().getValue(), mViewModel.getNotelist(),
                    getActivity());
        }
        recyclerView = view.findViewById(R.id.NoterecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(noteAdapter);

        mViewModel.getNotelist().observe(getActivity(), new Observer<List<Note>>() {
            @Override
            public void onChanged(List<Note> notes) {
                noteAdapter.setNoteList(notes);
                noteAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.iniNote();
        spinner.setSelection(arrayAdapter.getPosition(noteBookViewModel.getChooseNote().getValue()),true);

        Log.i("gong","调用了resume的方法");
    }
}
