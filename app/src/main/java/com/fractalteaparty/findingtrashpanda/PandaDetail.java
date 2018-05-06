package com.fractalteaparty.findingtrashpanda;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;

/**
 * Created by daichij on 3/15/18.
 */

public class PandaDetail extends SingleFragmentActivity
    implements MainFragment.Callbacks {

    public static Intent newIntent(Context pkgContext, String pandaName) {
        Intent intent = new Intent(pkgContext, PandaDetail.class);
        intent.putExtra("panda", pandaName);

        return intent;
    }

    @Override
    protected Fragment createFragment() {
        String panda = getIntent().getStringExtra("panda");

        Log.d("pantrack 2", panda);

        return PandaDetailFrag.newInstance(panda);
    }

    @Override
    public void onPandaSelected(String s) {
        // Oops, not implemented
    }
}
