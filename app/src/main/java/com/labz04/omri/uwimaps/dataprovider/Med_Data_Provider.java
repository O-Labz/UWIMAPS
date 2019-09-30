package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/3/2015.
 */
public class Med_Data_Provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> med_details = new HashMap<String,List<String>>();

        List<String> Chemistry_department = new ArrayList<String>();
        Chemistry_department.add("Med Faculty Main Office");
        Chemistry_department.add("Child and Adolescents");
        Chemistry_department.add("Community Health Psychiatry");
        Chemistry_department.add("Medicine");
        Chemistry_department.add("Micro Biology");
        Chemistry_department.add("Surgery");
        Chemistry_department.add("School of Nursing");
        Chemistry_department.add("UWI Health Center");
        Chemistry_department.add("University Hospital");
        Chemistry_department.add("Radiology");
        Chemistry_department.add("Anaesthesia");
        Chemistry_department.add("Intensive Care");
        Chemistry_department.add("Obstetrics Gynaecology");

        med_details.put("Medical Science", Chemistry_department);


        return med_details;
    }
}
