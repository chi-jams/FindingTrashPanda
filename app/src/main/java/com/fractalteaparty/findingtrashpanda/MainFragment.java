package com.fractalteaparty.findingtrashpanda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;

import java.util.Map;
import java.util.UUID;

/**
 * Created by stephenagee on 3/14/18.
 */

public class MainFragment extends Fragment implements OnMapReadyCallback{
    MapView mMapView;
    private GoogleMap googleMap;

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, view, false);

        mMapView = (MapView) v.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(this);



        return v;
    }


    @Override
    public void onMapReady(GoogleMap googleMap){
        GoogleMap map = googleMap;
        map.getUiSettings().setMyLocationButtonEnabled(false);
        try {
            map.setMyLocationEnabled(true);
        } catch (SecurityException e){
            e.printStackTrace();
        }


    }

    @Override
    public void onResume(){
        mMapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}
