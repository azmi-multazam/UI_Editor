package com.zam.uieditor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;



import android.app.Activity;

import android.content.Intent;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.format.DateFormat;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SaveActivity extends Activity{
	
	Uri profUri;
	
	ImageView iview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//LayoutInflater inflater = LayoutInflater.from(this);
		//View viewInflatedFromXml = inflater.inflate(R.layout.activity_save, null);
		//setContentView(viewInflatedFromXml);
		setContentView(R.layout.activity_save);
		
		//pref = getSharedPreferences("MyPrefs", 0);
		iview = (ImageView) findViewById(R.id.photo);
		
		Bundle bundle = this.getIntent().getExtras();
			Bitmap bmA = bundle.getParcelable("BITMAP_1");
		GraphicsUtil graphicUtil = new GraphicsUtil();
		iview.setImageBitmap(graphicUtil.getCircleBitmap(bmA, 16));
		
	/*	String fondo = pref.getString("PHOTOkey", "null");
		if(fondo == "null"){
			String errorMessage = "Loading ERROR..!!! SET Photo dulu...";
			Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_LONG);
			toast.show();
		} else {
			//byte[] b = Base64.decode(fondo,Base64.DEFAULT);
			//InputStream is = new ByteArrayInputStream(b);
			//Bitmap pic =	BitmapFactory.decodeStream(is);	
			//GraphicsUtil graphicUtil = new GraphicsUtil();
			iview.setImageURI(Uri.parse(fondo));			
			}
		*/
		Button button = (Button) findViewById(R.id.button_ok);
		
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				
				iview.setDrawingCacheEnabled(true);
				
				Bitmap bitmap = iview.getDrawingCache();
				String root = Environment.getExternalStorageDirectory().toString();
				File newDir = new File(root + "/UI_Editor");
				newDir.mkdirs();
				//Random gen = new Random();
				//int n = 10000;
				//n = gen.nextInt(n);
				Date dt = new Date();
				CharSequence s = DateFormat.format("yyyyMMdd_kkmmss", dt.getTime());
				String fotoname = "photo_"+ s +".png";
				File file = new File (newDir, fotoname);
				if (file.exists ()) file.delete ();
				try {
					FileOutputStream out = new FileOutputStream(file);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
					out.flush();
					out.close();
					Toast.makeText(getApplicationContext(), "disimpan di folder /UI_Editor", Toast.LENGTH_LONG ).show();
					} catch (Exception e) {
						
					}
				
				BitmapDrawable bitmapDrawable  = (BitmapDrawable)iview.getDrawable();
				Bitmap profPic = bitmapDrawable.getBitmap();
				 
				// Save this bitmap to a file.
				File cache = getApplicationContext().getExternalCacheDir();
				File sharefile = new File(cache, "circle.png");
				
				try {
					FileOutputStream out = new FileOutputStream(sharefile);
					profPic.compress(Bitmap.CompressFormat.PNG, 100, out);
					out.flush();
					out.close();
					} catch (IOException e) {
						
					}
				
				profUri = Uri.parse("file://" + sharefile);

				Intent iph = new Intent();
				iph.setAction("com.zam.owner.CHANGE_PROFILE_PICTURE");			
				iph.putExtra("PHOTOkey", profUri.toString());
				sendBroadcast(iph);
				
				
				
				finish();
				}
			});
		
		Button buttonCancel = (Button) findViewById(R.id.button_cancel);
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
			});
		
		}
	}
	
