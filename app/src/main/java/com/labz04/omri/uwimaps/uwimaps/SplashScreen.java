package com.labz04.omri.uwimaps.uwimaps;



import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import com.labz04.omri.uwimaps.R;


/**
 * Created by omri on 12/29/15.
 */
public class SplashScreen extends AppCompatActivity
{

    private static int Screen_Delay = 3000;

    static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_CONTACTS = 0;
    static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {

                // Assume thisActivity is the current activity
                int permissionCheck = ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int permissionCheck2 = ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.ACCESS_FINE_LOCATION);
                int permissionCheck3 = ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.ACCESS_COARSE_LOCATION);


                // Here, thisActivity is the current activity
                if (ContextCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    // Should we show an explanation?
                    if (ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                        // Show an expanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.

                    } else {

                        // No explanation needed, we can request the permission.

                        ActivityCompat.requestPermissions(SplashScreen.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_CONTACTS);

                        // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                        // app-defined int constant. The callback method gets the
                        // result of the request.
                    }
                }


                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, Screen_Delay);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }


            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}
