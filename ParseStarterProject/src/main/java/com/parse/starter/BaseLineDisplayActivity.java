package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.parse.ParseUser;

public class BaseLineDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_line_display);

        TextView baselineTextView = (TextView) findViewById(R.id.baselineText);
        baselineTextView.setText(Double.toString(Math.round((Double) ParseUser.getCurrentUser().get("baseline")*1000.0)/1000.0));
    }
}