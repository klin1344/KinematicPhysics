package com.klin1344.kinematicphysics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by kevin on 6/8/15.  This class takes the results passed to it from the previous class
 * and simply displays the results from the calculations in a ListView.
 */
public class Results extends AppCompatActivity {
    private ListView listView;
    private ImageView image;
    private ArrayList<String> list;
    private TextView titleView;


    /* create the layout -  the picture of the equations and then a the answers in a listview */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0277BD")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        listView = (ListView) findViewById(R.id.listView);
        titleView = (TextView) findViewById(R.id.titleView);
        image = (ImageView) findViewById(R.id.imageView);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        /* get the information from the intent and then add it to the ArrayList, which is
           used to create the listview using the simple, default ArrayAdapter.
         */

        if(bundle != null)
        {
            list = new ArrayList<String>();
            DecimalFormat n = new DecimalFormat("#.###");
            list.add(getString(R.string.initial_velocity) + ": " + n.format((double) bundle.getDouble("vI")) + " " + getString(R.string.m_s));
            list.add(getString(R.string.final_velocity) + ": " + n.format((double) bundle.getDouble("vF")) + " " + getString(R.string.m_s));
            list.add(getString(R.string.time) + ": " + n.format((double) bundle.getDouble("time")) + " " + getString(R.string.seconds));
            list.add(getString(R.string.acceleration) + ": " + n.format((double) bundle.getDouble("acceleration")) + " " + getString(R.string.m_s_s));
            list.add(getString(R.string.initial_x) + ": " + n.format((double) bundle.getDouble("deltaXI")) + " " + getString(R.string.meters));
            list.add(getString(R.string.final_x) + ": " + n.format((double) bundle.getDouble("deltaXF")) + " " + getString(R.string.meters));



            if ((boolean) bundle.get("checked")) {
                list.add(getString(R.string.max_height) + ": " + n.format((double) bundle.getDouble("height")) + " " + getString(R.string.meters));
                list.add(getString(R.string.max_range) + ": " + n.format((double) bundle.getDouble("range")) + " " + getString(R.string.meters));
                list.add(getString(R.string.launch_angle) + ": " + n.format((double) bundle.getDouble("theta")) + " " + getString(R.string.degrees));
                titleView.setText(getString(R.string.projectile));
                image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.projectile_motion));
            }
            else {
                titleView.setText(getString(R.string.one_dimensional));
                image.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.kinematic_equations));
            }

        }
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Results.this, Kinematics.class);
        startActivity(intent);
        //super.onBackPressed();
        finish();
    }
}
