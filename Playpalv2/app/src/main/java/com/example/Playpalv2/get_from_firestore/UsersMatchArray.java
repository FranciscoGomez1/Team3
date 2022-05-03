package com.example.Playpalv2.get_from_firestore;

import com.google.firebase.firestore.auth.User;

import java.util.List;

public class UsersMatchArray {
    private List<String> users;

    public UsersMatchArray() {
    }

    public UsersMatchArray(List<String> users) {
        this.users = users;
    }

    public List<String> getUsers() {
        return users;
    }

    public void setUsers(List<String> users) {
        this.users = users;
    }

    public String getMatchee(String userId){
        if (userId.equals(users.get(0))){
            return users.get(1);
        }else{
            return users.get(0);
        }
    }
}
