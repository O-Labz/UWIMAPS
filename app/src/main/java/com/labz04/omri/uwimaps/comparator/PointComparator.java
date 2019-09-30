package com.labz04.omri.uwimaps.comparator;

/**
 * Created by Omri on 7/4/2015.
 */
public class PointComparator //implements Comparator<Double>
{

    //@Override
    public int compare(PointWithDistance o1, PointWithDistance o2) {
        return (o1.getDistance()<o2.getDistance() ? -1 : (o1.getDistance()==o2.getDistance() ? 0 : 1));
    }
}
