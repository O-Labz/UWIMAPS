package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by omri on 2/19/16.
 */
public class Banks_Data_Provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> bank_details = new HashMap<String,List<String>>();

        List<String> Banks = new ArrayList<String>();
        Banks.add("National Commercial Bank");
        Banks.add("Bank of Nova Scotia");
        Banks.add("Jamaica National");
        Banks.add("U.W.I Credit Union");



        bank_details.put("Campus Banks", Banks);

        return bank_details;
    }
}
