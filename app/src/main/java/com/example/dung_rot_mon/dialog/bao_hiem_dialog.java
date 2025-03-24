package com.example.dung_rot_mon.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.dung_rot_mon.R;

public class bao_hiem_dialog  extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bao_hiem, container, false);

        Button btnClose = view.findViewById(R.id.button);
        btnClose.setOnClickListener(v -> dismiss()); // Đóng Dialog khi nhấn nút

        return view;
    }
}