package com.gabrielmartinmoran.ourcraft.ourcraft_2.playerdata;

import com.google.gson.Gson;

public class PlayerData {
    public int level;
    public int totalXP;

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    public static PlayerData fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, PlayerData.class);
    }
}
