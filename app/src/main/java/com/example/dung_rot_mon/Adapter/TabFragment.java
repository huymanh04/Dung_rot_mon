package com.example.dung_rot_mon.Adapter;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.dung_rot_mon.R;

public class TabFragment extends Fragment {

    private static final String ARG_POSITION = "position";

    public static TabFragment newInstance(int position) {
        TabFragment fragment = new TabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int position = getArguments() != null ? getArguments().getInt(ARG_POSITION) : 1;
        int layoutId = position == 1 ? R.layout.fragment_tu_lai : R.layout.fragment_co_tai_xe;
        return inflater.inflate(layoutId, container, false);
    }
}
