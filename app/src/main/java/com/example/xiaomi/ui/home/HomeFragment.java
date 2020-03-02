package com.example.xiaomi.ui.home;

import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import com.example.xiaomi.R;
import com.example.xiaomi.databinding.FragmentHomeBinding;
import com.example.xiaomi.model.Dbservice;
import com.example.xiaomi.util.TranNum;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {


        homeViewModel = new ViewModelProvider(requireActivity()).
                get(HomeViewModel.class);

        FragmentHomeBinding binding;
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_home, container, false);
        binding.setData(homeViewModel);
        binding.setLifecycleOwner(getActivity());
        return binding.getRoot();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onPause() {
        super.onPause();

    }
}