package com.dizae;

import com.dizae.database.UserDAO;
import com.dizae.models.entities.User;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	Button btLogin;
	EditText editTextUserName,editTextPassword;
	UserDAO userDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		userDAO=new UserDAO(this);
		userDAO=userDAO.open();
		
		final EditText editTextUserName=(EditText)findViewById(R.id.email);
		final EditText editTextPassword=(EditText)findViewById(R.id.senha);

		btLogin = (Button) findViewById(R.id.btLogin);

		btLogin.setOnClickListener(new View.OnClickListener() {                 
			@Override
			public void onClick(View arg0) {
				/* Verifica��o de login com bd remoto
	 			startProcess.execute(editTextUserName,editTextPassword);
				
				try {
					if (startProcess.get()==1){
						Toast.makeText(MainActivity.this, "Bem-vindo!", Toast.LENGTH_SHORT).show();
						//mensagemExibir("Login","Usuario Valido!");
						//openOrCreateDatabase();
						chamaHome();
						
					}
					else
						Toast.makeText(MainActivity.this, "Usuário ou senha inválidos!", Toast.LENGTH_LONG).show();
						//mensagemExibir("Login","UsuÃ¡rio ou senha invÃ¡lidos!");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	 			 */
				String userName=editTextUserName.getText().toString();
				String password=editTextPassword.getText().toString();
				
				String storedPassword=userDAO.getSinlgeEntry(userName);
	 			
	 			
				if(password.equals(storedPassword))
				{
					Toast.makeText(LoginActivity.this, "Bem vindo!", Toast.LENGTH_LONG).show();
					chamarHomeRank();
				}
				else
				{
					Toast.makeText(LoginActivity.this, "Usu�rio ou senha incorretos!", Toast.LENGTH_LONG).show();
				}                         
			}
		});
		
		Button cadastro = (Button) findViewById(R.id.btCadastro);		
		cadastro.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chamaCadastro();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void chamarHomeRank () {
		Intent intent = new Intent(this, HomeRankingActivity.class); 
		//intent.putExtra("user",userId);
		startActivity (intent);   
	}
	
	public void chamaHome(){
		Intent entra = new Intent(this, UsuarioActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);
	}
	
	public void chamaCadastro(){
		Intent entra = new Intent(this, CadastroActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);
	}
	
	protected void onDestroy() {
		super.onDestroy();
	    // Close The Database
		userDAO.close();
	}

}
