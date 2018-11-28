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
        String address = intent.getExtras().getString("address");

        TextView mTextView = (TextView) findViewById(R.id.center);
        TextView mAddress = (TextView) findViewById(R.id.address);
        mTextView.setText(center);
        mAddress.setText(address);

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

                        if (response.isEmpty()) {
                            mTextView.setText("No materials found for this center.");
                            mTextView.setTextColor(0xffFF3232);
                            return;
                        }

                        final String[] responseArray = response.split("\\r?\\n");
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
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 5, 10, 5);

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
                            mTextView.setText("No schedules found for this center.");
                            mTextView.setTextColor(0xffFF3232);
                            return;
                        }

                        final String[] responseArray = response.split("\\r?\\n");
                        Log.d("MyApp", "response: " + response);
                        mTextView.setText("");
                        for (int i = 0; i < responseArray.length; i += 3) {
                            Log.d("MyApp", "responseArray: " + responseArray[i]);
                            TextView day_of_week = new TextView(mTextView.getContext());
                            TextView time = new TextView(mTextView.getContext());

                            LinearLayout llhorizontal = new LinearLayout(mTextView.getContext());
                            llhorizontal.setOrientation(LinearLayout.HORIZONTAL);

                            day_of_week.setText(responseArray[i]);
                            day_of_week.setWidth(250);
                            time.setText(responseArray[i+1] + " to " + responseArray[i+2]);

                            llhorizontal.addView(day_of_week, lp);
                            llhorizontal.addView(time, lp);

                            ll.addView(llhorizontal, lp);



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
