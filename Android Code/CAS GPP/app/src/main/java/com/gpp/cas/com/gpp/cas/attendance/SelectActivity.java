package com.gpp.cas.com.gpp.cas.attendance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import com.gpp.cas.StaffHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vaibhav on 10/03/2016.
 */
public class SelectActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    //Declaring an Spinner
    private Spinner spinnerClass, spinnerSubject, spinnerType;
    String teacher_id=null, class_id=null, subject_id=null, type_name=null;
    private Button btnStartAttendance;
    //An ArrayList for Spinner Items
    private ArrayList<String> classlist;
    private ArrayList<String> subjectslist;
    private List<String> attendanceType;
    //JSON Array
    private JSONArray result, subs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_class_attendance);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        teacher_id = sharedPreferences.getString(Config.USERNAME_SHARED_PREF, null);
        //Initializing the ArrayList
        classlist = new ArrayList<String>();
        subjectslist = new ArrayList<String>();
        attendanceType = new ArrayList<String>();

        //Initializing Spinner
        spinnerClass = (Spinner) findViewById(R.id.class_spinner);
        spinnerSubject = (Spinner) findViewById(R.id.subject_spinner);
        spinnerType = (Spinner) findViewById(R.id.type_spinner);
        btnStartAttendance = (Button) findViewById(R.id.submitButton);
        spinnerClass.setOnItemSelectedListener(this);
        spinnerSubject.setOnItemSelectedListener(this);
        spinnerType.setOnItemSelectedListener(this);
        btnStartAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(SelectActivity.this, AttendanceActivity.class);
                    intent.putExtra("classID", class_id);
                    intent.putExtra("subjectID", subject_id);
                    intent.putExtra("attnType", type_name);
                    intent.putExtra("teacherID", teacher_id);
                    startActivity(intent);
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(),"Button Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
        //This method will fetch the data from the URL
        getData();
        getSubjects();
        addTypes();
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
        spinnerClass.setAdapter(new ArrayAdapter<String>(SelectActivity.this, android.R.layout.simple_spinner_dropdown_item, classlist));
    }

    private void getSubjects() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SUBJECTID_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            subs = j.getJSONArray(Config.JSON_ARRAY2);

                            //Calling method getStudents to get the students from the JSON Array
                            getSubs(subs);
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

    private void getSubs(JSONArray j) {
        //Traversing through all the items in the json array
        for (int i = 0; i < j.length(); i++) {
            try {
                //Getting json object
                JSONObject json = j.getJSONObject(i);

                //Adding the name of the student to array list
                subjectslist.add(json.getString(Config.TAG_SUBID));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        spinnerSubject.setAdapter(new ArrayAdapter<String>(SelectActivity.this, android.R.layout.simple_spinner_dropdown_item, subjectslist));
    }

    private void addTypes() {
        //declare one arraylist and add practical and theoty as types dj
        attendanceType.add("Practical");
        attendanceType.add("LECTURE");
        spinnerType.setAdapter((new ArrayAdapter<String>(SelectActivity.this, android.R.layout.simple_spinner_dropdown_item, attendanceType)));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        int id = parent.getId();
        switch (id) {
            case R.id.class_spinner:
                class_id = parent.getItemAtPosition(pos).toString();
                break;
            case R.id.subject_spinner:
                subject_id = parent.getItemAtPosition(pos).toString();
                break;
            case R.id.type_spinner:
                type_name = parent.getItemAtPosition(pos).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
