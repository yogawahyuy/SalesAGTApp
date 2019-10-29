package com.example.salesagt.Adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomAdapterDashboard extends ViewPager {
    private boolean enable;

    public CustomAdapterDashboard(Context context){super(context);}
    public CustomAdapterDashboard(Context context, AttributeSet attr){super(context,attr);}

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (this.enable){ return super.onTouchEvent(ev);}
        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (this.enable){return super.onInterceptTouchEvent(ev);}
        return false;
    }
    public void setPagingEnabled(boolean enabled) {
        this.enable = enabled;
    }
}
