package com.dizae;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dizae.database.ProblemaDAO;
import com.dizae.database.UserDAO;
import com.dizae.models.entities.Problema;

public class RepProblActivity extends Activity {
	
	private Spinner aspn;
	private List<String> categorias = new ArrayList<String>();
	private String categoria;
	private ProblemaDAO proDAO;
	EditText etDescricao;
	Button btReportar, btLogin;
	Problema problema;
	UserDAO userDAO;
	static String user;
	
	//Puxa o nome do usuário que vem da Home, que já veio do Login
	public static void pegarUser(String userName) {
		user = userName;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rep_probl);	
		
		proDAO = new ProblemaDAO(this);
		proDAO.open();
		
		userDAO=new UserDAO(this);
		userDAO=userDAO.open();
				
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
				Toast.makeText(RepProblActivity.this, "opcao Selecionado: " + categoria, Toast.LENGTH_LONG).show();
			}
 
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
 
			}
		});
		
		//metodo que conecta o layout de reportar problema com a função já existente no banco
		btReportar=(Button)findViewById(R.id.btVoltar);
		btReportar.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				
				String descricao=etDescricao.getText().toString();
				
				if(descricao.equals("")) {
						Toast.makeText(getApplicationContext(), "Descrição não informada!", Toast.LENGTH_LONG).show();
						return;
				} if(categoria.equals("")) {
					Toast.makeText(getApplicationContext(), "Categoria não selecionada!", Toast.LENGTH_LONG).show();
					return;
				} else {
					//não consegui um meio existente de "pegar" o usuario para passar para o problema
					//User user = new User(); //passando usuario vazio
					//user = userDAO.getUser(id);
					problema = new Problema (userDAO.getUser(user), descricao, categoria, "foto", 0, 0);
					proDAO.inserEntry(problema);
				    Toast.makeText(getApplicationContext(), "Problema cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
				    chamaRanking();
				}
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
	
	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		userDAO.close();
		proDAO.close();
	}
}





