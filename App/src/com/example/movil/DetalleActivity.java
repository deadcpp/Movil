package com.example.movil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import test.restaurant.library.Httppostaux;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import test.restaurant.library.Httppostaux;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class DetalleActivity extends Activity {
	  final Context context = this;
	  
	  public List Pedido = new ArrayList<Pedido>();
	  public Mesa Mesa = new Mesa("","");
	
	  private ArrayList<String> arrayid;
	  private ArrayList<String> arraycantidad;
	  private String observacion;
	  public Integer total = 0;
	  public String detalle = "";
	  
	  
	  ListView list;
	  TextView cantidad;
	  TextView precio;
	  TextView nombre;
	  TextView totalvista;
	  TextView descripcion;
	  Button Btngetdata;
	  ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	  //URL to get JSON Array
	  private static String url = "http://200.14.84.183/~17325436/Appmovil/obtenerIngredientes.php";
	  //JSON Node Names
	  private static final String TAG_ingredientes = "ingredientes";
	  private static final String TAG_id = "id";
	  private static final String TAG_nombre = "nombre";
	  private static final String TAG_descripcion = "descripcion";
	  private static final String TAG_precio = "precio";
	  private static final String TAG_calorias = "calorias";
	  private static final String TAG_cantidad = "cantidad";
	  private static final String TAG_detalle = "";
	  JSONArray android = null;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_detalle);
	        
	        //OBTENER DATOS DE LA OTRA VISTA
			Intent info=getIntent();
			arrayid=info.getStringArrayListExtra("id");
			arraycantidad=info.getStringArrayListExtra("cantidad");
			Mesa.observaciones=info.getStringExtra("observaciones");
			Mesa.mesa = info.getStringExtra("mesa");
			
			//DEBUG
			for(int i=0; i< arrayid.size(); i++){
				Log.d("RECIBO", "id: " + arrayid.get(i));
				Log.d("RECIBO", "cantidad: " + arraycantidad.get(i));
				Pedido.add(new Pedido(arrayid.get(i), arraycantidad.get(i)));
			}
			Log.d("RECIBO", "observacion: " + Mesa.observaciones);
	        
	        
	        oslist = new ArrayList<HashMap<String, String>>();
	        new JSONParse().execute();
	       
	    }
	    
		public void confirm(View view){
			//add to database
	
			
			String URL_connect="http://200.14.84.183/~17325436/Appmovil/detalle.php";
			
								
			
			
			
			Intent intent = new Intent(this, MesasActivity.class);
			TextView entradaButton = (TextView) findViewById(R.id.confirm);
			Toast.makeText(DetalleActivity.this, "Orden registrada", Toast.LENGTH_LONG).show();
			startActivity(intent);
		}
		
		public void cancel(View view){
			//go to mainactivity
			Intent intent = new Intent(this, MesasActivity.class);
			TextView entradaButton = (TextView) findViewById(R.id.cancel);
			startActivity(intent);
		}
	    
	    
	    private class JSONParse extends AsyncTask<String, String, JSONObject> {
	    	private ProgressDialog pDialog;
		      @Override
		        protected void onPreExecute() {
		            super.onPreExecute();
		             //id = (TextView)findViewById(R.id.id);
		       nombre = (TextView)findViewById(R.id.nombre);
		       
		       //cantidad = (TextView)findViewById(R.id.cantidad);
		       //descripcion = (TextView)findViewById(R.id.descripcion);
		            pDialog = new ProgressDialog(DetalleActivity.this);
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
		            android = json.getJSONArray(TAG_ingredientes);
		            for(int i = 0; i < android.length(); i++){
		            JSONObject c = android.getJSONObject(i);
		            // Storing  JSON item in a Variable
		            String id = c.getString(TAG_id);
		            String nombre = c.getString(TAG_nombre);
		            String descripcion = c.getString(TAG_descripcion);
		            String precio = c.getString(TAG_precio);
		            String calorias = c.getString(TAG_calorias);
		            String cantidad = "0";
		            // Adding value HashMap key => value
		            HashMap<String, String> map = new HashMap<String, String>();
		            map.put(TAG_id, id);
		            map.put(TAG_nombre, nombre);
		            map.put(TAG_descripcion, descripcion);
		            map.put(TAG_precio, precio);
		            map.put(TAG_calorias, calorias);
		            
		            
		            //AGREGAR SOLO LOS PEDIDO QUE FUERON TOMADOS
		            for(int ix=0; ix < Pedido.size(); ix++){
		            	if( ((Pedido)Pedido.get(ix)).id.contains(id) ){
		            		map.put(TAG_cantidad, ((Pedido)Pedido.get(ix)).cantidad);
		            		
		            		total += Integer.parseInt(precio)*Integer.parseInt(((Pedido)Pedido.get(ix)).cantidad);
		            		
		            		detalle = nombre + " uni: " +  ((Pedido)Pedido.get(ix)).cantidad + " $" + precio + ".";
		            		map.put(TAG_detalle,  detalle);
		            		oslist.add(map);
		            	}
		            }
		            
		            
		            
		            list=(ListView)findViewById(R.id.detalle);
		            ListAdapter adapter = new SimpleAdapter(DetalleActivity.this, oslist, R.layout.list_detalle,
		                new String[] { TAG_detalle }, new int[] {R.id.nombre});
		            list.setAdapter(adapter);
		            
		            
		            
		            
		            
		            }//end json size
		            
		            Log.d("TOTAL", Integer.toString(total));
		            
		            totalvista=(TextView)findViewById(R.id.total);
		            totalvista.setText("MESA " + Mesa.mesa + "\nTOTAL A PAGAR $" + Integer.toString(total));
		            
		            
		            
		            
		            
		        } catch (JSONException e) {
		          e.printStackTrace();
		        }
		       }
		    }
		}