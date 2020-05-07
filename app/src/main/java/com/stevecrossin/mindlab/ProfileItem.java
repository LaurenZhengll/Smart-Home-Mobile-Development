package com.stevecrossin.mindlab;

public class ProfileItem {
    private int id;
    private String profileQues, profileInfo;

    //Constructor
    public ProfileItem(int id, String profileQues, String profileInfo) {
        this.id = id;
        this.profileQues = profileQues;
        this.profileInfo = profileInfo;
    }

    //Setter & Getter Methods
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getProfileQues() {
        return profileQues;
    }

    public void setProfileQues(String profileQues) {
        this.profileQues = profileQues;
    }

    public String getProfileInfo() {
        return profileInfo;
    }

    public void setProfileInfo(String profileInfo) {
        this.profileInfo = profileInfo;
    }
}
