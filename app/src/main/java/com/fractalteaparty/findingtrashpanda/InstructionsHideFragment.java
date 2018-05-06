package com.fractalteaparty.findingtrashpanda;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by bajafresh12 on 3/14/18.
 */

public class InstructionsHideFragment extends AuthFrag {
    private String mPandaName;
    private Button mHideNowButton;
    private Button mHideLaterButton;
    private TextView mReadyToHide;

    public static InstructionsHideFragment newInstance(String pandaName) {
        Bundle args = new Bundle();
        InstructionsHideFragment fragment = new InstructionsHideFragment();
        fragment.setArguments(args);
        args.putString(FoundPandaFragment.PANDA_NAME, pandaName);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPandaName = getArguments().getString(FoundPandaFragment.PANDA_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_hide_instructions, view, false);
        mReadyToHide = v.findViewById(R.id.ready_to_hide);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.hide_panda_title), mPandaName);
        mReadyToHide.setText(text);
        mHideNowButton = v.findViewById(R.id.hide_panda_button);
        mHideNowButton.setText(String.format(res.getString(R.string.hide_insert_name), mPandaName));
        mHideNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the location, push to database, etc
                Toast.makeText(getContext(), mPandaName + " hidden", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });

        mHideLaterButton = v.findViewById(R.id.hide_later);
        mHideLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the location, push to database, etc
                Toast.makeText(getContext(), "Don't forget to hide " + mPandaName + "!", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }
        });
        return v;
    }
}
