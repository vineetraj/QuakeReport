package com.example.android.quakereport;

/**
 * Created by vineet on 22-Jan-17.
 */

public class Earthquake {
    private double mag;
    private String name;
    private long mTimeInMillSeconds;
    /** Website URL of the earthquake */
    private String mUrl;

    /**
     * Constructs a new {@link Earthquake} object.
     *
     * @param mag is the magnitude (size) of the earthquake
     * @param name is the location where the earthquake happened
     * @param timeInMillSeconds is the time in milliseconds (from the Epoch) when the
     *                           earthquake happened
     * @param url is the website URL to find more details about the earthquake
     */
    public Earthquake(double mag, String name, long timeInMillSeconds, String url) {
        this.mag = mag;
        this.name = name;
        this.mTimeInMillSeconds = timeInMillSeconds;
        this.mUrl= url;
    }

    public double getMag() {
        return mag;
    }

    public String getName() {
        return name;
    }

    public long getTimeInMillSeconds() {
        return mTimeInMillSeconds;
    }
    /**
     * Returns the website URL to find more information about the earthquake.
     */
    public String getUrl() {
        return mUrl;
    }
}
