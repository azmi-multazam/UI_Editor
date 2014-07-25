package com.zam.uieditor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import yuku.ambilwarna.AmbilWarnaDialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("CommitPrefEdits")
public class ProfileActivity extends Activity implements OnItemSelectedListener {
	private static final int  PICK_FROM_GALLERY = 0;
	private static final int  PICK_FOR_COVER = 1;
	
	final int PIC_CROP = 2;
	final int COVER_CROP = 3;
	
	int color = 0xFFFFFFFF;
	int colorq = 0xFFFFFFFF;
	
	int ax = 0;
	int ay = 0;
	int ox = 0;
	int oy = 0;

	private String imUri, sqUri, coUri;
	private Uri picUri, coverUri;
	Uri profUri;
		
	public static final String MyPREFERENCES = "MyPrefs" ;
	
	public static final String Photo = "PHOTOkey";
	public static final String sqPhoto = "PHOTOkey";
	public static final String covPhoto = "COVERkey";
	
	public static final String Name = "NAMEkey";
	public static final String SizeName = "SIZEkey";
	public static final String ColorName = "COLORkey";
	
	public static final String Quote = "QUOTEKey";	
	public static final String SizeQuote = "SIZEqey";
	public static final String ColorQuote = "COLORqey";

		
	TextView name, quote, et_sizename, et_colorname, et_sizequote, et_colorquote,
				view_name, view_quote;
	
	ImageView pilihPhoto, picView, sendPhoto, frameCircle,
				saveCircle, saveSquare,
				picSquare, sendSquare, frameSquare,
				galleryCover, cover, sendCover,
				saveName, saveQuote, lainnya,
				pickColorName, pickColorQuote,
				clrbox, clrqbox;

	Editor editor;
	SharedPreferences pref;
	SharedPreferences PrefSetting;
	
	Bundle bnd;
	
	Spinner spinnerStyleName, spinnerStyleQuote;
	
	String styleName, styleQuote, 
			vname, vsize, vfs, vfsc, vclr, vquote, vqsize, vqfs, vqclr;
	
	LinearLayout l1, l2, lbul, lkot, layoutcover;
	FrameLayout fls, flc;
	
	
	private String[] stringStyle = { "Regular", "Bold", "Italic", "Bold Italic", "Cstm Regular", "Cstm Bold", "Cstm Italic", "Cstm Bold Italic"};	
//	private String[] stringStyleCust = { "Normal", "Bold", "Italic", "Bold Italic"};	
	private String[] stringStyleQuote = { "Regular", "Bold", "Italic", "Bold Italic", "Cstm Regular", "Cstm Bold", "Cstm Italic", "Cstm Bold Italic"};	
//	private String[] stringStyleQuoteCust = { "Normal", "Bold", "Italic", "Bold Italic"};

	
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View viewInflatedFromXml = inflater.inflate(R.layout.activity_profile, null);
		setContentView(viewInflatedFromXml);
		
		CopyAssets();
		
		//SET ID
		picView = (ImageView)findViewById(R.id.crop);
		frameCircle = (ImageView)findViewById(R.id.frame_circle);
		picSquare = (ImageView)findViewById(R.id.crop_sq);
		frameSquare = (ImageView)findViewById(R.id.frame_square);
		cover = (ImageView)findViewById(R.id.cover_view);
		
		clrbox = (ImageView)findViewById(R.id.clr_prev);
		clrqbox = (ImageView)findViewById(R.id.clrq_prev);
		
//		accorName = (ImageView)findViewById(R.id.accord_onoff_name);
//		accorQuote = (ImageView)findViewById(R.id.accord_onoff_quote);
		
		name = (TextView) findViewById(R.id.editText_name);
		quote = (TextView) findViewById(R.id.editText_quote);

		et_sizename = (TextView) findViewById(R.id.edit_size_name);
		et_colorname = (TextView) findViewById(R.id.edit_color_name);
		
		et_sizequote = (TextView) findViewById(R.id.edit_size_quote);
		et_colorquote = (TextView) findViewById(R.id.edit_color_quote);
		
		view_name = (TextView) findViewById(R.id.display_profile_name);
		view_quote = (TextView) findViewById(R.id.quote_label);
		
		spinnerStyleName = (Spinner) findViewById(R.id.spinner_text_style_name);		
		spinnerStyleQuote = (Spinner) findViewById(R.id.spinner_text_style_quote);		
		
		flc=(FrameLayout)findViewById(R.id.lay_circle);		
		fls=(FrameLayout)findViewById(R.id.lay_square);		
	
		lbul=(LinearLayout)findViewById(R.id.save_send_bulat);		
		lkot=(LinearLayout)findViewById(R.id.save_send_kotak);			
		
		//SET LAYOUT PROFILE
		PrefSetting = getSharedPreferences("MySettings",0);
		String setLayCir = PrefSetting.getString("myLay", "null");
		String setCropCover = PrefSetting.getString("myCoverQuality", "null");
		
		// Gets the layout params that will allow you to resize the layout
		cover.getLayoutParams().height = (int) getResources().getDimension(R.dimen.cover_one);
		
		if(setLayCir.equals("null")) {
			flc.setVisibility(View.VISIBLE);
			lbul.setVisibility(View.VISIBLE);
			fls.setVisibility(View.GONE);
			lkot.setVisibility(View.GONE);
		}else{
			flc.setVisibility(View.VISIBLE);
			lbul.setVisibility(View.VISIBLE);
			fls.setVisibility(View.GONE);
			lkot.setVisibility(View.GONE);
		}
		if(setLayCir.equals("Square")) {
			flc.setVisibility(View.GONE);
			lbul.setVisibility(View.GONE);
			fls.setVisibility(View.VISIBLE);
			lkot.setVisibility(View.VISIBLE);
		}
		else if (setLayCir.equals("Circle")) {
			flc.setVisibility(View.VISIBLE);
			lbul.setVisibility(View.VISIBLE);
			fls.setVisibility(View.GONE);
			lkot.setVisibility(View.GONE);
		}
		
		if(setCropCover.equals("null")){
			ax = Integer.getInteger(null, 3);
			ay = Integer.getInteger(null, 2);
			ox = Integer.getInteger(null, 600);
			oy = Integer.getInteger(null, 400);
		}else{
			ax = Integer.getInteger(null, 3);
			ay = Integer.getInteger(null, 2);
			ox = Integer.getInteger(null, 600);
			oy = Integer.getInteger(null, 400);
		}
		
		if(setCropCover.equals("High 1")){
			cover.getLayoutParams().height = (int) getResources().getDimension(R.dimen.cover_one);
			ax = Integer.getInteger(null, 3);
			ay = Integer.getInteger(null, 2);
			ox = Integer.getInteger(null, 600);
			oy = Integer.getInteger(null, 400);
	        }
		else if (setCropCover.equals("Medium 1")){
			cover.getLayoutParams().height = (int) getResources().getDimension(R.dimen.cover_one);
			ax = Integer.getInteger(null, 3);
			ay = Integer.getInteger(null, 2);
			ox = Integer.getInteger(null, 450);
			oy = Integer.getInteger(null, 300);
		}
		else if (setCropCover.equals("Low 1")){
			cover.getLayoutParams().height = (int) getResources().getDimension(R.dimen.cover_one);
			ax = Integer.getInteger(null, 3);
			ay = Integer.getInteger(null, 2);
			ox = Integer.getInteger(null, 300);
			oy = Integer.getInteger(null, 200);
		}
		else if(setCropCover.equals("High 2")){
			cover.getLayoutParams().height = (int) getResources().getDimension(R.dimen.cover_two);
			ax = Integer.getInteger(null, 2);
			ay = Integer.getInteger(null, 1);
			ox = Integer.getInteger(null, 600);
			oy = Integer.getInteger(null, 300);
	        }
		else if (setCropCover.equals("Medium 2")){
			cover.getLayoutParams().height = (int) getResources().getDimension(R.dimen.cover_two);
			ax = Integer.getInteger(null, 2);
			ay = Integer.getInteger(null, 1);
			ox = Integer.getInteger(null, 400);
			oy = Integer.getInteger(null, 200);
		}
		else if (setCropCover.equals("Low 2")){
			cover.getLayoutParams().height = (int) getResources().getDimension(R.dimen.cover_two);
			ax = Integer.getInteger(null, 2);
			ay = Integer.getInteger(null, 1);
			ox = Integer.getInteger(null, 300);
			oy = Integer.getInteger(null, 150);
		}
		
		
		final Typeface typename = Typeface.createFromFile(new File(Environment.getExternalStorageDirectory(), "/UI_Editor/fonts/name.ttf"));
		final Typeface typequote = Typeface.createFromFile(new File(Environment.getExternalStorageDirectory(), "/UI_Editor/fonts/quote.ttf"));
		
		SharedPreferences sharedPreferences = getSharedPreferences("EvoPrefsFile",Context.MODE_PRIVATE);
		SharedPreferences sharedPref = getSharedPreferences("EvoPrefsFile",Context.MODE_PRIVATE);
		
		pref = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
		editor= pref.edit();
		
		//SET VIEW NAME
		vname = pref.getString(Name,"Owner");
		vsize = pref.getString(SizeName, "null");
	    vfs = pref.getString("style", "Regular");
	    vclr = pref.getString("color", "null");
	    
	    if(vfs.equals("Bold")) {
	    	view_name.setTypeface(null, Typeface.BOLD);
	    	}
	    else if(vfs.equals("Italic")) {
	    	view_name.setTypeface(null, Typeface.ITALIC);
        	}
	    else if(vfs.equals("Bold Italic")) {
         	view_name.setTypeface(null, Typeface.BOLD_ITALIC);
         	}
	    else if(vfs.equals("Regular")) {
         	view_name.setTypeface(null, Typeface.NORMAL);
         	}
	    else if(vfs.equals("Cstm Bold")) {
			view_name.setTypeface(typename, Typeface.BOLD);
			}
	    else if(vfs.equals("Cstm Italic")) {
        	view_name.setTypeface(typename, Typeface.ITALIC);
        	}
	    else if(vfs.equals("Cstm Bold Italic")) {
	    	view_name.setTypeface(typename, Typeface.BOLD_ITALIC);
        	}
	    else if(vfs.equals("Cstm Regular")) {
        	view_name.setTypeface(typename, Typeface.NORMAL);
        	}
	    
	    
         float mySize = 0;
         try{
         	mySize = Integer.parseInt(vsize);
         }catch (NumberFormatException nfe){
         	System.out.println("angka tidak valid" + nfe);
         }
             		
 		if(vsize.equals("null")){
 			view_name.setTextSize(15);
 		}else{
 			view_name.setTextSize(mySize);
		
 		}
 		
 		view_name.setText(vname);
 		
 		if(vclr.equals("null")){
 			view_name.setTextColor(0xFFFFFFFF);
 		}else{
 			view_name.setTextColor(Color.parseColor(vclr));
 		}

 		
	    
	    vquote = pref.getString(Quote,"lagi mikir apa?");
		vqsize = pref.getString(SizeQuote, "null");
	    vqfs = pref.getString("styleq", "Regular");
	    vqclr = pref.getString("colorq", "null");
	    
	    if(vqfs.equals("Bold")) {
	    	view_quote.setTypeface(null, Typeface.BOLD);
	    	}
	    else if(vqfs.equals("Italic")) {
        	view_quote.setTypeface(null, Typeface.ITALIC);
         	}	    
	    else if(vqfs.equals("Bold Italic")) {
         	view_quote.setTypeface(null, Typeface.BOLD_ITALIC);
         	}	    
	    else if(vqfs.equals("Regular")) {
         	view_quote.setTypeface(null, Typeface.NORMAL);
         	}
	    else if(vqfs.equals("Cstm Bold")) {
	    	view_quote.setTypeface(typequote, Typeface.BOLD);
	    	}
	    else if(vqfs.equals("Cstm Italic")) {
        	view_quote.setTypeface(typequote, Typeface.ITALIC);
         	}	    
	    else if(vqfs.equals("Cstm Bold Italic")) {
         	view_quote.setTypeface(typequote, Typeface.BOLD_ITALIC);
         	}	    
	    else if(vqfs.equals("Cstm Regular")) {
         	view_quote.setTypeface(typequote, Typeface.NORMAL);
         	}


         float myQuote = 0;
         try{
         	myQuote = Integer.parseInt(vqsize);
         }catch (NumberFormatException nfe){
         	System.out.println("angka tidak valid" + nfe);
         }
             		
 		if(vqsize.equals("null")){
 			view_quote.setTextSize(15);
 		}else{
 			view_quote.setTextSize(myQuote);
		
 		}
 		
 		view_quote.setText(vquote);
 		
 		if(vqclr.equals("null")){
 			view_quote.setTextColor(0xFF00FF00);
 		}else{
 			view_quote.setTextColor(Color.parseColor(vqclr));
 		}
		
		//SET EDIT TEXT
		if (pref.contains(Name))	{
			name.setText(pref.getString(Name, ""));
			}
		if (pref.contains(Quote))	{
			quote.setText(pref.getString(Quote, ""));
			}
		
		if (pref.contains(SizeName))	{
			et_sizename.setText(pref.getString(SizeName, ""));
			}else{
				et_sizename.setText("14");
				}
		if (pref.contains(ColorName))	{
			et_colorname.setText(pref.getString(ColorName, ""));
			}else{		
				et_colorname.setText("#FFFFFFFF");
			}
		
		if (pref.contains(SizeQuote))	{		
			et_sizequote.setText(pref.getString(SizeQuote, ""));
			}else{
				et_sizequote.setText("16");
			}
		if (pref.contains(ColorQuote))	{
			et_colorquote.setText(pref.getString(ColorQuote, ""));
			}else{
				et_colorquote.setText("#FF00FF00");
			}
		
		String clb = pref.getString(ColorName, "null");
		if(clb == "null"){
			clrbox.setBackgroundColor(0xFFFFFFFF);
 		}else{
 			clrbox.setBackgroundColor(Color.parseColor(clb));
 		}
		
		String clqb = pref.getString(ColorQuote, "null");
		if(clqb == "null"){
			clrqbox.setBackgroundColor(0xFF00FF00);
 		}else{
 			clrqbox.setBackgroundColor(Color.parseColor(clqb));
 		}
		
		imUri = pref.getString(Photo, "null");
		  if (imUri == "null") {
		    	 picView.setImageResource(R.drawable.ic_launcher);	 	    	 
			     } else {
			    	 picView.setImageURI(Uri.parse(imUri));
			     }
		  
		  sqUri = sharedPreferences.getString(sqPhoto, "null");
		  if (sqUri == "null") {
		    	 picSquare.setImageResource(R.drawable.ic_qs_default_user);	 	    	 
			     } else {
			    	 picSquare.setImageURI(Uri.parse(sqUri));
			     }
		  
		  coUri = sharedPref.getString(covPhoto, "null");
		  if (coUri == "null") {
		    	 cover.setImageResource(R.drawable.cover_bg);	 	    	 
			     } else {
			    	 cover.setImageURI(Uri.parse(coUri));
			     }
		  
		  	InputStream is1 = this.getResources().openRawResource(R.drawable.frmcircle);
			Bitmap origBitmap1 = BitmapFactory.decodeStream(is1);
			frameCircle.setImageBitmap(origBitmap1);
			
			InputStream is2 = this.getResources().openRawResource(R.drawable.sq_bg);
			Bitmap origBitmap2 = BitmapFactory.decodeStream(is2);
			frameSquare.setImageBitmap(origBitmap2);
			
			ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,	android.R.layout.simple_spinner_item, stringStyle);
			adapter_state.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerStyleName.setAdapter(adapter_state);
			spinnerStyleName.setOnItemSelectedListener(this);
	
			ArrayAdapter<String> adapter_state_quote = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, stringStyleQuote);
			adapter_state_quote.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinnerStyleQuote.setAdapter(adapter_state_quote);
			spinnerStyleQuote.setOnItemSelectedListener(this);
	
			//SPINNER
			spinnerStyleQuote.setSelection(pref.getInt("spinQstyle", 0));
			spinnerStyleName.setSelection(pref.getInt("spinNstyle", 0));
			/*		
			InputStream an = this.getResources().openRawResource(R.drawable.up);
			final Bitmap up = BitmapFactory.decodeStream(an);
			
			InputStream aq = this.getResources().openRawResource(R.drawable.down);
			final Bitmap down = BitmapFactory.decodeStream(aq);
			
			accorName.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(FOCUSED_STATE_SET[0]==1){
						l1.setVisibility(View.GONE);
						accorName.setImageBitmap(down);
						FOCUSED_STATE_SET[0]=0;
						}
					else {
						l1.setVisibility(View.VISIBLE);
						accorName.setImageBitmap(up);
						FOCUSED_STATE_SET[0]=1;
						}
					}
				});
			
			accorQuote.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(FOCUSED_STATE_SET[0]==1){
				l2.setVisibility(View.GONE);
				accorQuote.setImageBitmap(down);
				FOCUSED_STATE_SET[0]=0;
				}
				else {
				l2.setVisibility(View.VISIBLE);
				//l2.setVisibility(View.GONE);
				accorQuote.setImageBitmap(up);
				FOCUSED_STATE_SET[0]=1;
				}
				}
				});
			
	*/	
			
			
		pickColorName = (ImageView)findViewById(R.id.lay_switch);
		pickColorName.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				openDialog(false);
				//Intent ic = new Intent(ProfileActivity.this, PickColor.class);
				//startActivity(ic);
			}
		});
		
		pickColorQuote = (ImageView)findViewById(R.id.pick_qcolor);
		pickColorQuote.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				openDialogQ(false);
				//Intent ic = new Intent(ProfileActivity.this, PickColor.class);
				//startActivity(ic);
			}
		});
			
		pilihPhoto = (ImageView)findViewById(R.id.gallery);			
			
		pilihPhoto.setOnClickListener( new View.OnClickListener() {
				
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
				// call android default gallery
				intent.setType( "image/*" );
				startActivityForResult (Intent. createChooser (intent,	"Complete action using" ), PICK_FROM_GALLERY );
				}
			});		
		
		galleryCover = (ImageView)findViewById(R.id.gallery2);			
		
		galleryCover.setOnClickListener( new OnClickListener() {
				
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				// call android default gallery
				intent.setType( "image/*" );
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult (Intent.createChooser(intent, "Select Picture"), PICK_FOR_COVER );
				}
			});		
		

		sendPhoto = (ImageView) findViewById(R.id.send_circle);
		
		sendPhoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				
				BitmapDrawable bitmapDrawable  = (BitmapDrawable)picView.getDrawable();
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
		        editor.putString(Photo, profUri.toString()); //true or false
		        editor.commit();

				Intent iph = new Intent();
				iph.setAction("com.zam.owner.CHANGE_PROFILE_PICTURE");			
				iph.putExtra(Photo, profUri.toString());
				sendBroadcast(iph);
			}
			});
		
		saveCircle = (ImageView) findViewById(R.id.save_to_sd);
		saveCircle.setEnabled(false);
		saveCircle.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent icr = new Intent(ProfileActivity.this, SaveActivity.class);
				icr.putExtras(bnd);
				startActivity(icr);
			}
		});
		
		saveSquare = (ImageView) findViewById(R.id.sq_to_sd);
		saveSquare.setEnabled(false);
		saveSquare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent icr = new Intent(ProfileActivity.this, SqActivity.class);
				icr.putExtras(bnd);
				startActivity(icr);
			}
		});
		
		sendSquare = (ImageView) findViewById(R.id.send_square);
		
		sendSquare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				
				BitmapDrawable bitmapDrawable  = (BitmapDrawable)picSquare.getDrawable();
				Bitmap sqPic = bitmapDrawable.getBitmap();
				 
				// Save this bitmap to a file.
				File cache = getApplicationContext().getExternalCacheDir();
				File sqfile = new File(cache, "square.png");
				
				try {
					FileOutputStream sout = new FileOutputStream(sqfile);
					sqPic.compress(Bitmap.CompressFormat.PNG, 100, sout);
					sout.flush();
					sout.close();
					} catch (IOException e) {
						
					}
				
				
				Uri squareUri = Uri.parse("file://" + sqfile);
				SharedPreferences sharedPreferences = getSharedPreferences("EvoPrefsFile",Context.MODE_PRIVATE);
		        SharedPreferences.Editor editor = sharedPreferences.edit(); //opens the editor
		        editor.putString(sqPhoto, squareUri.toString()); //true or false
		        editor.commit();
				
				Intent iphoto = new Intent();
				iphoto.setAction("com.zam.owner.CHANGE_PROFILE_PICTURE");			
				iphoto.putExtra(sqPhoto, squareUri.toString());
				sendBroadcast(iphoto);

			}
			});
		
		sendCover = (ImageView) findViewById(R.id.send_cover);
		
		sendCover.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				
				BitmapDrawable bitmapDrawable  = (BitmapDrawable)cover.getDrawable();
				Bitmap covPic = bitmapDrawable.getBitmap();
				 
				// Save this bitmap to a file.
				File cache = getApplicationContext().getExternalCacheDir();
				File covfile = new File(cache, "cover.png");
				
				try {
					FileOutputStream cout = new FileOutputStream(covfile);
					covPic.compress(Bitmap.CompressFormat.PNG, 100, cout);
					cout.flush();
					cout.close();
					} catch (IOException e) {
						
					}
				
				
				Uri covUri = Uri.parse("file://" + covfile);
				SharedPreferences sharedPref = getSharedPreferences("EvoPrefsFile",Context.MODE_PRIVATE);
		        SharedPreferences.Editor editr = sharedPref.edit(); //opens the editor
		        editr.putString(covPhoto, covUri.toString()); //true or false
		        editr.commit();

				
				Intent ico = new Intent();
				ico.setAction("com.zam.cover.CHANGE_COVER_PICTURE");
			    ico.putExtra(covPhoto, covUri.toString());
				sendBroadcast(ico);
			}
		});
		
		saveName = (ImageView) findViewById(R.id.sv_name);
		saveName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent iname = new Intent();
				iname.setAction("com.zam.owner.CHANGE_PROFILE_NAME");
				
				String n = name.getText().toString();
				String szn = et_sizename.getText().toString();
				String clrn = et_colorname.getText().toString();
				
				iname.putExtra(Name, n);
				iname.putExtra(SizeName, szn);
				iname.putExtra(ColorName, clrn);
				iname.putExtra("style", styleName);
				sendBroadcast(iname);
				
				if(styleName.equals("Bold")) {
					view_name.setTypeface(null, Typeface.BOLD);
					}
				else if(styleName.equals("Italic")) {
	            	view_name.setTypeface(null, Typeface.ITALIC);
	            	}
				else if(styleName.equals("Bold Italic")) {
	            	view_name.setTypeface(null, Typeface.BOLD_ITALIC);
	            	}
				else if(styleName.equals("Regular")) {
	            	view_name.setTypeface(null, Typeface.NORMAL);
	            	}
				else if(styleName.equals("Cstm Bold")) {
					view_name.setTypeface(typename, Typeface.BOLD);
		            }
				else if(styleName.equals("Cstm Italic")) {
		           	view_name.setTypeface(typename, Typeface.ITALIC);
		           	}
				else if(styleName.equals("Cstm Bold Italic")) {
		        	view_name.setTypeface(typename, Typeface.BOLD_ITALIC);
		           	}
				else if(styleName.equals("Cstm Regular")) {
		        	view_name.setTypeface(typename, Typeface.NORMAL);
		           	}
				
				
				if(vclr == null){
					view_name.setTextColor(0xFF000000);
	     		}else{
	     			view_name.setTextColor(Color.parseColor(clrn));
	     		}
	        	

				view_name.setText(n);
				view_name.setTextSize((float)Integer.parseInt(szn));
				
				
				editor.putString(Name, n);
				editor.putString(SizeName, szn);
				editor.putString(ColorName, clrn);

				editor.putString("color", clrn);
				editor.putString("style", styleName);
				
				editor.commit();
				
				
			}
			});


		saveQuote = (ImageView) findViewById(R.id.sv_quote);
		saveQuote.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent iquote = new Intent();
				iquote.setAction("com.zam.cover.CHANGE_QUOTE_LABEL");
				
				String q = quote.getText().toString();
				String szq = et_sizequote.getText().toString();
				String clrq = et_colorquote.getText().toString();
				
				iquote.putExtra(Quote, q);
				iquote.putExtra(SizeQuote, szq);
				iquote.putExtra(ColorQuote, clrq);
				iquote.putExtra("styleq", styleQuote);
				sendBroadcast(iquote);
				
				if(styleQuote.equals("Bold")) {
					view_quote.setTypeface(null, Typeface.BOLD);
	            	}
				else if(styleQuote.equals("Italic")) {
	            	view_quote.setTypeface(null, Typeface.ITALIC);
	            	}
				else if(styleQuote.equals("Bold Italic")) {
					view_quote.setTypeface(null, Typeface.BOLD_ITALIC);
	            	}
				else if(styleQuote.equals("Regular")) {
	            	view_quote.setTypeface(null, Typeface.NORMAL);
	            	}
				else if(styleQuote.equals("Cstm Bold")) {
					view_quote.setTypeface(typequote, Typeface.BOLD);
	            	}
				else if(styleQuote.equals("Cstm Italic")) {
	            	view_quote.setTypeface(typequote, Typeface.ITALIC);
	            	}
				else if(styleQuote.equals("Cstm Bold Italic")) {
					view_quote.setTypeface(typequote, Typeface.BOLD_ITALIC);
	            	}
				else if(styleQuote.equals("Cstm Regular")) {
	            	view_quote.setTypeface(typequote, Typeface.NORMAL);
	            	}
				
				if(vqclr == null){
					view_quote.setTextColor(0xFF00FF00);
	     		}else{
	     			view_quote.setTextColor(Color.parseColor(clrq));
	     		}
	        	

				view_quote.setText(q);
				view_quote.setTextSize((float)Integer.parseInt(szq));

				editor.putString(Quote, q);	
				editor.putString(SizeQuote, szq);
				editor.putString(ColorQuote, clrq);

				editor.putString("colorq", clrq);
				editor.putString("styleq", styleQuote);
				
				editor.commit();
			}
			});
		}

	private void CopyAssets() {
		AssetManager assetManager = getAssets();
        String[] files = null;
        try {
            files = assetManager.list("files");
        } catch (IOException e) {
            Log.e("tag", e.getMessage());
        }
        for(String filename : files) {
        	System.out.println("File name => "+filename);
            InputStream in = null;
            OutputStream out = null;
            try {
              in = assetManager.open("files/"+filename);
			  // if files resides inside the "Files" directory it self
              out = new FileOutputStream(Environment.getExternalStorageDirectory().toString() +"/UI_Editor/fonts/" + filename);
              copyFile(in, out);
              in.close();
              in = null;
              out.flush();
              out.close();
              out = null;
            } catch(Exception e) {
                Log.e("tag", e.getMessage());
            }
        }
    }

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		 byte[] buffer = new byte[1024];
	        int read;
	        while((read = in.read(buffer)) != -1){
	          out.write(buffer, 0, read);
	        }
	    }

	protected void onActivityResult( int requestCode, int resultCode, final Intent data){
		super.onActivityResult(requestCode,	resultCode, data);
		
		if (requestCode == PICK_FROM_GALLERY ) {
			if (data !=null){
				picUri = data.getData();
				// carry out the crop operation
				performCrop();
				}
			}
		
		if (requestCode == PICK_FOR_COVER ) {
			if (data !=null){
				coverUri = data.getData();
				// carry out the crop operation
				coverCrop();
				}
			}
		
		// user is returning from cropping the image
		else if (requestCode == PIC_CROP) {
			if (data !=null){
			// get the returned data
			Bundle extras = data.getExtras();
			// get the cropped bitmap
			Bitmap thePic = extras.getParcelable("data");
			
			bnd = new Bundle();
			bnd.putParcelable("BITMAP_1", thePic);
			// retrieve a reference to the ImageView
			picSquare.setImageBitmap(thePic);
			// display the returned cropped image
			GraphicsUtil graphicUtil = new GraphicsUtil();
			// picView.setImageBitmap(graphicUtil.getRoundedShape(thePic,(float)1.5,92));
			picView.setImageBitmap(graphicUtil.getCircleBitmap(thePic, 16));
			
			saveCircle.setEnabled(true);
			saveSquare.setEnabled(true);
			
			Intent i = new Intent();
			i.setAction("com.zam.owner.CHANGE_PROFILE_PICTURE");
		    i.putExtra("PHOTOkey", "");
			sendBroadcast(i);
			
			}			
			}
		
		else if (requestCode == COVER_CROP) {
			if (data !=null){
			// get the returned data
			Bundle extras = data.getExtras();
			// get the cropped bitmap
			Bitmap cov = extras.getParcelable("data");
			// retrieve a reference to the ImageView
			cover.setImageBitmap(cov);
			
			Intent ic = new Intent();
			//ByteArrayOutputStream baos = new ByteArrayOutputStream();
			//cov.compress(Bitmap.CompressFormat.JPEG, 50, baos);
			//bm is the bitmap object
			//byte[] b = baos.toByteArray();
			ic.setAction("com.zam.cover.CHANGE_COVER_PICTURE");
		    ic.putExtra("COVERkey", "");
			sendBroadcast(ic);
			
			}			
			}
		}

		/**
		 * Helper method to carry out crop operation
		 */
		private void performCrop() {
			// take care of exceptions
			try {
				// call the standard crop action intent (the user device may not
				// support it)
				Intent cropIntent = new Intent("com.android.camera.action.CROP");
				// indicate image type and Uri
				cropIntent.setDataAndType(picUri, "image/*");
				// set crop properties
				cropIntent.putExtra("crop", "true");
				// indicate aspect of desired crop
				cropIntent.putExtra("aspectX", 1);
				cropIntent.putExtra("aspectY", 1);
				// indicate output X and Y
				cropIntent.putExtra("outputX", 256);
				cropIntent.putExtra("outputY", 256);
				// retrieve data on return
				cropIntent.putExtra("return-data", true);
				// start the activity - we handle returning in onActivityResult
				startActivityForResult(cropIntent, PIC_CROP);

								
			}
			// respond to users whose devices do not support the crop action
			catch (ActivityNotFoundException anfe) {
				// display an error message
				String errorMessage = "oops - MUNGKIN DEVICE NGGA SUPPORT CROPP!";
				Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
				toast.show();
			}
		}

		
		
		
		private void coverCrop() {		    
			// take care of exceptions
			try {
				// call the standard crop action intent (the user device may not
				// support it)
				Intent cropIntent = new Intent("com.android.camera.action.CROP");
				// indicate image type and Uri
				cropIntent.setDataAndType(coverUri, "image/*");
				// set crop properties
				cropIntent.putExtra("crop", "true");
				// indicate aspect of desired crop
				cropIntent.putExtra("aspectX", ax);
				cropIntent.putExtra("aspectY", ay);
				// indicate output X and Y
				cropIntent.putExtra("outputX", ox);
				cropIntent.putExtra("outputY", oy);
				// retrieve data on return
				cropIntent.putExtra("return-data", true);
				// start the activity - we handle returning in onActivityResult
				startActivityForResult(cropIntent, COVER_CROP);

								
			}
			// respond to users whose devices do not support the crop action
			catch (ActivityNotFoundException anfe) {
				// display an error message
				String errorMessage = "oops - MUNGKIN DEVICE NGGA SUPPORT CROPP!";
				Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
				toast.show();
			}
		}

		
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		int i = spinnerStyleName.getSelectedItemPosition();
		int j = spinnerStyleQuote.getSelectedItemPosition();
	//	int ic = spinnerStyleNameCust.getSelectedItemPosition();
	//	int jc = spinnerStyleQuoteCust.getSelectedItemPosition();		
		
		//spinnerStyleName.setSelection(position);
		//styleName = (String) spinnerStyleName.getSelectedItem();
		styleName = stringStyle[i];
		editor.putInt("spinNstyle", i);
		
	//	styleNameCust = stringStyleCust[ic];
	//	editor.putInt("spinNstyleCust", ic);
		
		//spinnerStyleQuote.setSelection(position);
		//styleQuote = (String) spinnerStyleQuote.getSelectedItem();
		styleQuote = stringStyleQuote[j];
		editor.putInt("spinQstyle", j);
		
	//	styleQuoteCust = stringStyleQuoteCust[jc];
	//	editor.putInt("spinQstyleCust", jc);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	void openDialog(boolean supportsAlpha) {
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(ProfileActivity.this, color, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				//Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
				ProfileActivity.this.color = color;
				displayColor();
			}

			@Override
			public void onCancel(AmbilWarnaDialog dialog) {
				Toast.makeText(getApplicationContext(), "Batal", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.show();
	}

	void displayColor() {
		et_colorname.setText(String.format("#%08x", color));
		//String boxcolor = String.format("#%08x", color);
		clrbox.setBackgroundColor(color);
	}
		
	void openDialogQ(boolean supportsAlpha) {
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(ProfileActivity.this, colorq, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				//Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
				ProfileActivity.this.colorq = color;
				displayColorQ();
			}

			@Override
			public void onCancel(AmbilWarnaDialog dialog) {
				Toast.makeText(getApplicationContext(), "Batal", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.show();
	}

	void displayColorQ() {
		et_colorquote.setText(String.format("#%08x", colorq));
		//String boxcolor = String.format("#%08x", color);
		clrqbox.setBackgroundColor(colorq);
	}
}