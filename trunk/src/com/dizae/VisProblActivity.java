package com.dizae;

import com.dizae.database.ProblemaDAO;
import com.dizae.models.entities.Problema;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class VisProblActivity extends Activity {
	
	Button btLogin;
	TextView tvProb,tvTitulo;
	Problema prob;
	ProblemaDAO proDAO;
	String titulo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_visualizar_problema);
		
		proDAO = new ProblemaDAO(this);
		proDAO.open();
		
		btLogin = (Button) findViewById(R.id.btLogin);
		tvTitulo = (TextView) findViewById(R.id.tvTitulo);
		tvProb = (TextView) findViewById(R.id.tvProb);
		
		//Carrega os valores passados pela homeRanking
		Intent intent = getIntent();		
		if (intent != null) {			
			titulo = intent.getStringExtra("titulo");
			tvTitulo.setText(titulo);
			//buscar problema por titulo, cria objeto problema para manipular
			////prob = proDAO.getProblema(titulo);			
		}		
		
		//metodo que volta para homeRanking
		btLogin.setOnClickListener(new View.OnClickListener() {			
			public void onClick(View v) {
				chamaRanking();
			}
		});
		
	}
	
	public void chamaRanking(){
		Intent i = new Intent(this, HomeRankingActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

}
