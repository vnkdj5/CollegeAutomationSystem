package com.gpp.cas.com.gpp.cas.resultV1.resultview;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

/**
 *  Created by Vaibhav on 18/04/2016.
 */
public class ShowResult extends AppCompatActivity implements Spinner.OnItemSelectedListener {
    ListView listView;
    Spinner spinnerSubject;
    JSONArray subs,result;
    String student_id=null,subject_id;
    ArrayList<String> subjectslist=new ArrayList<String>();
    ArrayList<ResultItem> list=new ArrayList<ResultItem>();
    ResultViewAdapter adapter;
    int flag=0; //for first time request
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result_view);
        spinnerSubject=(Spinner) findViewById(R.id.spinnerSubjectRV);
        SharedPreferences sp=getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        student_id=sp.getString(Config.USERNAME_SHARED_PREF,null);
        listView=(ListView) findViewById(R.id.listViewRV);
       // Toast.makeText(getApplicationContext(), subject_id, Toast.LENGTH_LONG).show();
        getData();
        adapter=new ResultViewAdapter(this,list);
        spinnerSubject.setOnItemSelectedListener(this);
        getSubjects();

    }
    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SERVER_URL+"/get_result.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray("result_list");

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
                        //Toast.makeText(getApplicationContext(),"Server Connection Failed",Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request

                params.put("subject_id", subject_id);
                params.put("student_id",student_id);

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
                String test_type = json.getString("test_type");
               String marks = json.getString("marks");
                String maxmarks=json.getString("max_marks");
                ResultItem resultItem=new ResultItem();
                resultItem.setType(test_type);
                resultItem.setMarks(marks);
                resultItem.setMaxMarks(maxmarks);
                list.add(resultItem);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        listView.setAdapter(new ResultViewAdapter(this, list));

    }
    private void getSubjects() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SERVER_URL+"/get_subjects_result.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    //   Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

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
                params.put("student_id", student_id);
                params.put("student_Id", student_id);


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
        spinnerSubject.setAdapter(new ArrayAdapter<String>(ShowResult.this, android.R.layout.simple_spinner_dropdown_item, subjectslist));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(flag==0)
        {
            subject_id = parent.getSelectedItem().toString();
            getData();
            adapter.notifyDataSetChanged();
            flag=1;
        }
        else {
            subject_id = parent.getSelectedItem().toString();
            for (int j = 0; j < adapter.getCount(); j++) {
                list.remove(j);
                list.clear();
                adapter.notifyDataSetChanged();
            }
            getData();
            Toast.makeText(this, "Subject Changed.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
