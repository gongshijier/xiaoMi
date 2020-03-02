package com.example.xiaomi.ui.todobook;

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
import com.example.xiaomi.model.TodoBook;
import com.example.xiaomi.ui.adapter.TodoBAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TodoBookFragment extends Fragment {

    private TodoBookViewModel mViewModel;
    private View view;
    private RecyclerView todoBRecy;
    TodoBAdapter todoBAdapter;
    private EditText editBook;
    private TextView addBookN;
    private TextView addbookO;
    private FloatingActionButton floatingActionButton;

    public static TodoBookFragment newInstance() {
        return new TodoBookFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(TodoBookViewModel.class);
        view = inflater.inflate(R.layout.todo_book_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel

        //实现recyclerView
        todoBRecy = view.findViewById(R.id.todoBookRecy);
        todoBAdapter = new TodoBAdapter(getActivity(),mViewModel.getTodobooks(),mViewModel.getChoseBook());
        todoBRecy.setAdapter(todoBAdapter);
        todoBRecy.setLayoutManager(new GridLayoutManager(getActivity(),2));
        mViewModel.getTodobooks().observe(requireActivity(), new Observer<List<TodoBook>>() {
            @Override
            public void onChanged(List<TodoBook> todoBooks) {
                todoBAdapter.settodoB(todoBooks);
                todoBAdapter.notifyDataSetChanged();
            }
        });

        //实现添加book功能
        floatingActionButton =view.findViewById(R.id.addtodoBook);
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
                        todoBAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }
        });

    }

}
