package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/3/2015.
 */
public class Law_Data_Provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> law_details = new HashMap<String,List<String>>();

        List<String> Law = new ArrayList<String>();
        Law.add("Law Faculty Main Office");
        Law.add("Lecture Room 1A");
        Law.add("Lecture Room 2B");
        Law.add("Lecture Room 1");
        Law.add("Lecture Room 2");
        Law.add("Lecture Room 3");
        Law.add("Seminar Room 1");
        Law.add("Seminar Room 2");



        law_details.put("Law", Law);


        return law_details;
    }
}
