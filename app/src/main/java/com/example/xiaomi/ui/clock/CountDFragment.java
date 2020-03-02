package com.example.xiaomi.ui.clock;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.xiaomi.R;
import com.example.xiaomi.databinding.CountDFragmentBinding;
import com.example.xiaomi.util.TranNum;

public class CountDFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    Switch timeSwitch;
    View view;
    CountDViewModel mViewModel;
    TextView showTime;
    Handler handler;
    ImageView start;
    ImageView restart;
    ImageView pause;

    TextView timeOverN;
    TextView timeOVerY;

    Button button;

    Runnable runnable;
    ClockViewModel clockViewModel;
    Vibrator vibrator;
    long[] pattern = {2000,1000}; //震动1000间2000

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return new TimePickerDialog(getActivity(), this, 0, 30, true);

    }

    public static CountDFragment newInstance() {
        return new CountDFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        clockViewModel = new ViewModelProvider(requireActivity()).get(ClockViewModel.class);
        //使用viewModel和databinding需要在onCreatView否则binding的数据源头没有
        mViewModel = new ViewModelProvider(requireActivity()).get(CountDViewModel.class);

        CountDFragmentBinding databinding;
        databinding = DataBindingUtil.inflate(inflater, R.layout.count_d_fragment,
                container, false);
        databinding.setData(mViewModel);
        databinding.setLifecycleOwner(getActivity());
        view = databinding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //定位控件
        start = view.findViewById(R.id.cdStart);
        restart = view.findViewById(R.id.cdRestart);
        pause = view.findViewById(R.id.cdPause);


        mViewModel.getNeedstay().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean == false) {
                    //监听到boolean值不需要保存状态
                    runnable = new Runnable() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void run() {
//                        while(true){
                            if (mViewModel.countDown()) {

                                if (clockViewModel.getHandlerMutableLiveDataD().getValue() == null) {

                                } else {
                                    clockViewModel.getHandlerMutableLiveDataD().getValue().postDelayed(this, 1000);

                                }
                            } else {
                                clockViewModel.getHandlerMutableLiveDataD().getValue().removeCallbacksAndMessages(null);

                                vibrator = (Vibrator) requireActivity().getSystemService(Context.VIBRATOR_SERVICE);
                                vibrator.vibrate(pattern,0);
                                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                                final AlertDialog dialog = builder.create();
                                //设置对话框布局
                                View dialogView = View.inflate(requireActivity(), R.layout.time_over, null);
                                dialog.setView(dialogView);
                                dialog.show();
                                timeOVerY = dialogView.findViewById(R.id.timeOverY);
                                timeOVerY.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        vibrator.cancel();
                                        dialog.dismiss();
                                    }
                                });

                            }

//                        }
                        }
                    };
                } else {
                    start.setVisibility(View.INVISIBLE);
                    pause.setVisibility(View.VISIBLE);
                    restart.setVisibility(View.VISIBLE);
                    pause.setImageResource(R.drawable.ic_pause);
                }
            }
        });


        //切换倒计时和正计时
        timeSwitch = view.findViewById(R.id.cDSwitch);
        timeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NavController navController = Navigation.findNavController(buttonView);
                navController.navigate(R.id.action_countDFragment_to_clockFragment);

            }
        });
        showTime = view.findViewById(R.id.textView10);
        showTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timepicker = new CountDFragment();
                timepicker.show(getActivity().getSupportFragmentManager(), "time Picker");
            }
        });


        //开始计时
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((mViewModel.getClockTime().getValue() == "00:00:00") || (mViewModel.getClockTime().getValue() == "00:00")) {

                    Toast.makeText(requireContext(), "点击时间设置时长！", Toast.LENGTH_SHORT).show();
                } else {
                    mViewModel.getNeedstay().postValue(true);
                    if (clockViewModel.getHandlerMutableLiveDataD().getValue() == null) {
                        clockViewModel.getHandlerMutableLiveDataD().setValue(new Handler());
                    }

                    clockViewModel.getHandlerMutableLiveDataD().getValue().postDelayed(runnable, 1000);
                }
            }
        });

        //设置restart键

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getNeedstay().postValue(false);
                restart.setVisibility(View.INVISIBLE);
                pause.setVisibility(View.INVISIBLE);
                start.setVisibility(View.VISIBLE);
                clockViewModel.getHandlerMutableLiveDataD().getValue().removeCallbacksAndMessages(null);
                mViewModel.setClockTime("00:00:00");
            }
        });
        //设置暂停键

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mViewModel.getNeedstay().getValue() == false) {
                    pause.setImageResource(R.drawable.ic_pause);
                    if (clockViewModel.getHandlerMutableLiveDataD().getValue() == null) {
                        handler = new Handler();
                        Log.i("gong", "创建新的handler");
                        clockViewModel.getHandlerMutableLiveDataD().setValue(handler);
                    }
                    mViewModel.getNeedstay().setValue(true);
                    clockViewModel.getHandlerMutableLiveDataD().getValue().postDelayed(runnable, 1000);
                } else {

                    Log.i("gong", "执行了暂停操作");
                    clockViewModel.getHandlerMutableLiveDataD().getValue().removeCallbacksAndMessages(null);
                    pause.setImageResource(R.drawable.ic_start);
                    mViewModel.getNeedstay().setValue(false);
                }
            }
        });


    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Log.i("gong", TranNum.secToTime(hourOfDay * 3600 + minute * 60));
        mViewModel.setClockTime(TranNum.secToTime(hourOfDay * 3600 + minute * 60));
    }
}
