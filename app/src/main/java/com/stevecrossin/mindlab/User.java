package com.stevecrossin.mindlab;

import java.util.ArrayList;

public class User {
    private String password;
    private String username;
    private String email;
    private String  address;
    private ArrayList<Event> events = new ArrayList<>();

    public User() {
    }

    public User(String username, String email, String password, String address) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void getUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void  setEmail(String Email) {
        this.email = email;
    }

    public void setEvents(ArrayList<Event> events){
        this.events = events;
    }

    public void addEvent(Event event){
        if(events == null){
            events = new ArrayList<>();
        }
        events.add(event);
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}



