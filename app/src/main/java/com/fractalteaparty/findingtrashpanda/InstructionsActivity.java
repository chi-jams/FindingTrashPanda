package com.fractalteaparty.findingtrashpanda;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by bajafresh12 on 3/15/18.
 */

public class InstructionsActivity extends SingleFragmentActivity {

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, InstructionsActivity.class);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        return InstructionsFragment.newInstance();
    }
}
