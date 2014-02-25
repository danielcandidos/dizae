package com.dizae.tasks;

import org.json.JSONObject;

import android.os.AsyncTask;

public class UsuariosAsyncTask extends AsyncTask<Void, Void, JSONObject>{

	@Override
	protected JSONObject doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public interface UsuariosListener{
		void onSalvaProblema(JSONObject object);
		void onEditarProblema(JSONObject object);
		void onBuscarProblema(JSONObject object);		
	}
	
	public enum UsuariosAction{
		CASDASTRAR,EDITAR,BUSCAR;
	}
	

}
