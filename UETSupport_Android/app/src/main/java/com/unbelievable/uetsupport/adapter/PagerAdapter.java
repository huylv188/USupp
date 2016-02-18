package com.unbelievable.uetsupport.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.unbelievable.uetsupport.R;
import com.unbelievable.uetsupport.common.Constant;
import com.unbelievable.uetsupport.fragments.HelpFragment;
import com.unbelievable.uetsupport.fragments.NotificationFragment;
import com.unbelievable.uetsupport.fragments.ProfileFragment;
import com.unbelievable.uetsupport.fragments.SocialFragment;
import com.unbelievable.uetsupport.fragments.NewsFragment;

/**
 * Created by Nam on 11/20/2015.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    private Activity activity;
    private int tab_icon_size;
    private int[] icon = {
        R.mipmap.ic_web_white_24dp,
                R.mipmap.ic_society_white,
                R.mipmap.ic_notifications_white_24dp,
                R.mipmap.ic_help_outline_white_24dp,
                R.mipmap.ic_menu_white_24dp
    };

    public PagerAdapter(FragmentManager fm, Activity activity) {
        super(fm);
        this.activity = activity;
        tab_icon_size = (int) activity.getResources().getDimension(R.dimen.tab_icon_size);
    }

    private final int numbOfTabs = 5;

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable drawable = activity.getResources().getDrawable(icon[position]);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth()/2, drawable.getIntrinsicHeight()/2);
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BOTTOM);
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                NewsFragment news = new NewsFragment();
                return news;
            case 1:
                SocialFragment social = new SocialFragment();
                return social;
            case 3:
                HelpFragment help = new HelpFragment();
                return help;
            case 4:
                ProfileFragment personal = new ProfileFragment();
                return personal;
            default:
                NotificationFragment menu = new NotificationFragment();
                return menu;
        }
    }

    @Override
    public int getCount() {
        return numbOfTabs;
    }
}
