package com.wp.webview.dataconnect;


import com.wp.webview.Job_Swipe;
import com.wp.webview.Store_Swipe;
import com.wp.webview.Professional_Swipe;
import com.wp.webview.Project_Swipe;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
	private Context _context;
	
	public ViewPagerAdapter(Context context, FragmentManager fm) {
		super(fm);	
		_context=context;
		
		}
	@Override
	public Fragment getItem(int position) {
		Fragment f = new Fragment();
		switch(position){
		case 0:
			f=Project_Swipe.newInstance(_context);	
			break;
		case 1:
			f=Professional_Swipe.newInstance(_context);	
			break;
		case 2:
			f=Job_Swipe.newInstance(_context);	
			break;
		case 3:
			f=Store_Swipe.newInstance(_context);	
			break;
		}
		return f;
	}
	@Override
	public int getCount() {
		return 4;
	}

}
