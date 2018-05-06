package com.fractalteaparty.findingtrashpanda;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Created by stephenagee on 3/14/18.
 */

public class ViewPagerActivity extends AuthActivity {

    private ViewPager mViewPager;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, ViewPagerActivity.class);
        return i;
    }

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
                Log.i("ohai", "Page " + position);
                switch (position) {
                    case 0:
                        return PersonalPageFragment.newInstance();
                    case 1:
                        return MainFragment.newInstance();
                    case 2:
                        return LeaderboardFragment.newInstance();
                }
                return null;
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        mViewPager.setCurrentItem(1);
        Log.i("ohai", "Got here");
    }

}
