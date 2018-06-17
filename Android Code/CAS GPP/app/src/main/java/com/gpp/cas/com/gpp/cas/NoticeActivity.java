package com.gpp.cas.com.gpp.cas;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gpp.cas.Config;
import com.gpp.cas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * Created by Vaibhav on 20/04/2016.
 */
public class NoticeActivity extends AppCompatActivity implements OnItemSelectedListener {

    //Declare GUI variables to store user data for sending email
    Spinner spinnerClass;

    EditText editTextEmailSubject;
    EditText editTextEmailContent;

    private ArrayList<String> classlist;
    private ArrayList<String> emailList;
    JSONArray result,emails;
    private  String teacher_id=null,class_id=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        //get reference of GUI controls for retrieving user data from screen
        spinnerClass=(Spinner) findViewById(R.id.class_spinner_Notice);
        editTextEmailSubject = (EditText) findViewById(R.id.editTextEmailSubject);
        editTextEmailContent = (EditText) findViewById(R.id.editTextEmailContent);
        SharedPreferences sp=getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        teacher_id=sp.getString(Config.USERNAME_SHARED_PREF,null);
        classlist=new ArrayList<String>();
        emailList=new ArrayList<String>();
        getData();  //For fetching class_list from server

        spinnerClass.setOnItemSelectedListener(this);

    }

    public void onButtonClickSend(View v)
    {
        //get to, subject and content from the user input and store as string.
        //String emailTo 		= editTextEailTo.getText().toString();
        String emailSubject 	= editTextEmailSubject.getText().toString();
        String emailContent 	= editTextEmailContent.getText().toString();
        Vector vector=new Vector();
        for(int i=0;i<emailList.size();i++)
        {
            vector.add(emailList.get(i).toString());
        }
        String []listArray=new String[emailList.size()];
        vector.copyInto(listArray);
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,listArray);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailContent);
        /// use below 2 commented lines if need to use BCC an CC feature in email
        //emailIntent.putExtra(Intent.EXTRA_CC, new String[]{ to});
        //emailIntent.putExtra(Intent.EXTRA_BCC, new String[]{to});
        ////use below 3 commented lines if need to send attachment
        // emailIntent .setType("image/jpeg");
        //emailIntent .putExtra(Intent.EXTRA_SUBJECT, "My Picture");
        // emailIntent .putExtra(Intent.EXTRA_STREAM, Uri.parse("file://sdcard/captureimage.png"));

        //need this to prompts email client only
        emailIntent.setType("message/rfc822");

        startActivity(Intent.createChooser(emailIntent, "Select an Email Client:"));
    }

    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.CLASSID_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray(Config.JSON_ARRAY);

                            //Calling method getStudents to get the students from the JSON Array
                            getClasses(result);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("teacher_id", teacher_id);
                params.put("teacher_Id", teacher_id);


                //returning parameter
                return params;
            }
        };

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getClasses(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                classlist.add(json.getString(Config.TAG_CLASSNAME));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        spinnerClass.setAdapter(new ArrayAdapter<String>(NoticeActivity.this, android.R.layout.simple_spinner_dropdown_item, classlist));
    }
    //---------------------------------------------------------------------------------------------------
    private void getEmails() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SERVER_URL+"/notice_email.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        // Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            emails = j.getJSONArray("email_list");

                            //Calling method getStudents to get the students from the JSON Array
                            getEmailFromArray(emails);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("class_id", class_id);
                params.put("teacher_Id", teacher_id);


                //returning parameter
                return params;
            }
        };

        //Creating a request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
    }

    private void getEmailFromArray(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                emailList.add(json.getString("email_id"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    //------------------------------------------------------------------------------------------------
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        class_id=parent.getSelectedItem().toString();

        getEmails();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
