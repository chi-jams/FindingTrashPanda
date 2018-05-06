package com.fractalteaparty.findingtrashpanda;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

/**
 * Created by bajafresh12 on 3/15/18.
 */

public class InstructionsHideActivity extends SingleFragmentActivity {


    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, InstructionsHideActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        String pandaName = getIntent().getStringExtra(FoundPandaFragment.PANDA_NAME);
        return InstructionsHideFragment.newInstance(pandaName);
    }
}
