package com.sample.mytodo.page;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    private MemoFragment memoFragment = new MemoFragment();
    private TodayFragment todayFragment = new TodayFragment();
    private ScheduleFragment scheduleFragment = new ScheduleFragment();
    private SettingsFragment settingsFragment = new SettingsFragment();

    public TodayFragment getTodayFragment() {
        return todayFragment;
    }

    public ScheduleFragment getScheduleFragment() {
        return scheduleFragment;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return memoFragment;
            case 1:
                return todayFragment;
            case 2:
                return scheduleFragment;
            case 3:
                return settingsFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "메일&메모";
            case 1:
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("(오늘) M/d ", Locale.KOREA);
                char ch = "일월화수목금토".charAt(calendar.get(Calendar.DAY_OF_WEEK)-1);
                return sdf.format(new Date()) + ch;
            case 2:
                return "일정관리";
            case 3:
                return "설정";
        }
        return "hahaha";
    }
}
