package com.example.xiaomi.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaomi.R;
import com.example.xiaomi.model.NoteBook;
import com.example.xiaomi.model.TodoBook;
import com.example.xiaomi.model.TodoThing;

import org.litepal.crud.DataSupport;

import java.util.List;

public class TodoBAdapter extends RecyclerView.Adapter<TodoBAdapter.ViewHolder> {
    private TextView editN;
    private TextView editY;
    private EditText editInfo;
    private  Context context;
    List<TodoBook> todoBooks;
    MutableLiveData<List<TodoBook>> todobooks;
    MutableLiveData<String> choose;

    TextView deleBookY;
    TextView deleBookN;
    public TodoBAdapter(Context context, MutableLiveData<List<TodoBook>> todobooks, MutableLiveData<String> choseBook) {
    this.context = context;
    this.todobooks = todobooks;
    this.choose=choseBook;
    this.todoBooks = todobooks.getValue();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.todobook,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.textView.setText(todoBooks.get(position).getBookname());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choose.postValue(todoBooks.get(position).getBookname());

                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_todoBook_to_todoFragment);
            }
        });
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(position==0){

                }
                else {

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    final AlertDialog dialog = builder.create();
                    //设置对话框布局
                    View dialogView = View.inflate(context, R.layout.deletebook, null);
                    dialog.setView(dialogView);
                    dialog.show();
                    deleBookN = dialogView.findViewById(R.id.bookDeleN);
                    deleBookY = dialogView.findViewById(R.id.bookDeleY);
                    deleBookY.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DataSupport.delete(TodoBook.class,todoBooks.get(position).getId());
                            todoBooks.remove(position);
                            notifyDataSetChanged();
                            dialog.dismiss();
                        }
                    });
                    deleBookN.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });

                }
                return false;
            }
        });

        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final AlertDialog dialog = builder.create();
                //设置对话框布局
                View dialogView = View.inflate(context, R.layout.change_name, null);
                dialog.setView(dialogView);
                dialog.show();
                editInfo =dialogView.findViewById(R.id.bookNameE);
                editN=dialogView.findViewById(R.id.bookNameN);
                editY = dialogView.findViewById(R.id.bookNameY);
                editN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                editY.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TodoBook todoBook = new TodoBook();
                        todoBook.setBookname(editInfo.getText().toString());  todoBook.update(todoBooks.get(position).getId());
                        TodoThing todoThing = new TodoThing();
                        todoThing.setBookName(editInfo.getText().toString());
                        todoThing.updateAll("bookName=?",todoBooks.get(position).getBookname());

                        todoBooks.get(position).setBookname(editInfo.getText().toString());
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return todoBooks.size();
    }

    public void settodoB(List<TodoBook> todoBookList) {
        this.todoBooks=todoBookList;

        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView    = itemView .findViewById(R.id.todoBookR);
            textView = itemView.findViewById(R.id.todoBookT);
        }
    }
}
