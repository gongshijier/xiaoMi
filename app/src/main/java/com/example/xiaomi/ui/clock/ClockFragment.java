package com.example.xiaomi.ui.clock;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.xiaomi.R;
import com.example.xiaomi.databinding.ClockFragmentBinding;
import com.example.xiaomi.databinding.FragmentHomeBinding;

public class ClockFragment extends Fragment {

    private ClockViewModel mViewModel;
    Switch aSwitch;
    Handler handler;
    ImageView start;
    ImageView restart;
    ImageView pause;




    Runnable runnable;

    View view;

    public static ClockFragment newInstance() {
        return new ClockFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        mViewModel = new ViewModelProvider(requireActivity())
                .get(ClockViewModel.class);


        ClockFragmentBinding binding;
        binding = DataBindingUtil.inflate(inflater, R.layout.clock_fragment, container,
                false);
        binding.setData(mViewModel);
        binding.setLifecycleOwner(getActivity());
        view = binding.getRoot();
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        start = view.findViewById(R.id.cStart);
        restart = view.findViewById(R.id.cRestart);
        pause = view.findViewById(R.id.cPause);

        //确定是否需要重置界面
        mViewModel.getNeedstay().observe(requireActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean==false){
                    //监听到boolean值不需要保存状态
                    runnable = new Runnable() {
                        @Override
                        public void run() {
//                        while(true){
                            mViewModel.countTime();
                            if (mViewModel.getHandlerMutableLiveData().getValue() == null) {

                            }else {

                                mViewModel.getHandlerMutableLiveData().getValue().postDelayed(this, 1000);

                            }

//                        }
                        }
                    };
                }
                else {
                    start.setVisibility(View.INVISIBLE);
                    pause.setVisibility(View.VISIBLE);
                    restart.setVisibility(View.VISIBLE);
                    pause.setImageResource(R.drawable.ic_pause);
                }
            }
        });


        //开始计时

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.getNeedstay().postValue(true);


                if(mViewModel.getHandlerMutableLiveData().getValue()==null){
                    mViewModel.getHandlerMutableLiveData().setValue(new Handler());
                    Log.i("gong","创建新的handler");
                }

                    mViewModel.getHandlerMutableLiveData().getValue().postDelayed(runnable, 1000);



            }
        });

        //restart计时
        restart.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           mViewModel.getNeedstay().postValue(false);
                                           restart.setVisibility(View.INVISIBLE);
                                           pause.setVisibility(View.INVISIBLE);
                                           start.setVisibility(View.VISIBLE);
                                           mViewModel.getHandlerMutableLiveData().getValue().removeCallbacks(runnable);
                                           mViewModel.setTimeShow(0);

                                       }
                                   }
        );

        //设置暂停

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mViewModel.getNeedstay().getValue()==false){
                    pause.setImageResource(R.drawable.ic_pause);
                    if (mViewModel.getHandlerMutableLiveData().getValue()==null) {
                        handler = new Handler();
                        Log.i("gong","创建新的handler");
                        mViewModel.getHandlerMutableLiveData().setValue(handler);
                    }
                    mViewModel.getNeedstay().setValue(true);
                    mViewModel.getHandlerMutableLiveData().getValue().postDelayed(runnable, 1000);
                }
              else{

                    Log.i("gong","执行了暂停操作");
                    mViewModel.getHandlerMutableLiveData().getValue().removeCallbacksAndMessages(null);
                    pause.setImageResource(R.drawable.ic_start);
                    mViewModel.getNeedstay().setValue(false);
                }
            }
        });


        aSwitch = view.findViewById(R.id.timeSwitch);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                NavController navController = Navigation.findNavController(buttonView);
                navController.navigate(R.id.action_clockFragment_to_countDFragment);
            }
        });




//        vibrator.cancel();


    }

}
