package com.fractalteaparty.findingtrashpanda;

import com.google.android.gms.games.Player;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by daichij on 5/7/18.
 */

public class User implements Comparable<User>{
    public int points;
    public int num_finds;
    public Map<String, Integer> panda_finds;
    public String cur_panda;

    public User() {
        points = 0;
        num_finds = 0;
        panda_finds = new TreeMap<>();
        cur_panda = null;
    }

    public int compareTo(User u)
    {
        return(u.points - this.points);
    }
}
