package com.example.movil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class BebestibleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bebestible);
	}

	public void next(View view){
		Intent intent = new Intent(this, DetalleActivity.class);
		TextView entradaButton = (TextView) findViewById(R.id.next);
		startActivity(intent);
	}
}
