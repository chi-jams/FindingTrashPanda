package com.fractalteaparty.findingtrashpanda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by bajafresh12 on 3/14/18.
 */

public class InstructionsFragment extends Fragment {
    private Button mVerifyButton;


    public static InstructionsFragment newInstance() {
        Bundle args = new Bundle();

        InstructionsFragment fragment = new InstructionsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_instructions, view, false);

        mVerifyButton = (Button) v.findViewById(R.id.verify_button);
        mVerifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = FoundPandaActivity.newIntent(getContext());
                startActivity(i);
            }
        });
        return v;
    }
}
