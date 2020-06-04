package com.stevecrossin.mindlab;
import android.location.Address;

import java.util.ArrayList;

public class User {

    private String username;
    private String email;
    private String password;
    private String HeartRate;
    private String BloodPressure;
    private String address;
    private ArrayList<Event> events = new ArrayList<>();

    public User() {
        //Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String password, String HeartRate, String BloodPressure, String address) {

        this.username = username;
        this.email = email;
        this.password = password;
        this.HeartRate = HeartRate;
        this.BloodPressure = BloodPressure;
        this.address = address;
    }

    public User(String username, String email, String password, String address) {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeartRate() {
        return HeartRate;
    }

    public void setHeartRate(String HeartRate) {
        this.HeartRate = HeartRate;
    }

    public String getBloodPressure() {
        return BloodPressure;
    }

    public void setBloodPressure(String BloodPressure) {
        this.BloodPressure = BloodPressure;
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
