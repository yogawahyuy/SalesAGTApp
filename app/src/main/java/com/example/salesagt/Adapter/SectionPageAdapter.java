package com.example.salesagt.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.salesagt.Fragments.DashboardFragment;
import com.example.salesagt.Fragments.MyProgressFragment;

public class SectionPageAdapter extends FragmentPagerAdapter {
    private Context context;
    public SectionPageAdapter(Context context,FragmentManager fm) {
        super(fm);
        context=context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position==0){
            return new DashboardFragment();
        }else if(position==1){
            return new MyProgressFragment();
        }else {
            return new DashboardFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
