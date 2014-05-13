package com.wp.webview;

import com.wp.webview.Home.myWebClient;
import com.wp.webview.dataconnect.ConnectionDetector;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Store_Swipe extends Fragment {

    public static final String ACTION_INTENT = "com.arsyah.workedprofile.action.UI_UPDATE";
    String search_keyword, Pilihan;
    static WebView web;
    private ListView lv;
	ProgressBar progressBar;
	int backButtonCount = 0;
	
	ArrayAdapter<String> adapter;
	//private Context _context;
	 // flag for Internet connection status
    Boolean isInternetPresent = false;
    
    // Connection detector class
    ConnectionDetector cd;

    //String KeyWord = value ;
    String keyword = null;

    
    protected BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(ACTION_INTENT.equals(intent.getAction())) {
            	
            	keyword = intent.getStringExtra("UI_KEY");
            	
            	 if(keyword != null)
                 {
            		 
            		lv.setVisibility(View.INVISIBLE);
 			    	web.setVisibility(View.VISIBLE);
                 	RefreshPage();
                 }
            	
            }
        }

    };

    
    public static Fragment newInstance(Context context) {
    	Store_Swipe f = new Store_Swipe();
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentFilter filter = new IntentFilter(ACTION_INTENT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(receiver, filter);
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        
        View view = inflater.inflate(R.layout.store_swipe, container, false);
        
        init(view);
       
        return view;
    }

    void init(View view) {
       
        
        web = (WebView)view.findViewById(R.id.webkit);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
        
        // Listview Data
        String[] Category = {"All Categories","Fashion", "Smartphone", "Food", "Computer", "Camera",
        						"Car", "MotorCycle", "Jewerly", "Music", "Book", 
        						"Automotive"};
        
        lv = (ListView)view.findViewById(R.id.list_view);
        
        
        // Adding items to listview
        adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.list_row, R.id.product_name, Category);
        lv.setAdapter(adapter);
        
        // Click event for single list row
        lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

				// Get the item that was clicked
				// Menangkap nilai text yang dklik
				Object o = Store_Swipe.this.adapter.getItem(position);
				keyword = o.toString();
								
				// get Internet status
			     boolean isInternetPresent = cd.isConnectingToInternet();

			     // check for Internet status
			     if (isInternetPresent) 
			     {
			          
			    	 lv.setVisibility(View.INVISIBLE);
			    	 web.setVisibility(View.VISIBLE);
			    	 RefreshPage();
			    	 /*Intent in = new Intent(getActivity().getApplicationContext(), Timeline.class);
					 in.putExtra(TAG_PILIHAN, pilihan);
		             startActivity(in);*/
			    	 
			    	 
			     }
			     else {
		             // Internet connection is not present
		             // Ask user to connect to Internet
		             /*showAlertDialog(People_Swipe.this, "No Internet Connection",
		                     "Please connect your device to internet and restart the application", false);*/
		             
		             	Toast toast= Toast.makeText(getActivity().getApplicationContext(), "No internet connection, Please connect your device to internet", Toast.LENGTH_SHORT);  
		           	   	toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
		           	   	toast.show();
		             
		           // setContentView(R.layout.connection_error ); /* display blank / error display in the screen when no internet connection */
		         }
				

			}

			
		});		
        
        /* Check Internet connection */
        cd = new ConnectionDetector(getActivity().getApplicationContext());
            
     // get Internet status
        isInternetPresent = cd.isConnectingToInternet();
        
      //Add javasrcipt Function in App
        final MyJavaScriptInterface myJavaScriptInterface = new MyJavaScriptInterface(getActivity().getParent());
        web.addJavascriptInterface(myJavaScriptInterface, "AndroidFunction");
        
   /* 
        if(keyword != null)
        {

        	RefreshPage();
        }
        else
        {
        	
        	
            if (isInternetPresent) 
            {                
                  web.setWebViewClient(new myWebClient());
                  web.getSettings().setJavaScriptEnabled(true);
              	  web.loadUrl("http://mobile.workedprofile.com/mobile/linkedin/search-people.html");
                  
            }
            else
            {
                // Internet connection is not present
                // Ask user to connect to Internet
            	web.loadUrl("about:blank");
                progressBar.setVisibility(View.GONE);
                
                Toast toast= Toast.makeText(getActivity().getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT);  
          	   	toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
          	   	toast.show();
            }

        	
        } */
        

    }
    

	protected void ClearWebview() {

    	web.removeAllViews();
        web.clearHistory();
        web.clearCache(true);
        web.loadUrl("about:blank");
 
    }
    
    @SuppressLint("NewApi")
	protected void RefreshPage() {

    	progressBar.setVisibility(View.VISIBLE);
    	
    	 /* Check Internet connection */
        cd = new ConnectionDetector(getActivity().getApplicationContext());
            
     // get Internet status
        isInternetPresent = cd.isConnectingToInternet();
    	
    	if (keyword != null && !keyword.isEmpty()) 
         {
    		 
    		
    		 if (isInternetPresent) 
             {                
    			 	web.setWebViewClient(new myWebClient());
    	    	    web.getSettings().setJavaScriptEnabled(true);
    	    	    file:///android_asset/mypage.html
    	    	    web.loadUrl("file:///android_asset/store-list.html");
    	    	    //web.loadUrl("http://mobile.workedprofile.com/mobile/linkedin/search-people.html");
                   
             }
             else
             {
                 // Internet connection is not present
                 // Ask user to connect to Internet
             	web.loadUrl("about:blank");
                 progressBar.setVisibility(View.GONE);
                 
                Toast toast= Toast.makeText(getActivity().getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT);  
           	   	toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
           	   	toast.show();
             }

         }
         else
         {
        	 
        	 	lv.setVisibility(View.INVISIBLE);
	     	 	web.setVisibility(View.GONE);
	     	 	progressBar.setVisibility(View.GONE);
        	
                 Toast toast= Toast.makeText(getActivity().getApplicationContext(), "Please Insert Your Keyword", Toast.LENGTH_SHORT);  
           	   	 toast.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_HORIZONTAL, 0, 0);
           	   	 toast.show();
         } 
    	
    	
     }
    
    public boolean onKeyDown(int keyCode, KeyEvent event) 
 	{
 		if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
 			web.goBack();
 			return true;
 		}
 		return super.getActivity().onKeyDown(keyCode, event);
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
    
  //Native Webview Interconnection 
    public class MyJavaScriptInterface {
		Context mContext;

	    MyJavaScriptInterface(Context c) {
	        mContext = c;
	    }
	    
	    public void showToast(String toast){
	        Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show();
	    }
	    
	    public void StoreDetail(){
    		
	    	Intent in = new Intent (getActivity().getApplicationContext(), Store_Detail.class);
        	startActivity(in);

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

