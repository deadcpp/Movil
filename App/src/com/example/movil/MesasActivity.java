package com.example.movil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MesasActivity extends Activity {

	private Boolean activar;
	private String table;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mesas);
	}
	public void habilitarMesa(View view){
		EditText number_input =  (EditText) findViewById(R.id.number_input);
		activar = false;
		table = number_input.getText().toString().trim();
		new TheTask().execute();
		//postData(number_input.getText().toString().trim(), false);
		Toast.makeText(this, "Mesa habilitada", Toast.LENGTH_LONG).show();
	}
	public void atenderMesa(View view){
		Intent intent = new Intent(this, MainActivity.class);
		EditText number_input =  (EditText) findViewById(R.id.number_input);
		String value = number_input.getText().toString().trim();
		Log.d("Mesas numero:", value);
		activar = true;
		table = number_input.getText().toString().trim();
		new TheTask().execute();
		//postData(number_input.getText().toString().trim(), true);
		intent.putExtra("mesa", value);
		startActivity(intent);
	}
	public void listarMesas(View view){
		Intent intent = new Intent(this, ListaMesasActivity.class);
		startActivity(intent);
	}
	
	
	class TheTask extends AsyncTask<Void, Void, Void>
	{
	 @Override
	 protected Void doInBackground(Void... params) {
			Log.d("postData", "config");
		    HttpClient httpclient = new DefaultHttpClient();
		    String url;
		    
		    if (activar){
		    	url = new String("http://200.14.84.183/~17325436/Appmovil/ocuparMesa.php");	
		    	
		    }else{
		    	url = new String("http://200.14.84.183/~17325436/Appmovil/liberarMesa.php");
		    }
		    Log.d("url=", url);
		    HttpPost httppost = new HttpPost(url);
		    Log.d("postData", "setting values");
		    try {
		        // Add your data
		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
		        nameValuePairs.add(new BasicNameValuePair("id", table));
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

		        // Execute HTTP Post Request
				Log.d("postData", "request");
		        HttpResponse response = httpclient.execute(httppost);
		        Log.d("postData", "request done");
		    } catch (ClientProtocolException e) {
		        // TODO Auto-generated catch block
		    } catch (IOException e) {
		        // TODO Auto-generated catch block
		    }
	   return null; 
	 }
	}
	public void postData(String table, Boolean activar) {
	    // Create a new HttpClient and Post Header
		Log.d("postData", "config");
	    HttpClient httpclient = new DefaultHttpClient();
	    String url;
	    
	    if (activar){
	    	url = new String("http://200.14.84.183/~17325436/Appmovil/ocuparMesa.php");	
	    	
	    }else{
	    	url = new String("http://200.14.84.183/~17325436/Appmovil/liberarMesa.php");
	    }
	    Log.d("url=", url);
	    HttpPost httppost = new HttpPost(url);
	    Log.d("postData", "setting values");
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("id", table));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
			Log.d("postData", "request");
	        HttpResponse response = httpclient.execute(httppost);
	        Log.d("postData", "request done");
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	} 
}
