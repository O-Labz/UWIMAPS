package com.labz04.omri.uwimaps.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.labz04.omri.uwimaps.database.LocationDatabase;
import com.labz04.omri.uwimaps.gps.GPS_MODULE;
import com.labz04.omri.uwimaps.R;
import com.labz04.omri.uwimaps.uwimaps.MapChoice;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Omri on 6/11/2015.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback
{

    private String value;

    private SharedPreferences sharedPref1;

    public List<String> come = new ArrayList<String>();
    int type;
    public double onee ;
    public double twoe ;

    public double one ;
    public double two ;
    LatLng uwi = new LatLng(onee, twoe);

    public String name;

    public MapFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        FragmentTransaction trans = getFragmentManager().beginTransaction();
        SupportMapFragment mfrag = new SupportMapFragment();
        trans.add(R.id.content, mfrag).commit();
        mfrag.getMapAsync(this);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {

        View row = inflater.inflate(R.layout.fragment_map, container, false);

        return row;
    }


    @Override
    public void onMapReady(GoogleMap map)
    {
        GPS_MODULE current = new GPS_MODULE(getActivity());
        current.getLocation();
        double curone = current.getLatitude();
        double curtwo = current.getLongitude();

        LatLng now = new LatLng(curone, curtwo);
        LatLng uwi = new LatLng(18.005972, -76.746770);

        map.setMyLocationEnabled(true);


        LocationDatabase myDB = new LocationDatabase(getActivity());
        PolylineOptions polyline = new PolylineOptions();
        Cursor CSR = myDB.get_Data(myDB);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(now, 17));
        value = come.toString();
        if(CSR != null)
        for (int hold = 0; hold < come.size(); hold++) {
            CSR.moveToFirst();
            do {
                value = come.get(hold);

                if(value.equals(CSR.getString(0)))
                {
                    map.addMarker(new MarkerOptions().title(value).position(new LatLng(CSR.getDouble(1), CSR.getDouble(2))));
                    polyline.add(new LatLng(CSR.getDouble(1), CSR.getDouble(2))).width(5).color(Color.RED).geodesic(true);
                    map.addPolyline(polyline);
                    //polyline.add(uwi).width(5).color(Color.RED).geodesic(true);
                }
            }
            while (CSR.moveToNext());
        }

        polyline.add(now).width(5).color(Color.RED).geodesic(true);

        RetPref();

        if (RetPref().isEmpty())
        {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        } else if(RetPref().equals("GoogleMap.MAP_TYPE_SATELLITE"))
        {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }else if(RetPref().equals("GoogleMap.MAP_TYPE_HYBRID"))
        {
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }else if(RetPref().equals("GoogleMap.MAP_TYPE_TERRAIN"))
        {
            map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(uwi, 16));
        //System.out.println("Element: " + value);



    }


    public String RetPref()
    {
        sharedPref1 = getActivity().getPreferences(Context.MODE_PRIVATE);
        String Vmap = sharedPref1.getString("Map_Type","");
        return Vmap;
    }

}

