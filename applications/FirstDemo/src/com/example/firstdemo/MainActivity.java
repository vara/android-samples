package com.example.firstdemo;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends FragmentActivity {

    GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {

        int i = item.getItemId();
        if (i == R.id.action_show_my_location) {
            goToMyLocation();
        }
        else if (i == R.id.menu_normal_map) {
            map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        }
        else if (i == R.id.menu_teren) {
            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        }
        else if (i == R.id.menu_hybrid) {
            map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        }

        return super.onMenuItemSelected(featureId, item);
    }

    /**
     *
     * @return
     */
    private LatLng  getMyPosition() {

        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {

            Log.i("MAIN", "Location is : " + location);

            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            return new LatLng(latitude, longitude);
        }

        return null;
    }

    private void goToMyLocation() {
//        Log.i("MAIN", ": Go to my location");
//

        map.setMyLocationEnabled(true);

        LatLng myPosition = getMyPosition();

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 13));

        if (myPosition != null) {

            Log.i("MAIN", "Set Location to : " + myPosition);
            map.addMarker(new MarkerOptions().position(myPosition).title("Start"));

        } else {
            Log.w("MAIN", "Location is null");
        }
    }
}
