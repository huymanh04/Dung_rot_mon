package com.example.dung_rot_mon.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dung_rot_mon.Fragment.Chat;
import com.example.dung_rot_mon.Fragment.Chat_.tab_chat;
import com.example.dung_rot_mon.Fragment.Chat_.tab_tb;


public class viewchatAdapter extends FragmentStateAdapter {


    public viewchatAdapter(@NonNull Chat fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
            return new tab_chat();
            default:
                return new tab_tb();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
