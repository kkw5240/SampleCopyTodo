package com.sample.mytodo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.util.Log;

import com.sample.mytodo.page.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    TabLayout tabLayout;// = new TabLayout();
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tab_layout);

        viewPager = findViewById(R.id.pager);
        final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        viewPager.setOffscreenPageLimit(2);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 1) {
                    viewPagerAdapter.getTodayFragment().refresh();
                }
                if(position == 2) {
                    viewPagerAdapter.getScheduleFragment().refresh();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
