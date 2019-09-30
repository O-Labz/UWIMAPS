package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/4/2015.
 */
public class Libraries_Data_Provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> lib_details = new HashMap<String,List<String>>();

        List<String> Library = new ArrayList<String>();
        Library.add("Main Library");
        Library.add("Science Library");
        Library.add("Social Science Library");
        Library.add("Med Library");
        Library.add("Law Library");
        Library.add("Old Library");

        lib_details.put("Library List", Library);


        return lib_details;
    }
}
