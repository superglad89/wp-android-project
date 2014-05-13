package com.wp.webview;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;


public class SplashActivity extends Activity {

	// Session Manager Class
	SessionManager session;
	
	// flag for Internet connection status
    Boolean isLogin = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
			
		// Session class instance
        session = new SessionManager(getApplicationContext());
		
		Thread timer = new Thread() {
			public void run() {
				try {
					//berapa lama splashscreen akan ditampilkan dalam milisecond
					sleep(3000);
				} catch (InterruptedException e) {
					// TODO: handle exception
					e.printStackTrace();
				} finally {
					//activity yang akan dijalankan setelah splashscreen selesai
					
					
					// get Login status
		            isLogin = session.isLoggedIn();
		            
		            if(isLogin)
			            {
				            	Intent i = new Intent(SplashActivity.this, Swipe_Menu.class);
								startActivity(i);
			            }
		           
		            
		            else
			            {
			            	Intent i = new Intent (getApplicationContext(), LoginFirst.class);
			            	startActivity(i);
			            }

				}
			}
		};
		timer.start();
	}

}

