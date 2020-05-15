package com.example.filereader;

public class Coordinates {
    private  String CoordinateX;
    private String CoordinateY;

    public  Coordinates()
    {

    }

    public Coordinates(String x, String y)
    {
        this.CoordinateX = x;
        this.CoordinateY =y;


    }

    public String getCoordinateX() {
        return CoordinateX;
    }

    public String getCoordinateY() {
        return CoordinateY;
    }

    public void setCoordinateX(String coordinateX) {
        CoordinateX = coordinateX;
    }

    public void setCoordinateY(String coordinateY) {
        CoordinateY = coordinateY;
    }
}
