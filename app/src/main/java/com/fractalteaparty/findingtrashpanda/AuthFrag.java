package com.fractalteaparty.findingtrashpanda;

import android.os.Bundle;
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

    protected User mUserInfo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        db = FirebaseDatabase.getInstance();

        if (mUser == null) return; // lol

        mUserRef = db.getReference().getRef().child("users").child(mUser.getUid());
        mUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) {
                    mUserInfo = new User();
                    Log.i("authfrag", "Didn't find a user... making a new one");
                }
                else {
                    mUserInfo = dataSnapshot.getValue(User.class);
                    Log.i("authfrag", String.format("Got a user! Hi %s!", mUser.getDisplayName()));
                }

                //mUserInfo.name = mUser.getDisplayName();
                mUserRef.setValue(mUserInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mUser = mAuth.getCurrentUser();
    }
}
