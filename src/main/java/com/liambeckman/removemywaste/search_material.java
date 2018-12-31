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
import com.liambeckman.removemywaste.db.Materials;
import java.util.List;


public class search_material extends AppCompatActivity {

    Button mButton;
    EditText mEdit;
    Button mButtonClear;

    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_material);

        // Do initial search. This returns all Materials in databases.
        doMySearch("");

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        EditText edit_txt = findViewById(R.id.editText1);
        mButton = findViewById(R.id.button1);
        mButtonClear = findViewById(R.id.buttonClear);

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

        mButton.setOnClickListener(view -> {
            mEdit = findViewById(R.id.editText1);
            mEdit.getText().toString();

            hideKeyboard(view);
            doMySearch(mEdit.getText().toString());
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
                doMySearch(query);
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

    public void doMySearch(final String query) {
        // Note: Db references should not be in an activity.
        mDb = AppDatabase.buildDatabase(getApplicationContext());

        // https://developer.android.com/training/volley/simple#java
        final TextView mTextView = findViewById(R.id.textView3);
        final Button[] materials = new Button[100];
        final LinearLayout ll = findViewById(R.id.linearResults);
        final LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ll.removeAllViews();

        List<Materials> responseArray = mDb.materialsModel().searchMaterials(query);
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
