package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/3/2015.
 */
public class Rest_Data_Provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> rest_details = new HashMap<String,List<String>>();

        List<String> eats = new ArrayList<String>();
        eats.add("Kentucky Fried Chicken");
        eats.add("The Spot");
        eats.add("Yao");
        eats.add("Juici Patties");
        eats.add("Pages Cafe'");
        eats.add("BeeHive");
        eats.add("Dukunoo Deli");
        eats.add("Bucks");

        rest_details.put("Campus Restaurants", eats);


        return rest_details;
    }
}
