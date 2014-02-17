package com.dizae;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class UsuarioActivity extends Activity {
	
	Button editButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_usuario);
		
		editButton=(Button)findViewById(R.id.button1);
		editButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chamaeditarUsuario();
				
			
			}
		});

	}
		public void chamaeditarUsuario(){
			Intent chama = new Intent(this, EditarUsuarioActivity.class);
			chama.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(chama);
		}
		
		@Override
		protected void onDestroy() {
			super.onDestroy();
			
		}



}
