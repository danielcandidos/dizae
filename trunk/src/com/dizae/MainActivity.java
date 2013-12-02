package com.dizae;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.*;
import android.widget.*;

public class MainActivity extends Activity {
	
	Button btLogin, btCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
   
        
        btLogin = (Button) findViewById(R.id.btLogin);
        btCadastrar = (Button) findViewById(R.id.btCadastrar);
        
        
        
        btLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
			chamarLogin();
				
			}
		});
        
        btCadastrar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				chamarCadastro();
				
			}
		});
    
    }
    
    

    
    
    public void chamarLogin () {
    	
    	Intent intent = new Intent (this, LoginActivity.class);
    	startActivity (intent);
    	
   
    }
    
    public void chamarCadastro() {
    	Intent intent = new Intent (this, CadastroActivity.class);
    	startActivity(intent);
    	
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
