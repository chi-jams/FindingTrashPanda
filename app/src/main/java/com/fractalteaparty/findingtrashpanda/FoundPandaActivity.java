package com.fractalteaparty.findingtrashpanda;

import android.content.Context;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.Parcelable;
import android.support.v4.app.Fragment;

/**
 * Created by bajafresh12 on 3/15/18.
 */

public class FoundPandaActivity extends SingleFragmentActivity {

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
                // Process the messages array.
                System.out.print("Here are the messages: ");
                System.out.println(messages[0]);
            }
        }
    }

    @Override
    protected Fragment createFragment(){
        System.out.println("Nope I went here");
        return FoundPandaFragment.newInstance();
    }
}
