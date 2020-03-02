package com.example.xiaomi.ui.clock;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.xiaomi.util.TranNum;

import java.util.logging.Handler;

public class CountDViewModel extends ViewModel {

    MutableLiveData<Handler> handlerMutableLiveData ;
  public   MutableLiveData<String> clockTime;
  MutableLiveData<Boolean> needstay;

    public MutableLiveData<Handler> getHandlerMutableLiveData() {
        return handlerMutableLiveData;
    }

    public CountDViewModel() {
        if(clockTime==null){
            clockTime = new MutableLiveData<>();
            clockTime.setValue(TranNum.secToTime(0));
        }
        if(handlerMutableLiveData ==null){
            handlerMutableLiveData = new MutableLiveData<>();
        }
        if(needstay ==null){
            needstay = new MutableLiveData<>();
            needstay.postValue(false);
        }
    }

    public MutableLiveData<String> getClockTime() {
               return clockTime;
    }

    public void setClockTime(String string) {
        if(clockTime==null){
            clockTime = new MutableLiveData<>();
            clockTime.setValue(TranNum.secToTime(0));
        }
        clockTime.postValue(string);
    }

    public boolean  countDown(){
        Log.i("gong",clockTime.getValue());
      if(TranNum.formatTurnSecond(clockTime.getValue())>0){

          if(clockTime==null){
              clockTime = new MutableLiveData<>();
              clockTime.setValue(TranNum.secToTime(0));
          }
          clockTime.postValue(TranNum.secToTime(TranNum.formatTurnSecond(clockTime.getValue())-1));
          return true;
      }
      else {
          return false;
      }
    }

    public MutableLiveData<Boolean> getNeedstay() {
        return needstay;
    }
}
