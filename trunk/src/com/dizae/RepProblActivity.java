package com.dizae;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dizae.database.ProblemaDAO;
import com.dizae.database.UserDAO;
import com.dizae.models.entities.Problema;
import com.dizae.tasks.ProblemasAsyncTask;
import com.dizae.tasks.ProblemasAsyncTask.ProblemasAction;
import com.dizae.tasks.ProblemasAsyncTask.ProblemasListener;

public class RepProblActivity extends Activity implements ProblemasListener{
	
	//Layout
	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerList;
	private Spinner aspn;
	private List<String> categorias = new ArrayList<String>();
	private String categoria;
	private ProblemaDAO proDAO;
	EditText etDescricao, etTitulo;
	Button btReportar, btLogin;
	Problema problema;
	UserDAO userDAO;
	static String user;
	// Sidebar Options
	private TextView side_home, side_nova_ocorrencia, side_conf, side_user_perfil;
	
	//Puxa o nome do usuário que vem da Home, que já veio do Login
	public static void pegarUser(String userName) {
		user = userName;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rep_probl);
		
		initSideBar();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.sidebar);
        
		proDAO = new ProblemaDAO(this);
		proDAO.open();
		
		userDAO=new UserDAO(this);
		userDAO=userDAO.open();
		
		etTitulo=(EditText)findViewById(R.id.etTitulo);
		etDescricao=(EditText)findViewById(R.id.etDescricao);		
		
		categorias.add("Saúde");
		categorias.add("Educação");
		categorias.add("Segurança");
		categorias.add("Transporte");
		categorias.add("Iluminação pública");
		categorias.add("Limpeza urbana");
				
 
		//Identifica o Spinner no layout
		aspn = (Spinner) findViewById(R.id.spinner1);
		//Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList listaSelecione
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categorias);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		aspn.setAdapter(spinnerArrayAdapter);
				
		//Método do Spinner para capturar o item selecionado
		aspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
				//pega opcao pela posição
				categoria = parent.getItemAtPosition(posicao).toString();
				//imprime um Toast na tela com o opcao que foi selecionado
				Toast.makeText(RepProblActivity.this, "opcao Selecionada: " + categoria, Toast.LENGTH_LONG).show();
			}
 			@Override
			public void onNothingSelected(AdapterView<?> parent) {
 
			}
		});
		
		//metodo que conecta o layout de reportar problema com a função já existente no banco
		btReportar=(Button)findViewById(R.id.btVoltar);
		btReportar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				registarProblema();
			}
		});
		
		//botao "visualizar lista de problemas" que estava sem função
		btLogin = (Button)findViewById(R.id.btLogin);
		btLogin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				chamaRanking();
			}
		});
	}
	
	protected void registarProblema() {
		// TODO Auto-generated method stub
		String descricao=etDescricao.getText().toString();
		String titulo=etTitulo.getText().toString();
		String categoria = (String) aspn.getSelectedItem();
		
		if(descricao.equals("")) {
				Toast.makeText(getApplicationContext(), "Descrição não informada!", Toast.LENGTH_LONG).show();
				return;
		} if(categoria.equals("")) {
			Toast.makeText(getApplicationContext(), "Categoria não selecionada!", Toast.LENGTH_LONG).show();
			return;
		} else {
			//problema = new Problema (userDAO.getUser(user), titulo, descricao, categoria, "foto", 0, 0);
			//proDAO.inserEntry(problema);
		    //Toast.makeText(getApplicationContext(), "Problema cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
		    //chamaRanking();
			Problema p = new Problema();
			p.setTitulo(titulo);
			p.setCategoria(categoria);
			p.setDescricao(descricao);
			p.setLongitude(0);
			p.setLatitude(0);
			
			
			new ProblemasAsyncTask(this,ProblemasAction.CASDASTRAR,p).execute();
		}
		
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
	
	public void chamaRanking(){
		Intent i = new Intent(this, HomeRankingActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
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
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}	
	
	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		userDAO.close();
		proDAO.close();
	}

	@Override
	public void onSalvaProblema(JSONObject object) {
		// TODO Auto-generated method stub
		try {
			boolean error = object.getBoolean("error");
			if(!error){
				chamaRanking();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void onEditarProblema(JSONObject object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBuscarProblema(JSONObject object) {
		// TODO Auto-generated method stub
		
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





