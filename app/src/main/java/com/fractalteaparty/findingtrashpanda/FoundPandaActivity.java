package com.fractalteaparty.findingtrashpanda;

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcA;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * Created by bajafresh12 on 3/15/18.
 */

public class FoundPandaActivity extends SingleFragmentActivity {

    String mPayload;
    private static String PANDA_NAME = "ftp.PandaName.key";

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, FoundPandaActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        String pandaName = getIntent().getStringExtra(PANDA_NAME);
        return FoundPandaFragment.newInstance(pandaName);
    }
}
