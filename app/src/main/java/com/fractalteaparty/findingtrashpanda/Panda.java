package com.fractalteaparty.findingtrashpanda;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by daichij on 5/5/18.
 */

@IgnoreExtraProperties
public class Panda {
    public String name;
    public String state;
    public String hidden_life;
    public double lat, lon;
    public String uid_current_owner;
    public long date_hidden;

    public Panda() {

    }

    public Panda(String name, String state, String hidden_life) {
        this.name = name;
        this.state = state;
        this.hidden_life = hidden_life;
        uid_current_owner = "";
    }


}
