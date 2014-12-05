package com.example.movil;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.util.Log; 
import android.os.AsyncTask;

public class TestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
	  	    	new AsyncTaskParseJson().execute();
			
	}

	public class AsyncTaskParseJson extends AsyncTask<String, String, String> {
		 
        final String TAG = "AsyncTaskParseJson.java";
 
        // set your json string url here
        String yourJsonStringUrl = "http://200.14.84.183/~17486949/appmoviles/obtenerIngredientes.php";
 
        // contacts JSONArray
        JSONArray dataJsonArr = null;
 
        @Override
        protected void onPreExecute() {}
 
        @Override
        protected String doInBackground(String... arg0) {
 
            try {
 
                // instantiate our json parser
                JsonParser jParser = new JsonParser();
 
                // get json string from url
                JSONObject json = jParser.getJSONFromUrl(yourJsonStringUrl);
 
                // get the array of users
                dataJsonArr = json.getJSONArray("ingredientes");
 
                List ingredientes = new ArrayList<String>();
                
                // loop through all users
                for (int i = 0; i < dataJsonArr.length(); i++) {
 
                    JSONObject c = dataJsonArr.getJSONObject(i);
 
                    // Storing each json item in variable
                    String id = c.getString("id");
                    String nombre = c.getString("nombre");
                    String id_categoria = c.getString("id_categoria");
                    String descripcion = c.getString("descripcion");
                    
                    ingredientes.add(nombre);
 
                    
                    
                    // show the values in our logcat
                    Log.e(TAG, "id: " + id 
                            + ", nombre: " + nombre
                            + ", id_categoria: " + id_categoria
                            + ", descripcion: " + descripcion);
                    
                    ListView listview=(ListView)findViewById(R.id.lista);
            		ArrayAdapter<String> adapter = new ArrayAdapter<String>(TestActivity.this,android.R.layout.simple_list_item_1, ingredientes);
            		listview.setAdapter(adapter);
 
                }
 
            } catch (JSONException e) {
                e.printStackTrace();
            }
 
            return null;
        }
 
        @Override
        protected void onPostExecute(String strFromDoInBg) {}
    }
	


}
