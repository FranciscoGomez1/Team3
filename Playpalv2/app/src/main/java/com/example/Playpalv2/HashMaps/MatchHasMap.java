package com.example.Playpalv2.HashMaps;

import java.util.HashMap;
import java.util.List;


public class MatchHasMap {
    private HashMap<String, Object> users = new HashMap<>();

    public MatchHasMap() {
    }
    public  void addTopMap(List<String> inUsers){
        users.put("users", inUsers);
    }

    public HashMap<String, Object> getMap(){
        return users;
    }
}
