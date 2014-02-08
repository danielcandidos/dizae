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

public class HomeRankingActivity extends Activity {
	
	private Spinner aspn;
	private List<String> listaSelecione = new ArrayList<String>();
	private String opcao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_ranking);
		
		listaSelecione.add("Selecione");
		listaSelecione.add("Mais recentes");
		listaSelecione.add("Seu bairro");
		listaSelecione.add("Ranking 10");
				
		//Identifica o Spinner no layout
		aspn = (Spinner) findViewById(R.id.spinner1);
		//Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList listaSelecione
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaSelecione);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		aspn.setAdapter(spinnerArrayAdapter);
		
		//Método do Spinner para capturar o item selecionado
		aspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
		 
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
				//pega opcao pela posição
				opcao = parent.getItemAtPosition(posicao).toString();
				//imprime um Toast na tela com o opcao que foi selecionado
				Toast.makeText(HomeRankingActivity.this, "opcao Selecionado: " + opcao, Toast.LENGTH_LONG).show();
			}
		
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
		 
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_recl, menu);
		return true;
	}

}
