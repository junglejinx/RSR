package com.learning.suman.rsr;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class RSR extends Activity {

    //variable declarations
    static int screen_time_delay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rsr);

        //variable initialization
        screen_time_delay=3000;
        //Loads this page for 3 secs and then loads the main page
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                final Intent mainPageIntent=new Intent(RSR.this,MainActivity.class);
                RSR.this.startActivity(mainPageIntent);
                RSR.this.finish();

            }
        },screen_time_delay) ;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rsr, menu);
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
}
