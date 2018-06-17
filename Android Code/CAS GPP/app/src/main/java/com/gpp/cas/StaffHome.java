package com.gpp.cas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.gpp.cas.com.gpp.cas.NoticeActivity;
import com.gpp.cas.com.gpp.cas.attendance.SelectActivity;
import com.gpp.cas.com.gpp.cas.attendance.show.ShowAttendanceTeacher;
import com.gpp.cas.com.gpp.cas.timetable.ActivityTimetable;

public class StaffHome extends AppCompatActivity {
	
	Button buttonLogout,btnAttendance,btnResult;
    TextView teacher_id;
    int backButtonCount=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staff_main);
		buttonLogout=(Button) findViewById(R.id.buttonLogout);
        teacher_id=(TextView) findViewById(R.id.teacher_id_tv);
        SharedPreferences sp=getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
        teacher_id.setText(sp.getString(Config.USERNAME_SHARED_PREF, null));
        btnAttendance=(Button) findViewById(R.id.buttonAttendance);
        btnResult=(Button) findViewById(R.id.buttonResult);
        backButtonCount=0;
                btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(StaffHome.this, SelectActivity.class));
            }
        });
        buttonLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
				logout();					
			}
        });
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(StaffHome.this, com.gpp.cas.com.gpp.cas.resultV1.SelectResultActivity.class));
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Error in Staff Home", Toast.LENGTH_SHORT).show();
                }
            }
        });
	}

    public void showTeacherTimetable( View v)
    {
        startActivity(new Intent(StaffHome.this, ActivityTimetable.class));
    }
    public void showNoticeActivity(View v)
        {
            startActivity(new Intent(StaffHome.this, NoticeActivity.class));
    }
    public void showAttendanceTeacher(View v)
    {
        startActivity(new Intent(StaffHome.this, ShowAttendanceTeacher.class));
    }
    @Override
    protected void onResume() {
        super.onResume();
        backButtonCount=0;
    }
    @Override
    public void onBackPressed() {
        if(backButtonCount >= 1) {
            logout();
        }
        else
        {
            Toast.makeText(this, "Press the back button once again to logout.", Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }
    }
	//Logout function
    public  void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
 
                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(Config.SHARED_PREF_NAME,Context.MODE_PRIVATE);
                        //Getting editor
                        SharedPreferences.Editor editor = preferences.edit();
 
                        //Puting the value false for loggedin
                        editor.putBoolean(Config.LOGGEDIN_SHARED_PREF, false);
 
                        //Putting blank value to username
                        editor.putString(Config.USERNAME_SHARED_PREF, "");
                        editor.putString(Config.USER_TYPE, "");
                        //Saving the sharedpreferences
                        editor.commit();
 
                        //Starting login activity
                        Intent intent = new Intent(StaffHome.this, MainActivity.class);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.logout) {
            logout();
        }
        if(id==R.id.about)
        {
            startActivity(new Intent(this,aboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }


}
