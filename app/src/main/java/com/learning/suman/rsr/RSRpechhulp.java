package com.learning.suman.rsr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class RSRpechhulp extends FragmentActivity implements OnMapReadyCallback {

    //Declarations
    private GoogleMap mMap;
    private static TextView msgTextView;
    private static Button msgCloseBtn;
    private static Button bellRSRnuBtn;
    public static Button mainPageLoadButton;
    static int counter=0;
    static String telephoneNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsrpechhulp);

        //Initializations
        telephoneNumber="tel:09000400097";
        msgTextView=(TextView)findViewById(R.id.msgTextView);
        msgCloseBtn=(Button)findViewById(R.id.closeBtn);
        bellRSRnuBtn =(Button)findViewById(R.id.belRSRNuButton);
        mainPageLoadButton=(Button)findViewById(R.id.RSR_home_button);

        //used as .getMap() is deprecated, to set callback on map fragment
        SupportMapFragment mapFragment=(SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        setUpMapIfNeeded();


        //load Main page when button clicked
        mainPageLoadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homepageLoadIntent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(homepageLoadIntent);
            }
        });


        /*Bel nu button clicked...
          Alert message visible/Invisible depending on the counter value
          Setting the button icon*/
        bellRSRnuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(counter==0) {
                    msgTextView.setVisibility(View.VISIBLE);
                    msgCloseBtn.setVisibility(View.VISIBLE);
                    bellRSRnuBtn.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.phone_button_press,0,0,0);
                    counter=1;
                }
                else {

                    Intent phoneIntent = new Intent(Intent.ACTION_CALL);
                    phoneIntent.setData(Uri.parse(telephoneNumber));
                    startActivity(phoneIntent);
                    counter=0;
                }
            }
        });

        /*Annuleren button clicked event...
          Change back Alert close button and text message to invisible
          Set the initial button icon*/

        msgCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                msgTextView.setVisibility(View.INVISIBLE);
                msgCloseBtn.setVisibility(View.INVISIBLE);
                bellRSRnuBtn.setCompoundDrawablesWithIntrinsicBounds(R.mipmap.phonebtnsec,0,0,0);
                counter=0;
            }
        });

    }





    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    private void setUpMapIfNeeded() {
        // check if map is null.
        if (mMap == null) {
            // get map from the SupportMapFragment.

            mMap=((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

            if (mMap != null) {
                setUpMap();
            }
        }
    }


    private void setUpMap() {
    double latitude;
    double longitude;

    //checks if device connected to internet
    if(isNetworkAvailable()){

        if (mMap != null) {

            mMap.setMyLocationEnabled(true);

            // set map type as normal
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

            //get location service and assign to location manager
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            if (locationManager == null) {
                Toast.makeText(getApplicationContext(), "check the internet settings...", Toast.LENGTH_LONG).show();
            } else {
                Criteria criteria = new Criteria();

                // Get the name of the best provider
                String provider = locationManager.getBestProvider(criteria, true);

                //check if provider is available
                if (provider != null) {
                    // Get Current Location
                    Location myLocation = locationManager.getLastKnownLocation(provider);

                    //check if location is found
                    if (myLocation != null) {
                        if (myLocation.getLatitude() != 0) {

                            // Get latitude of the current location
                            latitude = myLocation.getLatitude();

                            // Get longitude of the current location
                            longitude = myLocation.getLongitude();

                            // Create a LatLng object for the current location
                            LatLng latLng = new LatLng(latitude, longitude);

                            // Show the current location in Google Map
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                            mMap.animateCamera(CameraUpdateFactory.zoomTo(14));

                            //get address from current location
                            Geocoder geocoder;
                            List<Address> addresses = null;

                            if (Geocoder.isPresent()) {
                                geocoder = new Geocoder(this, Locale.getDefault());
                                try {
                                    //  1 is the max location result returned
                                    addresses = geocoder.getFromLocation(latitude, longitude, 1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                            String address = null;
                            if (addresses != null) {
                                //get the street name
                                address = addresses.get(0).getAddressLine(0);
                            }
                            String city = null;
                            if (addresses != null) {
                                //get the city
                                city = addresses.get(0).getLocality();
                            }
                            String country = null;
                            if (addresses != null) {
                                //get the country
                                country = addresses.get(0).getCountryName();
                            }
                            String postalCode = null;
                            //get the postcode
                            if (addresses != null) postalCode = addresses.get(0).getPostalCode();


                            //set the marker
                            mMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(getString(R.string.location_marker_message))
                                    .snippet(address + "," + '\n' + postalCode + "," + '\n' + city + "," + '\n' + country)
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.markericon))).showInfoWindow();

                            //create custom window to display address and display message
                            mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                                @Override
                                public View getInfoWindow(Marker marker) {
                                    int maxWidth = 100;
                                    int maxHeight = 55;
                                    int alpha_value=150;
                                    LinearLayout locationInfoLayout = new LinearLayout(getApplicationContext());
                                    locationInfoLayout.setOrientation(LinearLayout.VERTICAL);
                                    locationInfoLayout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.button_color));
                                    Drawable transparency = locationInfoLayout.getBackground();
                                    transparency.setAlpha(alpha_value);

                                    //title of the marker
                                    TextView title = new TextView(getApplicationContext());
                                    title.setTextColor(Color.BLACK);
                                    title.setGravity(Gravity.CENTER);
                                    title.setTypeface(null, Typeface.BOLD);
                                    title.setText(marker.getTitle());

                                    //show address
                                    TextView snippet = new TextView(getApplicationContext());
                                    snippet.setTextColor(Color.BLACK);
                                    snippet.setGravity(Gravity.CENTER);
                                    snippet.setText(marker.getSnippet());

                                    //marker message
                                    TextView infoText = new TextView(getApplicationContext());
                                    infoText.setText(R.string.location_marker_popup_info);
                                    infoText.setMaxWidth(maxWidth);
                                    infoText.setMaxHeight(maxHeight);
                                    infoText.setTextColor(Color.BLACK);
                                    infoText.setGravity(Gravity.CENTER);

                                    locationInfoLayout.addView(title);
                                    locationInfoLayout.addView(snippet);
                                    locationInfoLayout.addView(infoText);

                                    return locationInfoLayout;

                                }

                                @Override
                                public View getInfoContents(Marker marker) {

                                    return null;


                                }
                            });

                        } else {
                            Toast.makeText(getApplicationContext(), "latitude/longitude: not found", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Location not found.Please enable your GPS", Toast.LENGTH_LONG).show();


                    }
                } else {
                    Toast.makeText(getApplicationContext(), "No service available,please check your service provider", Toast.LENGTH_LONG).show();
                }


            }


        } else {
            Toast.makeText(getApplicationContext(), "error in map", Toast.LENGTH_LONG).show();
        }

    }
    else {
        Toast.makeText(getApplicationContext(), "Please turn on your internet connection", Toast.LENGTH_LONG).show();
    }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

    }

    //check if the internet is available
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
