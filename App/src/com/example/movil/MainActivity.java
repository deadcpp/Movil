package com.example.movil;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	public Mesa Mesa = new Mesa("","");
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Intent info = getIntent();
		Mesa.mesa = info.getStringExtra("mesa");
		Log.d("intent_data", info.getStringExtra("mesa")); // APP se cae!
		System.out.println("MainActivity");
		//array con el menu (lista)
		List menu = new ArrayList<Menu>();

		
		menu.add("Entrada"); //0
		menu.add("Plato de Fondo"); //1
		menu.add("Postre"); //2
		menu.add("Bebestible"); //3
		menu.add("Menu del dia"); //4
		menu.add("Administrador"); //5

		
		
		ListView listview=(ListView)findViewById(R.id.listaNotas);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menu);
		listview.setAdapter(adapter);
		
		//onclick por cada fila
		listview.setOnItemClickListener(new OnItemClickListener() {	   
	    	   @Override
		       public void onItemClick(AdapterView<?> parent, View view, int position, long id){        
	    				//--Ir a vista : Opciones--//

	    		   		
	    		   
	    		   		//Notas item=NotaList2.get(position);
	    		   		
	    		   		//ENTRADA
	    		   		if(position == 0){
	    		   
		    				Intent ir_a = new Intent (MainActivity.this, EntradaActivity.class);
		    				ir_a.putExtra("mesa", Mesa.mesa);
		    				Log.d("ENVIO MESA", Mesa.mesa);
		    				startActivity(ir_a);
	    		   		}
	    		   		
	    		   		//Plato
	    		   		if(position == 1){
	    		   
		    				Intent ir_a = new Intent (MainActivity.this, PlatoActivity.class);		        			
		    				startActivity(ir_a);
	    		   		}
	    		   		
	    		   		//Postre
	    		   		if(position == 2){
	    		   
		    				Intent ir_a = new Intent (MainActivity.this, PostreActivity.class);		        			
		    				startActivity(ir_a);
	    		   		}
	    		   		
	    		   		//Bebestible
	    		   		if(position == 3){
	    		   
		    				Intent ir_a = new Intent (MainActivity.this, BebestibleActivity.class);		        			
		    				startActivity(ir_a);
	    		   		}
	    		   		
	    		   		//Menu
	    		   		/*if(position == 4){
	    		   
		    				Intent ir_a = new Intent (MainActivity.this, MenuRapidoActivity.class);		        			
		    				startActivity(ir_a);
	    		   		}*/
	    		   		
	    		   		//administrador
	    		   		if(position == 5){
	    		    		   
		    				Intent ir_a = new Intent (MainActivity.this, ListaMesasActivity.class);		        			
		    				startActivity(ir_a);
	    		   		}
	    		   		
	    		   		
	    	   }
	    		 
		        });
			
		}
		
		
}
