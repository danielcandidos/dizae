package com.dizae;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class RepProblActivity extends Activity {
	
	private Spinner aspn;
	private List<String> categorias = new ArrayList<String>();
	private String categoria;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rep_probl);
		
		categorias.add("Saúde");
		categorias.add("Educação");
		categorias.add("Segurança");
		categorias.add("Transporte");
		categorias.add("Iluminação pública");
		categorias.add("Limpeza urbana");
 
		//Identifica o Spinner no layout
		aspn = (Spinner) findViewById(R.id.spinner1);
		//Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList nomes
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		aspn.setAdapter(spinnerArrayAdapter);
 
		//Método do Spinner para capturar o item selecionado
		aspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
 
			public void onItemSelected1(AdapterView<?> parent, View v, int posicao, long id) {
				//pega nome pela posição
				categoria = parent.getItemAtPosition(posicao).toString();
				//imprime um Toast na tela com o nome que foi selecionado
				Toast.makeText(RepProblActivity.this, "Nome Selecionado: " + categoria, Toast.LENGTH_LONG).show();
			}
 
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
 
			}

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}
	
	
}





