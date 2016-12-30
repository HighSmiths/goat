package com.example.android.swipescreenpractice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Created by Owner on 12/29/2016.
 */

public class SwipeAdapter extends FragmentStatePagerAdapter {

    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int i) {
        if (i == 1)
            return new PageFragment();
        else
            return new TestFragment();

//        Fragment fragment = new PageFragment();
//        Bundle bundle = new Bundle();
//        bundle.putInt("count", i+1);
//        Log.d("COUNT", ""+ bundle.getInt("count"));
//        fragment.setArguments(bundle);
//        return fragment;
    }

    @Override
    public int getCount(){
        return 2;
    }


}
