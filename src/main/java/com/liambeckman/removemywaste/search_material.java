package com.liambeckman.removemywaste;

import android.app.SearchManager;
import android.content.Context;
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


public class search_material extends AppCompatActivity {

    Button mButton;
    EditText mEdit;
    TextView mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_material);

       mButton = (Button)findViewById(R.id.button1);
mButton.setOnClickListener(new View.OnClickListener() {
    	public void onClick(View view) {
            mEdit   = (EditText)findViewById(R.id.editText1);
            mEdit.getText().toString();
            //mText.setText("Welcome "+mEdit.getText().toString()+"!");
            doMySearch(mEdit.getText().toString());
      	}
        });


    }

    public void doMySearch (final String query){
        // https://developer.android.com/training/volley/simple#java
        final TextView mTextView = (TextView) findViewById(R.id.textView3);
        /*
        final Button material1 = (Button) findViewById(R.id.material1);
        final Button material2 = (Button) findViewById(R.id.material2);
        final Button material3 = (Button) findViewById(R.id.material3);
        final Button material4 = (Button) findViewById(R.id.material4);
        final Button material5 = (Button) findViewById(R.id.material5);

        final Button[] materials = {material1, material2, material3, material4, material5};
        */
        final Button[] materials = new Button[100];
        final LinearLayout ll = (LinearLayout) findViewById(R.id.linearResults);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);



        // ...

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://removemywaste.liambeckman.com/search-materials?search=" + query;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the repspose string.
                        //mTextView.setText(response);
                        final String[] responseArray = response.split("\\r?\\n");
                        //Log.d("MyApp", "response: " + response);
                        mTextView.setText("");
                        for (int i = 0; i < responseArray.length; i += 2) {
                            Log.d("MyApp", "responseArray: " + responseArray[i] + " : " + responseArray[i+1]);
                            Button newMaterial = new Button(mTextView.getContext());


                            final String insert;
                            final Boolean pro;
                            if (responseArray[i + 1].equals("pro")) {
                                insert = responseArray[i] + " \u2605";
                                pro = true;
                            }
                            else {
                                insert = responseArray[i];
                                pro = false;
                            }
                            newMaterial.setText(insert);

                            newMaterial.setId(i);
                            newMaterial.setAllCaps(false);
                            ll.addView(newMaterial, lp);
                            final String material = responseArray[i];

                            newMaterial.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getApplicationContext(), materials.class);
                                    i.putExtra("material", material);
                                    i.putExtra("pro", pro);
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
        ll.removeAllViews();

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
