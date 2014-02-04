package com.dizae;

import java.util.concurrent.ExecutionException;

import com.dizae.content.HomeFragment;
import com.dizae.database.UserDAO;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	Button btLogin, btCadastrar;
	UserDAO userDAO;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		
	
	     userDAO=new UserDAO(this);
	     userDAO=userDAO.open();
	     
	     final  EditText editTextUserName=(EditText)findViewById(R.id.email);
		 final  EditText editTextPassword=(EditText)findViewById(R.id.senha);
	     
	     btLogin=(Button)findViewById(R.id.btLogin);
	     btLogin.setOnClickListener(new View.OnClickListener() {
	 		
	 		public void onClick(View v) {
	 			// TODO Auto-generated method stub
	 			
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
					Toast.makeText(MainActivity.this, "Bem vindo!", Toast.LENGTH_LONG).show();
					chamaHome();
				}
				else
				{
					Toast.makeText(MainActivity.this, "Usu�rio ou senha incorretos!", Toast.LENGTH_LONG).show();
				}
			}
		});
		
		Button cadastro = (Button) findViewById(R.id.btCancelar);		
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
	
	public void chamaCadastro(){
		Intent entra = new Intent(this, CadastroActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);
	}
	
	/*
	private DrawerLayout mDrawerLayout;
    private LinearLayout mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    
    private TextView mapOption;
    private TextView userOption;

    //private CharSequence mDrawerTitle;
    //private CharSequence mTitle;    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_home);
        
        inicialize();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (LinearLayout) findViewById(R.id.sidebar);
        
        
    }
    
    private void changeFragment(Fragment fragment){
    	 
         Bundle args = new Bundle();         

         FragmentManager fragmentManager = getFragmentManager();
         fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

               
         mDrawerLayout.closeDrawer(mDrawerList);
    }
    
    
    
    private void inicialize(){
    	mapOption = (TextView) findViewById(R.id.sidebar_map_option);    	
    	mapOption.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				changeFragment(new MapFragment());
			}
		});
    	
    	userOption = (TextView) findViewById(R.id.sidebar_user_email);
    	userOption.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {				
				//changeFragment(new HomeFragment());
			}
		});
    	
    }
    */

}
