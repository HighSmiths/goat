package com.example.android.goatchat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.android.goatchat.fragments.AddFriendsFragment;
import com.example.android.goatchat.fragments.FriendListFragment;
import com.example.android.goatchat.fragments.LogoutFragment;
import com.example.android.goatchat.fragments.MessageListFragment;


/**
 * Created by Owner on 12/29/2016.
 */

public class SwipeAdapter extends FragmentStatePagerAdapter {

    public SwipeAdapter(FragmentManager fm) {
        super(fm);
    }
    @Override
    public Fragment getItem(int i) {
        Log.d(Constants.LOG_TAG, "i = " + i);
        Fragment fragment = new Fragment();
        switch(i) {
            case 0:
                fragment = new MessageListFragment();
                break;
            case 1:
                fragment = new AddFriendsFragment();
                break;
            case 2:
                fragment = new FriendListFragment();
                break;
            default:
                fragment = new LogoutFragment();
                break;



        }

        return fragment;
    }

    @Override
    public int getCount(){
        return 5;
    }


}