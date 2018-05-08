package com.fractalteaparty.findingtrashpanda;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

/**
 * Created by daichij on 5/5/18.
 */

public class AuthFrag extends Fragment {
    protected FirebaseAuth mAuth;
    protected FirebaseUser mUser;

    protected FirebaseDatabase db;
    protected DatabaseReference mUserRef;
    protected ValueEventListener userCheck;

    protected User mUserInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        db = FirebaseDatabase.getInstance();

        if (mUser == null) return; // lol

        mUserRef = db.getReference().getRef().child("users").child(mUser.getUid());
        userCheck = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    mUserInfo = new User();
                    Log.i("authfrag", "Didn't find a user... making a new one");
                }
                else {
                    mUserInfo = dataSnapshot.getValue(User.class);
                    Log.i("authfrag", String.format("Got a user! Hi %s!", mUser.getDisplayName()));
                    Log.i("authfrag", getContext().toString());
                }

                //mUserInfo.name = mUser.getDisplayName();
                mUserRef.setValue(mUserInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mUserRef.addValueEventListener(userCheck);
    }

    @Override
    public void onStart() {
        super.onStart();
        mUser = mAuth.getCurrentUser();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mUserRef != null)
            mUserRef.removeEventListener(userCheck);
        else
            Log.i("ohai", "Oops, got a null reference");
    }
}
