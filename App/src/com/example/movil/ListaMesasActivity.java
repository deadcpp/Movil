package com.example.movil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.movil.MesasActivity.TheTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ListaMesasActivity extends Activity {
	final Context context = this;
	ListView list;
	TextView estado;
	TextView id;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	private static String url = "http://200.14.84.183/~17325436/Appmovil/obtenerMesa.php";
	
	private static final String TAG_mesas = "mesas";
	private static final String TAG_id = "id";
	private static final String TAG_estado = "estado";
	JSONArray android = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lista_mesas);
		oslist = new ArrayList<HashMap<String, String>>();
        new JSONParse().execute();
		
		 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_mesas, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private class JSONParse extends AsyncTask<String, String, JSONObject> {
	       private ProgressDialog pDialog;
	       private String mesa;
	       class TheTask extends AsyncTask<Void, Void, Void>
	   	{
	   	 @Override
	   	 protected Void doInBackground(Void... params) {
	   			Log.d("postData", "config");
	   		    HttpClient httpclient = new DefaultHttpClient();
	   		    String url;
	   		    	url = new String("http://200.14.84.183/~17325436/Appmovil/ocuparMesa.php");	
	   		    Log.d("url=", url);
	   		    HttpPost httppost = new HttpPost(url);
	   		    Log.d("postData", "setting values");
	   		    try {
	   		        // Add your data
	   		        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	   		        nameValuePairs.add(new BasicNameValuePair("id", mesa));
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

	      @Override
	        protected void onPreExecute() {
	            super.onPreExecute();
	             id = (TextView)findViewById(R.id.id);
	             estado = (TextView)findViewById(R.id.estado);
	             //descripcion = (TextView)findViewById(R.id.descripcion);
	            pDialog = new ProgressDialog(ListaMesasActivity.this);
	            pDialog.setMessage("Getting Data ...");
	            pDialog.setIndeterminate(false);
	            pDialog.setCancelable(true);
	            pDialog.show();
	      }
	      @Override
	        protected JSONObject doInBackground(String... args) {
	        JsonParser jParser = new JsonParser();
	        // Getting JSON from URL
	        JSONObject json = jParser.getJSONFromUrl(url);
	        return json;
	      }
	       @Override
	         protected void onPostExecute(JSONObject json) {
	         pDialog.dismiss();
	         try {
	            // Getting JSON Array from URL
	            android = json.getJSONArray(TAG_mesas);
	            for(int i = 0; i < android.length(); i++){
	            JSONObject c = android.getJSONObject(i);
	            // Storing  JSON item in a Variable
	            String id = c.getString(TAG_id);
	            String estado = c.getString(TAG_estado);
	            // Adding value HashMap key => value
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put(TAG_id, "Mesa " + id);
	            if(estado.equals("t")){
	            	map.put(TAG_estado, "\t\t\t\t\tDisponible");
	            }
	            if(estado.equals("f")){
	            	map.put(TAG_estado, "\t\t\t\t\tNo disponible");
	            }
	            oslist.add(map);
	            list=(ListView)findViewById(R.id.list);
	            ListAdapter adapter = new SimpleAdapter(ListaMesasActivity.this, oslist, R.layout.list_m,
	                new String[] { TAG_id, TAG_estado }, new int[] {
	                    R.id.id,R.id.estado});
	            list.setAdapter(adapter);
	            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	                    @Override
	                    public void onItemClick(AdapterView<?> parent, View view,
	                                            int position, long id) {
	                        //Toast.makeText(EntradaActivity.this, "You Clicked at "+oslist.get(+position).get("nombre"), Toast.LENGTH_SHORT).show();
	                    		if (oslist.get(+position).get("estado").equals("\t\t\t\t\tNo disponible")){
	                    			Toast.makeText(ListaMesasActivity.this, "Esta mesa no se encuentra disponible", Toast.LENGTH_LONG).show();
	                    		} else {
		                			Intent intent = new Intent(ListaMesasActivity.this, MainActivity.class);
		                			Log.d("Mesas", TAG_mesas);
		                			Log.d("Mesas", oslist.get(+position).get("id").split(" ",2)[1]);
		                			mesa = oslist.get(+position).get("id").split(" ",2)[1];
		                			intent.putExtra("mesa", oslist.get(+position).get("id").split(" ",2)[1]);
		                			new TheTask().execute();
		                			startActivity(intent);
	                    		}
	                        
	                    }
	                });
	            }
	        } catch (JSONException e) {
	          e.printStackTrace();
	        }
	       }
	    }
}
