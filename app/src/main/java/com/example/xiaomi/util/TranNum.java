package com.example.xiaomi.util;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.xiaomi.R;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class TranNum {
    public static String transNum(String number) {
        String string = new String();
        string = "";
        for(int i=0;i<number.length();i++ ){
            if(Character.isDigit(number.charAt(i))){
                string = string + transchar(number.charAt(i));
            }else {
                string = string +number.charAt(i);
            }

        }

        return string;
    }

    private static String transchar(char charAt) {
        switch (charAt) {
            case '0':
                return "零";
            case '1':
                return "一";
            case '2':
                return "二";
            case '3':
                return "三";

            case '4':
                return "四";
            case '5':
                return "五";

            case '6':
                return "六";
            case '7':
                return "七";

            case '8':
                return "八";
            case '9':
                return "九";
            default:
                return "";
        }

    }

    public static String transHour(String format) {
        switch (Integer.parseInt(format)){
            case 0:
                return "zi shi\n子 时";
            case 1:
                return "zi shi\n子 时";
            case 2:
                return "chou shi\n丑 时";
            case 3:
                return "chou shi\n丑 时";
            case 4:
                return "yin shi\n寅 时";
            case 5:
                return "yin shi\n寅 时";
            case 6:
                return "mao shi\n卯 时";
            case 7:
                return "mao shi\n卯 时";
            case 8:
                return "chen shi\n辰 时";
            case 9:
                return "chen shi\n辰 时";
            case 10:
                return "si shi\n巳 时";
            case 11:
                return "si shi\n巳 时";
            case 12:
                return "wu shi\n午 时";
            case 13:
                return "wu shi\n午 时";
            case 14:
                return "wei shi\n未 时";
            case 15:
                return "wei shi\n未 时";
            case 16:
                return "shen shi\n申 时";
            case 17:
                return "shen shi\n申 时";
            case 18:
                return "you shi\n酉 时";
            case 19:
                return "you shi\n酉 时";
            case 20:
                return "wu shi\n戌 时";
            case 21:
                return "wu shi\n戌 时";
            case 22:
                return "hai shi\n亥 时";
            case 23:
                return "hai shi\n亥 时";
                default:
                    return "";

        }
    }



    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    public static int formatTurnSecond(String time) {
        String s = time;
        if(time.length()>6){

            int index1 = s.indexOf(":");
            int index2 = s.indexOf(":", index1 + 1);
            int hh = Integer.parseInt(s.substring(0, index1));
            int mi = Integer.parseInt(s.substring(index1 + 1, index2));
            int ss = Integer.parseInt(s.substring(index2 + 1));


            return hh * 60 * 60 + mi * 60 + ss;
        }
        else {

            int index2 = s.indexOf(":");
            int hh = 0;
            int mi = Integer.parseInt(s.substring(0, index2));
            int ss = Integer.parseInt(s.substring(index2+1 ));
            return hh * 60 * 60 + mi * 60 + ss;
        }
    }



}
