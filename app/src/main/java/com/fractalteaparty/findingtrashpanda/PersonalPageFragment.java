package com.fractalteaparty.findingtrashpanda;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by bajafresh12 on 3/14/18.
 */

public class PersonalPageFragment extends AuthFrag {
    private TextView mUsername;
    private TextView mPoints;
    private TextView mTotalFinds;
    private TextView mMostFoundPanda;
    private Button mLogoutBtn;

    public static PersonalPageFragment newInstance(){
        return new PersonalPageFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_personal_page, view, false);


        mUsername = v.findViewById(R.id.username);
        mPoints = v.findViewById(R.id.user_points);
        mTotalFinds = v.findViewById(R.id.user_amount_finds);
        mMostFoundPanda = v.findViewById(R.id.user_most_found);

        if (mUserRef != null) {
            mUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    updateUI();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("ppfrag", databaseError.toString());
                }
            });
        }

        mLogoutBtn = v.findViewById(R.id.sign_out);
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AuthUI.getInstance()
                        .signOut(getContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        getActivity().finish();
                        startActivity(ViewPagerActivity.newIntent(getContext()));
                    }
                });
            }
        });

        if (mUser != null && mUserInfo != null)
            updateUI();

        return v;
    }

    private void updateUI() {
        if (mUser != null && mUserInfo != null) {
            mUsername.setText(mUser.getDisplayName());
            mPoints.setText(Integer.toString(mUserInfo.points));
            mTotalFinds.setText(Integer.toString(mUserInfo.num_finds));
            String maxPanda = "No pandas found yet";
            int maxFinds = 0;
            for (Map.Entry<String, Integer> panda : mUserInfo.panda_finds.entrySet()) {
                if (panda.getValue() > maxFinds) {
                    maxPanda = panda.getKey();
                    maxFinds = panda.getValue();
                }
            }
            mMostFoundPanda.setText(maxPanda);
        }
    }
}
