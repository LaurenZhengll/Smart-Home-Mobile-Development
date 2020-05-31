package com.stevecrossin.mindlab;

public class Alert {
    private String Date;
    private String Time;
    private String Alert;
    private int X;
    private int Y;

    public Alert(){
    }

    public Alert(String Date, String Time,String Alert,int x,int y){
        this.Date = Date;
        this.Time = Time;
        this.Alert = Alert;
        this.X= X;
        this.X= Y;
    }

    public String getDate(){
        return Date;
    }
    public void getDate(String Date){
        this.Date = Date;
    }

    public String getTime(){
        return Time;
    }
    public void getTime(String Time){
        this.Time = Time;
    }

    public String getAlert(){
        return Alert;
    }

    public void getAlert(String Alert){
        this.Alert = Alert;
    }

    public int getX(){
        return X;
    }
    public void getX(int X){
        this.X = X;
    }

    public int getY(){
        return Y;
    }

    public void getY(int Y){
        this.Y = Y;
    }
}



