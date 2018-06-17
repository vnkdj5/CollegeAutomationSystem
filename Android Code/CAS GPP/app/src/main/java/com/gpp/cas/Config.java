package com.gpp.cas;
public class Config {

    //URL to our login.php file
    public static final String SERVER_URL="http://192.168.43.208/cas";
    public static final String LOGIN_URL = SERVER_URL+"/login.php";

 
    //Keys for email and password as defined in our $_POST['key'] in login.php
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
 
    //If server response is equal to this that means login is successful
    public static final String LOGIN_SUCCESS = "success";
 
    //Keys for Sharedpreferences111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111
    //This would be the name of our shared preferences
    public static final String SHARED_PREF_NAME = "myloginapp";
    
    //login type
    public static final String USER_TYPE="usertype";
 
    //This would be used to store the username of current logged in user
    public static final String USERNAME_SHARED_PREF = "username";
 
    //We will use this to store the boolean in sharedpreference to track user is loggedin or not
    public static final String LOGGEDIN_SHARED_PREF = "loggedin";

    public static final String CLASSID_URL = SERVER_URL+"/getSpinnerData.php";
    public static final String SUBJECTID_URL = SERVER_URL+"/get_subject.php" ;
    public static final String ATTENLIST_URL = SERVER_URL+"/getlist.php";
    public static  final  String ATTEN_URL=SERVER_URL+"/atten.php";
    public static final  String TIMETABLE_URL=SERVER_URL+"/timetable_s.php";
    public  static  final String ATTEN_VIEW_URL=SERVER_URL+"/view_atten.php";

    public static final String JSON_ARRAY = "student_list";
    public static final String JSON_ARRAY2 = "subject_list";
    public static final String TAG_CLASSNAME ="class_id" ;
    public static final String TAG_SUBID ="subject_id" ;
    public static final String TAG_TEACHERID ="teacher_id" ;

}