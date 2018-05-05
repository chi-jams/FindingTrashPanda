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

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, FoundPandaActivity.class);
        return intent;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
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
                    mPayload = new String(message.getRecords()[0].getPayload(), "UTF-8");
                    //this line removes the "en" language encoding at the beginning of the text
                    mPayload = mPayload.substring(3);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                System.out.println(mPayload);
            }
        }
    }



    @Override
    protected Fragment createFragment(){
        System.out.println("Nope I went here");
        onNewIntent(getIntent());
        return FoundPandaFragment.newInstance(mPayload);
    }
}
