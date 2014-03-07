package com.dizae.tasks;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.os.AsyncTask;

import com.dizae.database.ConexaoHttpClient;
import com.dizae.models.entities.Problema;
import com.dizae.tasks.ProblemasAsyncTask.ProblemasAction;

public class ProblemasAsyncTask extends AsyncTask<Void, Void, JSONObject> {

	private ProblemasListener listener;
	private final String URL_STRING= "http://ec2-54-200-36-55.us-west-2.compute.amazonaws.com/dizae/index.php/problemas/";
	private String actionString; 
	private ConexaoHttpClient conexaoHttpClient;
	private ProblemasAction action;
	private Problema problema;
	private int categoriaId;

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

	public ProblemasAsyncTask(ProblemasListener listener,ProblemasAction action,int categoriaId) {
		// TODO Auto-generated constructor stub
		this.listener = listener;
		this.action = action;
		this.categoriaId =categoriaId;
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
				return null;
				
			}
			
		}
		
		if(action ==ProblemasAction.BUSCAR_TODOS){
			actionString = "todos";
		}
		
		if(action == ProblemasAction.BUSCAR_CATEGORIAS){
			actionString = "categorias";
		}
		
		if(action == ProblemasAction.BUSCAR_POR_CATEGORIA){
			actionString = "categoria/"+categoriaId;
		}
		
		
		try {
			String stringResult = conexaoHttpClient.executaHttpGet(URL_STRING+actionString);
			JSONObject object = new JSONObject(stringResult);
			return object;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	protected void onPostExecute(JSONObject result){
		if(action==ProblemasAction.CASDASTRAR){
			listener.onSalvaProblema(result);
		}
		if(action==ProblemasAction.EDITAR){
			listener.onBuscarProblema(result);
		}
		if(action==ProblemasAction.BUSCAR_TODOS){
			listener.onBuscarProblemaTodos(result);
		}
		if(action==ProblemasAction.BUSCAR_CATEGORIAS){
			listener.onGetCategorias(result);
		}		
		if(action==ProblemasAction.BUSCAR_POR_CATEGORIA){
			listener.onBuscarProblemaCategoria(result);
		}
		
	}

	public interface ProblemasListener{
		void onSalvaProblema(JSONObject object);
		void onEditarProblema(JSONObject object);
		void onBuscarProblema(JSONObject object);
		void onBuscarProblemaTodos(JSONObject object);
		void onBuscarProblemaCategoria(JSONObject object);
		void onGetCategorias(JSONObject object);
	}

	public enum ProblemasAction{
		CASDASTRAR,EDITAR,BUSCAR,BUSCAR_TODOS,BUSCAR_POR_CATEGORIA,BUSCAR_CATEGORIAS;
	}

}
