package com.labz04.omri.uwimaps.uwimaps;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.labz04.omri.uwimaps.R;
import com.labz04.omri.uwimaps.fragments.Frag;

/**
 * Created by omri on 4/17/16.
 */
public class MapChoice extends DialogFragment
{
    final CharSequence [] map_items = {"Normal", "Satellite", "Hybrid", "Terrain"};

    private String selection, Vmap;

    private SharedPreferences sharedPref1;

    private String Map = "Map_Type";

    int Last_Choice, choice, maptype;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {

        RetPref();

        if (RetPref().isEmpty())
        {
            Last_Choice = 0;
            WritePref(Map,"GoogleMap.MAP_TYPE_NORMAL");
        } else if(RetPref().equals("GoogleMap.MAP_TYPE_SATELLITE"))
        {
            Last_Choice = 1;
        }else if(RetPref().equals("GoogleMap.MAP_TYPE_HYBRID"))
        {
            Last_Choice = 2;
        }else if(RetPref().equals("GoogleMap.MAP_TYPE_TERRAIN"))
        {
            Last_Choice = 3;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("        Choose Map Type").setSingleChoiceItems(map_items, Last_Choice, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                switch (which)
                {
                    case 0:
                        selection = (String) map_items[which];
                        choice = 0;
                        setSelection("GoogleMap.MAP_TYPE_NORMAL");
//                        setaptype(GoogleMap.MAP_TYPE_NORMAL);
                        WritePref(Map,getSelection());
                    break;
                    case 1:
                        selection = (String) map_items[which];
                        choice = 1;
                        setSelection("GoogleMap.MAP_TYPE_SATELLITE");
//                        setaptype(GoogleMap.MAP_TYPE_SATELLITE);
                        WritePref(Map,getSelection());
                        break;
                    case 2:
                        selection = (String) map_items[which];
                        choice = 2;
                        setSelection("GoogleMap.MAP_TYPE_HYBRID");
//                        setaptype(GoogleMap.MAP_TYPE_HYBRID);
                        WritePref(Map,getSelection());
                        break;
                    case 3:
                        selection = (String) map_items[which];
                        choice = 3;
                        setSelection("GoogleMap.MAP_TYPE_TERRAIN");
//                        setaptype(GoogleMap.MAP_TYPE_TERRAIN);
                        WritePref(Map,getSelection());
                        break;
                    default:
                        break;
                }

            }
        }).setPositiveButton("Save", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(getActivity(),  " Map Change Successful  " , Toast.LENGTH_LONG).show();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_only, new Frag()).commit();
            }
        });
        return builder.create();
    }


    public void setSelection(String selection) {
        this.selection = selection;
    }

    public String getSelection() {
        return selection;
    }

//    public void setaptype(int map_type) { this.maptype = map_type; }
//
//    public int getaptype(){return maptype = Integer.parseInt(RetPref());}

    public void WritePref(String map, String value)
    {
        sharedPref1 = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref1.edit();
        editor.putString(map,value);
        editor.apply();
    }


    //This function reads from the Shared preferences or in other words it gets the stored value in from memory
    public String RetPref()
    {
        sharedPref1 = getActivity().getPreferences(Context.MODE_PRIVATE);
        Vmap = sharedPref1.getString("Map_Type","");
        return Vmap;
    }


}
