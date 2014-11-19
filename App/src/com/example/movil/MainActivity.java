package com.example.movil;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
		    				startActivity(ir_a);
	    		   		}
	    		   		
	    		   		//administrador
	    		   		if(position == 5){
	    		    		   
		    				Intent ir_a = new Intent (MainActivity.this, Login.class);		        			
		    				startActivity(ir_a);
	    		   		}
	    	   }
	    		 
		        });
			
		}
		
		
}
