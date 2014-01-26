package com.dizae.content;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dizae.CadastroActivity;
import com.dizae.LoginActivity;
import com.dizae.R;
import com.dizae.UsuarioActivity;
import com.dizae.database.UserDAO;

public class HomeFragment extends Fragment {

	Button btLogin, btCadastrar;
	UserDAO userDAO;


	public HomeFragment(){

	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_home, container, false); 
		
		
		btLogin = (Button) rootView.findViewById(R.id.btActivityLogin);
		btCadastrar = (Button) rootView.findViewById(R.id.btActivityCadastrar);

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

		getActivity().setTitle("Login");
		return rootView;
	}

	public void chamarLogin () {
		Intent intent = new Intent(getActivity(), LoginActivity.class);        
		startActivity (intent);   
	}

	public void chamarCadastro() {
		Intent intent = new Intent(getActivity(),CadastroActivity.class);
		startActivity(intent);          
	}
	       



}
