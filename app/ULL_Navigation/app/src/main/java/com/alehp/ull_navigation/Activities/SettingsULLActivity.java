package com.alehp.ull_navigation.Activities;

import android.annotation.TargetApi;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.EditTextPreference;

import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.os.Bundle;

import android.support.v7.preference.SeekBarPreference;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alehp.ull_navigation.R;
import com.alehp.ull_navigation.Utils.AppCompatPreferenceActivity;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SettingsULLActivity extends AppCompatPreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private SeekBarPreference seekBarPreference;
    private SharedPreferences settingsPref;
    private SharedPreferences.Editor editorPref;
    private Toolbar toolbar;

    private static final String SHOW_RADIUS_STRING = "showRadius";
    private static final String MAX_RADIUS_STRING = "maxRadius";
    private static final String MIN_RADIUS_STRING = "minRadius";

    private SwitchPreference showRadiusPrefence;
    private EditTextPreference maxRadiusPreference;
    private EditTextPreference minRadiusPreference;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        settingsPref = PreferenceManager.getDefaultSharedPreferences(this);
        editorPref = settingsPref.edit();
        addPreferencesFromResource(R.xml.pref_nav_settings);


        setTopActionBar();


        showRadiusPrefence = (SwitchPreference) findPreference(SHOW_RADIUS_STRING);
        maxRadiusPreference = (EditTextPreference) findPreference(MAX_RADIUS_STRING);
        minRadiusPreference = (EditTextPreference) findPreference(MIN_RADIUS_STRING);

        maxRadiusPreference.setEnabled(settingsPref.getBoolean(SHOW_RADIUS_STRING ,false));
        minRadiusPreference.setEnabled(settingsPref.getBoolean(SHOW_RADIUS_STRING, false));

        maxRadiusPreference.setSummary(settingsPref.getString(MAX_RADIUS_STRING, null));
        minRadiusPreference.setSummary(settingsPref.getString(MIN_RADIUS_STRING, null));
    }

    private void setTopActionBar() {
        getLayoutInflater().inflate(R.layout.toolbar, (ViewGroup)findViewById(android.R.id.content));
        Toolbar toolbar = (Toolbar)findViewById(R.id.my_toolbar);
        toolbar.setTitle("Configuración");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        int horizontalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        int verticalMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
        int topMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, (int) getResources().getDimension(R.dimen.activity_vertical_margin) + 20, getResources().getDisplayMetrics());
        getListView().setPadding(horizontalMargin, topMargin, horizontalMargin, verticalMargin);

    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key){
            case SHOW_RADIUS_STRING:      //Activamos los radios
                maxRadiusPreference.setEnabled(sharedPreferences.getBoolean(key, false));
                minRadiusPreference.setEnabled(sharedPreferences.getBoolean(key, false));
                break;
            case MAX_RADIUS_STRING:
                maxRadiusPreference.setSummary(settingsPref.getString(MAX_RADIUS_STRING, null));
                break;
            case MIN_RADIUS_STRING:
                int auxMax = Integer.parseInt(settingsPref.getString(MAX_RADIUS_STRING, "0"));
                int auxMin = Integer.parseInt(settingsPref.getString(MIN_RADIUS_STRING, "0"));
                if (auxMin > auxMax) {
                    editorPref.putString(MIN_RADIUS_STRING, String.valueOf(auxMax));
                    editorPref.commit();
                    Toast.makeText(this, "El tamaño del circúlo menor tiene que superar al mayor", Toast.LENGTH_SHORT).show();
                }
                minRadiusPreference.setSummary(settingsPref.getString(MIN_RADIUS_STRING, null));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }
}
