package com.wp.webview;

 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import com.wp.webview.dataconnect.ConnectionDetector;
import com.wp.webview.dataconnect.CustomHttpClient;
import com.wp.webview.dataconnect.JSONParser;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wp.webview.dataconnect.ConnectionDetector;

public class Login extends ListActivity {
   
   EditText un,pw;
   TextView Join, id;
   Button login;
   String i,id_user, email_user, nama_user, res;
   String response = null;

   Boolean Server_Response = true;
   
 // flag for Internet connection status
   Boolean isInternetPresent = false;
   
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
        setContentView(R.layout.login);
        
        // creating connection detector class instance
        cd = new com.wp.webview.dataconnect.ConnectionDetector(getApplicationContext());
        
        DataList = new ArrayList<HashMap<String, String>>();
       
        un=(EditText)findViewById(R.id.username);
       
        pw=(EditText)findViewById(R.id.password);
       
        login=(Button)findViewById(R.id.button_login);
        
        Join=(TextView)findViewById(R.id.join);
             
        
     // Session Manager
        session = new SessionManager(getApplicationContext());   
          
		/*forgot_password.setText(Html.fromHtml("<a href=\"http://192.168.0.118/linkedin/forgotpassword.html\"><b>Forgot Password</b></a> "));
		forgot_password.setMovementMethod(LinkMovementMethod.getInstance());*/ 
        
        Join.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
        		 
				Intent i = new Intent (getApplicationContext(), SignUp_Native_First.class);
            	startActivity(i);
			}
        });
        
        login.setOnClickListener(new View.OnClickListener() {
         
         @Override
         
         public void onClick(View v) {
            
        	 // get Internet status
             isInternetPresent = cd.isConnectingToInternet();
             
          // check for Internet status
             if (isInternetPresent) 
             {

            	//Get username, password from EditText
				//email = un.getText().toString();
				//password = pw.getText().toString();
            	 
            	 if (un.getText().toString().length() == 0 ) {
					
	        		 Toast toast = Toast.makeText(getApplicationContext(),"Please enter your email", Toast.LENGTH_SHORT);
	        		 toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
	        		 toast.show();
	        	 }
	        	 else if (pw.getText().toString().length() == 0){
	        		 Toast toast = Toast.makeText(getApplicationContext(),"Please enter your password", Toast.LENGTH_SHORT);
	        		 toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
	        		 toast.show();
	        	 }
	        	 else 
	        	 {
	        		 new CheckData().execute();
	        		 
	        		/* Intent i = new Intent(getApplicationContext(), Home.class);
					 startActivity(i);*/

	        	 }
         }
         else
         {
        	 showAlertDialog(Login.this, "No Internet Connection",
 	         "Please connect your device to internet and restart the application", false);
         }
        	 
        }
          
            
      });
         
   }
   
   
	public class CheckData extends AsyncTask<Void, Void, Void>
       {
   	
    	  /**
  		 * Before starting background thread Show Progress Dialog
  		 * */
  		@Override
  		protected void onPreExecute() {
  			super.onPreExecute();
  			pDialog = new ProgressDialog(Login.this);
  			pDialog.setMessage("Signing in ...");
  			pDialog.setIndeterminate(false);
  			pDialog.setCancelable(true);
  			pDialog.show();
  		}
    	   
    	   @Override
    	   protected Void doInBackground(Void... params) {
    		   // TODO Auto-generated method stub

    		  ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
              
              postParameters.add(new BasicNameValuePair("USERNAME", un.getText().toString()));
                 
              postParameters.add(new BasicNameValuePair("PASSWORD", pw.getText().toString()));
    	

		        try{

		        	response = CustomHttpClient.executeHttpPost("http://workedprofile.com:8080/mobileweb/control/check-login-android", postParameters);   
		        	//response = CustomHttpClient.executeHttpPost("http://192.168.0.103:8080/mobileweb/control/check-login-android", postParameters);
		               
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
						
							session.createUsernamePasswordSession(un.getText().toString(), pw.getText().toString());
	 						
	 						/*Intent i = new Intent(getApplicationContext(), Home.class);
	 						i.putExtra("USERNAME",un.getText().toString());
	 						i.putExtra("PASSWORD",pw.getText().toString());
	 						startActivity(i);*/
							
							Intent i = new Intent(getApplicationContext(), Swipe_Menu.class);
	 						startActivity(i);
		
	 					}
						
						else
	 	                {
							   Toast toast= Toast.makeText(getApplicationContext(), "Sorry!! Wrong Username or Password Entered", Toast.LENGTH_SHORT);  
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

