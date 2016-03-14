package com.learning.suman.rsr;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {

    //Defining variables
    private static Button rsrPechhulpBtn;
    private static Button infoBtn;
    //GPSTracker gps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //Intitializations
        rsrPechhulpBtn=(Button)findViewById(R.id.RSRpechhulpButton);
        infoBtn=(Button)findViewById(R.id.infoButton);

        //OnClick listener for RSRpechchulp page load
        rsrPechhulpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*gps=new GPSTracker(MainActivity.this);
                if(gps.isCanGetLocation()){
                    double latitude=gps.getLatitude();
                    double longitude=gps.getLongitude();

                    Toast.makeText(getApplicationContext(),"ur location. lat:" +latitude+" long: "+ longitude,Toast.LENGTH_LONG).show();

                }

                else{
                    gps.showSettingsAlert();
                }*/
                RSRpechhulpPageLoad(v);
            }
        });

        //OnClick listener for Information page load
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPageLoad(v);
            }
        });

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Loads Activity RSR pechhulp page
    public void RSRpechhulpPageLoad(View view){
        /*Intent showLocation=new Intent(this,ShowLocation.class);
        startActivity(showLocation);*/
        Intent i=new Intent(this,RSRpechhulp.class);
        startActivity(i);
    }

    //Loads Information page
    public void infoPageLoad(View view){
        Intent i=new Intent(this,InfoActivity.class);
        startActivity(i);

    }
}


