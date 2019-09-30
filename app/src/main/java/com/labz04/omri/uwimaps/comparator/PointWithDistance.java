package com.labz04.omri.uwimaps.comparator;

import com.labz04.omri.uwimaps.fragments.BuildingsFragment;

/**
 * Created by Omri on 7/4/2015.
 */
public class PointWithDistance
{
    BuildingsFragment me;
    private String name;
    private double Latitude;
    private double Longitude;
    private double distance;

    public PointWithDistance(String name, double Latitude, double Longitude, double distance)
    {
        this.name = name;
        this.Latitude = Latitude;
        this.Longitude = Longitude;
        this.distance = distance;

    }

    public String getname() {
        return name;
    }


    public double getlat() {
        return Latitude;
    }

    public double getlong() {
        return Longitude;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }


}
