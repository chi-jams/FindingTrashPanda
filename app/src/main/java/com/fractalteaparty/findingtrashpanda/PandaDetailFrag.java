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

/**
 * Created by daichij on 3/15/18.
 */

public class PandaDetailFrag extends AuthFrag {

    private String mPanda;
    private ImageView mPic;
    private TextView mName;
    private TextView mStatus;
    private TextView mDescription;

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

        mPanda = getArguments().getString("panda");
        Log.d("pantrack 4", mPanda);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.panda_profile, containter, false);

        // Heh.
        String panData[] = mPanda.split(";");

        mPic = v.findViewById(R.id.panda_pic);
        // TODO: Grab panda pic

        mName = v.findViewById(R.id.panda_name);
        mName.setText(panData[0]);

        mStatus = v.findViewById(R.id.panda_status);
        mStatus.setText(panData[1]);

        mDescription = v.findViewById(R.id.panda_description);
        mDescription.setText("Nothing here but us chickens!");

        return v;
    }
}
