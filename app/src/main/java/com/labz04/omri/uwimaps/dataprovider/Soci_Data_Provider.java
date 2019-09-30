package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/3/2015.
 */
public class Soci_Data_Provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> soci_details = new HashMap<String,List<String>>();

        List<String> Chemistry_department = new ArrayList<String>();
        Chemistry_department.add("Faculty Office");
        Chemistry_department.add("Hotel and Tourism");
        Chemistry_department.add("Economics");
        Chemistry_department.add("Government");
        Chemistry_department.add("Mona School of Business North");
        Chemistry_department.add("Mona School of Business South");
        Chemistry_department.add("Sociology and Social Work");
        Chemistry_department.add("Psychology Unit");
        Chemistry_department.add("Institute for Gender and Development");


        soci_details.put("Social Science", Chemistry_department);


        return soci_details;
    }
}
