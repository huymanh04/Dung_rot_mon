package com.example.dung_rot_mon.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dung_rot_mon.Fragment.tab_account.AccountActivity;
import com.example.dung_rot_mon.Fragment.tab_account.ChangePass;
import com.example.dung_rot_mon.Fragment.tab_account.FavoriteCars;
import com.example.dung_rot_mon.Fragment.tab_account.MyAddress;
import com.example.dung_rot_mon.R;
import com.example.dung_rot_mon.Utils;


public class Accountt extends Fragment {

    private ImageView imageView;
    CardView cardMyAccount,ChangePass,cardMyAddress;
    CardView FavoriteCars;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        cardMyAccount = view.findViewById(R.id.cardMyAccount);
        imageView = view.findViewById(R.id.imageView);
        String base64String = Utils.convertImageToBase64(this, R.drawable.faceid);
        Bitmap bitmap = decodeBase64ToBitmap(base64String);
        imageView.setImageBitmap(bitmap);
        cardMyAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(getActivity(), AccountActivity.class);
                startActivity(a);
            }
        });
        FavoriteCars = view.findViewById(R.id.cardFavoriteCars);
        FavoriteCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getActivity(), FavoriteCars.class);
                startActivity(m);
            }
        });
        ChangePass = view.findViewById(R.id.cardChangePassword);
        ChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getActivity(), com.example.dung_rot_mon.Fragment.tab_account.ChangePass.class);
                startActivity(m);
            }
        });
        cardMyAddress = view.findViewById(R.id.cardMyAddress);
        cardMyAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent m = new Intent(getActivity(), MyAddress.class);
                startActivity(m);
            }
        });


        return view;
    }
    private Bitmap decodeBase64ToBitmap(String base64String) {
        // Giải mã chuỗi Base64
        byte[] decodedBytes = Base64.decode(base64String, Base64.DEFAULT);

        // Chuyển đổi byte array thành Bitmap
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}