package com.fractalteaparty.findingtrashpanda;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import android.view.View;

import java.io.UnsupportedEncodingException;

/**
 * Created by stephenagee on 3/14/18.
 */

public class ViewPagerActivity extends AuthActivity {
    private static String PANDA_NAME = "ftp.PandaName.key";

    private ViewPager mViewPager;

    private FloatingActionButton mPandaFab;
    String mPandaName;

    public static Intent newIntent(Context packageContext) {
        Intent i = new Intent(packageContext, ViewPagerActivity.class);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //handle NFC calls and extract payload
        onNewIntent(getIntent());

        setContentView(R.layout.activity_home_pager);
        getSupportActionBar().hide();

        mPandaFab = (FloatingActionButton) this.findViewById(R.id.panda_fab);
        mPandaFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = InstructionsFoundActivity.newIntent(getApplicationContext());
                startActivity(i);
            }
        });
        mPandaFab.setBackgroundTintList(ColorStateList.valueOf(Color.BLUE));

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
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //if we got here from a NFC intent
        if (intent != null && NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction())) {
            Parcelable[] rawMessages =
                    intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
            if (rawMessages != null) {
                NdefMessage[] messages = new NdefMessage[rawMessages.length];
                for (int i = 0; i < rawMessages.length; i++) {
                    messages[i] = (NdefMessage) rawMessages[i];
                }
                NdefMessage message = (NdefMessage) messages[0];
                // Process the messages array.
                System.out.print("Here are the messages: ");
                System.out.println(messages[0]);
                try {
                    mPandaName = new String(message.getRecords()[0].getPayload(), "UTF-8");
                    //this line removes the "en" language encoding at the beginning of the text
                    mPandaName = mPandaName.substring(3);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Intent i = FoundPandaActivity.newIntent(getApplicationContext());
                i.putExtra(PANDA_NAME, mPandaName);
                startActivity(i);
            }
        }
    }

}
