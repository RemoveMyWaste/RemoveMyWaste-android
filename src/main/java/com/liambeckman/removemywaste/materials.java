package com.liambeckman.removemywaste;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.liambeckman.removemywaste.db.AppDatabase;
import com.liambeckman.removemywaste.db.Centers;
import com.liambeckman.removemywaste.db.Disposal;
import com.liambeckman.removemywaste.db.Handling;

import java.util.List;

public class materials extends AppCompatActivity {


    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materials);

        final TextView mTextView = findViewById(R.id.material);
        final TextView centers = findViewById(R.id.centers);
        final TextView disposal = findViewById(R.id.disposalHome);
        Intent intent = getIntent();
        final String material = intent.getExtras().getString("material");

        String key = "pro";
        final Boolean pro = intent.getExtras().getBoolean(key);

        doMySearch(material);

        if (pro) {
            mTextView.setText(material + " \u2605");
            disposal.setText("professional disposal required" + " \u2605");
            searchCenters(material);
        }
        else {
            mTextView.setText(material);
            disposal.setText("home disposal allowed");
            searchDisposal(material);
        }

    }

    //handling
    public void doMySearch (final String query){
        // https://developer.android.com/training/volley/simple#java
        final TextView mTextView = findViewById(R.id.handling);

        mDb = AppDatabase.buildDatabase(getApplicationContext());
        List<Handling> responseArray = mDb.handlingInstructionsModel().findHandlingByMaterial(query);

        if (responseArray.isEmpty()) {
            mTextView.setText("No handling instructions found for this material.");
            mTextView.setTextColor(0xffFF3232);
            return;
        }

        String instructions = "";
        for (int i = 0; i < responseArray.size(); i++) {
            instructions += responseArray.get(i).instructions;
        }
        mTextView.setText(instructions);
    }

    public void searchDisposal (final String query){
        // https://developer.android.com/training/volley/simple#java
        final TextView mTextView = findViewById(R.id.disposal);

        mDb = AppDatabase.buildDatabase(getApplicationContext());
        List<Disposal> responseArray = mDb.disposalInstructionsModel().findDisposalByMaterial(query);

        if (responseArray.isEmpty()) {
            mTextView.setText("No disposal instructions found for this material.");
            mTextView.setTextColor(0xffFF3232);
            return;
        }

        String instructions = "";
        for (int i = 0; i < responseArray.size(); i++) {
            instructions += responseArray.get(i).instructions;
        }
        mTextView.setText(instructions);
    }


    public void searchCenters(String material) {
        final TextView mTextView = findViewById(R.id.centers);
        final Button[] materials = new Button[100];
        final LinearLayout ll = findViewById(R.id.linearCenters);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        mDb = AppDatabase.buildDatabase(getApplicationContext());
        List<Centers> responseArray = mDb.centersModel().searchCentersByMaterial(material);


        /*
        if (response.isEmpty()) {
            mTextView.setText("No centers found for this material.");
            mTextView.setTextColor(0xffFF3232);
            return;
        }
        */

        for (int i = 0; i < responseArray.size(); i += 2) {
            Log.d("MyApp", "responseArray: " + responseArray.get(i));
            Button newCenter = new Button(mTextView.getContext());
            TextView address = new TextView(mTextView.getContext());
            newCenter.setText(responseArray.get(i).name);
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
            final String mAddress = centerAddress;
            final int id = responseArray.get(i).id;

            newCenter.setOnClickListener(v -> {
                Intent i1 = new Intent(getApplicationContext(), centers.class);
                i1.putExtra("center", center);
                i1.putExtra("address", mAddress);
                i1.putExtra("id", id);
                startActivity(i1);
            });

        }
    }

}
