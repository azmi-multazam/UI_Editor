package com.zam.uieditor;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class Settings extends Activity implements OnItemSelectedListener {
	
	SharedPreferences PrefSetting;
	Editor editor;
	
	Spinner spinnerStyleLayout, spinnerCoverGallery;
	String styleLayout, ly, styleCoverGallery, scg;
	ImageView saveSettings ;
	
	private String[] stringStyleLayout = { "Circle", "Square"};
	private String[] stringCoverGallery = { "High 1", "Medium 1", "Low 1", "High 2", "Medium 2", "Low 2"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.option_name);
		
		saveSettings = (ImageView) findViewById(R.id.save_setting);
		
		spinnerStyleLayout = (Spinner) findViewById(R.id.spinner1);				
		spinnerCoverGallery = (Spinner) findViewById(R.id.spinner02);
		
		ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,	android.R.layout.simple_spinner_item, stringStyleLayout);
		adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerStyleLayout.setAdapter(adapter_state);
		spinnerStyleLayout.setOnItemSelectedListener(this);
		
		ArrayAdapter<String> adapter_cover = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stringCoverGallery);
		adapter_cover.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCoverGallery.setAdapter(adapter_cover);
		spinnerCoverGallery.setOnItemSelectedListener(this);
		
		PrefSetting = getSharedPreferences("MySettings",0);
		editor= PrefSetting.edit();

		spinnerStyleLayout.setSelection(PrefSetting.getInt("spinLstyle", 0));
		spinnerCoverGallery.setSelection(PrefSetting.getInt("spinCover", 0));
		
		saveSettings.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//finish();			
				AlertDialog.Builder warn = new AlertDialog.Builder(Settings.this);
				warn.setTitle("Setting");
				warn.setMessage("Simpan Pengaturan");
				warn.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface d, int go) {
						// TODO Auto-generated method stub
						//appRestart(null, go);
						editor.putString("myLay", ly);
						editor.putString("myCoverQuality", scg);
						
						editor.commit();
						
						Intent i = getBaseContext().getPackageManager().getLaunchIntentForPackage(getBaseContext().getPackageName());
						i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(i);
					}

				});
				warn.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}
				});
				warn.show();
			}
			});
		
		
	}

	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		int i = spinnerStyleLayout.getSelectedItemPosition();
		int l = spinnerCoverGallery.getSelectedItemPosition();
		
		//spinnerStyleName.setSelection(position);
		ly = stringStyleLayout[i];
		styleLayout = stringStyleLayout[i];
		editor.putInt("spinLstyle", i);
		
		scg = stringCoverGallery[l];
		styleCoverGallery = stringCoverGallery[l];
		editor.putInt("spinCover", l);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
}
