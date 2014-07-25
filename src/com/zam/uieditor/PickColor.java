package com.zam.uieditor;

import yuku.ambilwarna.AmbilWarnaDialog;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class PickColor extends Activity {
	TextView text1;
	int color = 0xffffff00;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final View button1 = findViewById(R.id.button1);
		final View button2 = findViewById(R.id.button2);
		text1 = (TextView) findViewById(R.id.text1);
		displayColor();

		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openDialog(false);
			}
		});

		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				openDialog(true);
			}
		});

	}

	void openDialog(boolean supportsAlpha) {
		AmbilWarnaDialog dialog = new AmbilWarnaDialog(PickColor.this, color, supportsAlpha, new AmbilWarnaDialog.OnAmbilWarnaListener() {
			@Override
			public void onOk(AmbilWarnaDialog dialog, int color) {
				Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT).show();
				PickColor.this.color = color;
				displayColor();
			}

			@Override
			public void onCancel(AmbilWarnaDialog dialog) {
				Toast.makeText(getApplicationContext(), "cancel", Toast.LENGTH_SHORT).show();
			}
		});
		dialog.show();
	}

	void displayColor() {
		text1.setText(String.format("Current color: 0x%08x", color));
	}

}
