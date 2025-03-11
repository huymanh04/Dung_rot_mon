package com.example.dung_rot_mon.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dung_rot_mon.Adapter.viewchatAdapter;
import com.example.dung_rot_mon.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Chat extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager2 mViewPager;
    private viewchatAdapter myViewPargerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager =view.findViewById(R.id.view_pager);
        myViewPargerAdapter = new viewchatAdapter(this);
        mViewPager.setAdapter(myViewPargerAdapter);

        new TabLayoutMediator(mTabLayout, mViewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Tin Nhắn");
                    break   ;
                case 1:
                    tab.setText("Thông báo");
                    break   ;
            }

        }).attach();

        return  view;
    }
}