package com.example.movil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class DetalleActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detalle);
	}
	
	public void confirm(View view){
		//add to database 
		Intent intent = new Intent(this, MainActivity.class);
		TextView entradaButton = (TextView) findViewById(R.id.confirm);
		startActivity(intent);
	}
	
	public void cancel(View view){
		//go to mainactivity
		Intent intent = new Intent(this, MainActivity.class);
		TextView entradaButton = (TextView) findViewById(R.id.cancel);
		startActivity(intent);
	}


}
