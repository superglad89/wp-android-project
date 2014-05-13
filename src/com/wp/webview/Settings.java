package com.wp.webview;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

/*
 * Demo of creating an application to open any URL inside the application and clicking on any link from that URl 
should not open Native browser but  that URL should open in the same screen.

- Load WebView with progress bar
 */
public class Settings extends Activity {
    /** Called when the activity is first created. */

	// Session Manager Class
	SessionManager session;
	
	ImageButton Back,Search;
	Button Logout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.setting);
        
        Back = (ImageButton) findViewById(R.id.account);
        Search = (ImageButton) findViewById(R.id.search_option);
        Logout = (Button) findViewById(R.id.logout);
        
        // Session class instance
        session = new SessionManager(getApplicationContext());
        
        Back.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				finish();
			}
		});
        
        Search.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent(Settings.this, Swipe_Menu.class);
				startActivity(i);
			}
		});
        
        /**
         * Logout button click event
         * */
        Logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// Clear the session data
				// This will clear all session data and 
				// redirect user to LoginActivity
				session.logoutUser();
				finish();
			}
		});
       
    }
	
}