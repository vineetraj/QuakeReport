package com.example.android.quakereport;
/*
*Created by vineet on 22-Jan-17.
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.
        ArrayList<Earthquake> earthquakes = QueryUtils.extractEarthquakes();

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes the list of earthquakes as input
        // we also have to add the “final” modifier on the EarthquakeAdapter local variable,
        // so that we could access the adapter variable within the OnItemClickListener
        final EarthquakeAdapter adapter = new EarthquakeAdapter(this, earthquakes, R.id.list);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        // Set a click listener on that View
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = (Earthquake) adapter.getItem(position);
                // Convert the String URL into a URI object (to pass into the Intent constructor)
                // The Intent constructor (that we want to use) requires a Uri object,
                // so we need to convert our URL (in the form of a String) into a URI.
                // Our earthquake URL is a more specific form of a URI
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());
                // Create a new intent to view the earthquake URI
                Intent intent = new Intent(Intent.ACTION_SENDTO, earthquakeUri);
                // Send the intent to launch a new activity
                startActivity(intent);
            }
        });
    }
}
