package com.example.xiaomi.ui.notebook;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.xiaomi.R;
import com.example.xiaomi.model.NoteBook;
import com.example.xiaomi.ui.adapter.NoteBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteBookFragment extends Fragment {

    private NoteBookViewModel mViewModel;
    private RecyclerView recyclerView;
    private NoteBAdapter noteBAdapter;

    private EditText editBook;
    private TextView addBookN;
    private TextView addbookO;
    private FloatingActionButton floatingActionButton;
    View view;
    public static NoteBookFragment newInstance() {
        return new NoteBookFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(getActivity()).get(NoteBookViewModel.class);
        view = inflater.inflate(R.layout.note_book_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        //初始化Notebook recyclerView
        recyclerView = view.findViewById(R.id.noteBookRecy);
        if(noteBAdapter==null){
            noteBAdapter = new NoteBAdapter(getContext(),

                    mViewModel.getNoteBooks(),mViewModel.getNoteBooks().getValue(),mViewModel.getChooseNote());
        }
       recyclerView.setLayoutManager(new GridLayoutManager(requireActivity(),2));
       recyclerView.setAdapter(noteBAdapter);
       mViewModel.getNoteBooks().observe(requireActivity(), new Observer<List<NoteBook>>() {
           @Override
           public void onChanged(List<NoteBook> noteBooks) {
               noteBAdapter.setNoteBooklist(noteBooks);

           }
       });


       //添加笔记本
        //实现添加book功能
        floatingActionButton =view.findViewById(R.id.addNoteBook);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                final AlertDialog dialog = builder.create();
                //设置对话框布局
                View dialogView = View.inflate(requireActivity(), R.layout.addbook, null);
                dialog.setView(dialogView);
                dialog.show();

                //dialog添加笔记本逻辑会话
                addBookN = dialogView.findViewById(R.id.todoCancle);
                addBookN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                editBook = dialogView.findViewById(R.id.todoEdit);
                addbookO = dialogView.findViewById(R.id.todoAddOk);
                addbookO.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewModel.saveBook(editBook.getText().toString());
                       noteBAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }
        });





       }

    @Override
    public void onResume() {
        super.onResume();

    }
}
