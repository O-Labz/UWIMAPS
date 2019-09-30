package com.labz04.omri.uwimaps.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.labz04.omri.uwimaps.R;

/**
 * Created by omri on 12/29/15.
 */
public class ImportFragment extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.activity_about,container,false);
    }
}
