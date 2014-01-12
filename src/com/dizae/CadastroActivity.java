package com.dizae;

import com.dizae.models.business.UserController;
import com.dizae.models.entities.User;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CadastroActivity extends Activity {

	Button registerButton, cancelButton;
	EditText nameEditText, emailEditText,
	passwordEditText, passwordConfirmeEditText, cpfEditText,
	enderecoEditText;

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



}
