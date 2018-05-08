package com.fractalteaparty.findingtrashpanda;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by daichij on 5/5/18.
 */

public class AuthActivity extends AppCompatActivity {
    private static final int RC_SIGN_IN = 123;

    protected FirebaseAuth mAuth;
    protected FirebaseUser mUser;

    protected FirebaseDatabase db;
    protected DatabaseReference mUserRef;

    protected User mUserInfo;

    @Override
    protected void onCreate(Bundle onSavedInstanceState) {
        super.onCreate(onSavedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        checkLogin();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //checkLogin();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                mUser = FirebaseAuth.getInstance().getCurrentUser();
                db = FirebaseDatabase.getInstance();
                mUserRef = db.getReference().getRef().child("users").child(mUser.getUid());
                mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (!dataSnapshot.exists()) {
                            mUserInfo = new User();
                            Log.i("authfrag", "Didn't find a user... making a new one");
                            Log.i("authfrag", mUser.getDisplayName());
                            mUserInfo.name = mUser.getDisplayName();
                            mUserRef.setValue(mUserInfo);
                        }
                        else {
                            mUserInfo = dataSnapshot.getValue(User.class);
                            Log.i("authfrag", String.format("Got a user! Hi %s!", mUser.getDisplayName()));
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                finish();
                startActivity(ViewPagerActivity.newIntent(getApplicationContext()));

            } else {
                // Sign in failed, check response for error code
                // ...
                Log.i("ohai", "Oops, didn't get a login");
            }
        }
    }

    private void checkLogin() {
        Log.i("ohai", "getting a user...");
        mUser = mAuth.getCurrentUser();
        if (mUser == null) {
            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build());

// Create and launch sign-in intent
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        }
        else {
            Log.i("ohai", "I did it without asking for login!");
            Log.i("ohai", mUser.toString());
            Log.i("ohai", String.format("Hello! I am %s", mUser.getDisplayName()));
            Log.i("ohai", String.format("Hello! My email is %s", mUser.getEmail()));

            db = FirebaseDatabase.getInstance();
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

                    mUserRef.setValue(mUserInfo);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        }
    }
}
