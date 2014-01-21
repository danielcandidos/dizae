package com.dizae;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {
	
	Button btLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		btLogin = (Button) findViewById(R.id.btLogin);
		

		btLogin.setOnClickListener(new View.OnClickListener() {                 
			@Override
			public void onClick(View arg0) {
				chamarHomeRank();                         
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
		startActivity (intent);   
	}

}
