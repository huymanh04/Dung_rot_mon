package com.example.dung_rot_mon.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.dialog.ChinhSachBaoMatDialogFragment;
import com.example.dung_rot_mon.dialog.HuongDanDialogFragment;
import com.example.dung_rot_mon.dialog.ThongTinDialogFragment;
import android.widget.TextView;

public class Support extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_support, container, false);

        // Tìm các nút Button trong layout
        View btnCall = view.findViewById(R.id.btnCall);
        View btnMessage = view.findViewById(R.id.btnMessage);
        View imageView = view.findViewById(R.id.imageSupport); // Tìm ImageView
        TextView tvCompany = view.findViewById(R.id.tvCompany);
        TextView tvPrivacy = view.findViewById(R.id.tvPrivacy);




        // Gán sự kiện click để mở Fragment mới
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHuongDanDialog();
            }
        });
        tvCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openThongTinDialog();
            }
        });
        tvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChinhSachBaoMatDialog();
                                        }
        });

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

    // Mở NewFragment khi bấm vào ảnh
    private void openHuongDanDialog() {
        HuongDanDialogFragment dialog = new HuongDanDialogFragment();
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        dialog.show(fragmentManager, "HuongDanDialog");
    }
    private void openThongTinDialog() {
        if (getActivity() != null) {
            ThongTinDialogFragment dialog = new ThongTinDialogFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            dialog.show(fragmentManager, "ThongTinDialog");
        }
    }
    private void openChinhSachBaoMatDialog() {
        if (getActivity() != null) {
            ChinhSachBaoMatDialogFragment dialog = new ChinhSachBaoMatDialogFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            dialog.show(fragmentManager, "ChinhSachBaoMatDialog");
        }
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
