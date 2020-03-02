package com.example.xiaomi.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.xiaomi.model.GSON.One;
import com.example.xiaomi.model.OneHome;
import com.example.xiaomi.util.TranNum;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeViewModel extends ViewModel {


    public MutableLiveData<OneHome> oneHome;

    public MutableLiveData<One> getOne() {
        if (one == null) {
            one = new MutableLiveData<>();
        }

        return one;
    }

    public void changeLogDay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("https://v1.hitokoto.cn/")
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    String jsondata = response.body().string();

                    Gson gson = new Gson();
                    One oneN = gson.fromJson(jsondata, One.class);
                    if (!(oneN.getFrom().matches("[\\u4e00-\\u9fa5]+"))){
                        oneN.setFrom("佚名");
                    }
                    one.postValue(oneN);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setOne(MutableLiveData<One> one) {
        this.one = one;
    }

    public MutableLiveData<One> one;


    public HomeViewModel() {
        changeLogDay();
        changeTime();
    }


    public MutableLiveData<OneHome> getOneHome() {
        if (oneHome == null) {
            oneHome = new MutableLiveData<>();
        }

        return oneHome;
    }

    public void setOneHome(MutableLiveData<OneHome> oneHome) {
        this.oneHome = oneHome;
    }

    public void changeTime() {
        if (oneHome == null) {
            oneHome = new MutableLiveData<>();
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");// HH:mm:ss
        SimpleDateFormat simpleDateFormatHour = new SimpleDateFormat("HH");
//获取当前时间
        Date date = new Date(System.currentTimeMillis());
        OneHome oneHomev = new OneHome();
        oneHomev.setTime(TranNum.transHour(simpleDateFormatHour.format(date)));
        oneHomev.setDate(TranNum.transNum(simpleDateFormat.format(date)));


        oneHome.postValue(oneHomev);
    }

}