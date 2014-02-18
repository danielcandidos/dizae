package com.dizae;

import java.util.ArrayList;
import java.util.List;

import com.dizae.database.ProblemaDAO;
import com.dizae.models.entities.Problema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class HomeRankingActivity extends Activity {
	
	//Layout
	private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;
    //
    private ListView listaProb;
	private Spinner aspn;
	private List<String> listaSelecione = new ArrayList<String>();
	private ArrayList<String> listaTemp = new ArrayList<String>();
	private String opcao;
	// Sidebar Options
	private TextView side_home, side_nova_ocorrencia, side_conf, side_user_perfil;
	//
	private ProblemaDAO proDAO;
	private Problema problema;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_ranking);
		
		initSideBar();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.sidebar);
		
        proDAO = new ProblemaDAO(this);
		proDAO.open();
        
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
				if (opcao != "Selecione"){
					Toast.makeText(HomeRankingActivity.this, "opcao Selecionada: " + opcao, Toast.LENGTH_LONG).show();
				}	
			}		
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
		 
			}
		});
		
		//Listagem dos titulos dos problemas com retorno (falho) do banco		
		////listaTemp = proDAO.getListarTudo();
		//Titulos default
		listaTemp.add("TITULO"); listaTemp.add("TITULO"); listaTemp.add("TITULO"); listaTemp.add("TITULO");
		listaProb = (ListView) findViewById(R.id.listView1);
		ArrayAdapter<String> arrayAdapterLista = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listaTemp);
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		listaProb.setAdapter(arrayAdapterLista);
		
		//Metodo para abrir janela do problema selecionado
		listaProb.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick (AdapterView<?> adapter, View v, int pos, long a) {
				// TODO Auto-generated method stub
				String tittle = adapter.getItemAtPosition(pos).toString();				
				Toast.makeText(HomeRankingActivity.this, "Problema selecionado: " + tittle, Toast.LENGTH_LONG).show();
				chamaProblema(tittle);
			}			
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_recl, menu);
		return true;
	}
	
	private void chamaProblema(String titulo){
		Intent i = new Intent(this, VisProblActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Bundle params = new Bundle();
		params.putString("titulo", titulo);
		i.putExtras(params);
		startActivity(i);
	}
	
	private void initSideBar(){
		side_home = (TextView) findViewById(R.id.sidebar_home);
		side_nova_ocorrencia = (TextView) findViewById(R.id.sidebar_nova_ocorrencia);
		side_conf = (TextView) findViewById(R.id.sidebar_conf);
		side_user_perfil = (TextView) findViewById(R.id.sidebar_user_perfil);
		
		
		side_nova_ocorrencia.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				novaOcorrencia();				
			}
		});
		
		side_home.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				home();		
			}
		});
		
		side_conf.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				perfil();
			}
		});
		
		side_user_perfil.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				conf();
			}
		});
	}

	protected void novaOcorrencia() {
		// TODO Auto-generated method stub
		mDrawerLayout.closeDrawer(mDrawerList);
		Intent entra = new Intent(this, RepProblActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);		
	}
	
	protected void home() {
		// TODO Auto-generated method stub
		mDrawerLayout.closeDrawer(mDrawerList);
		Intent entra = new Intent(this, HomeRankingActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);		
	}

	protected void conf() {
		// TODO Auto-generated method stub
		mDrawerLayout.closeDrawer(mDrawerList);
		Intent entra = new Intent(this, UsuarioActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);		
	}
	protected void perfil() {
		// TODO Auto-generated method stub
		mDrawerLayout.closeDrawer(mDrawerList);
		Intent entra = new Intent(this, EditarUsuarioActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);		
	}

	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		//userDAO.close();
		proDAO.close();
	}
}
