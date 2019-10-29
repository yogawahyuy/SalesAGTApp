package com.example.salesagt.Adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.salesagt.Fragments.DashboardFragment;
import com.example.salesagt.Fragments.DoneProgressFragment;
import com.example.salesagt.Fragments.ProgressFragment;
import com.example.salesagt.R;

public class ViewAdapterDashboard extends FragmentPagerAdapter {
    private Fragment[] childFragments;
    private Context mContext;

    public ViewAdapterDashboard(Context context,FragmentManager fm) {
        super(fm);
        mContext=context;
        childFragments = new Fragment[]{
                new ProgressFragment(),
                new DoneProgressFragment()
        };
    }

    @Override
    public Fragment getItem(int position) {

        return childFragments[position];
    }

    @Override
    public int getCount() {
        return childFragments.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position==0){
            return mContext.getString(R.string.Progressales);
        }else {
            return mContext.getString(R.string.done_sales);
        }
    }
}
