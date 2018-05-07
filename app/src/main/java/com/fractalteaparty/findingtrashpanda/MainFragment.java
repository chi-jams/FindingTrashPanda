package com.fractalteaparty.findingtrashpanda;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.cast.CastRemoteDisplayLocalService;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

/**
 * Created by stephenagee on 3/14/18.
 */

public class MainFragment extends AuthFrag implements OnMapReadyCallback{
    private Button mFoundButton;
    private FloatingActionButton mPandaFab;
    private MapView mMapView;
    private GoogleMap mMap;
    private RecyclerView mRecyclerView;
    private PandaAdapter mAdapter;
    private Callbacks mCallbacks;

    private FirebaseDatabase db;
    private DatabaseReference dbRef;

    private List<Panda> pandas;

    public interface Callbacks {
        void onPandaSelected(String s);
    }

    public static MainFragment newInstance(){
        return new MainFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        db = FirebaseDatabase.getInstance();
        dbRef = db.getReference();

        pandas = new ArrayList<Panda>();

        //PollService.setServiceAlarm(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, view, false);

        mMapView = (MapView) v.findViewById(R.id.map_view);
        mMapView.onCreate(savedInstanceState);

        mMapView.getMapAsync(this);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.panda_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        // TODO: Grab from firebase
        List<String> dummies = new ArrayList<String>();
        dummies.add("Steve;Hiding");
        dummies.add("Zelda;Hiding");
        dummies.add("Josh;Found");
        dummies.add("Chi;Found");
        dummies.add("Gustavo;Hiding");
        dummies.add("Morty;Hiding");
        dummies.add("Summer;Hiding");

        mAdapter = new PandaAdapter(dummies);
        mRecyclerView.setAdapter(mAdapter);

        dbRef.child("pandas").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.i("ohai", dataSnapshot.toString());

                pandas.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    Panda p = d.getValue(Panda.class);
                    pandas.add(p);
                    Log.i("ohai", String.format("Hi! I'm %s, I'm currently %s", p.name, p.state));
                }

                Log.i("ohai", String.format("I'm now aware of %d pandas", pandas.size()));
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("ohai", "Oops, something blew up");
                Log.e("ohai", databaseError.toString());
            }
        });

        return v;
    }

    public void drawCircle(LatLng point) {
        Random rand = new Random();
        double randLat = rand.nextDouble() * 0.002 + 0.0001;
        double randLon = rand.nextDouble() * 0.002 + 0.0001;
        int randOp = rand.nextInt(100) + 1;

        if (randOp < 25) {
            point = new LatLng(point.latitude - randLat, point.longitude - randLon);
        } else if (randOp > 25 && randOp < 50) {
            point = new LatLng(point.latitude + randLat, point.longitude - randLon);
        } else if (randOp > 50 && randOp < 75) {
            point = new LatLng(point.latitude - randLat, point.longitude + randLon);
        } else if (randOp > 75) {
            point = new LatLng(point.latitude + randLat, point.longitude + randLon);
        }

        CircleOptions circleOptions = new CircleOptions();

        circleOptions.center(point);
        circleOptions.radius(300);
        circleOptions.strokeColor(Color.BLACK);
        circleOptions.fillColor(0x30ff0000);
        circleOptions.strokeWidth(2);
        mMap.addCircle(circleOptions);
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        mMap = googleMap;
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        try {
            mMap.setMyLocationEnabled(true);
        } catch (SecurityException e){
            e.printStackTrace();
        }
        drawCircle(new LatLng(39.723, -105.14));
    }

    @Override
    public void onResume(){
        super.onResume();
        mMapView.onResume();
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

    private class PandaHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private ImageView mPandaIcon;
        private TextView mPandaName;
        private TextView mPandaStatus;

        // TODO: Change to actual class
        private Panda panda;

        public PandaHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_panda, parent, false));
            itemView.setOnClickListener(this);

            mPandaIcon = (ImageView) itemView.findViewById(R.id.panda_icon);
            mPandaName = (TextView) itemView.findViewById(R.id.panda_name);
            mPandaStatus = (TextView) itemView.findViewById(R.id.panda_status);
        }

        // TODO: Change bind to take in a panda obj
        public void bind(Panda p) {

            panda = p;
            mPandaName.setText(panda.name);
            mPandaStatus.setText(panda.state);
        }

        @Override
        public void onClick(View v) {
            //mCallbacks.onPandaSelected(panda);
            Intent detail = PandaDetail.newIntent(getContext(), panda.name);
            startActivity(detail);
        }
    }

    private class PandaAdapter extends RecyclerView.Adapter<PandaHolder> {
        // TODO: Change to Panda
        //private List<String> mPandas;

        public PandaAdapter(List<String> pandas) {
            //mPandas = pandas;
        }

        @Override
        public PandaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new PandaHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PandaHolder holder, int pos) {
            Panda p = pandas.get(pos);
            holder.bind(p);
        }

        @Override
        public int getItemCount() {
            return pandas.size();
        }
    }
}
