package com.zam.uieditor;


import java.io.File;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String root = Environment.getExternalStorageDirectory().toString();
		File newDir = new File(root + "/UI_Editor/fonts");
		newDir.mkdirs();
		
		try
		{
		    Resources res = getResources(); 
		    TabHost tabHost = getTabHost();  
		    TabHost.TabSpec spec; 
		    Intent intent;  
		    tabHost.clearAllTabs();
		    
		    // create an intent for the tab which points at the class file for that tab
		    intent = new Intent().setClass(this, ProfileActivity.class);

		    //give the tab a name and set the icon for the tab
		    spec = tabHost.newTabSpec("ProfileActivity").setIndicator("Profile").setContent(intent);
		    tabHost.addTab(spec);
	
		    intent = new Intent().setClass(this, OtherActivity.class);
		    spec = tabHost.newTabSpec("OtherActivity").setIndicator("Other").setContent(intent);
		    tabHost.addTab(spec);
	
		    tabHost.setCurrentTab(0);
		}
		catch(Exception e)
		{
			System.out.println(e.toString());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	public boolean onPrepareOptionsMenu(Menu menu){
        super.onPrepareOptionsMenu(menu);
        return true;
        } 
	
	public boolean onOptionsItemSelected(MenuItem item){
		// Handle item selection
		switch (item.getItemId()) {
		  case R.id. menu_item1:
		    Intent isett = new Intent(MainActivity.this, Settings.class);
			startActivity(isett);
		    return true;
		    
		  case R.id. menu_item2:
			    Intent ihelp = new Intent(MainActivity.this, HelpAct.class);
				startActivity(ihelp);
			    return true;
		    
		  case R.id. menu_item3:
			    Intent iab = new Intent(MainActivity.this, About.class);
				startActivity(iab);
			    return true;
		    
		  default:
		    //pass the event to parent, for options menu, use below
		    return super.onOptionsItemSelected(item);
		}
	    }

}
