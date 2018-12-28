package com.liambeckman.removemywaste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
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

import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;


public class disposal extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disposal);
        //final TextView mTextView = (TextView) findViewById(R.id.textView3);
        Intent intent = getIntent();
        String material = intent.getExtras().getString("material");

        final TextView mTextView = (TextView) findViewById(R.id.material);
        final TextView centers = (TextView) findViewById(R.id.centers);
        final TextView disposal = (TextView) findViewById(R.id.disposal);

        mTextView.setText(material);

        String key = "pro";
        final Boolean pro = intent.getExtras().getBoolean(key);

        if (pro) {
            centers.setText("professional disposal required" + " \u2605");
            searchCenters(material);
        }
        else {
            disposal.setText("home disposal allowed");
            //searchDisposal(material);
        }

        }

    public void searchCenters(String material) {
        final TextView mTextView = (TextView) findViewById(R.id.centers);
        final Button[] materials = new Button[100];
        final LinearLayout ll = (LinearLayout) findViewById(R.id.linearCenters);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://removemywaste.liambeckman.com/search-centers?search=" + material;

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

                        final String[] responseArray = response.split("\\r?\\n");
                        Log.d("MyApp", "response: " + response);
                        mTextView.setText("");
                        for (int i = 0; i < responseArray.length; i += 2) {
                            Log.d("MyApp", "responseArray: " + responseArray[i]);
                            Button newCenter = new Button(mTextView.getContext());
                            TextView address = new TextView(mTextView.getContext());
                            newCenter.setText(responseArray[i]);
                            newCenter.setId(i);
                            newCenter.setAllCaps(false);
                            ll.addView(newCenter, lp);

                            address.setText(responseArray[i+1]);

                            ll.addView(address, lp);

                            final String center = responseArray[i];

                            newCenter.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getApplicationContext(), centers.class);
                                    i.putExtra("center", center);
                                    startActivity(i);
                                }
                            });

                            //Materials[i].setText(responseArray[i*3]);
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

