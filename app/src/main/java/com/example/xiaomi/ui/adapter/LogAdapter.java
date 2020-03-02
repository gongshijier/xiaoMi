package com.example.xiaomi.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.xiaomi.R;
import com.example.xiaomi.model.DoneThing;
import com.example.xiaomi.model.LogDay;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.ViewHolder> {
    MutableLiveData<List<LogDay>> dongLive;
    List<LogDay> doneThingList;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.textView.setText(doneThingList.get(position).getTimeStart()+"    "+doneThingList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        if(doneThingList==null){
            doneThingList = new ArrayList<>();
        }
        return doneThingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.logText);
        }
    }

    public LogAdapter(MutableLiveData<List<LogDay>> dongLive, List<LogDay> doneThingList) {
        this.dongLive = dongLive;
        this.doneThingList = doneThingList;
    }

    public void setDoneThingList(List<LogDay> doneThingList) {
        this.doneThingList = doneThingList;
        notifyDataSetChanged();
    }
}
