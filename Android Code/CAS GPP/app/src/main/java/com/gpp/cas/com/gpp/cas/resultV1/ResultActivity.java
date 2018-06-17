package com.gpp.cas.com.gpp.cas.resultV1;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gpp.cas.Config;
import com.gpp.cas.MainActivity;
import com.gpp.cas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Vaibhav on 03/04/2016.
 */
public class ResultActivity extends AppCompatActivity {
    ListView listView;
    ResultAdapter adapter;
    List<Result> resultList=new ArrayList<Result>();
    Button btnSubmit;
    ProgressDialog dialog;
    RequestQueue mRequestQueue;
    String class_id,teacher_id,resultType,subject_id,maxMarks,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        listView = (ListView) findViewById(R.id.listViewSt);
        btnSubmit = (Button) findViewById(R.id.buttonSubmitResult);
        dialog=new ProgressDialog(this);
        class_id=getIntent().getStringExtra("classID");
        teacher_id=getIntent().getStringExtra("teacherID");
        resultType=getIntent().getStringExtra("resultType");
        subject_id=getIntent().getStringExtra("subjectID");
        maxMarks=getIntent().getStringExtra("maxMarks");
        url=Config.SERVER_URL+"/result_submit.php?result_type="+resultType+"&max_marks="+maxMarks+"&subject_id="+subject_id;
        dialog.setTitle("Fetching Data...");
        dialog.setMessage("Loading...");
        dialog.show();

        getData();
        adapter=new ResultAdapter(this, resultList);
        listView.setAdapter(adapter);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adapter.notifyDataSetChanged();
                try {
                    JSONArray result = new JSONArray();
                    for (int i = 0; i < resultList.size(); i++) {
                        if((resultList.get(i).getMarks())==null||resultList.get(i).getMarks()=="")
                        {
                            resultList.get(i).setMarks("0");
                        }
                        JSONObject jObjd = new JSONObject();
                        jObjd.put("student_id", resultList.get(i).getEnrollNo());
                        jObjd.put("marks", Integer.parseInt(resultList.get(i).getMarks()));
                        result.put(jObjd);

                    }
                    Toast.makeText(getBaseContext(),"Submitting Result..", Toast.LENGTH_SHORT).show();
                    Log.e("result", result.toString());



                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST,url, result, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    })

                  /*  {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();
                            //Adding parameters to request
                            params.put("result_type",resultType);
                            params.put("max_marks",maxMarks);
                            params.put("subject_id",subject_id);
                            //returning parameter
                            return params;
                        }
                    }
*/
                            ;
                    //Adding the Request
                    mRequestQueue = Volley.newRequestQueue(getApplicationContext());
                  mRequestQueue.add(jsonArrayRequest);
                    resultOk();
                    Toast.makeText(getBaseContext(),"Result Submitted Successfully..", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        })
        ;
    }
    public void getData()
    {
        try
        {
            //Create volley request objects
            JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Config.ATTENLIST_URL+"?class_id="+class_id+"&teacher_id="+teacher_id,
                    new Response.Listener<JSONArray>() {



                        @Override
                        public void onResponse(JSONArray response) {

                            hideDialog();
                            //parsing json
                            for(int i=0;i<response.length();i++){
                                try{
                                    JSONObject obj=response.getJSONObject(i);
                                    Result item=new Result();
                                    item.setEnrollNo(obj.getString("student_id"));
                                    //add to array
                                    resultList.add(item);
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
                    Toast.makeText(getApplicationContext(), "Connection Failed,", Toast.LENGTH_SHORT).show();

                }
            });
            try
            {
                mRequestQueue = Volley.newRequestQueue(this);
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
        alertBack();
    }

    public  void alertBack(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Do you want to cancel adding?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
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
    public void resultOk()
    {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Result Submitted Successfully.");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                        startActivity(intent);
                        //Showing the alert dialog

                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
