package com.example.android.quakereport;
/**
 * Created by vineet on 22-Jan-17.
 */

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquake>> {

    /**
     * URL for earthquake data from the USGS dataset
     */
    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=6&limit=10";

    /**
     * First we need to specify an ID for our loader.
     * This is only really relevant if we were using multiple loaders in the same activity.
     * We can choose any integer number, so we choose the number 1.
     * Constant value for the earthquake loader ID. We can choose any integer.
     * This really only comes into play if you're using multiple loaders.
     */
    private static final int EARTHQUAKE_LOADER_ID = 1;

    /**
     * Adapter for the list of earthquakes
     */
    private EarthquakeAdapter mAdapter;
    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        /** Find a reference to the {@link ListView} in the layout */
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        /** we need to hook up the TextView as the empty view of the ListView.
         * We can use the ListView setEmptyView() method*/
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        /** Create a new adapter that takes the list of earthquakes as input */
        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());

        /** Set the adapter on the {@link ListView} so the list can be populated in the user interface */
        earthquakeListView.setAdapter(mAdapter);

        /**Set an item click listener on the ListView, which sends an intent to a web browser
         to open a website with more information about the selected earthquake */
        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Find the current earthquake that was clicked on
                Earthquake currentEarthquake = (Earthquake) mAdapter.getItem(position);

                // Convert the String URL into a URI object (to pass into the Intent constructor)
                Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

                // Create a new intent to view the earthquake URI
                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

                // Send the intent to launch a new activity
                startActivity(websiteIntent);
            }
        });

        // Get a reference to the LoaderManager, in order to interact with loaders.
        LoaderManager loaderManager = getLoaderManager();
        Log.i("inside Eqact.", "initLoader() will b called");
        /**
         * To query the active network and determine if it has Internet connectivity.
         */
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        if (isConnected)
        /**
         * Initialize the loader. Pass in the int ID constant defined above and pass in null for
         * the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
         * because this activity implements the LoaderCallbacks interface).
         */
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);
        else
            mEmptyStateTextView.setText(R.string.no_internet);
    }

    @Override
    public Loader<List<Earthquake>> onCreateLoader(int id, Bundle args) {
        Log.i("inside Eqact.", "onCreateLoader()");
        //Create a new loader for the given URL
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    //This method updates the UI with the result
    @Override
    public void onLoadFinished(Loader<List<Earthquake>> loader, List<Earthquake> earthquakes) {
        Log.i("inside Eqact.", "onLoadFinished()");
        // Set empty state text to display "No earthquakes found."
        mEmptyStateTextView.setText(R.string.msg);
        // Hide loading indicator because the data has been loaded
        ProgressBar pgBar = (ProgressBar) findViewById(R.id.progress);
        pgBar.setVisibility(View.GONE);
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquake>> loader) {
        Log.i("inside Eqact.", "onLoaderReset()");
        // Loader reset, so we can clear out our existing data.
        mAdapter.clear();
    }
}
