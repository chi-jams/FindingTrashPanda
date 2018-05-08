package com.fractalteaparty.findingtrashpanda;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

/**
 * Created by bajafresh12 on 3/14/18.
 */

public class FoundPandaFragment extends AuthFrag {
    public static final String PANDA_NAME = "ftp.PandaName.key";
    private TextView mPandaName;
    private String mPassedPandaName;
    private ImageView mPandaImage;
    private TextView mPandaInfo;
    private Button mHideNowButton, mGoHomeButton;

    private DatabaseReference mPandaRef;
    private Panda mPanda;

    public static FoundPandaFragment newInstance(String mPayload) {
        Bundle args = new Bundle();
        args.putString(PANDA_NAME, mPayload);
        FoundPandaFragment fragment = new FoundPandaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPassedPandaName = getArguments().getString(PANDA_NAME);

        mPandaRef = db.getReference().getRef().child("pandas").child(mPassedPandaName);
        mPandaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mPanda = dataSnapshot.getValue(Panda.class);
                Log.i("fpfragment", String.format("It's %s at %f, %f", mPanda.name, mPanda.lat, mPanda.lon));

                mPanda.state = "Found";
                mPanda.uid_current_owner = mUser.getUid();

                long curTime = Calendar.getInstance().getTimeInMillis();
                mUserInfo.num_finds++;
                mUserInfo.points += 7 * (curTime - mPanda.date_hidden) / (1000 * 60);
                mUserInfo.panda_finds.put(mPanda.name, mUserInfo.panda_finds.containsKey(mPanda.name) ? mUserInfo.panda_finds.get(mPanda.name) + 1 : 1);
                mUserInfo.cur_panda = mPanda.name;

                mPandaRef.setValue(mPanda);
                mUserRef.setValue(mUserInfo);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("fpfragment", databaseError.toString());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_found_panda, view, false);

        //get NFC panda info
        mPandaName = v.findViewById(R.id.panda_name);
        mPandaInfo = v.findViewById(R.id.hidden_life_info);

        mPandaName.setText(mPassedPandaName);

        mHideNowButton = v.findViewById(R.id.hide_now_button);
        mHideNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = InstructionsHideActivity.newIntent(getActivity().getApplicationContext(), mPassedPandaName);
                startActivity(i);
                getActivity().finish();
            }
        });
        mGoHomeButton = v.findViewById(R.id.go_home);
        mGoHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

        return v;
    }


}
