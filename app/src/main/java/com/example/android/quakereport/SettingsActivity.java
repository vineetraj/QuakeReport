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

    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        /**
         * add additional logic in the EarthquakePreferenceFragment so that it is aware of the new ListPreference,
         * similar to what we did for the EditTextPreference.
         * In the onCreate() method of the fragment, find the “order by” Preference object according to its key.
         * Then call the bindPreferenceSummaryToValue() helper method on this Preference object,
         * which will set this fragment as the OnPreferenceChangeListener and update the summary
         * so that it displays the current value stored in SharedPreferences.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
            /**
             * we still need to update the preference summary when the settings activity is launched.
             * Given the key of a preference, we can use PreferenceFragment's findPreference() method to get the Preference object,
             * and setup the preference using a helper method called bindPreferenceSummaryToValue().
             */
            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagnitude);

            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);
        }

        /**
         * It's often useful for the app to know immediately when a preference is changed,
         * especially when that preference change should have some visible impact on the UI.
         * To do this, our PreferenceFragment can implement the OnPreferenceChangeListener interface to get notified when a preference changes.
         * Then when a single Preference has been changed by the user and is about to be saved,
         * the onPreferenceChange() method will be invoked with the key of the preference that was changed.
         * Note that this method returns a boolean, which allows us to prevent a proposed preference change by returning false.
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            //take care of updating the displayed preference summary after it has been changed
            String stringValue = newValue.toString();
            /**
             * Since this is the first ListPreference that the EarthquakePreferenceFragment is encountering,
             * update the onPreferenceChange() method in EarthquakePreferenceFragment
             * to properly update the summary of a ListPreference (using the label, instead of the key).
             */
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }

        /**
         * helper method to set the current EarhtquakePreferenceFragment instance as the listener on each preference.
         * We also read the current value of the preference stored in the SharedPreferences on the device,
         * and display that in the preference summary (so that the user can see the current value of the preference).
         */
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }
    }
}
