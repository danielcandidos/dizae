package com.dizae;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dizae.database.UserDAO;

public class MainActivity extends Activity {
	
	Button btLogin, btCadastrar;
	UserDAO userDAO;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);		
	
	     userDAO=new UserDAO(this);
	     userDAO=userDAO.open();	     
	     
	     btLogin=(Button)findViewById(R.id.btActivityLogin);
	     btLogin.setOnClickListener(new View.OnClickListener() {
	 		
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub	 			
	 			chamaLogin();					
			}
		});
		
		Button cadastro = (Button) findViewById(R.id.btActivityCadastrar);		
		cadastro.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				chamaCadastro();
			}
		});
		
	}
		@Override
		protected void onDestroy() {
			super.onDestroy();
		    // Close The Database
			userDAO.close();
		}
	
	public void chamaHome(){
		Intent entra = new Intent(this, UsuarioActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);
	}
	
	public void chamaLogin(){
		Intent entra = new Intent(this, LoginActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);
	}
	
	public void chamaCadastro(){
		Intent entra = new Intent(this, CadastroActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);
	}
	
	

}
