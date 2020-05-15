package com.example.filereader;

public class User {
    private String password;
    private String username;
    private String email;

    public User() {
    }

    public User(String username, String password, String email) {
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
}



