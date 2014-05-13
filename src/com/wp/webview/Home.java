package com.wp.webview;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import com.wp.webview.dataconnect.ConnectionDetector;
import com.wp.webview.dataconnect.CustomHttpClient;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

/*
 * Demo of creating an application to open any URL inside the application and clicking on any link from that URl 
should not open Native browser but  that URL should open in the same screen.

- Load WebView with progress bar
 */
public class Home extends Activity {
    /** Called when the activity is first created. */
	
	private static Home instance;
	
	WebView web;
	ProgressBar progressBar;
	 // flag for Internet connection status
    Boolean isInternetPresent = false;
    
    Boolean Server_Response = true;
    
    Context _context;
    
    private ProgressDialog pDialog;
	
	String response, res = null;
	    		
	 // Connection detector class
    ConnectionDetector cd;
    
    int backButtonCount = 0;
    
 // Session Manager Class
   	SessionManager session;
   	
   	String user_link, user_name, password;
   	String user, pass;
	
	Button Back;
	
	
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        instance = this;
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.home);
        
        web = (WebView) findViewById(R.id.webkit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        
      //Add javasrcipt Function in App
        final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(this);
        web.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        
        
        // Get USERNAME & PASSWORD from login activity
        Bundle extras = getIntent().getExtras();
  		if(extras !=null) {
  			
  		     user = extras.getString("USERNAME");
  		     pass = extras.getString("PASSWORD");
  		}
        
        /* Check Internet connection */
        cd = new ConnectionDetector(getApplicationContext());
        
        // Session class instance
        session = new SessionManager(getApplicationContext());
        
     // get user data from session
        HashMap<String, String> Uname = session.getUsernamePassword();
     
     // get username & password from share pref    
        user_name = Uname.get(SessionManager.KEY_USERNAME);
        
        password = Uname.get(SessionManager.KEY_PASSWORD);
            
     // get Internet status
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) 
        {
        	
        	if (user != null && !user.isEmpty() && pass !=null && pass.isEmpty()) 
        	{
        		web.setWebViewClient(new myWebClient());
        	    web.getSettings().setAllowFileAccess(true);
        	    
        	   //to detect website access from webview native 
        	    this.web.getSettings().setUserAgentString(
        	    	    this.web.getSettings().getUserAgentString() 
        	    	    + " "
        	    	    + getString(R.string.user_agent_suffix)
        	    	);
        	    
        	    web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                web.getSettings().setJavaScriptEnabled(true);
                web.loadUrl("https://workedprofile.com:8443/mobileweb/control/userAutoSignIn?USERNAME="+user+"&PASSWORD="+pass);
                //web.loadUrl("https://192.168.0.103:8443/mobileweb/control/main?USERNAME="+user+"&PASSWORD="+pass);
               
        	}
        	else
        	{
        		web.setWebViewClient(new myWebClient());
        	    web.getSettings().setAllowFileAccess(true);
        	    
        	  //to detect website access from webview native 
        	    this.web.getSettings().setUserAgentString(
        	    	    this.web.getSettings().getUserAgentString() 
        	    	    + " "
        	    	    + getString(R.string.user_agent_suffix)
        	    	);
        	    
        	    web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
                web.getSettings().setJavaScriptEnabled(true);
                web.loadUrl("https://workedprofile.com:8443/mobileweb/control/userAutoSignIn?USERNAME="+user_name+"&PASSWORD="+password);
                //web.loadUrl("https://192.168.0.103:8443/mobileweb/control/main?USERNAME="+user_name+"&PASSWORD="+password);
               
        	}
        		
        }
        else
        {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(Home.this, "No Internet Connection",
            "Please connect your device to internet", false);
            
            progressBar.setVisibility(View.GONE);
        }

    }
    
    public static Home getInstance() {
        return instance;
    }
     
    public class myWebClient extends WebViewClient
    {

    	public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) 
    	{
    	     handler.proceed();
    	}
    	
    	@Override
    	public void onPageStarted(WebView view, String url, Bitmap favicon) {
    		// TODO Auto-generated method stub
    		super.onPageStarted(view, url, favicon);
    	}
    	
    	@Override
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		// TODO Auto-generated method stub
    		
    		view.loadUrl(url);
    		return true;
    		
    	}
    	
    	@Override
    	public void onPageFinished(WebView view, String url) {
    		// TODO Auto-generated method stub
    		super.onPageFinished(view, url);
    		
    		progressBar.setVisibility(View.GONE);
    	}
    }
    

	public class MyJavaScriptInterface {
		Context mContext;

	    MyJavaScriptInterface(Context c) {
	        mContext = c;
	    }
	    
	    public void showToast(String toast){
	        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	    }
	    
	    public void goBackAndroid(){
	    	
	    	goBack();
	    }

	    
	    public void openAndroidDialog(String abc){
	    	
	    	new Logout().execute();
	    }

	}
	
 	public class Logout extends AsyncTask<Void, Void, Void>
    {
	
 	  /**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Home.this);
			pDialog.setMessage("Signing out ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}
 	   
 	   @Override
 	   protected Void doInBackground(Void... params) {
 		   // TODO Auto-generated method stub

 		  ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();

		        try{

		        	 response = CustomHttpClient.executeHttpPost("http://workedprofile.com:8080/mobileweb/control/logout-android", postParameters);
		        	//response = CustomHttpClient.executeHttpPost("http://192.168.0.103:8080/mobileweb/control/logout-android", postParameters);
		               
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
						
							//clearApplicationData();
							
							Home.getInstance().clearApplicationData();
							
							ClearWebview();
							
							session.logoutUser();
							
							finish();
							
						}
						
						else
		                {
							   Toast toast= Toast.makeText(getApplicationContext(), "Logout Failed", Toast.LENGTH_SHORT);  
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
 	
 	
	
	protected void ClearWebview() {

   	//Clear all webview cookies
		
	 /*  CookieSyncManager.createInstance(this);
       CookieManager cookieManager = CookieManager.getInstance();
       cookieManager.removeAllCookie();
       cookieManager.setAcceptCookie(false);

       web = new WebView(this);
       WebSettings ws = web.getSettings();
       ws.setSaveFormData(false);
       ws.setSavePassword(false);*/
		
		//web.removeAllViews();
       web.clearHistory();
       web.clearCache(true);

   }
	
	public void clearApplicationData() {
	        File cache = getCacheDir();
	        File appDir = new File(cache.getParent());
	        if (appDir.exists()) {
	            String[] children = appDir.list();
	            for (String s : children) {
	                if (!s.equals("lib")) {
	                    deleteDir(new File(appDir, s));
	                    Log.i("TAG", "**************** File /data/data/APP_PACKAGE/" + s + " DELETED *******************");
	                }
	            }
	        }
	    }
	
	public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
 	
 	public void goBack()
 	{
 		if(web.canGoBack() == true){
            web.goBack();
        }else{
            finish();
        }
   
 	}
    
    
 // To handle "Back" key press event for WebView to go back to previous screen.
 	@Override
 	public boolean onKeyDown(int keyCode, KeyEvent event) 
 	{
 		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
 			
 			web.goBack();
 			
 			return true;
 		}
 		return super.onKeyDown(keyCode, event);
 	
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
    
 // Hide The App
    public void onBackPressed()
    {
        if(backButtonCount >= 1)
        {
        	web.clearHistory();
	        web.clearCache(true);
        	
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            
        }
        else
        {
        	Toast toast= Toast.makeText(getApplicationContext(), "Press again to exit", Toast.LENGTH_SHORT);  
       	   	toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
       	   	toast.show();
       	   	
            backButtonCount++;
        }
    }
    
}