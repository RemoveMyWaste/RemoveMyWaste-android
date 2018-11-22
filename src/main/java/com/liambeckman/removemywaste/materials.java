package com.liambeckman.removemywaste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class materials extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials);

        final TextView mTextView = (TextView) findViewById(R.id.material);
        Intent intent = getIntent();
        final String material = intent.getExtras().getString("material");

        String key = "pro";
        final Boolean pro = intent.getExtras().getBoolean(key);

        if (pro) {
            mTextView.setText(material + " \u2605");
        }
         else {
            mTextView.setText(material);
        }

        Button disposal = (Button) findViewById(R.id.centers);
        disposal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), disposal.class);
                i.putExtra("material", material);
                i.putExtra("pro", pro);
                startActivity(i);
            }
        });

        doMySearch(material);
    }

    public void doMySearch (final String query){
        // https://developer.android.com/training/volley/simple#java
        final TextView mTextView = (TextView) findViewById(R.id.handling);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://removemywaste.liambeckman.com/search-handling?search=" + query;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the repspose string.
                        //mTextView.setText(response);
                        //Log.d("MyApp", "response: " + response);
                        mTextView.setText(response);
                            //materials[i].setText(responseArray[i*3]);

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


}
