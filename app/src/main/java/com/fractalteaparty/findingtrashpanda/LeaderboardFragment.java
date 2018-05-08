package com.fractalteaparty.findingtrashpanda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.games.Player;
import com.google.android.gms.maps.MapView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by stephenagee on 3/15/18.
 */

public class LeaderboardFragment extends AuthFrag {
    private RecyclerView mRecyclerView;
    private LeaderboardAdapter mAdapter;
    private List<User> mListUsers;
    private DatabaseReference mUsers;



    public static LeaderboardFragment newInstance(){
        return new LeaderboardFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mListUsers = new ArrayList<>();
        mUsers = db.getReference().getRef().child("users");

        mUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mListUsers.clear();
                for (DataSnapshot d : dataSnapshot.getChildren()){
                    mListUsers.add(d.getValue(User.class));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        if (mListUsers.isEmpty()){
            Log.e("Heyo", "this is not here why");
        }
        for (User u : mListUsers){
            Log.i("stuff", u.name);
        }

        Collections.sort(mListUsers);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup view, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_leaderboard, view, false);
        mRecyclerView = (RecyclerView) v.findViewById(R.id.leaderboard_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    private void updateUI(){
        mAdapter = new LeaderboardAdapter(mListUsers);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class LeaderboardHolder extends RecyclerView.ViewHolder{
        private TextView mUserNameTV;
        private TextView mUserScoreTV;
        public LeaderboardHolder(View itemView){
            super(itemView);
            mUserNameTV = (TextView) itemView.findViewById(R.id.player_username_tv);
            mUserScoreTV = (TextView) itemView.findViewById(R.id.leaderboard_score_tv);
        }
        public void bind(User player){
            mUserNameTV.setText(player.name);
            mUserScoreTV.setText(Integer.toString(player.points));
        }
    }

    private class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardHolder>{
        private List<User> mPlayers;

        public LeaderboardAdapter(List<User> players){
            mPlayers = players;
        }

        @Override
        public LeaderboardHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.leaderboard_list_element, parent, false);
            return new LeaderboardHolder(view);
        }

        @Override
        public void onBindViewHolder(LeaderboardHolder holder, int position) {
            holder.bind(mPlayers.get(position));
        }

        @Override
        public int getItemCount() {
            return mPlayers.size();
        }
    }
}
