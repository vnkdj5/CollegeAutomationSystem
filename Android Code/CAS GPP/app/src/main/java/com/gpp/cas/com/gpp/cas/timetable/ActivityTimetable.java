package com.gpp.cas.com.gpp.cas.timetable;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Vaibhav on 30/03/2016.
 */

public class ActivityTimetable extends AppCompatActivity implements Spinner.OnItemSelectedListener{
ListView listView;
    String userId,userType;
    private ArrayList<String>  daylist=new ArrayList<String>();
    TimetableAdapter adapter;
    Spinner daySpinner;
    String day;
    Button show;
    private JSONArray result;
    List<Timetable> list=new ArrayList<Timetable>();
    Calendar myCalendar = Calendar.getInstance(TimeZone.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        listView = (ListView) findViewById(R.id.listViewTimetable);
        daySpinner=(Spinner) findViewById(R.id.spinnerDay);
        show=(Button) findViewById(R.id.buttonDaySelect);
        SharedPreferences sp=getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        userId=sp.getString(Config.USERNAME_SHARED_PREF,null);
        userType=sp.getString(Config.USER_TYPE,null);

       getData();
        adapter=new TimetableAdapter(this, list);
        getDays();
        daySpinner.setOnItemSelectedListener(this);

       show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int j=0;j<adapter.getCount();j++)
                {
                    list.remove(j);
                    list.clear();

                    adapter.notifyDataSetChanged();
                }
                getData();;
            }
        });

    }
    private void getData() {
        //Creating a string request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.TIMETABLE_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject j = null;
                        try {
                            //Parsing the fetched Json String to JSON Object
                            j = new JSONObject(response);

                            //Storing the Array of JSON String to our JSON Array
                            result = j.getJSONArray("timetable");

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
                params.put("user_id", userId);
                params.put("day", day);
                params.put("user_type",userType);

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
                String time = json.getString("time");
                String period = json.getString("period");
                Timetable timetable=new Timetable();
                timetable.setTime(time);
                timetable.setPeriod(period);
                list.add(timetable);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        listView.setAdapter(new TimetableAdapter(this, list));

    }
	//get todays day
    public void getToday()
    {
        int d = myCalendar.get(Calendar.DAY_OF_WEEK);
        switch(d)
        {
            case 2:day="Monday"; break;
            case 3:day="Tuesday"; break;
            case 4:day="Wednesday"; break;
            case 5:day="Thursday"; break;
            case 6:day="Friday"; break;
            case 7:day="Saturday"; break;
            default:day="Sunday";
        }
    }
	//for spinner data
    private void getDays()
    {
        daylist.add("Select Day");
        daylist.add("Monday");
        daylist.add("Tuesday");
        daylist.add("Wednesday");
        daylist.add("Thursday");
        daylist.add("Friday");
        daylist.add("Saturday");
        daySpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,daylist));
    }
    int flag=0;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

if(flag==0)
{
    getToday();getData();
    flag=1;
}
        else {
    day = parent.getItemAtPosition(pos).toString();
    for (int j = 0; j < adapter.getCount(); j++) {
        list.remove(j);
        list.clear();
        list.clear();
        adapter.notifyDataSetChanged();
    }
    getData();
    Toast.makeText(getApplicationContext(),"Timetable Changed",Toast.LENGTH_SHORT).show();
}

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {


    }
}

