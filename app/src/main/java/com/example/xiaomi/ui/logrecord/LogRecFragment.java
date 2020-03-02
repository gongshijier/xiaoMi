package com.example.xiaomi.ui.logrecord;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

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
import android.widget.CalendarView;

import com.example.xiaomi.R;
import com.example.xiaomi.model.DoneThing;
import com.example.xiaomi.model.LogDay;
import com.example.xiaomi.ui.adapter.LogAdapter;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.List;

public class LogRecFragment extends Fragment {

    private View view;
    private LogRecViewModel mViewModel;
    private CalendarView calendarView;
    private RecyclerView logRecy;
    private LogAdapter logAdapter;
    public static LogRecFragment newInstance() {
        return new LogRecFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.log_rec_fragment, container, false);
        mViewModel = new ViewModelProvider(requireActivity()).get(LogRecViewModel.class);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //设置日历监听
        calendarView =view.findViewById(R.id.calendarView);
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHH:mm");
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                mViewModel.update(year,month+1,dayOfMonth);

            }
        });


        //初始化recyclerview
        logRecy = view.findViewById(R.id.doneLogRecy);
        logRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        if (logAdapter==null){
            logAdapter= new LogAdapter(mViewModel.getDonLive(),mViewModel.getDonLive().getValue());
        }
        logRecy.setAdapter(logAdapter);


        mViewModel.getDonLive().observe(requireActivity(), new Observer<List<LogDay>>() {
            @Override
            public void onChanged(List<LogDay> doneThings) {
                logAdapter.setDoneThingList(doneThings);
            }
        });













    }

    @Override
    public void onResume() {
        super.onResume();
        mViewModel.iniData();
    }
}
