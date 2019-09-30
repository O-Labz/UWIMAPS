package com.labz04.omri.uwimaps.uwimaps;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.labz04.omri.uwimaps.R;

/**
 * Created by omri on 1/24/16.
 */
public class Help extends AppCompatActivity
{
    Toolbar mtoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        mtoolbar = (Toolbar) findViewById(R.id.my_ttoolbar);
        setSupportActionBar(mtoolbar);

        // Get a support ActionBar corresponding to this toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


}
