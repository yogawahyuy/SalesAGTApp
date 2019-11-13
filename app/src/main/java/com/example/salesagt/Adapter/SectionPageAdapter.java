package com.example.salesagt.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.salesagt.Fragments.DashboardFragment;
import com.example.salesagt.Fragments.MyProgressFragment;
import com.example.salesagt.Fragments.NotifFragment;
import com.example.salesagt.Fragments.ProfileFragment;

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
        }else if (position==2){
            return new NotifFragment();
        }else{
            return new ProfileFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
