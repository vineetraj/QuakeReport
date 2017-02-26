package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by vineet on 23-Jan-17.
 */

public class EarthquakeAdapter extends ArrayAdapter {

    ArrayList mArrayList;

    public EarthquakeAdapter(Context context, ArrayList arrayList) {
        super(context, 0, arrayList);

        this.mArrayList = arrayList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        // Check if there is an existing list item view (called convertView) that we can reuse,
        // otherwise, if convertView is null, then inflate a new list item layout.
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.earthquake_list_item, parent, false);
        }

        // Find the earthquake at the given position in the list of earthquakes
        Earthquake earthquake = (Earthquake) getItem(position);

        // Find the TextView with view ID magnitude
        TextView magTextView = (TextView) listItemView.findViewById(R.id.magnitude);
        // Format the magnitude to show 1 decimal place
        String formatMagnitude = formatMag(earthquake.getMag());
        // Display the magnitude of the current earthquake in that TextView
        magTextView.setText(formatMagnitude);

        String originalLocation = earthquake.getName();
        String primaryLocation;
        String locationOffset;

        if (originalLocation.contains(" of ")) {
            String[] parts = originalLocation.split(" of ");
            locationOffset = parts[0] + " of ";
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView primaryLocationView = (TextView) listItemView.findViewById(R.id.primary_location);
        primaryLocationView.setText(primaryLocation);

        TextView locationOffsetView = (TextView) listItemView.findViewById(R.id.location_offset);
        locationOffsetView.setText(locationOffset);


        // Display the location of the current earthquake in that TextView
        /////nameTextView.setText(earthquake.getName());

        // Create a new Date object from the time in milliseconds of the earthquake
        Date dateObject = new Date(earthquake.getTimeInMillSeconds());

        // Find the TextView with view ID date
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date);
        // Format the date string
        String formattedDate = formatDate(dateObject);
        // Display the date of the current earthquake in that TextView
        dateTextView.setText(formattedDate);

        // Find the TextView with view ID time
        TextView timeView = (TextView) listItemView.findViewById(R.id.time);
        // Format the time string
        String formattedTime = formatTime(dateObject);
        // Display the time of the current earthquake in that TextView
        timeView.setText(formattedTime);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthquake.getMag());
        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);

        // Return the list item view that is now showing the appropriate data
        return listItemView;
    }

    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    public String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM DD, yyyy");
        return dateFormat.format(dateObject);
    }

    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    public String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM");
        return timeFormat.format(dateObject);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    public String formatMag(double mag) {
        DecimalFormat formatter = new DecimalFormat("0.0");
        return formatter.format(mag);
    }

    public int getMagnitudeColor(double magnitude) {
        int color = (int) magnitude;
        switch (color) {
            case 0:
            case 1:
                int magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);
                return magnitude1Color;

            case 2:
                int magnitude2Color = ContextCompat.getColor(getContext(), R.color.magnitude2);
                return magnitude2Color;
            case 3:
                int magnitude3Color = ContextCompat.getColor(getContext(), R.color.magnitude3);
                return magnitude3Color;
            case 4:
                int magnitude4Color = ContextCompat.getColor(getContext(), R.color.magnitude4);
                return magnitude4Color;
            case 5:
                int magnitude5Color = ContextCompat.getColor(getContext(), R.color.magnitude5);
                return magnitude5Color;
            case 6:
                int magnitude6Color = ContextCompat.getColor(getContext(), R.color.magnitude6);
                return magnitude6Color;
            case 7:
                int magnitude7Color = ContextCompat.getColor(getContext(), R.color.magnitude7);
                return magnitude7Color;
            case 8:
                int magnitude8Color = ContextCompat.getColor(getContext(), R.color.magnitude8);
                return magnitude8Color;
            case 9:
                int magnitude9Color = ContextCompat.getColor(getContext(), R.color.magnitude9);
                return magnitude9Color;
            default:
                int magnitude10Color = ContextCompat.getColor(getContext(), R.color.magnitude10plus);
                return magnitude10Color;
        }
    }
    /**
    NOTE: Another Way to Implement this switch case is as follows

    private int getMagnitudeColor(double magnitude) {
    int magnitudeColorResourceId;
    int magnitudeFloor = (int) Math.floor(magnitude);
    switch (magnitudeFloor) {
        case 0:
        case 1:
            magnitudeColorResourceId = R.color.magnitude1;
            break;
        case 2:
            magnitudeColorResourceId = R.color.magnitude2;
            break;
        case 3:
            magnitudeColorResourceId = R.color.magnitude3;
            break;
        case 4:
            magnitudeColorResourceId = R.color.magnitude4;
            break;
        case 5:
            magnitudeColorResourceId = R.color.magnitude5;
            break;
        case 6:
            magnitudeColorResourceId = R.color.magnitude6;
            break;
        case 7:
            magnitudeColorResourceId = R.color.magnitude7;
            break;
        case 8:
            magnitudeColorResourceId = R.color.magnitude8;
            break;
        case 9:
            magnitudeColorResourceId = R.color.magnitude9;
            break;
        default:
            magnitudeColorResourceId = R.color.magnitude10plus;
            break;
    }
    return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
 }
    */
}
