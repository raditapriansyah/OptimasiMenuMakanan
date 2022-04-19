package net.gumcode.optimasimenumakanan.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import net.gumcode.optimasimenumakanan.fragment.FragmentAkg;
import net.gumcode.optimasimenumakanan.fragment.FragmentKeterangan;
import net.gumcode.optimasimenumakanan.fragment.FragmentHasil;

/**
 * Created by A. Fauzi Harismawan on 11/17/2016.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private int NumbOfTabs;
    private CharSequence[] Titles;

    public ViewPagerAdapter(FragmentManager paramFragmentManager, CharSequence[] paramArrayOfCharSequence, int paramInt) {
        super(paramFragmentManager);
        this.Titles = paramArrayOfCharSequence;
        this.NumbOfTabs = paramInt;
    }

    public int getCount() {
        return this.NumbOfTabs;
    }

    public Fragment getItem(int paramInt) {
        if (paramInt == 0)
            return new FragmentAkg();
        if (paramInt == 1)
            return new FragmentHasil();
        return new FragmentKeterangan();
    }

    public CharSequence getPageTitle(int paramInt) {
        return this.Titles[paramInt];
    }
}