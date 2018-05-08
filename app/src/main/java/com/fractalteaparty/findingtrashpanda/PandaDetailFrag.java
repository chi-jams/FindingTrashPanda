package com.fractalteaparty.findingtrashpanda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by daichij on 3/15/18.
 */

public class PandaDetailFrag extends AuthFrag {

    private Panda mPanda;
    private String mPandaName;
    private ImageView mPic;
    private TextView mName;
    private TextView mStatus;
    private TextView mDescription;

    private FirebaseDatabase db;
    private DatabaseReference pandaRef;

    public static PandaDetailFrag newInstance(String panda) {
        Bundle args = new Bundle();
        args.putString("panda", panda);

        PandaDetailFrag frag = new PandaDetailFrag();
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("pantrack 3", getArguments().toString());
        mPandaName = getArguments().getString("panda");
        Log.d("pantrack 4", mPandaName);

        db = FirebaseDatabase.getInstance();
        pandaRef = db.getReference().getRef().child("pandas").child(mPandaName);

        pandaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ohai", dataSnapshot.toString());

                mPanda = dataSnapshot.getValue(Panda.class);
                Log.i("ohai", String.format("It's %s!", mPanda.name));
                mName.setText(mPanda.name);
                mStatus.setText(mPanda.state);
                mDescription.setText(mPanda.hidden_life);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ohai", "Oops, something blew up");
                Log.e("ohai", databaseError.toString());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.panda_profile, container, false);


        mName = v.findViewById(R.id.panda_name);
        //mName.setText(mPanda.name);

        mStatus = v.findViewById(R.id.panda_status);
        //mStatus.setText(mPanda.state);

        mDescription = v.findViewById(R.id.panda_description);
        //mDescription.setText(mPanda.hidden_life);

        return v;
    }
}
