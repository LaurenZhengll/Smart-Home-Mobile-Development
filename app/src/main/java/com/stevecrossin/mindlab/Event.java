package com.stevecrossin.mindlab;


import java.util.ArrayList;

public class Event {
    private String date;
    private String alert_type;
    ArrayList<Coordinates> cdlist = new ArrayList<>();

    public Event()
    {

    }

    public Event(String date, String alert_type, ArrayList<Coordinates> coordinates)
    {
        this.alert_type= alert_type;
        this.date= date;
        cdlist =coordinates;

    }

    public String getDate()
{
    return date;
}

    public  String getAlert_type()
    {
        return alert_type;
    }


    public void setAlert_type(String alert_type) {
        this.alert_type = alert_type;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public  void AddCoordinates(String x, String y)
    {
    Coordinates coordinates = new Coordinates(x,y);
    cdlist.add(coordinates);

    }

    public ArrayList<Coordinates> getCoordinates(){
        return cdlist;
    }

}
