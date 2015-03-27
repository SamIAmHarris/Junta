package com.jackrabbitmobile.junta.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jackrabbitmobile.junta.R;
import com.jackrabbitmobile.junta.fragment.LoginNameFragment;
import com.jackrabbitmobile.junta.fragment.LoginNumberFragment;

/**
 * Created by SamMyxer on 1/29/15.
 */
public class LoginTwoActivity extends FragmentActivity {
    ViewPager viewPager=null;
    int numberOfViewPagerChildren = 2;
    int lastIndexOfViewPagerChildren = numberOfViewPagerChildren - 1;
    public static String name;
    public static String email;
    public static String password;
    public static int phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_two);
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(new LoginAdapter(getSupportFragmentManager()));

    }
    class LoginAdapter extends FragmentStatePagerAdapter
    {

        public LoginAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int i) {
            Fragment fragment=null;
            if(i==0)
            {
                fragment = new LoginNameFragment();
            }
            if(i==1)
            {
                fragment = new LoginNumberFragment();
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return numberOfViewPagerChildren;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0) {
                return "Login Credentials";
            }
            if(position == 1) {
                return "Phone Number";
            }

            return "";
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            if(object instanceof LoginNameFragment){
                view.setTag(2);
            }
            if(object instanceof LoginNumberFragment){
                view.setTag(1);
            }
            return super.isViewFromObject(view, object);
        }
    }

}



