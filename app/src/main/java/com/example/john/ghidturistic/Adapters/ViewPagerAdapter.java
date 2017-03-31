package com.example.john.ghidturistic.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.john.ghidturistic.Fragments.ListFragment;
import com.example.john.ghidturistic.Fragments.MapFragment;

/**
 * Created by John on 3/15/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    int numOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int numOfTabs){
        super(fm);
        this.numOfTabs=numOfTabs;

    }
    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                MapFragment map=new MapFragment();
                return map;
            case 1:
                ListFragment list=new ListFragment();
                return list;
            default:
                MapFragment defMap=new MapFragment();
                return defMap;

        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
