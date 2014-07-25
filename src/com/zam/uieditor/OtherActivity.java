package com.zam.uieditor;

import java.io.File;
import yuku.ambilwarna.AmbilWarnaDialog;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View.OnClickListener;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class OtherActivity extends Activity implements OnItemSelectedListener {
	
	
	private static final int  PICK_FOR_PIC1 = 4;
	private static final int  PICK_FOR_PIC2 = 5;
	private static final int  PICK_FOR_PIC3 = 6;
	
	public static final String MyPREFS 		= "Prefs" ;
	
	public static final String Text1 		= "AZMI1";
	public static final String Text1Size 	= "AZMI1size";
	public static final String Text1Color 	= "AZMI1color";
	
	public static final String Text2 		= "AZMI2";
	public static final String Text2Size 	= "AZMI2size";
	public static final String Text2Color 	= "AZMI2color";
	
	public static final String Text3 		= "AZMI3";
	public static final String Text3Size	= "AZMI3size";
	public static final String Text3Color	= "AZMI3color";
	
	public static final String Carrier1 	= "PIC1";
	public static final String Carrier2 	= "PIC2";
	public static final String Carrier3 	= "PIC3";
	
	int color1 = 0xFFFFFFFF;
	int color2 = 0xFFFFFFFF;
	int color3 = 0xFFFFFFFF;
	
	private String pic1Uri, pic2Uri, pic3Uri;

	Uri uri1, uri2, uri3;
	
	TextView carrierText1, carrierText2, carrierText3,
				ettext1Size, ettext1Color, 
				ettext2Size, ettext2Color, 
				ettext3Size, ettext3Color;
	
	ImageView galleryPic1, galleryPic2, galleryPic3,
				pic1View, pic2View, pic3View,
				saveCarrierText1, saveCarrierText2, saveCarrierText3,
				accordionArrow1, accordionArrow2, accordionArrow3,
				pickClr1, pickClr2, pickClr3,
				colorBox1, colorBox2, colorBox3;
	
	Editor edt;
	SharedPreferences pref, sharedPref1, sharedPref2, sharedPref3;
	
	Spinner spinnerStyleText1, spinnerStyleText2, spinnerStyleText3;
	String styleText1, styleText2, styleText3,
			strStyle1, strStyle2, strStyle3,
			strSize1, strSize2, strSize3,
			strColor1, strColor2, strColor3;
	
	private String[] stringStyleText1 = { "Regular", "Bold", "Italic", "Bold Italic", "Cstm Regular", "Cstm Bold", "Cstm Italic", "Cstm Bold Italic"};
	private String[] stringStyleText2 = { "Regular", "Bold", "Italic", "Bold Italic", "Cstm Regular", "Cstm Bold", "Cstm Italic", "Cstm Bold Italic"};
	private String[] stringStyleText3 = { "Regular", "Bold", "Italic", "Bold Italic", "Cstm Regular", "Cstm Bold", "Cstm Italic", "Cstm Bold Italic"};

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		LayoutInflater inflater = LayoutInflater.from(this);
		View viewInflatedFromXml = inflater.inflate(R.layout.activity_other, null);
		setContentView(viewInflatedFromXml);
		
		//setContentView(R.layout.activity_other);
		
		pic1View = (ImageView)findViewById(R.id.pic1_view);
		pic2View = (ImageView)findViewById(R.id.pic2_view);
		pic3View = (ImageView)findViewById(R.id.pic3_view);
			
		carrierText1 = (TextView) findViewById(R.id.editText_carrier1);
		carrierText2 = (TextView) findViewById(R.id.editText_carrier2);
		carrierText3 = (TextView) findViewById(R.id.editText_carrier3);
		
		ettext1Size = (TextView) findViewById(R.id.etsize1);
		ettext2Size = (TextView) findViewById(R.id.etsize2); 
		ettext3Size = (TextView) findViewById(R.id.etsize3);
		
		ettext1Color = (TextView) findViewById(R.id.etcolor1);
		ettext2Color = (TextView) findViewById(R.id.etcolor2); 
		ettext3Color = (TextView) findViewById(R.id.etcolor3);
		
		pickClr1 = (ImageView) findViewById(R.id.color1_picker);
		pickClr2 = (ImageView) findViewById(R.id.color2_picker);
		pickClr3 = (ImageView) findViewById(R.id.color3_picker);
		
		colorBox1 = (ImageView) findViewById(R.id.colorpreview1);
		colorBox2 = (ImageView) findViewById(R.id.colorpreview2);
		colorBox3 = (ImageView) findViewById(R.id.colorpreview3);
		
		sharedPref1 = getSharedPreferences("EvoPrefsFile",Context.MODE_PRIVATE);
		sharedPref2 = getSharedPreferences("EvoPrefsFile",Context.MODE_PRIVATE);
		sharedPref3 = getSharedPreferences("EvoPrefsFile",Context.MODE_PRIVATE);
		
		spinnerStyleText1 = (Spinner) findViewById(R.id.spinstyletext1);
		spinnerStyleText2 = (Spinner) findViewById(R.id.spinstyletext2);
		spinnerStyleText3 = (Spinner) findViewById(R.id.spinstyletext3);
		
		ArrayAdapter<String> adapter_state_text1 = new ArrayAdapter<String>(this,	android.R.layout.simple_spinner_item, stringStyleText1);
		adapter_state_text1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerStyleText1.setAdapter(adapter_state_text1);
		spinnerStyleText1.setOnItemSelectedListener(this);
		
		ArrayAdapter<String> adapter_state_text2 = new ArrayAdapter<String>(this,	android.R.layout.simple_spinner_item, stringStyleText2);
		adapter_state_text2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerStyleText2.setAdapter(adapter_state_text2);
		spinnerStyleText2.setOnItemSelectedListener(this);
		
		ArrayAdapter<String> adapter_state_text3 = new ArrayAdapter<String>(this,	android.R.layout.simple_spinner_item, stringStyleText3);
		adapter_state_text3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerStyleText3.setAdapter(adapter_state_text3);
		spinnerStyleText3.setOnItemSelectedListener(this);
		
		final Typeface typetext1 = Typeface.createFromFile(new File(Environment.getExternalStorageDirectory(), "/UI_Editor/fonts/carrier1.ttf"));
		final Typeface typetext2 = Typeface.createFromFile(new File(Environment.getExternalStorageDirectory(), "/UI_Editor/fonts/carrier2.ttf"));
		final Typeface typetext3 = Typeface.createFromFile(new File(Environment.getExternalStorageDirectory(), "/UI_Editor/fonts/carrier3.ttf"));
	
		pref = getSharedPreferences(MyPREFS, Context.MODE_PRIVATE);
		edt = pref.edit();
		
		//strText1 = pref.getString(Text1,"Text1");
		strStyle1 = pref.getString("style1", "Regular");
		strSize1 = pref.getString(Text1Size, "null");
		strColor1 = pref.getString(Text1Color, "null");
		
		if(strStyle1.equals("Bold")) {
	    	carrierText1.setTypeface(null, Typeface.BOLD);
	    	}
	    else if(strStyle1.equals("Italic")) {
	    	carrierText1.setTypeface(null, Typeface.ITALIC);
        	}
	    else if(strStyle1.equals("Bold Italic")) {
         	carrierText1.setTypeface(null, Typeface.BOLD_ITALIC);
         	}
	    else if(strStyle1.equals("Regular")) {
         	carrierText1.setTypeface(null, Typeface.NORMAL);
         	}
	    else if(strStyle1.equals("Cstm Bold")) {
			carrierText1.setTypeface(typetext1, Typeface.BOLD);
			}
	    else if(strStyle1.equals("Cstm Italic")) {
        	carrierText1.setTypeface(typetext1, Typeface.ITALIC);
        	}
	    else if(strStyle1.equals("Cstm Bold Italic")) {
	    	carrierText1.setTypeface(typetext1, Typeface.BOLD_ITALIC);
        	}
	    else if(strStyle1.equals("Cstm Regular")) {
        	carrierText1.setTypeface(typetext1, Typeface.NORMAL);
        	}
		
		float mySize1 = 0;
        try{
        	mySize1 = Integer.parseInt(strSize1);
        }catch (NumberFormatException nfe){
        	System.out.println("angka tidak valid" + nfe);
        }
            		
		if(strSize1.equals("null")){
			carrierText1.setTextSize(18);
		}else{
			carrierText1.setTextSize(mySize1);
		}
		
		if(strColor1.equals("null")){
			carrierText1.setTextColor(0xFF000000);
			colorBox1.setBackgroundColor(0xFF000000);
 		}else{
 			carrierText1.setTextColor(Color.parseColor(strColor1));
 			colorBox1.setBackgroundColor(Color.parseColor(strColor1));
 		}
		
		strStyle2 = pref.getString("style2", "Regular");
		strSize2 = pref.getString(Text2Size, "null");
		strColor2 = pref.getString(Text2Color, "null");
		
		if(strStyle2.equals("Bold")) {
	    	carrierText2.setTypeface(null, Typeface.BOLD);
	    	}
	    else if(strStyle2.equals("Italic")) {
	    	carrierText2.setTypeface(null, Typeface.ITALIC);
        	}
	    else if(strStyle2.equals("Bold Italic")) {
         	carrierText2.setTypeface(null, Typeface.BOLD_ITALIC);
         	}
	    else if(strStyle2.equals("Regular")) {
         	carrierText2.setTypeface(null, Typeface.NORMAL);
         	}
	    else if(strStyle2.equals("Cstm Bold")) {
			carrierText2.setTypeface(typetext2, Typeface.BOLD);
			}
	    else if(strStyle2.equals("Cstm Italic")) {
        	carrierText2.setTypeface(typetext2, Typeface.ITALIC);
        	}
	    else if(strStyle2.equals("Cstm Bold Italic")) {
	    	carrierText2.setTypeface(typetext2, Typeface.BOLD_ITALIC);
        	}
	    else if(strStyle2.equals("Cstm Regular")) {
        	carrierText2.setTypeface(typetext2, Typeface.NORMAL);
        	}
		
		float mySize2 = 0;
        try{
        	mySize2 = Integer.parseInt(strSize2);
        }catch (NumberFormatException nfe){
        	System.out.println("angka tidak valid" + nfe);
        }
            		
		if(strSize2.equals("null")){
			carrierText2.setTextSize(18);
		}else{
			carrierText2.setTextSize(mySize2);
		
		}
		
		if(strColor2.equals("null")){
			carrierText2.setTextColor(0xFF0000FF);
			colorBox2.setBackgroundColor(0xFF0000FF);
 		}else{
 			carrierText2.setTextColor(Color.parseColor(strColor2));
 			colorBox2.setBackgroundColor(Color.parseColor(strColor2));
 		}
		
		strStyle3 = pref.getString("style3", "Regular");
		strSize3 = pref.getString(Text3Size, "null");
		strColor3 = pref.getString(Text3Color, "null");
		
		if(strStyle3.equals("Bold")) {
	    	carrierText3.setTypeface(null, Typeface.BOLD);
	    	}
	    else if(strStyle3.equals("Italic")) {
	    	carrierText3.setTypeface(null, Typeface.ITALIC);
        	}
	    else if(strStyle3.equals("Bold Italic")) {
         	carrierText3.setTypeface(null, Typeface.BOLD_ITALIC);
         	}
	    else if(strStyle3.equals("Regular")) {
         	carrierText3.setTypeface(null, Typeface.NORMAL);
         	}
	    else if(strStyle3.equals("Cstm Bold")) {
			carrierText3.setTypeface(typetext3, Typeface.BOLD);
			}
	    else if(strStyle3.equals("Cstm Italic")) {
        	carrierText3.setTypeface(typetext3, Typeface.ITALIC);
        	}
	    else if(strStyle3.equals("Cstm Bold Italic")) {
	    	carrierText3.setTypeface(typetext3, Typeface.BOLD_ITALIC);
        	}
	    else if(strStyle3.equals("Cstm Regular")) {
        	carrierText3.setTypeface(typetext3, Typeface.NORMAL);
        	}
		
		float mySize3 = 0;
        try{
        	mySize3 = Integer.parseInt(strSize3);
        }catch (NumberFormatException nfe){
        	System.out.println("angka tidak valid" + nfe);
        }
            		
		if(strSize3.equals("null")){
			carrierText3.setTextSize(18);
		}else{
			carrierText3.setTextSize(mySize3);
		}
		
		if(strColor3.equals("null")){
			carrierText3.setTextColor(0xFFFF00FF);
			colorBox3.setBackgroundColor(0xFFFF00FF);
 		}else{
 			carrierText3.setTextColor(Color.parseColor(strColor3));
 			colorBox3.setBackgroundColor(Color.parseColor(strColor3));
 		}
		
		if (pref.contains(Text1))	{
			carrierText1.setText(pref.getString(Text1, ""));
			}
		if (pref.contains(Text1Size))	{
			ettext1Size.setText(pref.getString(Text1Size, ""));
			}else{
				ettext1Size.setText("18");
				}
		if (pref.contains(Text1Color))	{
			ettext1Color.setText(pref.getString(Text1Color, ""));
			}else{		
				ettext1Color.setText("#FF000000");
			}
		
		
		if (pref.contains(Text2))	{
			carrierText2.setText(pref.getString(Text2, ""));
			}
		if (pref.contains(Text2Size))	{
			ettext2Size.setText(pref.getString(Text2Size, ""));
			}else{
				ettext2Size.setText("18");
				}
		if (pref.contains(Text2Color))	{
			ettext2Color.setText(pref.getString(Text2Color, ""));
			}else{		
				ettext2Color.setText("#FF0000FF");
			}
		
		if (pref.contains(Text3))	{
			carrierText3.setText(pref.getString(Text3,""));
			}
		if (pref.contains(Text3Size))	{
			ettext3Size.setText(pref.getString(Text3Size, ""));
			}else{
				ettext3Size.setText("18");
				}
		
		if (pref.contains(Text3Color))	{
			ettext3Color.setText(pref.getString(Text3Color, ""));
			}else{		
				ettext3Color.setText("#FFFF00FF");
			}

		pic1Uri = sharedPref1.getString(Carrier1, "null");
		  if (pic1Uri == "null") {
		    	 pic1View.setImageResource(R.drawable.sel_normal);	 	    	 
			     } else {
			    	 pic1View.setImageURI(Uri.parse(pic1Uri));
			     }
		  
		  pic2Uri = sharedPref2.getString(Carrier2, "null");
		  if (pic2Uri == "null") {
		    	 pic2View.setImageResource(R.drawable.sel_normal);	 	    	 
			     } else {
			    	 pic2View.setImageURI(Uri.parse(pic2Uri));
			     }
		  
		  pic3Uri = sharedPref3.getString(Carrier3, "null");
		  if (pic3Uri == "null") {
		    	 pic3View.setImageResource(R.drawable.sel_normal);	 	    	 
			     } else {
			    	 pic3View.setImageURI(Uri.parse(pic3Uri));
			     }

			spinnerStyleText1.setSelection(pref.getInt("spin1style", 0));
			spinnerStyleText2.setSelection(pref.getInt("spin2style", 0));
			spinnerStyleText3.setSelection(pref.getInt("spin3style", 0));
	/*		
			InputStream an = this.getResources().openRawResource(R.drawable.up);
			final Bitmap up = BitmapFactory.decodeStream(an);
			
			InputStream aq = this.getResources().openRawResource(R.drawable.down);
			final Bitmap down = BitmapFactory.decodeStream(aq);
			
			accordionArrow1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(FOCUSED_STATE_SET[0]==1){
						ll1.setVisibility(View.GONE);
						accordionArrow1.setImageBitmap(down);
						FOCUSED_STATE_SET[0]=0;
						}
					else {
						ll1.setVisibility(View.VISIBLE);
						accordionArrow1.setImageBitmap(up);
						FOCUSED_STATE_SET[0]=1;
						}
					}
				});
			
			accordionArrow2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(FOCUSED_STATE_SET[0]==1){
						ll2.setVisibility(View.GONE);
						accordionArrow2.setImageBitmap(down);
						FOCUSED_STATE_SET[0]=0;
						}
					else {
						ll2.setVisibility(View.VISIBLE);
						accordionArrow2.setImageBitmap(up);
						FOCUSED_STATE_SET[0]=1;
						}
					}
				});
			
			accordionArrow3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					if(FOCUSED_STATE_SET[0]==1){
						ll3.setVisibility(View.GONE);
						accordionArrow3.setImageBitmap(down);
						FOCUSED_STATE_SET[0]=0;
						}
					else {
						ll3.setVisibility(View.VISIBLE);
						accordionArrow3.setImageBitmap(up);
						FOCUSED_STATE_SET[0]=1;
						}
					}
				});
			
	*/	  	
			pickClr1 = (ImageView)findViewById(R.id.color1_picker);
			pickClr1.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					openDialog1(false);
					//Intent ic = new Intent(ProfileActivity.this, PickColor.class);
					//startActivity(ic);
				}
			});
			
			pickClr2 = (ImageView)findViewById(R.id.color2_picker);
			pickClr2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					openDialog2(false);
					//Intent ic = new Intent(ProfileActivity.this, PickColor.class);
					//startActivity(ic);
				}
			});
			
			pickClr3 = (ImageView)findViewById(R.id.color3_picker);
			pickClr3.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					openDialog3(false);
					//Intent ic = new Intent(ProfileActivity.this, PickColor.class);
					//startActivity(ic);
				}
			});
			
		galleryPic1 = (ImageView)findViewById(R.id.gall1);
		galleryPic2  = (ImageView)findViewById(R.id.gall2);
		galleryPic3  = (ImageView)findViewById(R.id.gall3);
		
		galleryPic1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_FOR_PIC1);
	        }
	});
		
		galleryPic2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_FOR_PIC2);
	        }
	});
		
		galleryPic3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(Intent.createChooser(intent, "Select Picture"),PICK_FOR_PIC3);
	        }
	});
		
		
		
		saveCarrierText1 = (ImageView) findViewById(R.id.sv_text1);
		saveCarrierText2 = (ImageView) findViewById(R.id.sv_text2);
		saveCarrierText3 = (ImageView) findViewById(R.id.sv_text3);
		
		saveCarrierText1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent itext1 = new Intent();
				itext1.setAction("com.zam.statusbar.CHANGE_TEXT1_LABEL");
				String t1 = carrierText1.getText().toString();
				String tsz1 = ettext1Size.getText().toString();
				String clr1 = ettext1Color.getText().toString();
				
				itext1.putExtra(Text1, t1);
				itext1.putExtra(Text1Size, tsz1);
				itext1.putExtra(Text1Color, clr1);
				itext1.putExtra("style1", styleText1);	
				sendBroadcast(itext1);
				
				if(styleText1.equals("Bold")) {
					carrierText1.setTypeface(null, Typeface.BOLD);
					}
				else if(styleText1.equals("Italic")) {
					carrierText1.setTypeface(null, Typeface.ITALIC);
	            	}
				else if(styleText1.equals("Bold Italic")) {
					carrierText1.setTypeface(null, Typeface.BOLD_ITALIC);
	            	}
				else if(styleText1.equals("Regular")) {
					carrierText1.setTypeface(null, Typeface.NORMAL);
	            	}
				else if(styleText1.equals("Cstm Bold")) {
					carrierText1.setTypeface(typetext1, Typeface.BOLD);
		            }
				else if(styleText1.equals("Cstm Italic")) {
					carrierText1.setTypeface(typetext1, Typeface.ITALIC);
		           	}
				else if(styleText1.equals("Cstm Bold Italic")) {
					carrierText1.setTypeface(typetext1, Typeface.BOLD_ITALIC);
		           	}
				else if(styleText1.equals("Cstm Regular")) {
					carrierText1.setTypeface(typetext1, Typeface.NORMAL);
		           	}
				
				carrierText1.setTextColor(Color.parseColor(clr1));	     		
				
				carrierText1.setTextSize((float)Integer.parseInt(tsz1));
				
				edt.putString(Text1, t1);
				edt.putString(Text1Size, tsz1);
				edt.putString(Text1Color, clr1);
				edt.putString("style1", styleText1);				
				edt.commit();
			}
			});
		
		saveCarrierText2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent itext2 = new Intent();
				itext2.setAction("com.zam.statusbar.CHANGE_TEXT2_LABEL");
				String t2 = carrierText2.getText().toString();
				String tsz2 = ettext2Size.getText().toString();
				String clr2 = ettext2Color.getText().toString();
				
				itext2.putExtra(Text2, t2);
				itext2.putExtra(Text2Size, tsz2);
				itext2.putExtra(Text2Color, clr2);
				itext2.putExtra("style2", styleText2);
				sendBroadcast(itext2);
				
				if(styleText2.equals("Bold")) {
					carrierText2.setTypeface(null, Typeface.BOLD);
					}
				else if(styleText2.equals("Italic")) {
					carrierText2.setTypeface(null, Typeface.ITALIC);
	            	}
				else if(styleText2.equals("Bold Italic")) {
					carrierText2.setTypeface(null, Typeface.BOLD_ITALIC);
	            	}
				else if(styleText2.equals("Regular")) {
					carrierText2.setTypeface(null, Typeface.NORMAL);
	            	}
				else if(styleText2.equals("Cstm Bold")) {
					carrierText2.setTypeface(typetext2, Typeface.BOLD);
		            }
				else if(styleText2.equals("Cstm Italic")) {
					carrierText2.setTypeface(typetext2, Typeface.ITALIC);
		           	}
				else if(styleText2.equals("Cstm Bold Italic")) {
					carrierText2.setTypeface(typetext2, Typeface.BOLD_ITALIC);
		           	}
				else if(styleText2.equals("Cstm Regular")) {
					carrierText2.setTypeface(typetext2, Typeface.NORMAL);
		           	}
				
				carrierText2.setTextColor(Color.parseColor(clr2));
				carrierText2.setTextSize((float)Integer.parseInt(tsz2));

				edt.putString(Text2, t2);
				edt.putString(Text2Size, tsz2);
				edt.putString(Text2Color, clr2);
				edt.putString("style2", styleText2);
				edt.commit();
			}
			});
		
		saveCarrierText3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				
				Intent itext3 = new Intent();
				itext3.setAction("com.zam.statusbar.CHANGE_TEXT3_LABEL");
				String t3 = carrierText3.getText().toString();
				String tsz3 = ettext3Size.getText().toString();
				String clr3 = ettext3Color.getText().toString();
				
				itext3.putExtra(Text3, t3);
				itext3.putExtra(Text3Size, tsz3);
				itext3.putExtra(Text3Color, clr3);
				itext3.putExtra("style3", styleText3);
				sendBroadcast(itext3);

				if(styleText3.equals("Bold")) {
					carrierText3.setTypeface(null, Typeface.BOLD);
					}
				else if(styleText3.equals("Italic")) {
					carrierText3.setTypeface(null, Typeface.ITALIC);
	            	}
				else if(styleText3.equals("Bold Italic")) {
					carrierText3.setTypeface(null, Typeface.BOLD_ITALIC);
	            	}
				else if(styleText3.equals("Regular")) {
					carrierText3.setTypeface(null, Typeface.NORMAL);
	            	}
				else if(styleText3.equals("Cstm Bold")) {
					carrierText3.setTypeface(typetext3, Typeface.BOLD);
		            }
				else if(styleText3.equals("Cstm Italic")) {
					carrierText3.setTypeface(typetext3, Typeface.ITALIC);
		           	}
				else if(styleText3.equals("Cstm Bold Italic")) {
					carrierText3.setTypeface(typetext3, Typeface.BOLD_ITALIC);
		           	}
				else if(styleText3.equals("Cstm Regular")) {
					carrierText3.setTypeface(typetext3, Typeface.NORMAL);
		           	}
				
				carrierText3.setTextColor(Color.parseColor(clr3));
				carrierText3.setTextSize((float)Integer.parseInt(tsz3));
				
				edt.putString(Text3, t3);
				edt.putString(Text3Size, tsz3);
				edt.putString(Text3Color, clr3);
				edt.putString("style3", styleText3);
				edt.commit();
			}
			});

		
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
		super.onActivityResult(requestCode,	resultCode, data);	
		
		if (requestCode == PICK_FOR_PIC1 ) {
			//switch (requestCode){
			//case PICK_FOR_PIC1:
			
			if (data !=null){
				//picUri1 = data.getData();
				Uri uri1 = Uri.parse(data.getDataString());
				pic1View.setImageURI(uri1);
				
				sharedPref1 = getSharedPreferences("EvoPrefsFile",Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref1.edit(); //opens the editor
				editor.putString(Carrier1, uri1.toString()); //true or false
				editor.commit();	
	    
				Intent i = new Intent();
				i.setAction("com.zam.statusbar.CHANGE_PICTURE_1");
				i.putExtra("PIC1", uri1.toString() );
				sendBroadcast(i);
//				break;
			}
		
	    }
		
		if (requestCode == PICK_FOR_PIC2 ) {			
			if (data !=null){
				Uri uri2 = Uri.parse(data.getDataString());
				pic2View.setImageURI(uri2);
				
				sharedPref2 = getSharedPreferences("EvoPrefsFile",Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref2.edit(); //opens the editor
				editor.putString(Carrier2, uri2.toString()); //true or false
				editor.commit();	
	    
				Intent i2 = new Intent();
				i2.setAction("com.zam.statusbar.CHANGE_PICTURE_2");
				i2.putExtra("PIC2", uri2.toString() );
				sendBroadcast(i2);
				
			}
		}
		
		if (requestCode == PICK_FOR_PIC3 ) {			
			if (data !=null){
				//picUri1 = data.getData();
				Uri uri3 = Uri.parse(data.getDataString());
				pic3View.setImageURI(uri3);
				
				sharedPref3 = getSharedPreferences("EvoPrefsFile",Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref3.edit(); //opens the editor
				editor.putString(Carrier3, uri3.toString()); //true or false
				editor.commit();	
	    
				Intent i3 = new Intent();
				i3.setAction("com.zam.statusbar.CHANGE_PICTURE_3");
				i3.putExtra("PIC3", uri3.toString() );
				sendBroadcast(i3);
				
			}
		}
	
		
		}
	
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		int t1 = spinnerStyleText1.getSelectedItemPosition();
		int t2 = spinnerStyleText2.getSelectedItemPosition();
		int t3 = spinnerStyleText3.getSelectedItemPosition();

		styleText1 = stringStyleText1[t1];
		edt.putInt("spin1style", t1);
		
		styleText2 = stringStyleText2[t2];
		edt.putInt("spin2style", t2);
		
		styleText3 = stringStyleText3[t3];
		edt.putInt("spin3style", t3);

	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
	void openDialog1(boolean supportsAlpha) {
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(OtherActivity.this, color1, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				//Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
				OtherActivity.this.color1 = color;
				displayColor1();
			}

			@Override
			public void onCancel(AmbilWarnaDialog dialog) {
				Toast.makeText(getApplicationContext(), "Batal", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.show();
	}

	void displayColor1() {
		ettext1Color.setText(String.format("#%08x", color1));
		//String boxcolor = String.format("#%08x", color);
		colorBox1.setBackgroundColor(color1);
	}
	
	void openDialog2(boolean supportsAlpha) {
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(OtherActivity.this, color2, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				//Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
				OtherActivity.this.color2 = color;
				displayColor2();
			}

			@Override
			public void onCancel(AmbilWarnaDialog dialog) {
				Toast.makeText(getApplicationContext(), "Batal", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.show();
	}

	void displayColor2() {
		ettext2Color.setText(String.format("#%08x", color2));
		//String boxcolor = String.format("#%08x", color);
		colorBox2.setBackgroundColor(color2);
	}
	
	void openDialog3(boolean supportsAlpha) {
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(OtherActivity.this, color3, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				//Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
				OtherActivity.this.color3 = color;
				displayColor3();
			}

			@Override
			public void onCancel(AmbilWarnaDialog dialog) {
				Toast.makeText(getApplicationContext(), "Batal", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.show();
	}

	void displayColor3() {
		ettext3Color.setText(String.format("#%08x", color3));
		//String boxcolor = String.format("#%08x", color);
		colorBox3.setBackgroundColor(color3);
	}


}

