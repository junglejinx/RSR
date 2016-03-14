package com.learning.suman.rsr;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/*
import com.google.android.gms.maps.CameraUpdateFactory;

import com.google.android.gms.maps.OnMapReadyCallback;
*/


public class RSRpechhulp extends FragmentActivity {

    //Declarations
    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private static TextView msgTextView;
    private static Button msgCloseBtn;
    private static Button bellRSRnuBtn;
    int counter=0;
    GPSTracker gps;
    //GPSService gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsrpechhulp);
        setUpMapIfNeeded();

        //Initializations
        msgTextView=(TextView)findViewById(R.id.msgTextView);
        msgCloseBtn=(Button)findViewById(R.id.closeBtn);
        bellRSRnuBtn =(Button)findViewById(R.id.belRSRNuButton);
        gps= new GPSTracker(RSRpechhulp.this);

        if (!gps.canGetLocation) {
            gps.showSettingsAlert();
        } else {
            double latitude=gps.getLatitude();
            double longitude=gps.getLongitude();

            //Toast.makeText(getApplicationContext(), "ur location. lat:" + latitude + " long: " + longitude, Toast.LENGTH_LONG).show();
            //Toast.makeText(getApplicationContext(), , Toast.LENGTH_LONG).show();

        }
        //Alert message visible/Invisible
        bellRSRnuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter==0) {
                    msgTextView.setVisibility(View.VISIBLE);
                    msgCloseBtn.setVisibility(View.VISIBLE);
                }
                else {
                    counter=0;
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:09000400097"));
                    startActivity(callIntent);
                }
            }
        });

        //Change back Alert close button to invisible
        msgCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgTextView.setVisibility(View.INVISIBLE);
                msgCloseBtn.setVisibility(View.INVISIBLE);
                counter++;
            }
        });

    }





    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
       // mMap.addMarker(new MarkerOptions().position(new LatLng(52.336278, 4.870649)).title("Marker").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));

        mMap.setMyLocationEnabled(true);

    }


    //Loads MainActivity page
    public void rsrHomePageLoadPechhulpPage(View view){
        Intent i=new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
