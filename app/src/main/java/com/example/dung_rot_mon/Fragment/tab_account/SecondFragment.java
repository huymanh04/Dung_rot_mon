package com.example.dung_rot_mon.Fragment.tab_account;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import android.widget.TextView;

import com.example.dung_rot_mon.R;

public class SecondFragment extends Fragment {

    public SecondFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tai_khoan_cua_toi, container, false);

        // Lấy dữ liệu được truyền từ fragment trước (nếu có)
        String data = getArguments() != null ? getArguments().getString("key") : "No data";

        // Cập nhật dữ liệu lên TextView


        return rootView;
    }
}
