package com.fractalteaparty.findingtrashpanda;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;

/**
 * Created by stephenagee on 3/14/18.
 */

public class MainFragment extends Fragment implements OnMapReadyCallback{

    private MapView mMapView;
    private GoogleMap googleMap;
    private RecyclerView mRecyclerView;
    private PandaAdapter mAdapter;
    private Callbacks mCallbacks;

    public interface Callbacks {
        void onPandaSelected(String s);
    }

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

        mRecyclerView = (RecyclerView) v.findViewById(R.id.panda_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // TODO: Grab from firebase
        List<String> pandas = new ArrayList<String>();
        pandas.add("Steve;Hiding");
        pandas.add("Zelda;Hiding");
        pandas.add("Josh;Found");
        pandas.add("Chi;Found");
        pandas.add("Gustavo;Hiding");

        mAdapter = new PandaAdapter(pandas);
        mRecyclerView.setAdapter(mAdapter);

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

    private class PandaHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private ImageView mPandaIcon;
        private TextView mPandaName;
        private TextView mPandaStatus;

        // TODO: Change to actual class
        private String panda;

        public PandaHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_panda, parent, false));
            itemView.setOnClickListener(this);

            mPandaIcon = (ImageView) itemView.findViewById(R.id.panda_icon);
            mPandaName = (TextView) itemView.findViewById(R.id.panda_name);
            mPandaStatus = (TextView) itemView.findViewById(R.id.panda_status);
        }

        // TODO: Change bind to take in a panda obj
        public void bind(String s) {
            String info[] = s.split(";");

            panda = s;
            mPandaName.setText(info[0]);
            mPandaStatus.setText(info[1]);
        }

        @Override
        public void onClick(View v) {
            //mCallbacks.onPandaSelected(panda);
            Log.d("pantrack", panda);
            Intent detail = PandaDetail.newIntent(getContext(), panda);
            startActivity(detail);
        }
    }

    private class PandaAdapter extends RecyclerView.Adapter<PandaHolder> {
        // TODO: Change to Panda
        private List<String> mPandas;

        public PandaAdapter(List<String> pandas) {
            mPandas = pandas;
        }

        @Override
        public PandaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            return new PandaHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(PandaHolder holder, int pos) {
            String panda = mPandas.get(pos);
            holder.bind(panda);
        }

        @Override
        public int getItemCount() {
            return mPandas.size();
        }
    }
}
