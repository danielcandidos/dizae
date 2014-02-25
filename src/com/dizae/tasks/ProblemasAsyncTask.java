package com.dizae.tasks;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.dizae.database.ConexaoHttpClient;
import com.dizae.models.entities.Problema;

public class ProblemasAsyncTask extends AsyncTask<Void, Void, JSONObject> {

	private ProblemasListener listener;
	private final String URL_STRING= "http://ec2-54-200-36-55.us-west-2.compute.amazonaws.com/dizae/index.php/problemas/";
	private String actionString; 
	private ConexaoHttpClient conexaoHttpClient;
	private ProblemasAction action;
	private Problema problema;

	public ProblemasAsyncTask(ProblemasListener listener,ProblemasAction action,Problema problema){
		this.listener = listener;
		this.action = action;
		conexaoHttpClient = new ConexaoHttpClient();	
		this.problema = problema;
	}

	public ProblemasAsyncTask(ProblemasListener listener,ProblemasAction action,String problema){
		this.listener = listener;
		this.action = action;
		conexaoHttpClient = new ConexaoHttpClient();	
	}

	@Override
	protected JSONObject doInBackground(Void... arg0) {
		if(action == ProblemasAction.CASDASTRAR){
			actionString = "";
			try {
				actionString = actionString+"novo?titulo="+URLEncoder.encode(problema.getTitulo(),"UTF-8");
				actionString = actionString+"&descricao="+URLEncoder.encode(problema.getDescricao(),"UTF-8");
				actionString = actionString+"&categoria="+URLEncoder.encode(problema.getCategoria(),"UTF-8");
				actionString = actionString+"&usuario="+1;
				actionString = actionString+"&latitude="+problema.getLatitude();
				actionString = actionString+"&longitude"+problema.getLongitude();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		try {
			String stringResult = conexaoHttpClient.executaHttpGet(URL_STRING+actionString);
			JSONObject object = new JSONObject(stringResult);
			return object;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	protected void onPostExecute(JSONObject result){
		if(action==ProblemasAction.CASDASTRAR){
			listener.onSalvaProblema(result);
		}
		if(action==ProblemasAction.EDITAR){
			listener.onBuscarProblema(result);
		}
	}

	public interface ProblemasListener{
		void onSalvaProblema(JSONObject object);
		void onEditarProblema(JSONObject object);
		void onBuscarProblema(JSONObject object);		
	}

	public enum ProblemasAction{
		CASDASTRAR,EDITAR,BUSCAR;
	}

}
