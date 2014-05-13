package com.wp.webview;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.Gravity;
import android.widget.Toast;

public class SessionManager {
	// Shared Preferences
	SharedPreferences pref;
	
	// Editor for Shared preferences
	Editor editor;
	
	// Context
	Context _context;
	
	// Shared pref mode
	int PRIVATE_MODE = 0;
	
	// Sharedpref file name
	private static final String PREF_NAME = "AndroidHivePref";
	
	// All Shared Preferences Keys
	private static final String IS_LOGIN = "IsLoggedIn";
	
	// User name (make variable public to access from outside)
	public static final String KEY_ID = "id";
	
	// Email address (make variable public to access from outside)
	public static final String KEY_EMAIL = "email";
	
	// Email address (make variable public to access from outside)
	public static final String KEY_NAME = "name";
	
	// Username (make variable public to access from outside)
	public static final String KEY_USERNAME = "username";
	
	// Password (make variable public to access from outside)
	public static final String KEY_PASSWORD= "password";
	
	// link (make variable public to access from outside)
	public static final String KEY_LINK = "link";
		
	
	// Constructor
	public SessionManager(Context context){
		this._context = context;
		pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
		editor = pref.edit();
	}
	
	/**
	 * Create login session
	 * */
	public void createLoginSession(String id, String email, String name ){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		
		// Storing name in pref
		editor.putString(KEY_ID, id);
		
		// Storing email in pref
		editor.putString(KEY_EMAIL, email);
		
		// Storing name in pref
		editor.putString(KEY_NAME, name);
		
		// commit changes
		editor.commit();
	}	
	
	/**
	 * Create Username session
	 * */
	public void createUsernamePasswordSession(String username, String password){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		
		// Storing username in pref
		editor.putString(KEY_USERNAME, username);
		
		// Storing password in pref
		editor.putString(KEY_PASSWORD, password);
		
		// commit changes
		editor.commit();
	}	
	
	/**
	 * Create link session
	 * */
	public void createLinkSession(String link){
		// Storing login value as TRUE
		editor.putBoolean(IS_LOGIN, true);
		
		// Storing name in pref
		editor.putString(KEY_LINK, link);
		
		// commit changes
		editor.commit();
	}	
	
	/**
	 * Check login method wil check user login status
	 * If false it will redirect user to login page
	 * Else won't do anything
	 * */
	public void checkLogin(){
		// Check login status
		if(!this.isLoggedIn()){
			// user is not logged in redirect him to Login Activity
			Intent i = new Intent(_context, Login.class);
			// Closing all the Activities
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			
			// Add new Flag to start new Activity
			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			
			// Staring Login Activity
			_context.startActivity(i);
		}
		
	}
	
	/**
	 * Get stored session data
	 * */
	public HashMap<String, String> getUserDetails(){
		HashMap<String, String> user = new HashMap<String, String>();
		// user name
		user.put(KEY_ID, pref.getString(KEY_ID, null));
		
		// user email id
		user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
		
		// user name
		user.put(KEY_NAME, pref.getString(KEY_NAME, null));
		
		// return user
		return user;
	}
	
	/**
	 * Get stored link session data
	 * */
	public HashMap<String, String> getUsernamePassword(){
		HashMap<String, String> Uname = new HashMap<String, String>();
		// user name
		Uname.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));
		
		// user name
		Uname.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));
		
		// return user
		return Uname;
	}
	
	/**
	 * Get stored link session data
	 * */
	public HashMap<String, String> getLink(){
		HashMap<String, String> Link = new HashMap<String, String>();
		// user name
		Link.put(KEY_LINK, pref.getString(KEY_LINK, null));
		
		
		// return user
		return Link;
	}
	
	
	/**
	 * Clear session details
	 * */
	public void logoutUser(){
		// Clearing all data from Shared Preferences
		editor.clear();
		editor.commit();
			
		// After logout redirect user to Loing Activity
		Intent i = new Intent(_context, LoginFirst.class);
		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		
		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		
		// Staring Login Activity
		_context.startActivity(i);
		
	}
	
	/**
	 * Quick check for login
	 * **/
	// Get Login State
	public boolean isLoggedIn(){
		return pref.getBoolean(IS_LOGIN, false);
	}
}
