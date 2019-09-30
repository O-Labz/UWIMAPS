package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/4/2015.
 */
public class Buildings_Data_Provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> buildings_details = new HashMap<String,List<String>>();

        List<String> Buildings = new ArrayList<String>();
        Buildings.add("Student Administration Services");
        Buildings.add("UWI Health Center");
        Buildings.add("University Hospital");
        Buildings.add("Mona Information Technology Services");
        Buildings.add("Placement & Careers");
        Buildings.add("U.W.I Chapel");
        Buildings.add("Book Shop");
        Buildings.add("National Commercial Bank");
        Buildings.add("Bank of Nova Scotia ");
        Buildings.add("Assembly Hall");
        Buildings.add("Students Union");
        Buildings.add("Bursary");
        Buildings.add("Lodgings ");
        Buildings.add("News Talk");
        Buildings.add("Phillip Sherlock");
        Buildings.add("CARIMAC");
        Buildings.add("Office of Student Services and Development");
        Buildings.add("Commuting Students Lounge");


        buildings_details.put("Important Buildings", Buildings);

        return buildings_details;
    }
}
