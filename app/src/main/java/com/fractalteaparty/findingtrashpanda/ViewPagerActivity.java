package com.fractalteaparty.findingtrashpanda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;

import java.util.UUID;

/**
 * Created by stephenagee on 3/14/18.
 */

public class ViewPagerActivity extends AppCompatActivity {
    private ViewPager mViewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_pager);
        getSupportActionBar().hide();

        mViewPager = (ViewPager) findViewById(R.id.home_pager);
        FragmentManager fm = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
            @Override
            public Fragment getItem(int position) {
                switch (position) {
                    case 0:
                        System.out.println("Page " + position);
                        return PersonalPageFragment.newInstance();
                    case 1:
                        System.out.println("Page " + position);
                        return MainFragment.newInstance();
                    case 2:
                        System.out.println("Page " + position);
                        return LeaderboardFragment.newInstance();
                }
                System.out.println("Page " + position);
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        mViewPager.setCurrentItem(1);
        System.out.println("Gothere");
    }

}
