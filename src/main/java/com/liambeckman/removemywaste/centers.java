package com.liambeckman.removemywaste;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class centers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_centers);

        final TextView mTextView = (TextView) findViewById(R.id.textView3);

        Intent intent = getIntent();
        String material = intent.getExtras().getString("material");

        mTextView.setText(material);
    }
}
