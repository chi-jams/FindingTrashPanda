package com.fractalteaparty.findingtrashpanda;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.android.gms.maps.MapView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by stephenagee on 3/15/18.
 */

public class LeaderboardFragment extends Fragment {
    private List<Player> players;
    private RecyclerView mRecyclerView;
    private LeaderboardAdapter mAdapter;

    private class Player {
        private String userName;
        private int score;
        private String realName;

        Player(){
            //give the player a random username
            Random rand = new Random();
            Integer randNum = rand.nextInt(100);
            userName = "User " + randNum.toString();
            //give the player a score
            score = rand.nextInt();
            //give the player a real name
            realName = "Real Name " + randNum.toString();
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getRealName() {
            return realName;
        }

        public void setRealName(String realName) {
            this.realName = realName;
        }
    }


    public static LeaderboardFragment newInstance(){
        return new LeaderboardFragment();
    }


    //TODO: Get these from a database
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        players = new ArrayList<Player>();
        for (Integer i = 0; i < 10; i++){
            players.add(new Player());
        }
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
        mAdapter = new LeaderboardAdapter(players);
        mRecyclerView.setAdapter(mAdapter);
    }

    private class LeaderboardHolder extends RecyclerView.ViewHolder{
        private TextView mPlayernameTV;
        private TextView mUserNameTV;
        public LeaderboardHolder(View itemView){
            super(itemView);
            mPlayernameTV = (TextView) itemView.findViewById(R.id.player_name_tv);
            mUserNameTV = (TextView) itemView.findViewById(R.id.player_username_tv);

        }
        public void bind(Player player){
            mPlayernameTV.setText(player.getRealName());
            mUserNameTV.setText(player.getUserName());
        }
    }

    private class LeaderboardAdapter extends RecyclerView.Adapter<LeaderboardHolder>{
        private List<Player> mPlayers;

        public LeaderboardAdapter(List<Player> players){
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
