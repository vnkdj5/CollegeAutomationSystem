package com.gpp.cas.com.gpp.cas.result;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.gpp.cas.Config;
import com.gpp.cas.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vaibhav on 24/03/2016.
 */
public class ResultActivity extends Activity {
    ListView listView;
    ResultAdapter adapter;
    Button btnSubmit;
    ProgressDialog dialog;
    RequestQueue mRequestQueue;
    String class_id="COMPA1",teacher_id="CM0601";
    private List<Item> array = new ArrayList<Item>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        listView = (ListView) findViewById(R.id.listViewSt);
        btnSubmit = (Button) findViewById(R.id.buttonSubmitResult);
        adapter=new ResultAdapter(this, array);
        listView.setAdapter(adapter);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                for (int i = 0; i < array.size(); i++) {
                    Toast.makeText(getBaseContext(), "Name : "+array.get(i).getEnrollNo() +" text: "+array.get(i).getMark(), Toast.LENGTH_SHORT).show();

                }
            }
        });

        dialog=new ProgressDialog(this);
        dialog.setTitle("Fetching Data...");
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
                            Toast.makeText(getApplicationContext(),response.toString()+Config.ATTENLIST_URL+"?class_id="+class_id+"&teacher_id="+teacher_id,Toast.LENGTH_SHORT).show();
                            //parsing json
                            for(int i=0;i<response.length();i++){
                                try{
                                    JSONObject obj=response.getJSONObject(i);
                                    Item item=new Item();
                                    item.setEnrollNo(obj.getString("student_id"));
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
}
