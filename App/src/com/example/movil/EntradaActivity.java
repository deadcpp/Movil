package com.example.movil;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;


public class EntradaActivity extends Activity {	
	ListView list;
	String[] web = {
			"Google Plus",
			"Twitter",
			"Windows",
			"Bing",
			"Itunes",
			"Wordpress",
			"Drupal"
	};
  
		
	Integer[] imageId = {
			R.drawable.image1,
			R.drawable.image2,
			R.drawable.image3,
			R.drawable.image4,
			R.drawable.image5,
			R.drawable.image6,
			R.drawable.image7
  };
	
	
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_entrada);
    Ingredientes adapter = new
        Ingredientes(EntradaActivity.this, web, imageId);
    
    
    list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Toast.makeText(EntradaActivity.this, "You Clicked at " +web[+ position], Toast.LENGTH_SHORT).show();
                }
            });
  }
  
  public void next(View view){
	  	System.out.println("Open PlatoActivity.class");
		Intent intent = new Intent(this, PlatoActivity.class);
		TextView entradaButton = (TextView) findViewById(R.id.next);
		startActivity(intent);
  }
  
}