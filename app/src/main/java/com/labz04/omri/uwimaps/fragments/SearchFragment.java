package com.labz04.omri.uwimaps.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;
import com.labz04.omri.uwimaps.R;


/**
 * Created by omri on 3/28/16.
 */
public class SearchFragment extends Fragment
{

    public SearchFragment()
    {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View row = inflater.inflate(R.layout.fragment_search, container, false);

        return row;
    }


}
