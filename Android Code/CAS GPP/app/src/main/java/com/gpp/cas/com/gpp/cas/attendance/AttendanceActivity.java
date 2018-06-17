package com.gpp.cas.com.gpp.cas.attendance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gpp.cas.Config;
import com.gpp.cas.MainActivity;
import com.gpp.cas.R;
import com.gpp.cas.com.gpp.cas.resultV1.ResultActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttendanceActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private List<Item> array = new ArrayList<Item>();
    private ListView listView;

    private Adapter adapter;
    RequestQueue mRequestQueue;
    public String teacher_id=null, class_id=null, subject_id=null, type_name=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        listView = (ListView) findViewById(R.id.listViewStudents);
        class_id=getIntent().getStringExtra("classID");
        teacher_id=getIntent().getStringExtra("teacherID");
        type_name=getIntent().getStringExtra("attnType");
        subject_id=getIntent().getStringExtra("subjectID");
        adapter=new Adapter(this,array,class_id,subject_id,type_name);
        listView.setAdapter(adapter);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        mRequestQueue=Volley.newRequestQueue(this);


        try
        {
            //Create volley request objects
            JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Config.ATTENLIST_URL+"?class_id="+class_id+"&teacher_id="+teacher_id,
                    new Response.Listener<JSONArray>() {



                        @Override
                        public void onResponse(JSONArray response) {

                            hideDialog();
                            //Toast.makeText(getApplicationContext(),response.toString()+Config.ATTENLIST_URL+"?class_id="+class_id+"&teacher_id="+teacher_id,Toast.LENGTH_SHORT).show();
                            //parsing json
                            for(int i=0;i<response.length();i++){
                                try{
                                    JSONObject obj=response.getJSONObject(i);
                                    Item item=new Item();
                                    item.setEnroll_no(obj.getString("student_id"));
                                    //add to array
                                    array.add(item);
                                }catch(JSONException ex){
                                    ex.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "JSON ERROR", Toast.LENGTH_SHORT).show();

                                }

                            }
                            adapter.notifyDataSetChanged();


                        }}, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    hideDialog();
                    Toast.makeText(getApplicationContext(), "Volley ERROR", Toast.LENGTH_SHORT).show();

                }
            });
            try
            {
                // Adding request to request queue
                mRequestQueue.add(jsonArrayRequest);
            }
            catch(Exception e)
            {
                Toast.makeText(getApplicationContext(), "Adding request error", Toast.LENGTH_SHORT).show();

            }
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error in request", Toast.LENGTH_SHORT).show();
        }
    }

    public void hideDialog(){
        if(dialog !=null){
            dialog.dismiss();
            dialog=null;
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hideDialog();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        alertBack();

    }
    public  void alertBack(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to cancel attendance?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(AttendanceActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });

        alertDialogBuilder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
    public void attendanceOK(View v)
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Attendance Submitted Successfully.");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(AttendanceActivity.this, MainActivity.class);
                        startActivity(intent);
                        //Showing the alert dialog

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
