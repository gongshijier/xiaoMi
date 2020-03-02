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

import com.example.xiaomi.MainActivity;
import com.example.xiaomi.R;
import com.example.xiaomi.model.Note;
import com.example.xiaomi.model.NoteBook;
import com.example.xiaomi.model.TodoBook;
import com.example.xiaomi.model.UserInfo;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class NoteBAdapter extends RecyclerView.Adapter<NoteBAdapter.ViewHolder> {

    private TextView editN;
    private TextView editY;
    private EditText editInfo;
    public void setNoteBooklist(List<NoteBook> noteBooklist) {
        this.noteBooklist = noteBooklist;
        notifyDataSetChanged();
    }

    TextView deleBookY;
    TextView deleBookN;
    List<NoteBook> noteBooklist ;
    Context context;
    MutableLiveData<List<NoteBook>> noteBooks;
    MutableLiveData<String> chooseNote;

    public NoteBAdapter(Context context, MutableLiveData<List<NoteBook>> noteBooks,
                        List<NoteBook> value, MutableLiveData<String> chooseNote) {
        this.context = context;
        this.noteBooks = noteBooks;
        this.noteBooklist = value;
        this.chooseNote = chooseNote;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notebook_item,parent,false);
        ViewHolder viewHolder  = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.textView.setText(noteBooklist.get(position).getBookname());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseNote.postValue(noteBooklist.get(position).getBookname());
                NavController navController = Navigation.findNavController(v);
                navController.navigate(R.id.action_noteBook_to_note);
            }
        });
        holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(position==0){

                }else{

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
                            DataSupport.delete(NoteBook.class,noteBooklist.get(position).getId());
                            noteBooklist.remove(position);
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
                        NoteBook noteBook = new NoteBook();
                        noteBook.setBookname(editInfo.getText().toString());
                        Note note = new Note();
                        note.setBookname(editInfo.getText().toString());
                        note.updateAll("bookname=?",noteBooklist.get(position).getBookname());
                        noteBook.update(noteBooklist.get(position).getId());
                        noteBooklist.get(position).setBookname(editInfo.getText().toString());
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
        if(noteBooklist==null){
            noteBooklist = new ArrayList<>();
        }
        return noteBooklist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.noteBookR);
            textView = itemView.findViewById(R.id.noteBookT);
        }
    }
}
