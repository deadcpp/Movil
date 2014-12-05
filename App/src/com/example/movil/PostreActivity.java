package com.example.movil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


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

public class PostreActivity extends Activity {
	  final Context context = this;
	  
	  public List Pedido = new ArrayList<Pedido>();
	  public Mesa Mesa = new Mesa("","");
	
	  private ArrayList<String> arrayid;
	  private ArrayList<String> arraycantidad;
	  private String observacion;
	  
	  
	  ListView list;
	  TextView id;
	  TextView nombre;
	  TextView descripcion;
	  Button Btngetdata;
	  ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	  //URL to get JSON Array
	  private static String url = "http://200.14.84.183/~17325436/Appmovil/obtenerPostre.php";
	  //JSON Node Names
	  private static final String TAG_ingredientes = "ingredientes";
	  private static final String TAG_id = "id";
	  private static final String TAG_nombre = "nombre";
	  private static final String TAG_descripcion = "descripcion";
	  private static final String TAG_precio = "precio";
	  private static final String TAG_calorias = "calorias";
	  private static final String TAG_cantidad = "cantidad";
	  JSONArray android = null;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_postre);
	        
	        //OBTENER DATOS DE LA OTRA VISTA
			Intent info=getIntent();
			arrayid=info.getStringArrayListExtra("id");
			arraycantidad=info.getStringArrayListExtra("cantidad");
			Mesa.observaciones=info.getStringExtra("observaciones");
			Mesa.mesa=info.getStringExtra("mesa");
			
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
	    
	    public void next(View view){
	    	
	    	List arrayid = new ArrayList<String>();
			List arraycantidad = new ArrayList<String>();
			
			//Enviar s�lo objetos que contengan cantidad > 0
			for(int i=0; i< Pedido.size(); i++){
				if( Integer.parseInt( ((Pedido)Pedido.get(i)).cantidad ) > 0 ){
					arrayid.add( ((Pedido) Pedido.get(i)).id );
					arraycantidad.add( ((Pedido) Pedido.get(i)).cantidad );
					
					Log.d("SE ENVIA ", "id: " + ((Pedido) Pedido.get(i)).id + " cantidad: " + ((Pedido) Pedido.get(i)).cantidad);
				}
			}
			
			EditText editText = (EditText) findViewById(R.id.observaciones);
			String observacion = editText.getText().toString();
			
			//Agregar observaciones
			Mesa.observaciones += "Postre: " + observacion + "\n";
			
			Log.d("SE ENVIA ", "observaciones: " + Mesa.observaciones);
			
			Intent intent = new Intent(this, BebestibleActivity.class);
			//send arraylist String			
			intent.putStringArrayListExtra("id", (ArrayList<String>) arrayid);
			intent.putStringArrayListExtra("cantidad", (ArrayList<String>)arraycantidad);
			intent.putExtra("observaciones", Mesa.observaciones);
			intent.putExtra("mesa", Mesa.mesa);

	    	
			
			TextView entradaButton = (TextView) findViewById(R.id.next);
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
		            pDialog = new ProgressDialog(PostreActivity.this);
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
		            map.put(TAG_cantidad, cantidad);
		            oslist.add(map);
		            
		            Pedido.add(new Pedido(id, cantidad));
		            
		            list=(ListView)findViewById(R.id.list);
		            ListAdapter adapter = new SimpleAdapter(PostreActivity.this, oslist, R.layout.list_v,
		                new String[] { TAG_nombre }, new int[] {R.id.nombre});
		            list.setAdapter(adapter);
		            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		                    @Override
		                    public void onItemClick(AdapterView<?> parent, View view,
		                                            int position, long id) {
		                        //Toast.makeText(EntradaActivity.this, "You Clicked at "+oslist.get(+position).get("nombre"), Toast.LENGTH_SHORT).show();
		                        
		                        
		                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
		                        
		                        //TEST EDIT
		                        final EditText input = new EditText(context);
		                        input.setText("0");
		                        alertDialogBuilder.setView(input);
		                			// set title
		                			alertDialogBuilder.setTitle(oslist.get(+position).get("nombre"));
		                			
		                			//message
		                			String message = "Descripcion: " + oslist.get(+position).get("descripcion") + "\n " 
	            							+ "Price: $" + oslist.get(+position).get("precio") + "\n "
	            							+ "Calorias: " + oslist.get(+position).get("calorias") + "\n ";
	            							
		                			for(int j=0; j< Pedido.size(); j++){
	        							if( ((Pedido)Pedido.get(j)).id.contains(oslist.get(+position).get("id")) ){
	        								message += "Cantidad: " + ((Pedido)Pedido.get(j)).cantidad + "\n ";
	        								input.setText(((Pedido)Pedido.get(j)).cantidad);
	        								
	        							}
	        						}
		                			
		                			message += "\n\n Cantidad ingresada: ";
		                			
		                			final String msg = oslist.get(+position).get("nombre");
		                			final String idproduct = oslist.get(+position).get("id");
		                			// set dialog message
		                			alertDialogBuilder
		                				.setMessage(message)
		                				.setCancelable(false)
		                				/*.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
		                					public void onClick(DialogInterface dialog,int id) {
		                						// if this button is clicked, close
		                						// current activity
		                						EntradaActivity.this.finish();
		                					}
		                				  })*/
		                				.setNegativeButton("Ok",new DialogInterface.OnClickListener() {
		                					public void onClick(DialogInterface dialog,int id) {
		                						// if this button is clicked, just close
		                						// the dialog box and do nothing
		                						String value = input.getText().toString().trim();
		                						
		                						for(int j=0; j< Pedido.size(); j++){
		                							if( ((Pedido)Pedido.get(j)).id.contains(idproduct) ){
		                								((Pedido)Pedido.get(j)).cantidad = value;
		                								
		                								Log.d("CANTIDAD", "id: " + ((Pedido)Pedido.get(j)).id + " cantidad: " + ((Pedido)Pedido.get(j)).cantidad);
		                								
		                							}
		                						}
		                						
		                			            Toast.makeText(getApplicationContext(), "Cantidad: "+ value + " " + msg, Toast.LENGTH_SHORT).show();
		                						dialog.cancel();
		                					}
		                				});
		                			
		                				
		                 
		                				// create alert dialog
		                				AlertDialog alertDialog = alertDialogBuilder.create();
		                 
		                				// show it
		                				alertDialog.show();
		                		
		                        
		                    }
		                });
		            
		            Log.d("PEDIDO", "Largo: " + Pedido.size());
		            for(int ix=0; ix < Pedido.size(); ix++){
		            	Log.d("PEDIDO", "id: " + ((Pedido)Pedido.get(ix)).id + " cantidad: " + ((Pedido)Pedido.get(ix)).cantidad);
		            }
		            
		            }//end json size
		            
		            
		            
		            
		        } catch (JSONException e) {
		          e.printStackTrace();
		        }
		       }
		    }
		}