package com.fractalteaparty.findingtrashpanda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by bajafresh12 on 3/14/18.
 */

public class FoundPandaFragment extends Fragment {
    private TextView mPandaName;
    private ImageView mPandaImage;
    private TextView mPandaInfo;

    public static FoundPandaFragment newInstance() {
        Bundle args = new Bundle();

        FoundPandaFragment fragment = new FoundPandaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_found_panda, view, false);

        //get NFC panda info
        mPandaImage = v.findViewById(R.id.panda_picture);
        mPandaName = v.findViewById(R.id.panda_name);
        mPandaInfo = v.findViewById(R.id.hidden_life_info);

        mPandaName.setText("BillyBob");


        return v;
    }


}
