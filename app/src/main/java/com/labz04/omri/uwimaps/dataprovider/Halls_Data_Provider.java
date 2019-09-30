package com.labz04.omri.uwimaps.dataprovider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/3/2015.
 */
public class Halls_Data_Provider
{
    public static HashMap<String, List<String>> get_info()
    {
        HashMap<String,List<String>> halls_details = new HashMap<String,List<String>>();

        List<String> abc = new ArrayList<String>();
        abc.add("ABC Hall Office");
        abc.add("ABC Block A");
        abc.add("ABC Block B");
        abc.add("ABC Block C");

        List<String> chancellor = new ArrayList<String>();
        chancellor.add("Chancellor Hall Office");
        chancellor.add("Block Aye");
        chancellor.add("Block Runci");
        chancellor.add("Block Che'");
        chancellor.add("Block Dynamite");
        chancellor.add("Block X");

        List<String> taylor = new ArrayList<String>();
        taylor.add("Taylor Hall Office");
        taylor.add("Block Attica");
        taylor.add("Block Butchers");
        taylor.add("Block Roosters");
        taylor.add("Block Stallion");
        taylor.add("Block G");

        List<String> towers = new ArrayList<String>();
        towers.add("Tower Hall Office");
        towers.add("Tower 1");
        towers.add("Tower 2");
        towers.add("Tower 3");
        towers.add("Tower 4");
        towers.add("Tower 5");

        List<String> seacole = new ArrayList<String>();
        seacole.add("Mary Seacole Hall Office");
        seacole.add("Jade Block");
        seacole.add("Great Block");
        seacole.add("Hype Block");
        seacole.add("Crowd Block");
        seacole.add("Seacole Block Dynamite");

        List<String> irvin = new ArrayList<String>();
        irvin.add("Irvin Hall Office");
        irvin.add("Block Alpha");
        irvin.add("Block Bears");
        irvin.add("Block Chalise");
        irvin.add("Freshers Block");

        List<String> rex = new ArrayList<String>();
        rex.add("Rex Hall Office");
        rex.add("Cluster 1");
        rex.add("Cluster 2");
        rex.add("Cluster 3");
        rex.add("Cluster 4");
        rex.add("Cluster 5");
        rex.add("Cluster 6");
        rex.add("Cluster 7");
        rex.add("Cluster 8");
        rex.add("Cluster 9");

        List<String> preston = new ArrayList<String>();
        preston.add("Preston Hall Office");
        preston.add("Cluster Amsterdam");
        preston.add("Cluster Belqique");
        preston.add("Cluster Burgplatz");
        preston.add("Cluster Einheit");
        preston.add("Cluster Italia");
        preston.add("Cluster Los Matadores");
        preston.add("Cluster Sherwood Manor");
        preston.add("Cluster Regensen");
        preston.add("Cluster Olympia");
        preston.add("Cluster La Maison");



        List<String> new_living = new ArrayList<String>();
        new_living.add("138 Office");
        new_living.add("T 1");
        new_living.add("T 2");
        new_living.add("T 3");


        List<String> post = new ArrayList<String>();
        post.add("New Post Grad");
        post.add("Old PostGrad");

        halls_details.put("ABC Hall", abc);
        halls_details.put("Chancellor Hall", chancellor);
        halls_details.put("Taylor Hall", taylor);
        halls_details.put("Elsa Leo-Rhynie Towers Hall", towers);
        halls_details.put("Mary Seacole Hall", seacole);
        halls_details.put("Irvin Hall", irvin);
        halls_details.put("Rex Nettleford Hall", rex);
        halls_details.put("Aston Preston Hall", preston);
        halls_details.put("138 Living", new_living);
        halls_details.put("Post Graduate", post);

        return halls_details;
    }
}
