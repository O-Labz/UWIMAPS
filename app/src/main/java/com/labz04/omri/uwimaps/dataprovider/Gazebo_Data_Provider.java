package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/3/2015.
 */
public class Gazebo_Data_Provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> gazebo_details = new HashMap<String,List<String>>();

        List<String> Gaz = new ArrayList<String>();
        Gaz.add("Humanities Gazebos");
        Gaz.add("Nursing Gazebos");
        Gaz.add("Science and Tech Gazebos");
        Gaz.add("Law Gazebos");
        Gaz.add("Mona School of Business Gazebos");



        gazebo_details.put("Campus Gazebos", Gaz);

        return gazebo_details;
    }
}
