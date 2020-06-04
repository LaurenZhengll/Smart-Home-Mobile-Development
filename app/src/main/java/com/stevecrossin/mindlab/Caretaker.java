package com.stevecrossin.mindlab;

import com.stevecrossin.mindlab.User;

public class Caretaker extends User {

    private String givenname;
    private String familyname;
  private   String dob;
   private String contactnumber;
    private String  occupancy;
    private String Institution;
    private  String state;


    public Caretaker(String username, String email, String password, String address,String State, String givenname, String familyname, String dob, String contactnumber, String occupancy, String institution) {
        super(username, email, password, address);
        this.givenname = givenname;
        this.familyname = familyname;
        this.dob = dob;
        this.contactnumber = contactnumber;
        this.occupancy = occupancy;
        this.Institution = institution;
        this.state = State;
    }


    public String getGivenname() {
        return givenname;
    }

    public String getFamilyname() {
        return familyname;
    }

    public String getDob() {
        return dob;
    }

    public String getContactnumber() {
        return contactnumber;
    }

    public String getOccupancy() {
        return occupancy;
    }

    public String getInstitution() {
        return Institution;
    }


    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setContactnumber(String contactnumber) {
        this.contactnumber = contactnumber;
    }

    public void setOccupancy(String occupancy) {
        this.occupancy = occupancy;
    }

    public void setInstitution(String institution) {
        Institution = institution;
    }







}





