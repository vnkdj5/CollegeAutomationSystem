package com.gpp.cas;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by Vaibhav on 28/03/2016.
 */
public class TimetableActivity extends AppCompatActivity implements Spinner.OnItemSelectedListener{
    ListView listView;
    Spinner daySpinner;
    String day;
    Button show;
    private ArrayList<String>  daylist=new ArrayList<String>();
    private JSONArray result;
    private ArrayList<String> timelist=new ArrayList<String>();
    Calendar myCalendar = Calendar.getInstance(TimeZone.getDefault());
    ArrayAdapter<String> Res;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);
        listView = (ListView) findViewById(R.id.listViewTimetable);
        daySpinner=(Spinner) findViewById(R.id.spinnerDay);
        show=(Button) findViewById(R.id.buttonDaySelect);
        getToday();
        getData();
        Res = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timelist);
        listView.setAdapter(Res);
        getDays();
        daySpinner.setOnItemSelectedListener(this);

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int j=0;j<timelist.size();j++)
                {
                    timelist.remove(j);
                    Res.clear();
                    Res.notifyDataSetChanged();
                }
            getData();;
            }
        });

    }
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
 private void getDays()
 {
     daylist.add("Monday");
     daylist.add("Tuesday");
     daylist.add("Wednesday");
     daylist.add("Thursday");
     daylist.add("Friday");
     daylist.add("Saturday");
     daySpinner.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,daylist));
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

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //Adding parameters to request
                params.put("class_id", "COMPA1");
                params.put("day", day);


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
                String comb = time + "\t" + period+"";
                timelist.add(comb);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        //Setting adapter to show the items in the spinner
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, timelist));

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        day=parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
//note: Create arraylist  fetch data using StringRequest,  Create Simple listView done and Add spinner for day selection