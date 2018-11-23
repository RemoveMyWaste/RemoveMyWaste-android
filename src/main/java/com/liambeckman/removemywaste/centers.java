package com.liambeckman.removemywaste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

import android.content.Intent;

public class centers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centers);

        Intent intent = getIntent();
        String center = intent.getExtras().getString("center");

        final TextView mTextView = (TextView) findViewById(R.id.center);
        mTextView.setText(center);

        searchSchedules(center);

        searchMaterials(center);
    }



    public void searchMaterials(String center) {
        final TextView mTextView = (TextView) findViewById(R.id.status);
        final Button[] materials = new Button[100];
        final LinearLayout ll = (LinearLayout) findViewById(R.id.linearMaterials);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://removemywaste.liambeckman.com/search-centers-materials?search=" + center;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the repspose string.
                        //mTextView.setText(response);
                        final String[] responseArray = response.split("\\R");
                        Log.d("MyApp", "response: " + response);
                        mTextView.setText("");
                        for (int i = 0; i < responseArray.length; i++) {
                            Log.d("MyApp", "responseArray: " + responseArray[i]);
                            TextView material = new TextView(mTextView.getContext());

                            material.setText(responseArray[i]);

                            ll.addView(material, lp);


                            final String center = responseArray[i];


                            //materials[i].setText(responseArray[i*3]);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });

        mTextView.setText("Request sent. Waiting for Response...");
        //ll.removeAllViews();

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    public void searchSchedules(String center) {
        final TextView mTextView = (TextView) findViewById(R.id.status);
        final Button[] materials = new Button[100];
        final LinearLayout ll = (LinearLayout) findViewById(R.id.linearSchedules);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://removemywaste.liambeckman.com/search-schedules?search=" + center;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the repspose string.
                        //mTextView.setText(response);
                        if (response.isEmpty()) {
                            return;
                        }

                        final String[] responseArray = response.split("\\R");
                        Log.d("MyApp", "response: " + response);
                        mTextView.setText("");
                        for (int i = 0; i < responseArray.length; i += 3) {
                            Log.d("MyApp", "responseArray: " + responseArray[i]);
                            TextView day_of_week = new TextView(mTextView.getContext());
                            TextView time = new TextView(mTextView.getContext());

                            //day_of_week.setText(responseArray[i]);

                            int spacerNum = 10 - responseArray[i].length();
                            StringBuilder spacer = new StringBuilder();
                            for (int n = 0; n < spacerNum; n++) {
                                spacer.append(" ");
                            }

                            time.setText(responseArray[i] + spacer + ": " + responseArray[i+1] + " to " + responseArray[i+2]);

                            //ll.addView(day_of_week, lp);
                            ll.addView(time, lp);


                            final String center = responseArray[i];


                            //materials[i].setText(responseArray[i*3]);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });

        mTextView.setText("Request sent. Waiting for Response...");
        //ll.removeAllViews();

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
