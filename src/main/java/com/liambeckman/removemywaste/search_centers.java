package com.liambeckman.removemywaste;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
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

public class search_centers extends AppCompatActivity {

    Button mButton;
    EditText mEdit;
    TextView mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_centers);

        searchAllCenters();

    }

    public void searchAllCenters() {
        final TextView mTextView = (TextView) findViewById(R.id.centers);
        final Button[] materials = new Button[100];
        final LinearLayout ll = (LinearLayout) findViewById(R.id.linearCenters);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://removemywaste.liambeckman.com/search-all-centers?search=";

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
