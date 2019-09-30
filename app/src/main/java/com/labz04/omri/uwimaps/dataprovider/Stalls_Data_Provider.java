package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/3/2015.
 */
public class Stalls_Data_Provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> stalls_details = new HashMap<String,List<String>>();

        List<String> Stall_department = new ArrayList<String>();
        Stall_department.add("Main Library Stall");
        Stall_department.add("Humanities Stall");
        Stall_department.add("Social Science Stall");
        Stall_department.add("Science and Tech Stall 1");
        Stall_department.add("Science and Tech Stall 2");


        stalls_details.put("Campus Stalls", Stall_department);


        return stalls_details;
    }
}
