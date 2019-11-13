package com.example.salesagt;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.salesagt.Adapter.SectionPageAdapter;
import com.example.salesagt.Adapter.ViewAdapterDashboard;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class DashboardActivity extends AppCompatActivity {
    private ViewPager customViewPager;
    private BottomNavigationViewEx bottomNavigationViewEx;
    private MenuItem menuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        customViewPager=findViewById(R.id.customviewpager_dash);
        bottomNavigationViewEx=findViewById(R.id.bottom_nav_view);
        SectionPageAdapter adapter=new SectionPageAdapter(this,getSupportFragmentManager());
        customViewPager.setAdapter(adapter);

        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.ic_home:
                        customViewPager.setCurrentItem(0);
                        break;
                    case R.id.ic_product:
                        customViewPager.setCurrentItem(1);
                        break;
                    case R.id.ic_notification:
                        customViewPager.setCurrentItem(2);
                        break;
                    case R.id.ic_profile:
                        customViewPager.setCurrentItem(3);
                        break;
                }
                return false;
            }
        });
        customViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (menuItem!=null)menuItem.setChecked(false);
                else bottomNavigationViewEx.getMenu().getItem(0).setChecked(false);

                bottomNavigationViewEx.getMenu().getItem(position).setChecked(true);
                menuItem=bottomNavigationViewEx.getMenu().getItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
