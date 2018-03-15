package com.fractalteaparty.findingtrashpanda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.plus.model.people.Person;

/**
 * Created by bajafresh12 on 3/14/18.
 */

public class PersonalPageFragment extends Fragment {
    private TextView mUsername;
    private TextView mPoints;
    private TextView mTotalFinds;
    private TextView mMostFoundPanda;

    public static PersonalPageFragment newInstance(){
        return new PersonalPageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal_page, view, false);

        return v;
    }
}
