package com.learning.suman.rsr;

import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.media.audiofx.BassBoost;
import android.os.IBinder;
import android.preference.DialogPreference;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.google.android.gms.location.LocationListener;

/**
 * Created by Suman on 13-Mar-16. Functionalities of getting user location using GPS/NETWORK PROVIDER
 */
public class GPSTracker extends Service implements LocationListener {

    private final Context context;
    boolean isGPSEnabled=false;
    boolean isNetworkEnabled=false;
    boolean canGetLocation=false;
    Location location;
    double latitude;
    double longitude;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES=10;
    private static  final long  MIN_TIME_BW_UPDATES=1000*60*1;

    protected LocationManager locationManager;

    public GPSTracker(Context context){
        this.context=context;
        getLocation();
    }

    public  Location getLocation(){
        try{
            locationManager =(LocationManager)context.getSystemService(LOCATION_SERVICE);
            isGPSEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if(!isGPSEnabled && !isNetworkEnabled){

            }else{
                this.canGetLocation=true;
                if(isNetworkEnabled){
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);
                    //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);




                if(locationManager!=null){
                    location=locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if(location!=null){
                        latitude=location.getLatitude();
                        longitude=location.getLongitude();
                    }
                }
                }
                if(isGPSEnabled){
                    if(location==null){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,MIN_TIME_BW_UPDATES,MIN_DISTANCE_CHANGE_FOR_UPDATES, (android.location.LocationListener) this);
                        if(locationManager!=null){
                            location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                            if(location!=null){
                                latitude=location.getLatitude();
                                longitude=location.getLongitude();
                            }
                        }
                    }
                }
            }


        }catch (Exception e){
            e.printStackTrace();
        }
        return location;
    }


    public void stopUsingGPS(){
        if(locationManager!=null){


            locationManager.removeUpdates((android.location.LocationListener) GPSTracker.this);
        }
    }

    public double getLatitude(){
        if(location!=null){
            latitude=location.getLatitude();
        }
        return latitude;
    }

    public double getLongitude(){
        if(location!=null){
            longitude=location.getLongitude();
        }
        return longitude;
    }

    public boolean isCanGetLocation(){
        return this.canGetLocation;
    }

    public void showSettingsAlert(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(context);
        alertDialog.setTitle("GPS is Enabling");
        alertDialog.setMessage("GPS is not Enabled. Do u want to go to settings menu?");
        alertDialog.setPositiveButton("settings",new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent =new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            public  void onClick(DialogInterface dialog, int which){
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    public void onProviderDisabled(String arg0){

    }

    public void onProviderEnabled(String arg0){

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
