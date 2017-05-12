package com.slava.viewpager.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.slava.viewpager.fragment.CommandFragment;
import com.slava.viewpager.fragment.PhotoFragment;
import com.slava.viewpager.fragment.TitleFragment;


/**
 * Created by slava on 11.05.17.
 */

public class PagerAdapter  extends FragmentStatePagerAdapter{
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment =new Fragment();
        switch (position){
            case 0:
                return new TitleFragment();

            case 1:
                return new CommandFragment();

            case 2:
                return new PhotoFragment();

            default:
                Log.d("pager","default");
                break;

        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "Title";
            case 1:
                return "Button";
            case 2:
                return "Photo";
            default:
                return "No page found";
        }
    }
}
