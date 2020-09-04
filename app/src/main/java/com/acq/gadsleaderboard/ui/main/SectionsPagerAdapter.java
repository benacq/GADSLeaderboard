package com.acq.gadsleaderboard.ui.main;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.acq.gadsleaderboard.LearningLeadersFragment;
import com.acq.gadsleaderboard.R;
import com.acq.gadsleaderboard.SIQLeadersFragment;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.learning_leaders_text, R.string.skill_iq_leaders_text};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm, int behaviour) {
        super(fm, behaviour);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;

        switch (position){
            case 0:
                fragment = new LearningLeadersFragment();
                break;
            case 1:
                fragment = new SIQLeadersFragment();
                break;
        }
        assert fragment != null;
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}