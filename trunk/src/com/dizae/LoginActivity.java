package com.dizae;

import com.dizae.database.UserDAO;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends Activity {

	Button btLogin;
	EditText editTextUserName,editTextPassword;
	UserDAO userDAO;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		editTextUserName=(EditText)findViewById(R.id.email);
		editTextPassword=(EditText)findViewById(R.id.senha);

		userDAO=new UserDAO(this);
		userDAO=userDAO.open();

		btLogin = (Button) findViewById(R.id.btLogin);


		btLogin.setOnClickListener(new View.OnClickListener() {                 
			@Override
			public void onClick(View arg0) {
				logar();                         
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	public void logar(){
		
		
	}

	public void chamarHomeRank () {
		Intent intent = new Intent(this, HomeRankingActivity.class);        
		startActivity (intent);   
	}

}
