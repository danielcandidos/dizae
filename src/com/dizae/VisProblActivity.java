package com.dizae;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dizae.database.ProblemaDAO;
import com.dizae.models.entities.Problema;
import com.dizae.tasks.ProblemasAsyncTask;
import com.dizae.tasks.ProblemasAsyncTask.ProblemasAction;
import com.dizae.tasks.ProblemasAsyncTask.ProblemasListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class VisProblActivity extends Activity implements ProblemasListener{
	
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
			int problemaId = intent.getIntExtra("problemaId", 0);
			new ProblemasAsyncTask(this,ProblemasAction.BUSCAR_PROBLEMA,problemaId).execute();	
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

	@Override
	public void onSalvaProblema(JSONObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEditarProblema(JSONObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBuscarProblema(JSONObject object) {
		// TODO Auto-generated method stub
		preencherProblema(object);
		
	}

	private void preencherProblema(JSONObject object) {
		// TODO Auto-generated method stub
		JSONArray problemas;
		try {
			problemas = object.getJSONArray("problemas");
			JSONObject problema = problemas.getJSONObject(0);
			tvTitulo.setText(problema.getString("titulo"));
			tvProb.setText(problema.getString("descricao"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void onBuscarProblemaTodos(JSONObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBuscarProblemaCategoria(JSONObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGetCategorias(JSONObject object) {
		// TODO Auto-generated method stub
		
	}

}
