package com.labz04.omri.uwimaps.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.labz04.omri.uwimaps.gps.GPS_MODULE;
import com.labz04.omri.uwimaps.R;
import com.labz04.omri.uwimaps.uwimaps.MapChoice;

/**
 * Created by omri on 12/29/15.
 */
public class Frag extends Fragment implements OnMapReadyCallback
{

    private SharedPreferences sharedPref1;

    public Frag()
    {

    }

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        FragmentTransaction trans = getFragmentManager().beginTransaction();
        SupportMapFragment mfrag = new SupportMapFragment();
        trans.replace(R.id.content, mfrag).commit();
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
        double one = current.getLatitude();
        double two = current.getLongitude();

        LatLng uwi = new LatLng(one, two);
        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(uwi, 17));
        map.addMarker(new MarkerOptions().title("This is Your Current Location").position(uwi));
//        map.setMapType(def);


        RetPref();
        //This if then else statement makes the decision on what type of map is to be used.
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

    }

    //This function reads from the Shared preferences or in other words it gets the stored value in from memory
    public String RetPref()
    {
        sharedPref1 = getActivity().getPreferences(Context.MODE_PRIVATE);
        String Vmap = sharedPref1.getString("Map_Type","");
        return Vmap;
    }
}
