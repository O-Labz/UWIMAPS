package com.labz04.omri.uwimaps.fragments;

import android.app.AlertDialog;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.labz04.omri.uwimaps.adapter.DataAdapter;
import com.labz04.omri.uwimaps.comparator.PointWithDistance;
import com.labz04.omri.uwimaps.database.LocationDatabase;
import com.labz04.omri.uwimaps.dataprovider.Halls_Data_Provider;
import com.labz04.omri.uwimaps.gps.GPS_MODULE;
import com.labz04.omri.uwimaps.shortestpath.Dijkstra;
import com.labz04.omri.uwimaps.shortestpath.Graph;
import com.labz04.omri.uwimaps.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Omri on 6/1/2015.
 */
public class HallFragment extends Fragment
{
    HashMap<String,List<String>> faculty_category;
    List<PointWithDistance> helperList= new ArrayList<>();

    List<String> department_list;
    DataAdapter adapter;
    ExpandableListView Exp_list;
    double DIstance,Distance;

    Dijkstra find = new Dijkstra();
    Graph g = new Graph(find.GRAPH);
    MapFragment yes = new MapFragment();


    GPS_MODULE gps;
    public String Min;
    String dne;

    public String START = Min;
    public String END = dne;

    LocationDatabase myDB;


    public HallFragment()
    {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        faculty_category = Halls_Data_Provider.get_info();
        department_list = new ArrayList<String>(faculty_category.keySet());
        adapter =  new DataAdapter(getActivity(),faculty_category,department_list);

        View row = inflater.inflate(R.layout.fragment_halls, container, false);
        ExpandableListView Exp_list = (ExpandableListView) row.findViewById(R.id.exp_list_hall);
        Exp_list.setAdapter(adapter);


        Exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            private double distance(LatLng current, LatLng last) {
                if (last == null)
                    return 0;
                Location cL = new Location("");
                cL.setLatitude(current.latitude);
                cL.setLongitude(current.longitude);

                Location lL = new Location("");
                lL.setLatitude(last.latitude);
                lL.setLongitude(last.longitude);

                DIstance = (double) lL.distanceTo(cL);

                return DIstance;
            }


            // This class finds the closest node in the map to your current location.
            class DistanceComparator implements Comparator<PointWithDistance> {
                @Override
                public int compare(PointWithDistance lhs, PointWithDistance rhs) {
//                    double dis1 = lhs.getDistance();
//                    double dis2 = rhs.getDistance();
//                    if ( dis1 > dis2 )
//                    {
//                        return 1;
//                    }
//                    else if ( dis1 < dis2 )
//                    {
//                        return -1;
//                    }
//                    return 0;
                    return (lhs.getDistance() < rhs.getDistance() ? -1 : (lhs.getDistance() == rhs.getDistance() ? 0 : 1));
                }

            }

            public void closestNode() {
                double latitude = gps.getLatitude();
                double longitude = gps.getLongitude();
                LocationDatabase myDB = new LocationDatabase(getActivity());
                Cursor CSR = myDB.get_Data(myDB);
                CSR.moveToFirst();
                LatLng current = new LatLng(latitude, longitude);
                do {
                    CSR.getString(0);
                    CSR.getDouble(1);
                    CSR.getDouble(2);
                    double Dlat = CSR.getDouble(1);
                    double Dlong = CSR.getDouble(2);
                    Location cLoc = new Location("");
                    cLoc.setLatitude(current.latitude);
                    cLoc.setLongitude(current.longitude);

                    Location lLoc = new Location("");
                    lLoc.setLatitude(Dlat);
                    lLoc.setLongitude(Dlong);

                    Distance = (double) lLoc.distanceTo(cLoc);
                    PointWithDistance helper = new PointWithDistance(CSR.getString(0), Dlat, Dlong, Distance);
                    helperList.add(helper);
                    // Log.i("values", helper.getname()); //shows list content in log


                }
                while (CSR.moveToNext());
                Collections.sort(helperList, new DistanceComparator());
                // Since list is sorted above according to shortest distance based on your current location
                // Min is the item that is at the top of the list which is also the closest place to where you are
                Min = helperList.get(0).getname();
//                // Loop through elements of the list to see what is being added into the list.
//                for (int i = 0; i < helperList.size(); i++) {
//                    Object value = helperList.get(i).getDistance();
//                    System.out.println("Element: " + value);
//                }

                //Toast.makeText(getActivity(), "name:" + Min, Toast.LENGTH_SHORT).show();


            }

            //Show All Data.....this function is used to see the contents of the database
            public void Showmessage(String title, String message) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(true);
                builder.setTitle(title);
                builder.setMessage(message);
                builder.show();
            }

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String child_name = faculty_category.get(department_list.get(groupPosition)).get(childPosition);
                dne = child_name;
                gps = new GPS_MODULE(getActivity());
                closestNode();
                if (gps.canGetLocation()) {
                    double latitude = gps.getLatitude();
                    double longitude = gps.getLongitude();
                    LatLng current = new LatLng(latitude, longitude);

                    LocationDatabase myDB = new LocationDatabase(getActivity());
                    Cursor CSR = myDB.get_Data(myDB);
                    CSR.moveToFirst();
                    boolean mapping_stat = false;
                    // Loop through elements of the list to see what is being added into the list.

                    do {
                        //If the user's name equals to the string in its respective column
                        if (Min.equals(CSR.getString(0))) {
                            mapping_stat = true;
                            FragmentManager fragmentManager = getFragmentManager();
                            //yes.one = CSR.getDouble(1);
                            //yes.two = CSR.getDouble(2);
                            Double Dlat = CSR.getDouble(1);
                            Double Dlong = CSR.getDouble(2);
                            LatLng destin = new LatLng(Dlat, Dlong);
                            g.dijkstra(Min);
                            g.printPath(child_name);
                            for (int hold = 0; hold < g.patth.size(); hold++)
                            {
                                String value = g.patth.get(hold).toString();
                                yes.come.add(value);
                                System.out.println("Element: " + value);
                            }
                            yes.getActivity();
                            distance(current, destin);
                            fragmentManager.beginTransaction().replace(R.id.content_only, yes).commit();
                        }
                    }
                    //Loops through just incase there is more than one information in the database
                    while (CSR.moveToNext());
                    //If search is successful
                    if (mapping_stat) {
                        //Toast.makeText(getActivity(), String.valueOf(g.patth.size()), Toast.LENGTH_LONG).show();
                        Showmessage("Take This Path", g.buffer.toString());
                        Toast.makeText(getActivity(), "Ok lets go to: " + child_name + "\nYour Location is -\nLat: " + latitude + "\nLong: " + longitude + "\nDistance: " + DIstance, Toast.LENGTH_LONG).show();
//                        CSR.close();
                    } else {
                        Toast.makeText(getActivity(), "Not Yet in Database", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }

        });
        return row;
    }
}
