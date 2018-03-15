package com.fractalteaparty.findingtrashpanda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.TextView;

/**
 * Created by bajafresh12 on 3/14/18.
 */

public class PersonalPageFragment extends Fragment {
    private TextView mUsername;
    private TextView mPoints;
    private TextView mTotalFinds;
    private TextView mMostFoundPanda;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
