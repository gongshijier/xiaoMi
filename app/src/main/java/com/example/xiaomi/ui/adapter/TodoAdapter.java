package com.example.xiaomi.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaomi.R;
import com.example.xiaomi.model.DoneThing;
import com.example.xiaomi.model.LogDay;
import com.example.xiaomi.model.TodoThing;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.
        ViewHolder> {

    TextView deldteY;
    TextView deldteN;

    Context context;
    MutableLiveData<List<TodoThing>> todoLive;
    MutableLiveData<List<DoneThing>> dnoeLive;
    List<TodoThing> todoThings;

    public void setTodoThings(List<TodoThing> todoThings) {

        this.todoThings = todoThings;
    }

    public TodoAdapter(Context context, MutableLiveData<List<TodoThing>> todoLive, MutableLiveData<List<DoneThing>> dnoeLive, List<TodoThing> todoThings) {
        this.context = context;
        this.todoLive = todoLive;
        this.dnoeLive = dnoeLive;
        this.todoThings = todoThings;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate
                (R.layout.todo_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.textView.setText(todoThings.get(position).getContent());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
                SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");

                DoneThing doneThing = new DoneThing();
                doneThing.setContent(todoThings.get(position).
                        getContent());
                doneThing.settBookName(todoThings.get(position).
                        getBookName());
                 Date date = new Date(System.currentTimeMillis());
                doneThing.setDay(simpleDateFormat.format(date));
                doneThing.setTimeStart(simpleDateFormat2.format(date));
                doneThing.save();

                LogDay logDay = new LogDay();
                logDay.setContent(todoThings.get(position).getContent());
                logDay.settBookName(todoThings.get(position).getBookName());
                logDay.setDay(simpleDateFormat.format(date));
                logDay.setTimeStart(simpleDateFormat2.format(date));
                logDay.save();


                dnoeLive.setValue(DataSupport.where(
                        "bookName=?",todoThings.get(position).getBookName()
                ).find(DoneThing.class));
                DataSupport.delete(TodoThing.class,todoThings.get(position).getId());
                todoThings.remove(position);todoLive.setValue(todoThings);
                notifyItemRemoved(position);


            }
        });

        //设置长按删除
        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final  AlertDialog dialog = builder.create();
                View dialogview = View.inflate(context,R.layout.delete_todo,null);
                dialog.setView(dialogview);
                dialog.show();
                //点击确定  删除
                deldteY = dialogview.findViewById(R.id.sureDelete);
                deldteY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DataSupport.delete(TodoThing.class,todoThings.get(position).getId());
                        todoThings.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });
                deldteN = dialogview.findViewById(R.id.cancleDelete);
                deldteN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        if(todoThings  ==null){
            todoThings =new ArrayList<>();
        }
        return todoThings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView =itemView.findViewById(R.id.todoBOx);
            textView = itemView.findViewById(R.id.todoCon);
        }
    }
}
