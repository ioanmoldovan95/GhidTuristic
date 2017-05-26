package com.example.john.ghidturistic.Models;

/**
 * Created by John on 3/31/2017.
 */

public class User {

    private String name;
    private int type;
    private String email;

    public User(){

    }

    public User(String email){
        this.name=email;
        this.email=email;
    }

    public User(String name, String email){
        this.name=name;
        this.email=email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
