package com.example.xiaomi.ui.logrecord;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.xiaomi.model.DoneThing;
import com.example.xiaomi.model.LogDay;
import com.example.xiaomi.ui.adapter.DoneAdapter;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LogRecViewModel extends ViewModel {
    SimpleDateFormat simpleDateFormat =
            new SimpleDateFormat("yyyyMMdd");
    public LogRecViewModel() {

        Date date = new Date(System.currentTimeMillis());
        String dateT = simpleDateFormat.format(date);
        if (logLive==null){
            logLive = new MutableLiveData<>();
            logLive.postValue(DataSupport.where(
                    "day=?",dateT).find(LogDay.class));
        }
    }

    MutableLiveData<List<LogDay>> logLive;

    public MutableLiveData<List<LogDay>> getDonLive() {
        return logLive;
    }




    public void update(int year, int month, int date) {


        String t = ""+year+String.format("%02d",month)+String.format("%02d",date);
        Log.i("gong","数据库中查询结果"+t);
        logLive.postValue(DataSupport.where("day=?",t).
                find(LogDay.class));
    }

    public void iniData() {
        Date date = new Date(System.currentTimeMillis());
        String dateT = simpleDateFormat.format(date);
        if (logLive==null){
            logLive = new MutableLiveData<>();
        }
        logLive.postValue(DataSupport.where("day=?",dateT)
                .find(LogDay.class));

    }
// TODO: Implement the ViewModel
}
