package com.wp.webview;


import com.wp.webview.dataconnect.ConnectionDetector;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Profile_Edit extends Activity {
    /** Called when the activity is first created. */
	
	WebView web;
	ProgressBar progressBar;
	 // flag for Internet connection status
    Boolean isInternetPresent = false;
	    		
	 // Connection detector class
    ConnectionDetector cd;
	
    ImageButton Back,Search;
    
    //Upload foto tools
    private ImageView imageview;
    private int serverResponseCode = 0;
    private ProgressDialog dialog = null;
    
    
    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private Intent pictureActionIntent = null;
    Bitmap bitmap;
       
    private String upLoadServerUri = null;
    private String imagepath=null;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.profile_edit);
        Back = (ImageButton) findViewById(R.id.account);
        Search = (ImageButton) findViewById(R.id.search_option);
        
        web = (WebView) findViewById(R.id.webkit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        
        //Add javasrcipt Function in App
        final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(this);
        web.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        
        // getting intent data
        Bundle bundle = getIntent().getExtras();
        
        
       Search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(Profile_Edit.this, Swipe_Menu.class);
				startActivity(i);
			}
		});
        
        /* Check Internet connection */
        cd = new ConnectionDetector(getApplicationContext());
            
     // get Internet status
        isInternetPresent = cd.isConnectingToInternet();
        if (isInternetPresent) 
        {
        	  web.setWebViewClient(new myWebClient());
              web.getSettings().setJavaScriptEnabled(true);
              web.loadUrl("file:///android_asset/profile-edit.html");
              
        }
        else
        {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(Profile_Edit.this, "No Internet Connection",
            "Please connect your device to internet", false);
            
            progressBar.setVisibility(View.GONE);
        }

    }
    
    
    public class myWebClient extends WebViewClient
    {
    	//Allow SSL/Https
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
 	
 	
 	public class MyJavaScriptInterface {
		Context mContext;

	    MyJavaScriptInterface(Context c) {
	        mContext = c;
	    }
	    
	    public void showToast(String toast){
	        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	    }
	    
	    public void UploadPhoto(){
	    	
	    	Intent i = new Intent(Profile_Edit.this, Upload_Image.class);
			startActivity(i);
	    }
	    
	    public void EditData(){
	    	
	    	Intent i = new Intent(Profile_Edit.this, Education_Edit.class);
			startActivity(i);
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
	
 // Hide The App
    public void onBackPressed()
    {
    	Intent i = new Intent(Profile_Edit.this, Profile.class);
		startActivity(i);
        
    }
	
	
}