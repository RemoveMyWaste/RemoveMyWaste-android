package com.liambeckman.removemywaste;

import android.app.SearchManager;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.liambeckman.removemywaste.db.AppDatabase;
import com.liambeckman.removemywaste.db.Materials;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;


public class search_material extends AppCompatActivity {

    Button mButton;
    EditText mEdit;
    TextView mText;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_material);

        // Do initial search. This returns all Materials in databases.
        doMySearch("");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        EditText edit_txt = (EditText) findViewById(R.id.editText1);
        mButton = (Button) findViewById(R.id.button1);

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
                mEdit = (EditText) findViewById(R.id.editText1);
                mEdit.getText().toString();

                hideKeyboard(view);
                //mText.setText("Welcome "+mEdit.getText().toString()+"!");
                doMySearch(mEdit.getText().toString());
            }
        });


    }


    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void doMySearch(final String query) {
        // Note: Db references should not be in an activity.
        mDb = AppDatabase.buildDatabase(getApplicationContext());
        //mDb = Room.databaseBuilder(getApplicationContext(), AppDatabase.class)


        // https://developer.android.com/training/volley/simple#java
        final TextView mTextView = (TextView) findViewById(R.id.textView3);
        final Button[] materials = new Button[100];
        final LinearLayout ll = (LinearLayout) findViewById(R.id.linearResults);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        List<Materials> responseArray = mDb.materialsModel().findAllMaterials();
        for (Materials material : responseArray) {
            Log.d("mydatabase", material.name);
        }

        for (int i = 0; i < responseArray.size(); i++) {
            Button newMaterial = new Button(mTextView.getContext());

            final String insert;
            final Boolean pro;

            if (responseArray.get(i).pro) {
                insert = responseArray.get(i).name + " \u2605";
                pro = true;
            }
            else {
                insert = responseArray.get(i).name;
                pro = false;
            }

            newMaterial.setText(insert);

            newMaterial.setId(i);
            newMaterial.setAllCaps(false);
            ll.addView(newMaterial, lp);
            final String material = responseArray.get(i).name;

            newMaterial.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), materials.class);
                    i.putExtra("material", material);
                    i.putExtra("pro", pro);
                    startActivity(i);
                }
            });

        }
    }
}
