package com.stevecrossin.mindlab;

public class ProfileItem {
    private int id;
    private String profileQues, profileInfo;

    public ProfileItem(int id, String profileQues, String profileInfo) {
        this.id = id;
        this.profileQues = profileQues;
        this.profileInfo = profileInfo;
    }

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
