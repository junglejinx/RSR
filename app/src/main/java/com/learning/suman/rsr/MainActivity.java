package com.learning.suman.rsr;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;



public class MainActivity extends Activity {

    //Defining variables
    protected static Button rsrPechhulpBtn;
    protected static Button infoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        //Initialisations
        rsrPechhulpBtn=(Button)findViewById(R.id.RSRpechhulpButton);
        infoBtn=(Button)findViewById(R.id.info_button);

        //OnClick listener for RSRpechchulp page load
        rsrPechhulpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent RSR_pechhulp_Intent=new Intent(getApplicationContext(),RSRpechhulp.class);
                startActivity(RSR_pechhulp_Intent);
            }
        });

        //OnClick listener for Information page load
        infoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent info_load_intent=new Intent(getApplicationContext(),InfoActivity.class);
                startActivity(info_load_intent);
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


}


