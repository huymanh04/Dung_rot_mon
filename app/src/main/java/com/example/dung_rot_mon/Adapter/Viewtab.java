package com.example.dung_rot_mon.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.dung_rot_mon.Fragment.Home;

import java.util.ArrayList;
import java.util.List;

public class Viewtab extends FragmentStateAdapter {
    private final List<TabFragment> fragments = new ArrayList<>();
    public Viewtab(@NonNull Home fragmentActivity) {
        super(fragmentActivity);
        fragments.add(TabFragment.newInstance(1));
        fragments.add(TabFragment.newInstance(2));
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
    public TabFragment getFragment(int position) {
        return position >= 0 && position < fragments.size() ? fragments.get(position) : null;
    }

}
