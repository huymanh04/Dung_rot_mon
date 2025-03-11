package com.example.dung_rot_mon.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dung_rot_mon.Fragment.tab_account.AccountActivity;
import com.example.dung_rot_mon.Fragment.tab_account.ChangePass;
import com.example.dung_rot_mon.Fragment.tab_account.FavoriteCars;
import com.example.dung_rot_mon.Fragment.tab_account.MyAddress;
import com.example.dung_rot_mon.R;


public class Accountt extends Fragment {


    CardView cardMyAccount,ChangePass,cardMyAddress;
    CardView FavoriteCars;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        cardMyAccount = view.findViewById(R.id.cardMyAccount);
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
}