package com.labz04.omri.uwimaps.uwimaps;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.labz04.omri.uwimaps.R;


/**
 * Created by omri on 1/23/16.
 */

public class About extends AppCompatActivity
{
    Toolbar mytoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        mytoolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mytoolbar);

        // Get a support ActionBar corresponding to this toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

//    @Override
//    public void onBackPressed() {
//        Intent intent = new Intent(this,MainActivity.class);
//        startActivity(intent);
//    }

}

