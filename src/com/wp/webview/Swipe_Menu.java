package com.wp.webview;

import com.wp.webview.dataconnect.ViewPagerAdapter;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


public class Swipe_Menu extends FragmentActivity {
	private ViewPager _mViewPager;
	private ViewPagerAdapter _adapter;
	
	int  backButtonCount = 0;
	
	EditText Keyword;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.swipe_menu);
        
        ImageButton Hub = (ImageButton)findViewById(R.id.hub);
        ImageButton Search = (ImageButton)findViewById(R.id.search_option);
        ImageButton Profile = (ImageButton)findViewById(R.id.profile);
        Keyword = (EditText)findViewById(R.id.keyword);
        
        Keyword.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                    (keyCode == KeyEvent.KEYCODE_ENTER)) {
                  // Perform action on key press
                 // Toast.makeText(Swipe_Menu.this, Keyword.getText(), Toast.LENGTH_SHORT).show();
                	String value = Keyword.getText().toString(); /// just the value
                    sendValueToFragments(value);       
                	
                	return true;
                }
                return false;
            }
        });
        /**
         * Hub button click event
         * */
        Hub.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent (getApplicationContext(), Hub.class);
            	startActivity(i);
			}
		});
        
        /**
         * Profile button click event
         * */
        Profile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent i = new Intent (getApplicationContext(), Profile.class);
            	startActivity(i);
			}
		});
        
        /**
         * Search button click event
         * */
        
       /* Search.setOnClickListener(new View.OnClickListener() {          
            public void onClick(View v) {
                String value = Keyword.getText().toString(); /// just the value
                sendValueToFragments(value);       
            }
        });*/
        
        
        setUpView();
        setTab();
    }
    
    
    protected void sendValueToFragments(String value) {
	    // it has to be the same name as in the fragment
		Intent intent = new Intent("com.arsyah.workedprofile.action.UI_UPDATE");
	    intent.putExtra("UI_KEY", value);
	    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
	}
    
    private void setUpView(){    	
   	 _mViewPager = (ViewPager) findViewById(R.id.viewPager);
     _adapter = new ViewPagerAdapter(getApplicationContext(),getSupportFragmentManager());
     _mViewPager.setAdapter(_adapter);
	 _mViewPager.setCurrentItem(0);
    }
    private void setTab(){
			_mViewPager.setOnPageChangeListener(new OnPageChangeListener(){
			    		
						@Override
						public void onPageScrollStateChanged(int position) {}
						@Override
						public void onPageScrolled(int arg0, float arg1, int arg2) {}
						@Override
						public void onPageSelected(int position) {
							// TODO Auto-generated method stub
							switch(position){
							case 0:
								findViewById(R.id.first_tab).setVisibility(View.VISIBLE);
								findViewById(R.id.second_tab).setVisibility(View.INVISIBLE);
								findViewById(R.id.third_tab).setVisibility(View.INVISIBLE);
								findViewById(R.id.fourth_tab).setVisibility(View.INVISIBLE);
								
								TextView people = (TextView)findViewById(R.id.textView1);
								people.setTypeface(null,Typeface.BOLD);
								TextView jobs = (TextView)findViewById(R.id.textView2);
								jobs.setTypeface(null,Typeface.NORMAL);
								TextView companies = (TextView)findViewById(R.id.textView3);
								companies.setTypeface(null,Typeface.NORMAL);
								TextView groups = (TextView)findViewById(R.id.textView4);
								groups.setTypeface(null,Typeface.NORMAL);
								
								break;
								
							case 1:
								findViewById(R.id.first_tab).setVisibility(View.INVISIBLE);
								findViewById(R.id.second_tab).setVisibility(View.VISIBLE);
								findViewById(R.id.third_tab).setVisibility(View.INVISIBLE);
								findViewById(R.id.fourth_tab).setVisibility(View.INVISIBLE);
								
								TextView people2 = (TextView)findViewById(R.id.textView1);
								people2.setTypeface(null,Typeface.NORMAL);
								TextView jobs2 = (TextView)findViewById(R.id.textView2);
								jobs2.setTypeface(null,Typeface.BOLD);
								TextView companies2 = (TextView)findViewById(R.id.textView3);
								companies2.setTypeface(null,Typeface.NORMAL);
								TextView groups2 = (TextView)findViewById(R.id.textView4);
								groups2.setTypeface(null,Typeface.NORMAL);
								
								break;
								
							case 2:
								findViewById(R.id.first_tab).setVisibility(View.INVISIBLE);
								findViewById(R.id.second_tab).setVisibility(View.INVISIBLE);
								findViewById(R.id.third_tab).setVisibility(View.VISIBLE);
								findViewById(R.id.fourth_tab).setVisibility(View.INVISIBLE);
								
								TextView people3 = (TextView)findViewById(R.id.textView1);
								people3.setTypeface(null,Typeface.NORMAL);
								TextView jobs3 = (TextView)findViewById(R.id.textView2);
								jobs3.setTypeface(null,Typeface.NORMAL);
								TextView companies3 = (TextView)findViewById(R.id.textView3);
								companies3.setTypeface(null,Typeface.BOLD);
								TextView groups3 = (TextView)findViewById(R.id.textView4);
								groups3.setTypeface(null,Typeface.NORMAL);
								
								break;
								
							case 3:
								findViewById(R.id.first_tab).setVisibility(View.INVISIBLE);
								findViewById(R.id.second_tab).setVisibility(View.INVISIBLE);
								findViewById(R.id.third_tab).setVisibility(View.INVISIBLE);
								findViewById(R.id.fourth_tab).setVisibility(View.VISIBLE);
								
								TextView people4 = (TextView)findViewById(R.id.textView1);
								people4.setTypeface(null,Typeface.NORMAL);
								TextView jobs4 = (TextView)findViewById(R.id.textView2);
								jobs4.setTypeface(null,Typeface.NORMAL);
								TextView companies4 = (TextView)findViewById(R.id.textView3);
								companies4.setTypeface(null,Typeface.NORMAL);
								TextView groups4 = (TextView)findViewById(R.id.textView4);
								groups4.setTypeface(null,Typeface.BOLD);
								
								break;
							}
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