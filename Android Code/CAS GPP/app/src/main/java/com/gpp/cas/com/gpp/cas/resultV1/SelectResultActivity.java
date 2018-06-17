package com.gpp.cas.com.gpp.cas.resultV1;

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
import java.util.List;
import java.util.Map;

/**
 * Created by Vaibhav on 14/04/2016.
 */
public class SelectResultActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    //Declaring an Spinner
    private Spinner spinnerClass, spinnerSubject, spinnerType;
    String teacher_id=null, class_id=null, subject_id=null, type_name=null;
    private Button btnStartResult;
    EditText editTextMaxmark;
    //An ArrayList for Spinner Items
    private ArrayList<String> classlist;
    private ArrayList<String> subjectslist;
    private List<String> resultType;
    //JSON Array
    private JSONArray result, subs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_result);
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        teacher_id = sharedPreferences.getString(Config.USERNAME_SHARED_PREF, null);
        //Initializing the ArrayList
        classlist = new ArrayList<String>();
        subjectslist = new ArrayList<String>();
        resultType = new ArrayList<String>();

        //Initializing Spinner
        spinnerClass = (Spinner) findViewById(R.id.class_spinner_R);
        spinnerSubject = (Spinner) findViewById(R.id.subject_spinner_R);
        spinnerType = (Spinner) findViewById(R.id.type_spinner_R);
        btnStartResult = (Button) findViewById(R.id.submitButtonResult);
        editTextMaxmark=(EditText) findViewById(R.id.editTextMaxMarks);
        spinnerClass.setOnItemSelectedListener(this);
        spinnerSubject.setOnItemSelectedListener(this);
        spinnerType.setOnItemSelectedListener(this);
        btnStartResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(SelectResultActivity.this, ResultActivity.class);
                    intent.putExtra("classID", class_id);
                    intent.putExtra("subjectID", subject_id);
                    intent.putExtra("resultType", type_name);
                    intent.putExtra("teacherID", teacher_id);
                    intent.putExtra("maxMarks",editTextMaxmark.getText().toString());
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Button Error", Toast.LENGTH_LONG).show();
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
        spinnerClass.setAdapter(new ArrayAdapter<String>(SelectResultActivity.this, android.R.layout.simple_spinner_dropdown_item, classlist));
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
        spinnerSubject.setAdapter(new ArrayAdapter<String>(SelectResultActivity.this, android.R.layout.simple_spinner_dropdown_item, subjectslist));
    }

    private void addTypes() {

        resultType.add("Test_1");
        resultType.add("Test_2");
        resultType.add("Term_Work");
        resultType.add("Oral");
        resultType.add("Practical");
        resultType.add("Final_Exam");
        spinnerType.setAdapter((new ArrayAdapter<String>(SelectResultActivity.this, android.R.layout.simple_spinner_dropdown_item, resultType)));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long l) {
        int id = parent.getId();
        switch (id) {
            case R.id.class_spinner_R:
                class_id = parent.getItemAtPosition(pos).toString();
                break;
            case R.id.subject_spinner_R:
                subject_id = parent.getItemAtPosition(pos).toString();
                break;
            case R.id.type_spinner_R:
                type_name = parent.getItemAtPosition(pos).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
