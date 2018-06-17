package com.gpp.cas.com.gpp.cas.attendance.show;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;
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
 * Created by Vaibhav on 30/03/2016.
 */
public class ShowAttendance extends AppCompatActivity {
    ListView listView;
    List<Attendance> list=new ArrayList<Attendance>();
    ShowAdapter adapter;
    TextView studentId;
    String student_id;
    private JSONArray result;
    ProgressDialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_ahow);
        listView=(ListView) findViewById(R.id.listViewShowAt);
        studentId=(TextView) findViewById(R.id.student_atten_show);
        loading = ProgressDialog.show(this, "Loading...","Fetching Data",true,true);
        SharedPreferences sp=getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        student_id=sp.getString(Config.USERNAME_SHARED_PREF,null);
        studentId.setText(student_id);
        getData();
        adapter=new ShowAdapter(this,list);

    }

    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.SERVER_URL+"/view_atten2.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray("attendance");

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
                        Toast.makeText(getApplicationContext(), "Server Connection Failed", Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("student_id", student_id);



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
                String subject = json.getString("subject_id");
                String lecP = json.getString("LP");
                String lecA = json.getString("LA");
                String pracP = json.getString("PP");
                String pracA=json.getString("PA");
                String perc=json.getString("perc");
                Attendance attendance=new Attendance();
                attendance.setSubject(subject);
                attendance.setLP(lecP);
                attendance.setLA(lecA);
                attendance.setPP(pracP);
                attendance.setPA(pracA);
                attendance.setPERC(perc);
                list.add(attendance);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        listView.setAdapter(new ShowAdapter(this, list));
        loading.dismiss();

    }
}
