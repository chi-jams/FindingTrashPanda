package com.fractalteaparty.findingtrashpanda;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by bajafresh12 on 3/14/18.
 */

public class InstructionsHideFragment extends AuthFrag {
    public static final int REQUEST_LOCATION_PERMISSIONS = 0;
    private static final String[] LOCATION_PERMISSIONS = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
    };

    private String mPandaName;
    private Button mHideNowButton;
    private Button mHideLaterButton;
    private TextView mReadyToHide;
    private Location mCurrentLocation;
    private GoogleApiClient mClient;

    private FirebaseDatabase db;
    private DatabaseReference pandaRef;
    private Panda mPanda;

    public static InstructionsHideFragment newInstance(String pandaName) {
        Bundle args = new Bundle();
        InstructionsHideFragment fragment = new InstructionsHideFragment();
        fragment.setArguments(args);
        args.putString(FoundPandaFragment.PANDA_NAME, pandaName);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //get the extra (name of panda)
        mPandaName = getArguments().getString(FoundPandaFragment.PANDA_NAME);
        //setup the api client
        mClient = new GoogleApiClient.Builder(getActivity())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {
                        getActivity().invalidateOptionsMenu();
                    }
                    @Override
                    public void onConnectionSuspended(int i) {
                    }
                })
                .build();

        db = FirebaseDatabase.getInstance();
        pandaRef = db.getReference().getRef().child("pandas").child(mPandaName);

        pandaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ohai", dataSnapshot.getValue().toString());
                mPanda = dataSnapshot.getValue(Panda.class);
                Log.i("iHideFrags", String.format("%s: @(%f, %f)", mPanda.name, mPanda.lat, mPanda.lon));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ohai", "oops");
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //on start, we need to connect the client
        mClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        //on stop, we need to disconnect the client
        mClient.disconnect();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_hide_instructions, view, false);
        mReadyToHide = v.findViewById(R.id.ready_to_hide);
        Resources res = getResources();
        String text = String.format(res.getString(R.string.hide_panda_title), mPandaName);
        mReadyToHide.setText(text);

        mHideNowButton = v.findViewById(R.id.hide_panda_button);
        mHideNowButton.setText(String.format(res.getString(R.string.hide_insert_name), mPandaName));
        mHideNowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the location, push to database, etc
                if (hasLocationPermission()) {
                    getCurrentLocation();
                } else {
                    requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
                }
            }
        });

        mHideLaterButton = v.findViewById(R.id.hide_later);
        mHideLaterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return v;
    }


    private void getCurrentLocation(){
        LocationRequest request = LocationRequest.create();
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        request.setNumUpdates(1);
        request.setInterval(0);
        try {
            LocationServices.FusedLocationApi
                    .requestLocationUpdates(mClient, request, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            //Debug info - shows the location that was acquired
                            Log.i("Location, yo", "Got a fix: " + location);
                            System.out.println("Location Defined");
                            mCurrentLocation = location;
                            LatLng myPoint = new LatLng(
                                    mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude());
                            Log.i("Location, yo", myPoint.toString());

                            mPanda.lat = mCurrentLocation.getLatitude();
                            mPanda.lon = mCurrentLocation.getLongitude();
                            mPanda.uid_current_owner = mUser.getUid();
                            mPanda.state = "Hiding";
                            mPanda.date_hidden = Calendar.getInstance().getTimeInMillis();

                            pandaRef.setValue(mPanda);
                            mUserInfo.cur_panda = null;
                            mUserInfo.points += 100;
                            mUserRef.setValue(mUserInfo);
                            getActivity().finish();
                        }
                    });
        } catch (SecurityException e) {
            System.out.println("Uh Oh...Location Security Exception.");
            e.printStackTrace();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case REQUEST_LOCATION_PERMISSIONS:
                if (hasLocationPermission()) {
                    getCurrentLocation();
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private boolean hasLocationPermission() {
        int result = ContextCompat
                .checkSelfPermission(getActivity(), LOCATION_PERMISSIONS[0]);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void returnHome(){
        Toast.makeText(getContext(), "Don't forget to hide " + mPandaName + "!", Toast.LENGTH_LONG).show();
        getActivity().finish();
    }
}
