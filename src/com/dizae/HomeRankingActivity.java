package com.dizae;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dizae.database.ProblemaDAO;
import com.dizae.models.entities.Problema;
import com.dizae.tasks.ProblemasAsyncTask;
import com.dizae.tasks.ProblemasAsyncTask.ProblemasAction;
import com.dizae.tasks.ProblemasAsyncTask.ProblemasListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class HomeRankingActivity extends Activity implements ProblemasListener {

	//Layout
	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerList;
	JSONArray problemas;
	//
	private ListView listaProb;
	private SimpleAdapter adapter;
	private Spinner aspn;
	private List<String> listaSelecione = new ArrayList<String>();
	private ArrayList<String> listaTemp = new ArrayList<String>();
	private Map<Integer,Integer> categoriaMap ;
	private Map<Integer,Integer> problemaMap;
	private String opcao;
	// Sidebar Options
	private TextView side_home, side_nova_ocorrencia, side_conf, side_user_perfil;
	//
	private ProblemaDAO proDAO;
	private Problema problema;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_ranking);

		initSideBar();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (LinearLayout) findViewById(R.id.sidebar);

		proDAO = new ProblemaDAO(this);
		proDAO.open();



		//Método do Spinner para capturar o item selecionado


		//Listagem dos titulos dos problemas com retorno (falho) do banco		
		////listaTemp = proDAO.getListarTudo();
		//Titulos default
		//listaTemp.add("TITULO 01"); listaTemp.add("TITULO 02"); listaTemp.add("TITULO 03"); listaTemp.add("TITULO 04");
		listaProb = (ListView) findViewById(R.id.listView1);
		//ArrayAdapter<String> arrayAdapterLista = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, listaTemp);
		//spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
		//listaProb.setAdapter(arrayAdapterLista);

		new ProblemasAsyncTask(this,ProblemasAction.BUSCAR_TODOS,"").execute();
		new ProblemasAsyncTask(this,ProblemasAction.BUSCAR_CATEGORIAS,"").execute();

		//Metodo para abrir janela do problema selecionado
		listaProb.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick (AdapterView<?> adapter, View v, int pos, long a) {
				// TODO Auto-generated method stub
				String tittle = adapter.getItemAtPosition(pos).toString();				
				Toast.makeText(HomeRankingActivity.this, "Problema selecionado: " + tittle, Toast.LENGTH_LONG).show();
				//chamaProblema(tittle);
				mostrarProblema(problemaMap.get(pos));
			}			
		});

	}

	private Drawable LoadImageFromWebOperations(String url){
		try
		{
			InputStream is = (InputStream) new URL(url).getContent();
			Drawable d = Drawable.createFromStream(is, "src");
			return d;
		}catch (Exception e) {
			System.out.println("Exc="+e);
			return null;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.lista_recl, menu);
		return true;
	}

	private void chamaProblema(String titulo){
		Intent i = new Intent(this, VisProblActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Bundle params = new Bundle();
		params.putString("titulo", titulo);
		i.putExtras(params);
		startActivity(i);
	}

	private void mostrarProblema(int problemaId){
		Intent i = new Intent(getApplicationContext(), VisProblActivity.class);
		i.putExtra("problemaId",problemaId);
		startActivity(i);
	}

	public void buscarProblemas(){

		new ProblemasAsyncTask(this, ProblemasAction.BUSCAR_TODOS,"").execute();

	}

	private void initSideBar(){
		side_home = (TextView) findViewById(R.id.sidebar_home);
		side_nova_ocorrencia = (TextView) findViewById(R.id.sidebar_nova_ocorrencia);
		side_conf = (TextView) findViewById(R.id.sidebar_conf);
		side_user_perfil = (TextView) findViewById(R.id.sidebar_user_perfil);


		side_nova_ocorrencia.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				novaOcorrencia();				
			}
		});

		side_home.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				home();		
			}
		});

		side_conf.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				perfil();
			}
		});

		side_user_perfil.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				conf();
			}
		});
	}

	protected void novaOcorrencia() {
		// TODO Auto-generated method stub
		mDrawerLayout.closeDrawer(mDrawerList);
		Intent entra = new Intent(this, RepProblActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);		
	}

	protected void home() {
		// TODO Auto-generated method stub
		mDrawerLayout.closeDrawer(mDrawerList);
		Intent entra = new Intent(this, HomeRankingActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);		
	}

	protected void conf() {
		// TODO Auto-generated method stub
		mDrawerLayout.closeDrawer(mDrawerList);
		Intent entra = new Intent(this, UsuarioActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);		
	}
	protected void perfil() {
		// TODO Auto-generated method stub
		mDrawerLayout.closeDrawer(mDrawerList);
		Intent entra = new Intent(this, EditarUsuarioActivity.class);
		entra.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(entra);		
	}

	protected void onDestroy() {
		super.onDestroy();
		// Close The Database
		//userDAO.close();
		proDAO.close();
	}

	@Override
	public void onSalvaProblema(JSONObject object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onEditarProblema(JSONObject object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBuscarProblema(JSONObject object) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onBuscarProblemaTodos(JSONObject object) {
		// TODO Auto-generated method stub
		preencherLista(object);


	}

	private void preencherCategorias(JSONObject object){

		try {
			categoriaMap = new HashMap<Integer,Integer>();


			listaSelecione.clear();
			listaSelecione.add("todos");
			JSONArray categorias = object.getJSONArray("categorias");

			for (int i =0;i<categorias.length();i++) {

				JSONObject categoria = categorias.getJSONObject(i);
				categoriaMap.put(i+1,categoria.getInt("id") );
				listaSelecione.add(categoria.getString("nome"));
			}

			//Identifica o Spinner no layout
			aspn = (Spinner) findViewById(R.id.spinner1);
			//Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList listaSelecione
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaSelecione);
			ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			aspn.setAdapter(spinnerArrayAdapter);

			aspn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {		 
				@Override
				public void onItemSelected(AdapterView<?> parent, View v, int posicao, long id) {
					if(posicao>0){

						buscarPorcategoria(categoriaMap.get(posicao));
					}else{
						buscarProblemas();
					}
				}		
				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	protected void buscarPorcategoria(Integer integer) {
		// TODO Auto-generated method stub
		new ProblemasAsyncTask(this, ProblemasAction.BUSCAR_POR_CATEGORIA, integer).execute();

	}

	private void preencherLista(JSONObject object) {
		// TODO Auto-generated method stub
		problemaMap = new HashMap<Integer,Integer>();
		listaProb.setAdapter(null);
		String[] from = new String[] { "title", "description"};
		int[] to = new int[] { R.id.title, R.id.description };
		
		List<HashMap<String, Object>> fillMaps = new ArrayList<HashMap<String, Object>>();
		try {
			problemas = object.getJSONArray("problemas");
			for (int i =0;i<problemas.length();i++) {


				JSONObject problema = problemas.getJSONObject(i);
				problemaMap.put(i,problema.getInt("id"));
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("title", problema.getString("titulo")); // This will be shown in R.id.title
				map.put("description", problema.getString("descricao")); // And this in R.id.description
				//map.put("image", LoadImageFromWebOperations("http://ec2-54-200-36-55.us-west-2.compute.amazonaws.com/dizae/uploads/"+problema.getString("foto")));
				
				fillMaps.add(map);

			}
			
			adapter = new SimpleAdapter(this, fillMaps, R.layout.adater_problema, from, to);
			listaProb.setAdapter(adapter);
			
			
			
			
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					for (int i =0;i<problemas.length();i++) {
						try {
							JSONObject problema = problemas.getJSONObject(i);
							View v =adapter.getView(i, null, listaProb);
							ImageView iv = (ImageView)v.findViewById(R.id.image);
							URL url = new URL("http://ec2-54-200-36-55.us-west-2.compute.amazonaws.com/dizae/uploads/"+problema.getString("foto"));
					        //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
					        HttpGet httpRequest = null;

					        httpRequest = new HttpGet(url.toURI());

					        HttpClient httpclient = new DefaultHttpClient();
					        HttpResponse response = (HttpResponse) httpclient
					                .execute(httpRequest);

					        HttpEntity entity = response.getEntity();
					        BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
					        InputStream input = b_entity.getContent();

					        Bitmap bitmap = BitmapFactory.decodeStream(input);
					        
					        //bitmap.setHeight(75);
					        //bitmap.setWidth(75);

					        iv.setImageBitmap(bitmap);
					        iv.refreshDrawableState();
					        
					        //iv.setSystemUiVisibility(true);
					        
							
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (URISyntaxException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}						
						
						
					}					
				}
			}).start();
			
			
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void onBuscarProblemaCategoria(JSONObject object) {
		// TODO Auto-generated method stub
		preencherLista(object);

	}

	@Override
	public void onGetCategorias(JSONObject object) {
		// TODO Auto-generated method stub
		preencherCategorias(object);

	}
}
