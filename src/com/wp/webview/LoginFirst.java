package com.wp.webview;


import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

/*
 * Demo of creating an application to open any URL inside the application and clicking on any link from that URl 
should not open Native browser but  that URL should open in the same screen.

- Load WebView with progress bar
 */
public class LoginFirst extends Activity {
    /** Called when the activity is first created. */

	int backButtonCount = 0;
	
	Button Register, Login;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setContentView(R.layout.login_first);
        
        Register = (Button) this.findViewById(R.id.button_register);
		Login = (Button) this.findViewById(R.id.button_login);
        
        Register.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent (getApplicationContext(), SignUp_Native_First.class);
            	startActivity(i);
			}
		});
		
		Login.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent (getApplicationContext(), Login.class);
	            	startActivity(i);
				}
		});
       
    }
	
    
    // Hide The App
    public void onBackPressed()
    {
		if(backButtonCount >= 1)
        {
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