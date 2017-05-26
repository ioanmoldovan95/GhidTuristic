package com.example.john.ghidturistic.Adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.john.ghidturistic.Fragments.ListFragment;
import com.example.john.ghidturistic.Fragments.MapFragment;
import com.example.john.ghidturistic.Helpers.Constants;
import com.example.john.ghidturistic.Models.Objective;

import java.util.ArrayList;

/**
 * Created by John on 3/15/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    int numOfTabs;
    ArrayList<Objective> objectives;

    public ViewPagerAdapter(FragmentManager fm, int numOfTabs, ArrayList<Objective> objectives){
        super(fm);
        this.numOfTabs=numOfTabs;
        this.objectives=objectives;

    }
    @Override
    public Fragment getItem(int position) {
        Bundle args=new Bundle();
        args.putSerializable(Constants.Keys.OBJECTIVES_KEY,objectives);
        switch(position){
            case 0:
                MapFragment map=new MapFragment();
                map.setArguments(args);
                return map;
            case 1:
                ListFragment list=new ListFragment();
                list.setArguments(args);
                return list;
            default:
                MapFragment defMap=new MapFragment();
                defMap.setArguments(args);
                return defMap;

        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }

    public void setObjectives(ArrayList<Objective> objectives,int pos){
        this.objectives=objectives;

        if(pos==0) {
            ((MapFragment) this.getItem(0)).setObjectives(objectives);
        }else {
            ((ListFragment) this.getItem(1)).setObjectives(objectives);
        }
        this.notifyDataSetChanged();
    }
}
