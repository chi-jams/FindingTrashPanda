package com.fractalteaparty.findingtrashpanda;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by bajafresh12 on 3/14/18.
 */

public class InstructionsFoundFragment extends AuthFrag {

    private boolean mfoundPanda;

    public static InstructionsFoundFragment newInstance() {
        Bundle args = new Bundle();
        InstructionsFoundFragment fragment = new InstructionsFoundFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_found_instructions, view, false);
        return v;
    }
}
