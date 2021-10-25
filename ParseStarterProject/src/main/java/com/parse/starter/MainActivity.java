/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.parse.starter.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity implements View.OnKeyListener{

  ActivityMainBinding activityMainBinding;

  Boolean signUpModeActive = true;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_main);
    activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
    View view = activityMainBinding.getRoot();
    setContentView(view);


    /*ParseObject score = new ParseObject("Score");
    score.put("username","Jesus");
    score.put("score",50);


    score.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if(e == null){
          Log.i("Success","We save the score");
        }else{
          e.printStackTrace();
        }
      }
    });

    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Score");
    query.getInBackground("fADZViUugL", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if(e == null && object != null){

          object.put("score",85);
          object.saveInBackground();

          Log.i("username",object.getString("username"));
          Log.i("score",Integer.toString(object.getInt("score")));
        }
      }
    });*/

    /*ParseObject tweet = new ParseObject("Tweet");

    tweet.put("tweeet","my first tweet");

    tweet.saveInBackground(new SaveCallback() {
      @Override
      public void done(ParseException e) {
        if(e == null){
          Log.i("okay","save success");
        }else{
          e.printStackTrace();
        }
      }
    });*/

    /*ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Tweet");
    query.getInBackground("r6lo6xQI9N", new GetCallback<ParseObject>() {
      @Override
      public void done(ParseObject object, ParseException e) {
        if(e == null && object != null){

          object.put("tweeet","This is my second tweet");
          object.saveInBackground();

          Log.i("username",object.getString("username"));
          Log.i("tweeet",object.getString("tweeet"));
        }
      }
    });

    ParseQuery<ParseObject> query = ParseQuery.getQuery("Score").whereEqualTo("username","Jesus").setLimit(1);



    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(e == null){
          if(objects.size()>0){
            for(ParseObject object: objects){
              Log.i("username",object.getString("username"));
              Log.i("score",Integer.toString(object.getInt("score")));
            }
          }
        }
      }
    });

    ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("Score").whereGreaterThan("score",50);

    query.findInBackground(new FindCallback<ParseObject>() {
      @Override
      public void done(List<ParseObject> objects, ParseException e) {
        if(e == null && objects != null){
          for(ParseObject parseObject: objects){
            parseObject.put("score", parseObject.getInt("score")+20);
            parseObject.saveInBackground();
          }
        }
      }
    });*/

    /*ParseUser user = new ParseUser();

    user.setUsername("jquintero");
    user.setPassword("elguapo");

    user.signUpInBackground(new SignUpCallback() {
      @Override
      public void done(ParseException e) {
        if(e == null){
          //OK
          Log.i("Sign Up OK", "We did it");
        }else{
          e.printStackTrace();
        }
      }
    });*/

    /*ParseUser.logInInBackground("jquintero", "elguapo", new LogInCallback() {
      @Override
      public void done(ParseUser user, ParseException e) {
        if(user!=null){
          Log.i("Success", "We logged in");
        }else{
          e.printStackTrace();
        }
      }
    });*/

    /*ParseUser.logOut();

    if(ParseUser.getCurrentUser() != null){
      Log.i("Signed In",ParseUser.getCurrentUser().getUsername());
    }else{
      Log.i("no luck", "not signed in");
    }*/

    if(ParseUser.getCurrentUser()!=null){
      showUserList();
    }



    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }

  public void signupClicked(View view){
    if (activityMainBinding.usernameEditText.getText().toString().matches("") || activityMainBinding.passwordEditText.getText().toString().matches("")) {
      Toast.makeText(this,"A username and a password are required",Toast.LENGTH_SHORT).show();

    }else{

      if(signUpModeActive) {
        ParseUser user = new ParseUser();
        user.setUsername(activityMainBinding.usernameEditText.getText().toString());
        user.setPassword(activityMainBinding.passwordEditText.getText().toString());

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {
              Log.i("Signup", "Success");
              showUserList();
            } else {
              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
          }
        });
      }else{
        //Login
        ParseUser.logInInBackground(activityMainBinding.usernameEditText.getText().toString(), activityMainBinding.passwordEditText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {
            if(user != null){
              Log.i("Login", "ok!");
              showUserList();
            }else{
              Toast.makeText(MainActivity.this,"Not able to sign in",Toast.LENGTH_SHORT).show();
            }
          }
        });
      }

    }
  }

  public void switchView(View view) {

    if(view.getId() == activityMainBinding.textView.getId()) {
      if (signUpModeActive) {
        signUpModeActive = false;
        activityMainBinding.signupButton.setText("Login");
        activityMainBinding.textView.setText("or, Signup");
      } else {
        signUpModeActive = true;
        activityMainBinding.signupButton.setText("Signup");
        activityMainBinding.textView.setText("or, Login");
      }
    }else if(view.getId() == activityMainBinding.logoImageView.getId() || view.getId() == activityMainBinding.backgroundLayout.getId()){
      InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
      inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }



  }


  @Override
  public boolean onKey(View view, int i, KeyEvent keyEvent) {

    if(i==KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
      signupClicked(view);
    }

    return false;
  }

  public void showUserList(){
    Intent intent  = new Intent(getApplicationContext(),UserListActivity.class);
    startActivity(intent);
  }
}