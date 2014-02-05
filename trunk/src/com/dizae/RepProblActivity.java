package com.dizae;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
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
		
		categorias.add("Sa�de");
		categorias.add("Educa��o");
		categorias.add("Seguran�a");
		categorias.add("Transporte");
		categorias.add("Ilumina��o p�blica");
		categorias.add("Limpeza urbana");
 
		//Identifica o Spinner no layout
		aspn = (Spinner) findViewById(R.id.spinner1);
		//Cria um ArrayAdapter usando um padr�o de layout da classe R do android, passando o ArrayList nomes
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		aspn.setAdapter(spinnerArrayAdapter);
 
		//M�todo do Spinner para capturar o item selecionado
		aspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
 
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
				//pega nome pela posi��o
				categoria = parent.getItemAtPosition(posicao).toString();
				//imprime um Toast na tela com o nome que foi selecionado
				Toast.makeText(RepProblActivity.this, "Nome Selecionado: " + categoria, Toast.LENGTH_LONG).show();
			}
 
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
 
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





