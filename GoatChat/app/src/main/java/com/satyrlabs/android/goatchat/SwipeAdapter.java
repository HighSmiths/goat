package com.satyrlabs.android.goatchat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.satyrlabs.android.goatchat.fragments.AddFriendsFragment;
import com.satyrlabs.android.goatchat.fragments.FriendListFragment;
import com.satyrlabs.android.goatchat.fragments.LogoutFragment;
import com.satyrlabs.android.goatchat.fragments.MessageListFragment;


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
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "INBOX";
            case 1:
                return "ADD FRIENDS";
            case 2:
                return "SEND GOATS";
            case 3:
                return "SETTINGS";
        }
        return null;
    }


}
