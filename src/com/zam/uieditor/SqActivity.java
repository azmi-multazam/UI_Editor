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

public class SqActivity extends Activity{
	
	Uri profUri;
	
	ImageView iview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sq_save);

		iview = (ImageView) findViewById(R.id.photo);
		
		Bundle bundle = this.getIntent().getExtras();
		Bitmap bmA = bundle.getParcelable("BITMAP_1");

		iview.setImageBitmap(bmA);

		
		Button button = (Button) findViewById(R.id.btn_ok);
		
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
				//long n = System.currentTimeMillis();
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
				File sharefile = new File(cache, "square.png");
				
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
		
		Button buttonCancel = (Button) findViewById(R.id.btn_cancel);
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
			});
		}
	}
	
