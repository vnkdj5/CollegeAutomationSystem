package com.gpp.cas.com.gpp.cas.attendance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

/**
 * Created by vaibhav
 */
public class Adapter extends BaseAdapter{
    private LayoutInflater inflater;
    private Activity activity;
    private List<Item> items;
	String class_id,subject_id,type_name;
 
   
    public Adapter(Activity activity,List<Item> items,String class_id,String subject_id,String type_name){
        this.activity=activity;
        this.items=items;
		this.class_id=class_id;
		this.subject_id=subject_id;
		this.type_name=type_name;

		//PAss these parameters to the server in request method of buttons

    }
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       View v=convertView;
    	try
    	{
    		if(inflater==null)
    		{
    	
            inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		}
    		
        if(convertView ==null)
        {
            convertView=inflater.inflate(R.layout.student_list,null);
        }
       
           
            final TextView enroll= (TextView) convertView.findViewById(R.id.student_name);
            final Button btnPresent=(Button) convertView.findViewById(R.id.buttonPresent);
            final Button btnAbsent=(Button) convertView.findViewById(R.id.buttonAbsent);
            //getting data for row
            Item item=items.get(position);
            
            //title
            enroll.setText(item.getEnroll_no());
            
            btnPresent.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				final String status="1";

				
				

				// Instantiate the RequestQueue.
				RequestQueue queue = Volley.newRequestQueue(activity);


				// Request a string response from the provided URL.
				StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ATTEN_URL,
				            new Response.Listener<String>() {
				    @Override
				    public void onResponse(String response) {
				        // Display the first 500 characters of the response string.
				       // Toast.makeText(activity, response, Toast.LENGTH_LONG).show();
				    }
				}, new Response.ErrorListener() {
				    @Override
				    public void onErrorResponse(VolleyError error) {
				       
				    }
				})
				{
		            @Override
		            protected Map<String, String> getParams() throws AuthFailureError {
		                Map<String,String> params = new HashMap<>();
		                //Adding parameters to request
		                params.put("student_id", enroll.getText().toString());
						params.put("class_id",class_id);
						params.put("subject_id",subject_id);
						params.put("type",type_name);
		                params.put("status", status);

		                //returning parameter
		                return params;
		            }
		        };
				// Add the request to the RequestQueue.
				queue.add(stringRequest);

				items.remove(position); //or some other task

		            notifyDataSetChanged();
				}
			});
          
            btnAbsent.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final String status="0";
					// Instantiate the RequestQueue.
					RequestQueue queue = Volley.newRequestQueue(activity);

					// Request a string response from the provided URL.
					StringRequest stringRequest = new StringRequest(Request.Method.POST, Config.ATTEN_URL,
					            new Response.Listener<String>() {
					    @Override
					    public void onResponse(String response) {
					      //  Toast.makeText(activity, response, Toast.LENGTH_LONG).show();
					    }
					}, new Response.ErrorListener() {
					    @Override
					    public void onErrorResponse(VolleyError error) {
					       
					    }
					})
					{
			            @Override
			            protected Map<String, String> getParams() throws AuthFailureError {
			                Map<String,String> params = new HashMap<>();
			                //Adding parameters to request
			                params.put("student_id", enroll.getText().toString());
                            params.put("class_id",class_id);
                            params.put("subject_id",subject_id);
                            params.put("type",type_name);
			                params.put("status", status);

			                //returning parameter
			                return params;
			            }
			        };
					// Add the request to the RequestQueue.
					queue.add(stringRequest);

					items.remove(position); //or some other task
			           notifyDataSetChanged();

				}
			});

        return convertView;
    
    }
    	catch(Exception e){
Toast.makeText(activity, "Adapter Error", Toast.LENGTH_LONG).show();
    	}
    	return v;
    	}


}