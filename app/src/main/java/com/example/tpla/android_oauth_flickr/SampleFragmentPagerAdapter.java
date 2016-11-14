package com.example.tpla.android_oauth_flickr;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;



public class SampleFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "   Personal    ", "     New Pic   ", "      Photos    " };
    private Context context;


    public SampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment newFragment = null;
        switch (position){
            case 0: newFragment = new PhotosActivity();
                break;
            case 1: newFragment = new TakePicture();
                break;
            case 2: newFragment = new Library();
                break;
            default:
                break;
        }

        return newFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitles[position];
    }
}