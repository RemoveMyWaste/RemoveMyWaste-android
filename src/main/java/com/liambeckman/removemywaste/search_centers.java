package com.liambeckman.removemywaste;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.text.Spanned;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

import android.text.Html;

public class search_centers extends AppCompatActivity {

    Button mButton;
    EditText mEdit;
    TextView mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_centers);

        searchAllCenters("");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);



        EditText edit_txt = (EditText) findViewById(R.id.editText1);
        mButton = (Button)findViewById(R.id.button1);

        edit_txt.setOnEditorActionListener(new EditText.OnEditorActionListener() {
         @Override
         public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
             if (actionId == EditorInfo.IME_ACTION_DONE) {
                 mButton.performClick();

                 hideKeyboard(v);
                 return true;
             }
             return false;
         }
        });

        mButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                mEdit   = (EditText)findViewById(R.id.editText1);
                String query = mEdit.getText().toString();
                //mText.setText("Welcome "+mEdit.getText().toString()+"!");
                searchAllCenters(query);
                hideKeyboard(view);
                Log.d("MyApp", query);
            }
        });



    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public void searchAllCenters(final String query) {
        final TextView mTextView = (TextView) findViewById(R.id.centers);
        final Button[] materials = new Button[100];
        final LinearLayout ll = (LinearLayout) findViewById(R.id.linearCenters);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "https://removemywaste.liambeckman.com/search-all-centers?search=" + query;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the repspose string.
                        //mTextView.setText(response);

                        if (response.isEmpty()) {
                            mTextView.setText("No centers found.");
                            mTextView.setTextColor(0xffFF3232);
                            return;
                        }


                        final String[] responseArray = response.split("\\r?\\n");
                        Log.d("MyApp", "response: " + response);
                        mTextView.setText("");
                        for (int i = 0; i < responseArray.length; i += 2) {
                            Log.d("MyApp", "responseArray: " + Html.fromHtml(responseArray[i]));
                            Button newCenter = new Button(mTextView.getContext());
                            TextView address = new TextView(mTextView.getContext());
                            newCenter.setText(Html.fromHtml(responseArray[i]));
                            newCenter.setId(i);
                            newCenter.setAllCaps(false);
                            ll.addView(newCenter, lp);

                            address.setText(responseArray[i+1]);

                            ll.addView(address, lp);

                            final String center = responseArray[i];
                            final String mAddress = responseArray[i+1];

                            newCenter.setOnClickListener(new View.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    Intent i = new Intent(getApplicationContext(), centers.class);
                                    i.putExtra("center", center);
                                    i.putExtra("address", mAddress);
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
        ll.removeAllViews();

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

}
