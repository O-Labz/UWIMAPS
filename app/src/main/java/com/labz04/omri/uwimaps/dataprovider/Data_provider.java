package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/1/2015.
 */
public class Data_provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> faculty_details = new HashMap<String,List<String>>();

        List<String> Chemistry_department = new ArrayList<String>();
        Chemistry_department.add("Chemistry Main Office");
        Chemistry_department.add("Chemistry Lecture Theater 1");
        Chemistry_department.add("Chemistry Lecture Theater 2");
        Chemistry_department.add("Chemistry Lecture Theater 3");
        Chemistry_department.add("Chemistry Lecture Theater 4");
        Chemistry_department.add("Chemistry Lecture Theater 5");
        Chemistry_department.add("Chemistry Lecture Theater 6");
        Chemistry_department.add("Chemistry Lecture Theater 7");
        Chemistry_department.add("Chemistry Physics Lecture Theater");

        List<String> Computing_department = new ArrayList<String>();
        Computing_department.add("Computing Main Office");
        Computing_department.add("Computing Lecture Room");
        Computing_department.add("CR1");
        Computing_department.add("CR2");
        Computing_department.add("Lab1");
        Computing_department.add("Lab2");
        Computing_department.add("Software Engineering Lab");

        List<String> Geography_department = new ArrayList<String>();
        Geography_department.add("Room 1");
        Geography_department.add("Room 2");
        Geography_department.add("Room 3");
        Geography_department.add("Room 4");
        Geography_department.add("Room 5");
        Geography_department.add("Room 6");
        Geography_department.add("Museum");
        Geography_department.add("Marine Geology Unit");
        Geography_department.add("Lab 1");
        Geography_department.add("Lab 2");
        Geography_department.add("Lab 3");

        List<String> Life_Sciences_department = new ArrayList<String>();
        Life_Sciences_department.add("Life Science Main Office");
        Life_Sciences_department.add("Life Science Lecture Theater 1");
        Life_Sciences_department.add("Life Science Lecture Theater 2");
        Life_Sciences_department.add("Life Science Lecture Theater 3");
        Life_Sciences_department.add("Life Science Lecture Theater 4");
        Life_Sciences_department.add("Life Science Lecture Theater 5");
        Life_Sciences_department.add("Aquatic Sciences Lab");
        Life_Sciences_department.add("Block A");
        Life_Sciences_department.add("Block B");
        Life_Sciences_department.add("Block C");
        Life_Sciences_department.add("Block D");
        Life_Sciences_department.add("Block E");
        
        List<String> Math_department = new ArrayList<String>();
        Math_department.add("Math Main Office");
        Math_department.add("Math Lecture theater 1");
        Math_department.add("Math Lecture theater 2");
        Math_department.add("Math Lecture theater 3");
        Math_department.add("Math Lab");
        Math_department.add("Postgrad Room 1");
        Math_department.add("Postgrad Room 2");
        Math_department.add("Postgrad Room 3");

        List<String> Physics_department = new ArrayList<String>();
        Physics_department.add("Physics Main Office");
        Physics_department.add("Phys A");
        Physics_department.add("Phys B");
        Physics_department.add("Phys C");
        Physics_department.add("Phys D");
        Physics_department.add("Electronics Lab");
        Physics_department.add("Virtual Lab");
        Physics_department.add("Earthquake Unit");
        Physics_department.add("Electronics Unit");

        List<String> Engineering_department = new ArrayList<String>();
        Engineering_department.add("Engineering Main Office");
        Engineering_department.add("Engineering Lecture Theater 1");
        Engineering_department.add("Engineering Lecture Theater 2");
        Engineering_department.add("Engineering Lecture Theater 3");
        Engineering_department.add("Engineering Lecture Theater 4");
        Engineering_department.add("Engineering Lecture Theater 5");
        Engineering_department.add("Electronics Engineering Lab");

        faculty_details.put("Chemistry Department", Chemistry_department);
        faculty_details.put("Computing Department",Computing_department);
        faculty_details.put("Geography Department",Geography_department);
        faculty_details.put("Life Sciences Department",Life_Sciences_department);
        faculty_details.put("Math Department",Math_department);
        faculty_details.put("Physics Department",Physics_department);
        faculty_details.put("Engineering Department",Engineering_department);

        return faculty_details;
    }
}
