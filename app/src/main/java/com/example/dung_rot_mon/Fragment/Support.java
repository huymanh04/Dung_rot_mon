package com.example.dung_rot_mon.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dung_rot_mon.R;


public class Support extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        // Tìm các nút Button trong layout
        View btnCall = view.findViewById(R.id.btnCall);
        View btnMessage = view.findViewById(R.id.btnMessage);

        // Thêm OnClickListener cho nút "Gọi"
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallButtonClick();
            }
        });

        // Thêm OnClickListener cho nút "Nhắn tin"
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMessageButtonClick();
            }
        });

        return view;
    }
    // Phương thức xử lý sự kiện khi click nút "Gọi"
    public void onCallButtonClick() {
        String phoneNumber = "0908168269";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    // Phương thức xử lý sự kiện khi click nút "Nhắn tin"
    public void onMessageButtonClick() {
        String phoneNumber = "0908168269";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + phoneNumber));
        startActivity(intent);
    }
}