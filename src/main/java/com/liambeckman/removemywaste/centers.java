package com.liambeckman.removemywaste;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.Spanned;
import android.widget.TextView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.liambeckman.removemywaste.db.AppDatabase;
import com.liambeckman.removemywaste.db.Materials;
import com.liambeckman.removemywaste.db.Schedules;

import android.content.Intent;

import android.text.Html;
import android.net.Uri;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.osmdroid.api.IMapController;
import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;

public class centers extends AppCompatActivity {

    private AppDatabase mDb;
    MapView map = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centers);

        Intent intent = getIntent();
        final String center = intent.getExtras().getString("center");
        String address = intent.getExtras().getString("address");
        final Integer id = intent.getExtras().getInt("id");

        TextView mTextView = findViewById(R.id.center);
        TextView mAddress = findViewById(R.id.address);

        mTextView.setText(Html.fromHtml(center));
        mAddress.setText(address);

        Button mButton;
        mButton = findViewById(R.id.button2);
        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/search/?api=1&query="+Uri.encode(Html.fromHtml(center).toString())));
                if (browserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(browserIntent);
                }
            }
        });

        Button eButton;
        eButton = findViewById(R.id.error);
        eButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://removemywaste.liambeckman.com/issues"));
                if (browserIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(browserIntent);
                }
            }
        });

        searchSchedules(id);
        searchMaterials(id);

        //load/initialize the osmdroid configuration, this can be done
        Context ctx = getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));
        //setting this before the layout is inflated is a good idea
        //it 'should' ensure that the map has a writable location for the map cache, even without permissions
        //if no tiles are displayed, you can try overriding the cache path using Configuration.getInstance().setCachePath
        //see also StorageUtils
        //note, the load method also sets the HTTP User Agent to your application's package name, abusing osm's tile servers will get you banned based on this string

        //inflate and create the map
        //setContentView(R.layout.activity_main);

        map = findViewById(R.id.mapview);
        map.setTileSource(TileSourceFactory.MAPNIK);
        map.setMultiTouchControls(true);



        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String apiAddress = Uri.encode(address);
        //String url = "https://geocoding.geo.census.gov/geocoder/locations/onelineaddress?address=" + apiAddress + "&benchmark=9&format=json";
        String key = "AIzaSyCPb7MDJh3zsvepb2OMrjr0E0R5wk9Bdqw";
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + apiAddress + "&key=" + key;
        Log.d("MyApp", url);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.isEmpty()) {
                            mTextView.setText("No handling instructions found for this material.");
                            mTextView.setTextColor(0xffFF3232);
                            return;
                        }
                        // Display the repspose string.
                        //mTextView.setText(response);
                        Log.d("MyApp", "response: " + response);
                        //mTextView.setText(response);
                        //materials[i].setText(responseArray[i*3]);
                        double lat = 45.5;
                        double lng = -122.6;



                        JSONObject reader = null;
                        try {
                            reader = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        try {
                            JSONObject results = (JSONObject) reader.getJSONArray("results").get(0);
                            JSONObject geometry = results.getJSONObject("geometry");
                            JSONObject location = geometry.getJSONObject("location");

                            lat = location.getDouble("lat");
                            lng = location.getDouble("lng");
                            //Log.d("MyApp", "JSONObject: " + geometry.getJSONObject("location").getDouble("lat"));
                            //Log.d("MyApp", "JSONObject: " + reader.getJSONArray("results"));
                            //JSONObject b = a.getJSONObject(2);
                            //Log.d("MyApp", "JSONObject: " + b);
                            Log.d("MyApp", "lat: " + lat);
                            Log.d("MyApp", "lng: " + lng);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        IMapController mapController = map.getController();
                        mapController.setZoom(12);
                        GeoPoint startPoint = new GeoPoint(lat, lng);
                        mapController.setCenter(startPoint);


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });
        mTextView.setText("Request sent. Waiting for Response...");

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public void onResume(){
        super.onResume();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));
        map.onResume(); //needed for compass, my location overlays, v6.0.0 and up
    }

    public void onPause(){
        super.onPause();
        //this will refresh the osmdroid configuration on resuming.
        //if you make changes to the configuration, use
        //SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        //Configuration.getInstance().save(this, prefs);
        map.onPause();  //needed for compass, my location overlays, v6.0.0 and up
    }


    public void searchMaterials(int id) {
        final TextView mTextView = findViewById(R.id.status);
        final Button[] materials = new Button[100];
        final LinearLayout ll = findViewById(R.id.linearMaterials);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        mDb = AppDatabase.buildDatabase(getApplicationContext());
        List<Materials> list = mDb.materialsModel().searchCentersMaterials(id);

        /*
        if (response.isEmpty()) {
            mTextView.setText("No Materials found for this center.");
            mTextView.setTextColor(0xffFF3232);
            return;
        }
        */

        for (int i = 0; i < list.size(); i++) {
            TextView material = new TextView(mTextView.getContext());

            material.setText(list.get(i).name);

            ll.addView(material, lp);
        }

    }


    public void searchSchedules(int id) {
        final TextView mTextView = findViewById(R.id.status);
        final Button[] materials = new Button[100];
        final LinearLayout ll = findViewById(R.id.linearSchedules);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 5, 10, 5);

        mDb = AppDatabase.buildDatabase(getApplicationContext());
        List<Schedules> responseArray = mDb.schedulesModel().searchSchedules(id);

        /*
        if (response.isEmpty()) {
            mTextView.setText("No schedules found for this center.");
            mTextView.setTextColor(0xffFF3232);
            return;
        }
        */

        for (int i = 0; i < responseArray.size(); i++) {

            TextView day_of_week = new TextView(mTextView.getContext());
            TextView time = new TextView(mTextView.getContext());

            LinearLayout llhorizontal = new LinearLayout(mTextView.getContext());
            llhorizontal.setOrientation(LinearLayout.HORIZONTAL);

            int day = responseArray.get(i).day_of_week;
            String dayString = "";
            switch (day) {
                case(1):
                    dayString = "Sunday";
                    break;
                case(2):
                    dayString = "Monday";
                    break;
                case(3):
                    dayString = "Tuesday";
                    break;
                case(4):
                    dayString = "Wednesday";
                    break;
                case(5):
                    dayString = "Thursday";
                    break;
                case(6):
                    dayString = "Friday";
                    break;
                case(7):
                    dayString = "Saturday";
                    break;

            }

            day_of_week.setText(dayString);
            day_of_week.setWidth(250);
            time.setText(responseArray.get(i).time_open + " to " + responseArray.get(i).time_closed);

            llhorizontal.addView(day_of_week, lp);
            llhorizontal.addView(time, lp);

            ll.addView(llhorizontal, lp);

        }
    }
}
