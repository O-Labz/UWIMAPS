package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/3/2015.
 */
public class Hum_Data_Provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> hum_details = new HashMap<String,List<String>>();

        List<String> Hum = new ArrayList<String>();
        Hum.add("Humanities Faculty Main Office");
        Hum.add("N 1");
        Hum.add("N 2");
        Hum.add("N 3");
        Hum.add("N 4");
        Hum.add("NELT");
        Hum.add("Carimac");
        Hum.add("History and Archeology");
        Hum.add("Caribbean Studies");
        Hum.add("English Lit");
        Hum.add("School of Education");
        Hum.add("Modern Language and literature");
        Hum.add("Library and Information Studies");
        Hum.add("School of Education Lecture Theater");






        hum_details.put("Humanities Department", Hum);


        return hum_details;
    }
}
