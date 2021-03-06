package com.sardox.weatherapp.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sardox.weatherapp.recents.RecentFragment;
import com.sardox.weatherapp.weather.WeatherFragment;


public class SectionsPagerAdapter extends FragmentStatePagerAdapter {
    private int mTabCount;

    public SectionsPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.mTabCount = 0;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                return WeatherFragment.newInstance();
            case 1:
                return RecentFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mTabCount;
    }

    public void setCount(int count) {
        mTabCount = count;
    }
}
