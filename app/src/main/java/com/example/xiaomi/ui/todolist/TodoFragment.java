package com.example.xiaomi.ui.todolist;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.xiaomi.R;
import com.example.xiaomi.model.DoneThing;
import com.example.xiaomi.model.TodoThing;
import com.example.xiaomi.ui.adapter.DoneAdapter;
import com.example.xiaomi.ui.adapter.TodoAdapter;
import com.example.xiaomi.ui.todobook.TodoBookViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class TodoFragment extends Fragment {

    private EditText todoEdit;
    private TextView todoeditN;
    private TextView todoeditY;
    private TodoViewModel mViewModel;
    RecyclerView todoRecy;
    RecyclerView doneRecy;
    private FloatingActionButton addTodo;
    private View view;
    private Spinner spinner;
    private ArrayAdapter<String> arrayAdapter;
private TodoAdapter todoadapter;
private  DoneAdapter doneAdapter;
private TodoBookViewModel todoBookViewModel;
    public static TodoFragment newInstance() {
        return new TodoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(TodoViewModel.class);
        todoBookViewModel = new ViewModelProvider(requireActivity()).get(TodoBookViewModel.class);
        view = inflater.inflate(R.layout.todo_fragment, container, false);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //初始化下拉菜单
        spinner = view.findViewById(R.id.todoBookS);
        if (arrayAdapter == null) {
            arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, mViewModel.
                    getTodoBook());
        }
        spinner.setAdapter(arrayAdapter);
        //切换spinner时候切换数据
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mViewModel.updateTodo(spinner.getSelectedItem().toString());
                mViewModel.updateDone(spinner.getSelectedItem().toString());
                todoBookViewModel.getChoseBook().postValue(spinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //添加todo事件按钮
        addTodo = view.findViewById(R.id.floatingActionButton2);
        addTodo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                final AlertDialog dialog = builder.create();
                //设置对话框布局
                View dialogView = View.inflate(getActivity(), R.layout.addtodo, null);
                dialog.setView(dialogView);
                dialog.show();
                todoeditY = dialogView.findViewById(R.id.todoAddOk);
                todoeditN = dialogView.findViewById(R.id.todoCancle);
                todoEdit = dialogView.findViewById(R.id.todoEdit);
                todoeditN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                //确认添加todo
                todoeditY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewModel.saveTodo(spinner.getSelectedItem().toString(), todoEdit.getText().toString());
                        todoadapter.setTodoThings(mViewModel.getTodoLive().getValue());
                        todoadapter.notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
            }
        });


        //初始化recyclerView
        todoRecy = view.findViewById(R.id.recyclerView);
        doneRecy = view.findViewById(R.id.doneRecy);
        todoadapter = new TodoAdapter(getContext(),mViewModel.getTodoLive(),mViewModel.getDonList(),mViewModel.getTodoLive().getValue());
        todoRecy.setAdapter(todoadapter);
        todoRecy.setLayoutManager(new LinearLayoutManager(requireContext()));
        mViewModel.getTodoLive().observe(requireActivity(), new Observer<List<TodoThing>>() {
            @Override
            public void onChanged(List<TodoThing> todoThings) {
                todoadapter.setTodoThings(todoThings);
                todoadapter.notifyDataSetChanged();
            }
        });
        if (doneAdapter ==null){
            doneAdapter = new DoneAdapter(getContext(),mViewModel.getTodoLive(),mViewModel.getDonList(),mViewModel.getDonList().getValue());
        }
        doneRecy.setAdapter(doneAdapter);
        doneRecy.setLayoutManager(new LinearLayoutManager(requireContext()));


        mViewModel.getDonList().observe(requireActivity(), new Observer<List<DoneThing>>() {
            @Override
            public void onChanged(List<DoneThing> doneThings) {
                doneAdapter.setDoneThings(doneThings);
                doneAdapter.notifyDataSetChanged();
            }
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item,
                mViewModel.
                getTodoBook());
        spinner.setAdapter(arrayAdapter);
        Log.i("gong","重置spinnerAdapter");
        todoBookViewModel.getChoseBook().observe(requireActivity(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.i("gong","输出的变化的spinner:"+s);
                Log.i("gong",""+arrayAdapter.getPosition(s));
                spinner.setSelection(arrayAdapter.getPosition(s),true);
            }
        });
//        mViewModel.iniTodo();

             }
             }
