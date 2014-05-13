package com.wp.webview;


import java.util.HashMap;

import com.wp.webview.Profile.MyJavaScriptInterface;
import com.wp.webview.dataconnect.ConnectionDetector;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnKeyListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

/*
 * Demo of creating an application to open any URL inside the application and clicking on any link from that URl 
should not open Native browser but  that URL should open in the same screen.

- Load WebView with progress bar
 */
public class Hub extends Activity {
    /** Called when the activity is first created. */
	
	WebView web;
	ProgressBar progressBar;
	 // flag for Internet connection status
    Boolean isInternetPresent = false;
	    		
	 // Connection detector class
    ConnectionDetector cd;
    
 // Session Manager Class
   	SessionManager session;
   	
   	String user_link;
	
   	ImageButton Search;
   	EditText Keyword;
	
	
    @SuppressLint("NewApi")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.hub);
        Search = (ImageButton) findViewById(R.id.search_option);
        
        web = (WebView) findViewById(R.id.webkit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        
        Keyword = (EditText)findViewById(R.id.keyword);
        
        Keyword.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  // Perform action on key press
                 // Toast.makeText(Swipe_Menu.this, Keyword.getText(), Toast.LENGTH_SHORT).show();
                	String value = Keyword.getText().toString(); /// just the value
                	web.loadUrl("javascript:callFromActivity(\""+value+"\")");
                	
                	return true;
                }
                return false;
            }
        });
        
      //Add javasrcipt Function in App
        final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(this);
        web.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        
        // Session class instance
        session = new SessionManager(getApplicationContext());
        
     // get user data from session
        HashMap<String, String> Link = session.getLink();
     
     // get link from share pref    
        user_link = Link.get(SessionManager.KEY_LINK);
        
        Search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(Hub.this, Swipe_Menu.class);
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
                web.loadUrl("file:///android_asset/hub.html");

        }
        else
        {
            // Internet connection is not present
            // Ask user to connect to Internet
            showAlertDialog(Hub.this, "No Internet Connection",
            "Please connect your device to internet", false);
            
            progressBar.setVisibility(View.GONE);
        }

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
 	    
 	    public void RejectInvitation(){
 	    	showAlertDialog(Hub.this, "WARNING",
 	               "You want to reject this invitation?", false);
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
	
	
	
}