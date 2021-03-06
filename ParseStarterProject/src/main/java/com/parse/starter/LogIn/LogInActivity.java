/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.starter.MainFragments.MainActivity;
import com.parse.starter.R;

import java.util.ArrayList;


public class LogInActivity extends AppCompatActivity implements View.OnClickListener, View.OnKeyListener {

    Boolean signUpModeActive = true;
    TextView changeSignupModeTextView;
    EditText passwordEditText;
    EditText usernameEditText;
    EditText nameEditText;
    EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        RelativeLayout backgroundRelativeLayout = (RelativeLayout) findViewById(R.id.backgroundRelativeLayout);
        changeSignupModeTextView = (TextView) findViewById(R.id.changeSignupModeTextView);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        emailEditText = (EditText) findViewById(R.id.emailEditText);

        changeSignupModeTextView.setOnClickListener(this);
        backgroundRelativeLayout.setOnClickListener(this);
        passwordEditText.setOnKeyListener(this);

        ParseAnalytics.trackAppOpenedInBackground(getIntent());

//        if (ParseUser.getCurrentUser() != null) {
//            startActivity(new Intent(LogInActivity.this, MainActivity.class));
//        }
    }

    public void signUp(View view) {
        if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {
            Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();
        } else {

            if (signUpModeActive) {
                ArrayList<String> data = new ArrayList();
                data.add(nameEditText.getText().toString());
                data.add(emailEditText.getText().toString());
                data.add(usernameEditText.getText().toString());
                data.add(passwordEditText.getText().toString());
                Intent intent = new Intent(LogInActivity.this, GenderSelectionActivity.class);
                intent.putExtra("data", data);
                startActivity(intent);

            } else {
                ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if (user != null) {
                            Log.i("Signup", "Login successful");
                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LogInActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }
    }


    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        if (i == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
            signUp(view);
        }
        return false;
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.changeSignupModeTextView) {

            Button signupButton = (Button) findViewById(R.id.signupButton);

            if (signUpModeActive) {
                signUpModeActive = false;
                signupButton.setText("Login");
                changeSignupModeTextView.setText("Or, Signup");
                nameEditText.setVisibility(View.INVISIBLE);
                emailEditText.setVisibility(View.INVISIBLE);
            } else {
                signUpModeActive = true;
                signupButton.setText("Signup");
                changeSignupModeTextView.setText("Or, Login");
                nameEditText.setVisibility(View.VISIBLE);
                emailEditText.setVisibility(View.VISIBLE);
            }
        }
//        } else if (view.getId() == R.id.backgroundRelativeLayout) {
//            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//        }

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (ParseUser.getCurrentUser() != null) {
//            startActivity(new Intent(LogInActivity.this, MainActivity.class));
//        }
//    }
}
