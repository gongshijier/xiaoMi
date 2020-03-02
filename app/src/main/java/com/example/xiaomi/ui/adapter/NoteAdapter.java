package com.example.xiaomi.ui.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaomi.R;
import com.example.xiaomi.model.Note;

import org.litepal.crud.DataSupport;

import java.security.acl.NotOwnerException;
import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    List<Note> noteList;

    public void setNoteList(List<Note> noteList) {
        this.noteList = noteList;
    }

    MutableLiveData<List<Note>> mutableLiveData;

    public NoteAdapter(List<Note> noteList, MutableLiveData<List<Note>> mutableLiveData, Context context) {
        this.noteList = noteList;
        this.mutableLiveData = mutableLiveData;
        this.context = context;
    }



    Context context;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.textView.setText(noteList.get(position).getContent());

        //设置长按删除事件
        holder.textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                final AlertDialog dialog = builder.create();
                //设置对话框布局
                View dialogView = View.inflate(context, R.layout.deletenote, null);
                dialog.setView(dialogView);
                dialog.show();

                //设置删除
                dialogView.findViewById(R.id.cancleDelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialogView.findViewById(R.id.sureDelete).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DataSupport.delete(Note.class,noteList.get(position).getId());
                        noteList.remove(position);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }
                });

                return true;
            }
        });

        //设置修改
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavController navController = Navigation.findNavController(v);
                Bundle bundle = new Bundle();
                bundle.putInt("Noteid",noteList.get(position).getId());
                navController.navigate(R.id.action_note_to_editNote,bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(noteList==null){
            noteList = new ArrayList<>();
        }
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
       TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView11);
        }
    }
}
