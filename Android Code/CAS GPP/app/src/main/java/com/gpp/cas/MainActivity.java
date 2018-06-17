package com.gpp.cas;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends ActionBarActivity {
	EditText editTextUsername,editTextPassword;
Button buttonLogin;
ProgressDialog loading;

//boolean variable to check user is logged in or not
//initially it is false
private boolean loggedIn = false;
private String type=null;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        editTextUsername = (EditText) findViewById(R.id.username);
        editTextPassword = (EditText) findViewById(R.id.password);
        buttonLogin=(Button) findViewById(R.id.loginbutton);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loading = ProgressDialog.show(MainActivity.this, "Logging in...",null,true,true);
				login();				
			}
		});   
    }
    @Override
    protected void onResume() {
        super.onResume();
        //In onresume fetching value from sharedpreference
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        //Fetching the boolean value form sharedpreferences
        loggedIn = sharedPreferences.getBoolean(Config.LOGGEDIN_SHARED_PREF, false);
        type=sharedPreferences.getString(Config.USER_TYPE, null);
        //If we will get true
        if(loggedIn && type.equals("student")){
            //We will start the next activity
            Intent intent = new Intent(MainActivity.this, StudentMain.class);
            startActivity(intent);
        }
        if(loggedIn && type.equals("staff"))
        {
        	 Intent intent = new Intent(MainActivity.this, StaffHome.class);
             startActivity(intent);
         }
        }
    

    private void login(){
        //Getting values from edit texts
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    	 loading.dismiss();
                        //If we are getting success from server                    	
                       if(response.trim().equals("success"))
                       {
                            //Creating a shared preference
                            SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                            //Creating editor to store values to shared preferences
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            //Adding values to editor
                            editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                            editor.putString(Config.USERNAME_SHARED_PREF, username);
                            editor.putString(Config.USER_TYPE, "student");

                            //Saving values to editor
                            editor.commit();

                            //Starting profile activity
                            Intent intent = new Intent(MainActivity.this, StudentMain.class);
                            startActivity(intent);
                            Toast.makeText(getApplicationContext(), "Login Success!!", Toast.LENGTH_SHORT).show();
                 }
                       if(response.equals("teacher_success")){
                    	   //Creating a shared preference
                           SharedPreferences sharedPreferences = MainActivity.this.getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);

                           //Creating editor to store values to shared preferences
                           SharedPreferences.Editor editor = sharedPreferences.edit();

                           //Adding values to editor
                           editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, true);
                           editor.putString(Config.USERNAME_SHARED_PREF, username);
                           editor.putString(Config.USER_TYPE, "staff");

                           //Saving values to editor
                           editor.commit();
                    	   Intent intent = new Intent(MainActivity.this, StaffHome.class);
                           startActivity(intent);
                           Toast.makeText(getApplicationContext(), "Login Success!!", Toast.LENGTH_SHORT).show();
                    	   
                       }
                  else{
                            //If the server response is not success
                            //Displaying an error message on toast
                            Toast.makeText(MainActivity.this, "Invalid username or password", Toast.LENGTH_LONG).show();
                        }
                        
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //You can handle error here if you want
                    	Toast.makeText(getApplicationContext(), "Connection Failed.",Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                //Adding parameters to request
                params.put(Config.KEY_USERNAME, username);
                params.put(Config.KEY_PASSWORD, password);

                //returning parameter
                return params;
            }
        };

        //Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this,aboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
int backButtonCount=0;
    @Override
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to close the application.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }

}
