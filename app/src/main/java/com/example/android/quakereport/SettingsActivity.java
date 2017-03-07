package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by vineet on 06-Mar-17.
 */

public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }

    /**
     * inner class declared inside SettingsActivity class
     */
    //use OnPreferenceChangeListener interface to get notified when a preference changes
    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            /**
             * logic to make the EarthquakePreferenceFragment aware of the new ListPreference
             *
             * update the preference summary when the settings activity is launched.
             */
            // use PreferenceFragment's findPreference() method to get the Preference object
            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            // setup the preference using a helper method called bindPreferenceSummaryToValue()
            bindPreferenceSummaryToValue(minMagnitude);

            // find the “order by” Preference object according to its key
            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            // call the bindPreferenceSummaryToValue() helper method on this Preference object
            bindPreferenceSummaryToValue(orderBy);
        }

        /**
         * When a single Preference has been changed by the user and is about to be saved,
         * the onPreferenceChange() method is invoked with the key of the preference that was changed.
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String stringValue = newValue.toString();
            /**
             * If this is the first ListPreference that the EarthquakePreferenceFragment is encountering,
             * update the onPreferenceChange() method in EarthquakePreferenceFragment
             */
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    // update the summary of a ListPreference (using the label, instead of the key)
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                // take care of updating the displayed preference summary after it has been changed
                preference.setSummary(stringValue);
            }
            // This method returns a boolean, so we can return false to prevent a proposed preference change
            return true;
        }

        /**
         * this helper method setups up the preference
         */
        private void bindPreferenceSummaryToValue(Preference preference) {
            // set the current EarthquakePreferenceFragment instance as the listener on each preference to watch for value changes
            preference.setOnPreferenceChangeListener(this);
            // read the current value of the preference stored in the SharedPreferences on the device
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            // retrieve a String value from the preferences.
            String preferenceString = preferences.getString(preference.getKey(), "");
            // display the preference values in the preference summary (so that the user can see the current value of the preference)
            onPreferenceChange(preference, preferenceString);
        }
    }
}
