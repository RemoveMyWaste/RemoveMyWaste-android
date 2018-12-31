package com.liambeckman.removemywaste;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.liambeckman.removemywaste.db.AppDatabase;
import com.liambeckman.removemywaste.db.Centers;
import java.util.List;


public class search_centers extends AppCompatActivity {

    Button mButton;
    Button mButtonClear;
    EditText mEdit;

    protected AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_centers);

        searchAllCenters("");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        EditText edit_txt = findViewById(R.id.editText1);
        mButton = findViewById(R.id.button1);
        mButtonClear = findViewById(R.id.buttonClear);

        // hide keyboard after enter key is pressed
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

        // perform search if search button is pressed
        mButton.setOnClickListener(view -> {
            String query = edit_txt.getText().toString();
            searchAllCenters(query);
            hideKeyboard(view);
        });

        mButtonClear.setOnClickListener(view -> {
            edit_txt.setText("");
        });

        // updates search after any change to search text
        edit_txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mEdit   = findViewById(R.id.editText1);
                String query = mEdit.getText().toString();
                //mText.setText("Welcome "+mEdit.getText().toString()+"!");
                searchAllCenters(query);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void searchAllCenters(final String query) {
        final TextView mTextView = findViewById(R.id.centers);
        final LinearLayout ll = findViewById(R.id.linearCenters);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ll.removeAllViews();

        Log.d("MyApp", "query: " + query);
        mDb = AppDatabase.buildDatabase(getApplicationContext());
        List<Centers> responseArray = mDb.centersModel().searchCenters(query);

        /*
        if (response.isEmpty()) {
            mTextView.setText("No centers found.");
            mTextView.setTextColor(0xffFF3232);
            return;
        }
        */

        for (int i = 0; i < responseArray.size(); i++) {
            Button newCenter = new Button(mTextView.getContext());
            TextView address = new TextView(mTextView.getContext());
            final String insert = responseArray.get(i).name;
            newCenter.setText(insert);
            newCenter.setId(i);
            newCenter.setAllCaps(false);
            ll.addView(newCenter, lp);

            Integer street_number = responseArray.get(i).street_number;
            String street_direction = responseArray.get(i).street_direction;
            String street_name = responseArray.get(i).street_name;
            String street_type = responseArray.get(i).street_type;
            String city = responseArray.get(i).city;
            String state = responseArray.get(i).state;
            String zip = responseArray.get(i).zip;

            String centerAddress = street_number.toString() + " " + street_direction + " " + street_name + " " + street_type + " "  + city + " " + state + " " + zip;
            address.setText(centerAddress);

            ll.addView(address, lp);

            final String center = responseArray.get(i).name;
            final int id = responseArray.get(i).id;

            newCenter.setOnClickListener(v -> {
                Intent i1 = new Intent(getApplicationContext(), centers.class);
                i1.putExtra("center", center);
                i1.putExtra("address", centerAddress);
                i1.putExtra("id", id);
                startActivity(i1);
            });
        }
    }
}
