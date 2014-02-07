package com.dizae;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dizae.database.UserDAO;

public class CadastroActivity extends Activity {

	Button registerButton, cancelButton;
	EditText nameEditText, emailEditText,
	passwordEditText, passwordConfirmeEditText, cpfEditText,
	enderecoEditText;
	
	UserDAO userDAO;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_cadastro);
	this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		userDAO=new UserDAO(this);
		userDAO=userDAO.open();
		
		nameEditText=(EditText)findViewById(R.id.etNome);
		cpfEditText=(EditText)findViewById(R.id.etCpf);
		enderecoEditText=(EditText)findViewById(R.id.etEndereco);
		emailEditText=(EditText)findViewById(R.id.etEmail);
		passwordEditText=(EditText)findViewById(R.id.etSenha);
		passwordConfirmeEditText=(EditText)findViewById(R.id.etConSenha);
		
		
		registerButton=(Button)findViewById(R.id.btCadastrar);
		registerButton.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			
			String userName=nameEditText.getText().toString();
			String cpf=cpfEditText.getText().toString();
			String endereco=enderecoEditText.getText().toString();
			String email=emailEditText.getText().toString();
			String password=passwordEditText.getText().toString();
			String confirmPassword=passwordConfirmeEditText.getText().toString();
			
			if(userName.equals("")||cpf.equals("")||endereco.equals("")||email.equals("")||password.equals("")||confirmPassword.equals(""))
			{
					Toast.makeText(getApplicationContext(), "Campo vazio!", Toast.LENGTH_LONG).show();
					return;
			}
		
			if(!password.equals(confirmPassword))
			{
				Toast.makeText(getApplicationContext(), "Senhas diferentes!", Toast.LENGTH_LONG).show();
				return;
			}
			else
			{
			    userDAO.insertEntry(userName,cpf,endereco,email,password);
			    Toast.makeText(getApplicationContext(), "Conta criada com Sucesso!", Toast.LENGTH_LONG).show();
			    chamaLogin();
			}
		}
	});

}
	public void chamaLogin(){
		Intent entra = new Intent(this, MainActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		userDAO.close();
	}

	/*
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro);

		nameEditText = (EditText)findViewById(R.id.etNome);
		emailEditText = (EditText)findViewById(R.id.etEmail);
		passwordEditText = (EditText)findViewById(R.id.etSenha);
		passwordConfirmeEditText = (EditText)findViewById(R.id.etConSenha);
		cpfEditText = (EditText)findViewById(R.id.etCpf);
		enderecoEditText = (EditText)findViewById(R.id.etEndereco);
		cpfEditText = (EditText)findViewById(R.id.etCpf);
		registerButton = (Button)findViewById(R.id.btCadastrar);

		registerButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				cadastrar();				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.cadastro, menu);
		return true;
	}

	public boolean cadastrar() {

		User user = new User();

		user.setName(nameEditText.getText().toString());
		user.setCpf(cpfEditText.getText().toString());
		user.setEmail(emailEditText.getText().toString());
		try {
			user.setPassword(passwordEditText.getText().toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		user.setAddress(enderecoEditText.getText().toString());	
		
		try {
			return UserController.register(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		

		

	}
	*/



}
