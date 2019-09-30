package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/4/2015.
 */
public class Lecture_Theatres_Data_Provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> lecture_details = new HashMap<String,List<String>>();

        List<String> Sci_department = new ArrayList<String>();
        Sci_department.add("Science Lecture Theater");
        Sci_department.add("Pre Clinical Theater1");
        Sci_department.add("Inter-faculty Lecture Theater");
        Sci_department.add("Biology Lecture Theater");
        Sci_department.add("Chemistry Physics Lecture Theater");
        Sci_department.add("Chemistry Lecture Theater 5");

        List<String> Soci_department = new ArrayList<String>();
        Soci_department.add("Social Science Lecture Theater");
        Soci_department.add("GLT 1");
        Soci_department.add("GLT 2");
        Soci_department.add("GLT 3");
        Soci_department.add("SR  1 - 23");
        Soci_department.add("Psychology Unit");


        List<String> Human_department = new ArrayList<String>();
        Human_department.add("N 1");
        Human_department.add("N 2");
        Human_department.add("N 3");
        Human_department.add("N 4");
        Human_department.add("NELT");
        Human_department.add("New Arts Block");
        Human_department.add("School of Education Lecture Theater");

        List<String> Med_Sciences_department = new ArrayList<String>();
        Med_Sciences_department.add("Life Science Main Office");
        Med_Sciences_department.add("Life Science Lecture Theater1");
        Med_Sciences_department.add("Life Science Lecture Theater2");
        Med_Sciences_department.add("Life Science Lecture Theater3");
        Med_Sciences_department.add("Life Science Lecture Theater4");
        Med_Sciences_department.add("Life Science Lecture Theater5");

        List<String> Law_department = new ArrayList<String>();
        Law_department.add("Math Main Office");
        Law_department.add("Lecture Room 1A");
        Law_department.add("Lecture Room 2B");
        Law_department.add("Lecture Room 1");
        Law_department.add("Lecture Room 2");
        Law_department.add("Lecture Room 3");


        lecture_details.put("Science and Tech Theaters", Sci_department);
        lecture_details.put("Social Science Theaters", Soci_department);
        lecture_details.put("Humanities Theaters", Human_department);
        lecture_details.put("Medical Science Theaters", Med_Sciences_department);
        lecture_details.put("Law Theaters", Law_department);


        return lecture_details;
    }
}
