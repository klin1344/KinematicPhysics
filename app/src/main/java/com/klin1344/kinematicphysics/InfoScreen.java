package com.klin1344.kinematicphysics;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by kevin on 6/9/15.  This class simply displays the infoscreen resource, which
 * shows information about the app.
 */
public class InfoScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infoscreen);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0277BD")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        Intent intent = new Intent(InfoScreen.this, Kinematics.class);
        startActivity(intent);
        //super.onBackPressed();
        finish();
    }
}
