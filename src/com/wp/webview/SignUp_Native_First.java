package com.wp.webview;

 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.wp.webview.dataconnect.ConnectionDetector;
import com.wp.webview.dataconnect.CustomHttpClient;
import com.wp.webview.dataconnect.JSONParser;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.wp.webview.dataconnect.ConnectionDetector;

public class SignUp_Native_First extends Activity {
   
   EditText full_name, nick_name, email, password, re_password;
   RadioGroup radioSexGroup;
   RadioButton radioSexButton;
   Button Join;
   String i, res;
   String response = null;
   String radiovalue;
   
   Boolean Server_Response = true;
   
   
 // flag for Internet connection status
   Boolean isInternetPresent = false;
   
   Boolean isValid = false;
   
   
   // Connection detector class
   ConnectionDetector cd;
   
// Session Manager Class
   SessionManager session;
   
   private ProgressDialog pDialog;
   
   ArrayList<HashMap<String, String>> DataList;

   
    /** Called when the activity is first created. */
   
   @Override
   
   
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
      //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.signup_native_first);
        
        // creating connection detector class instance
        cd = new com.wp.webview.dataconnect.ConnectionDetector(getApplicationContext());
        
        DataList = new ArrayList<HashMap<String, String>>();
       
        
        email=(EditText)findViewById(R.id.email);
        
        password=(EditText)findViewById(R.id.password);
        
        re_password=(EditText)findViewById(R.id.re_password);

        Join=(Button)findViewById(R.id.next);
             
        
     // Session Manager
        session = new SessionManager(getApplicationContext());   
          
 
        Join.setOnClickListener(new View.OnClickListener() {
         
         @Override
         
         public void onClick(View v) {
            
        	 // send string to email check
        	 isEmailValid(email.getText().toString());
        	 
        	 // get Internet status
             isInternetPresent = cd.isConnectingToInternet();
             
          // check for Internet status
             if (isInternetPresent) 
             {
            	
	        	 if (email.getText().toString().length() == 0){
	        		 Toast toast = Toast.makeText(getApplicationContext(),"Please enter your email", Toast.LENGTH_SHORT);
	        		 toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
	        		 toast.show();
	        	 }
	        	 else if (password.getText().toString().length() == 0 ){
	        		 Toast toast = Toast.makeText(getApplicationContext(),"Please enter your password", Toast.LENGTH_SHORT);
	        		 toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
	        		 toast.show();
	        	 }   
	        	
	        	 else if (re_password.getText().toString().length() == 0){
	        		 Toast toast = Toast.makeText(getApplicationContext(),"Please enter your re-password", Toast.LENGTH_SHORT);
	        		 toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
	        		 toast.show();
	        	 }
	        	 else if (isValid == false){
	        		 Toast toast = Toast.makeText(getApplicationContext(),"Wrong email format", Toast.LENGTH_SHORT);
	        		 toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
	        		 toast.show();
	        	 }
            	 
	        	 else if (password.getText().toString().length() < 5){
	        		 Toast toast = Toast.makeText(getApplicationContext(),"Password minimal 5 characters", Toast.LENGTH_SHORT);
	        		 toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
	        		 toast.show();
	        	 }   
	        	 else if (re_password.getText().toString().length() < 5){
	        		 Toast toast = Toast.makeText(getApplicationContext(),"Re-password, minimal 5 character", Toast.LENGTH_SHORT);
	        		 toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
	        		 toast.show();
	        	 }
	        	 else if (!re_password.getText().toString().equals(password.getText().toString())){
	        		 Toast toast = Toast.makeText(getApplicationContext(),"Re-password doesn't match with password", Toast.LENGTH_SHORT);
	        		 toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
	        		 toast.show();
	        	 }

	        	 else 
	        	 {
	        	
	        		 // new Join().execute();
	        		 
	        		 Intent i = new Intent(getApplicationContext(), SignUp_Native_Second.class);
	        		 i.putExtra("Email", email.getText().toString());
					 i.putExtra("Password", password.getText().toString());
					 i.putExtra("Re-Passwod", re_password.getText().toString());
					 startActivity(i);
	        	 }
         }
         else
         {
        	 showAlertDialog(SignUp_Native_First.this, "No Internet Connection",
 	         "Please connect your device to internet and restart the application", false);
         }
        	 
        }
          
            
      });
         
   }
   
  // Check email format in Edittext 
   public boolean isEmailValid(String email) {
	   //boolean isValid = false;

	    String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
	    CharSequence inputStr = email;

	    Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
	    Matcher matcher = pattern.matcher(inputStr);
	    if (matcher.matches()) {
	        isValid = true;
	        
	        System.out.println("Email Format : True");
	    }
	    
	    System.out.println("Email Format : False");
	    return isValid;
	}
  
   
	public class Join extends AsyncTask<Void, Void, Void>
       {
   	
    	  /**
  		 * Before starting background thread Show Progress Dialog
  		 * */
  		@Override
  		protected void onPreExecute() {
  			super.onPreExecute();
  			pDialog = new ProgressDialog(SignUp_Native_First.this);
  			pDialog.setMessage("Please Wait ...");
  			pDialog.setIndeterminate(false);
  			pDialog.setCancelable(true);
  			pDialog.show();
  		}
    	   
    	   @Override
    	   protected Void doInBackground(Void... params) {
    		   // TODO Auto-generated method stub

    		  ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
              
              postParameters.add(new BasicNameValuePair("userLoginId", email.getText().toString()));
//              postParameters.add(new BasicNameValuePair("currentPassword", password.getText().toString()));
//              postParameters.add(new BasicNameValuePair("currentPasswordVerify", re_password.getText().toString()));
              
    	

		        try{

		        	   response = CustomHttpClient.executeHttpPost("http://workedprofile.com:8080/mobileweb/control/signup-user-android", postParameters);
		              
		        	   //response = CustomHttpClient.executeHttpPost("http://192.168.0.103:8080/mobileweb/control/signup-user-android", postParameters);
		               
		               res = response.toString();
		               
		               res = res.trim();
		               
		               res = res.replaceAll("\\s+","");
		               
		               //just get number/int value in web response
		               res = res.replaceFirst(".*?(\\d+).*", "$1");
		               
		               System.out.println("RESULT :" + res);
		               
		             
		        }
		         		   	
		        catch (ClientProtocolException e)
		        {
		        	Log.e("log_tag", "HTTP Error", e);
		        	Server_Response = false;
		          	
		        } 		   	
		        catch(Exception e) 
	     	  	{
	     		    Log.e("log_tag", "Error in http connection"+e.toString());
	     		    Server_Response = false;
	     		    
	     		}
	 		       return null;
		       
    	   }
    	   
    	   protected void onPostExecute(Void result) {
    		   // TODO Auto-generated method stub
    		  pDialog.dismiss();
 			// updating UI from Background Thread
    		  runOnUiThread(new Runnable() {
 				public void run() {
 					/**
 					 * Updating parsed JSON data into ListView
 					 * */
 					if (Server_Response == true) 	
 					{
	
	 					// if(DataList.size() > 0)
	 					if (res.equals("1")) 
	 	                {
							//session.createUsernamePasswordSession(un.getText().toString(), pw.getText().toString());
	 						/* 
	 						Toast toast = Toast.makeText(getApplicationContext(),"Join Success, Please login now", Toast.LENGTH_SHORT);
	 		        		 toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
	 		        		 toast.show();*/
	 					

	 						Intent i = new Intent(getApplicationContext(), SignUp_Native_Second.class);
	 						i.putExtra("Email", email.getText().toString());
	 						i.putExtra("Password", password.getText().toString());
	 						i.putExtra("Re-Passwod", re_password.getText().toString());
	 						startActivity(i);
		
		
	 					}
						
						else
	 	                {
							   Toast toast= Toast.makeText(getApplicationContext(), "Email already used, please use another email", Toast.LENGTH_SHORT);  
		 		        	   toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		 		        	   toast.show();
	 	                }
 					
 					}
 					else
 					{
 							Toast toast= Toast.makeText(getApplicationContext(), "Error, can not connect to server", Toast.LENGTH_SHORT);  
 							toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
 							toast.show();
 					}
				        
 				}

 			});

    		   
    	   }
		
       }
   

// SHow alert dialog internet connection
   public void showAlertDialog(Context context, String title, String message, Boolean status) {
       AlertDialog alertDialog = new AlertDialog.Builder(context).create();

       // Setting Dialog Title
       alertDialog.setTitle(title);

       // Setting Dialog Message
       alertDialog.setMessage(message);

       // Setting alert dialog icon
       alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);

       // Setting OK Button
       alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int which) {
           }
       });

       // Showing Alert Message
       alertDialog.show();
   } 
   
// back to register
   public void onBackPressed() {
	   
	   Intent i = new Intent (getApplicationContext(), LoginFirst.class);
   	   startActivity(i);
   	  
   	 }
  
  
}

