package com.dizae;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint.Join;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dizae.database.ProblemaDAO;
import com.dizae.database.UserDAO;
import com.dizae.models.entities.Problema;
import com.dizae.tasks.ProblemasAsyncTask;
import com.dizae.tasks.ProblemasAsyncTask.ProblemasAction;
import com.dizae.tasks.ProblemasAsyncTask.ProblemasListener;

public class RepProblActivity extends Activity implements ProblemasListener{

	//Layout
	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerList;
	private Spinner aspn;
	private List<String> categoriasList = new ArrayList<String>();
	private String categoria;
	private ProblemaDAO proDAO;
	EditText etDescricao, etTitulo;
	Button btReportar, btLogin,btFoto;
	Problema problema;
	UserDAO userDAO;
	static String user;
	
	//locatizacao
	double latitude, longitude;
	// Sidebar Options
	private TextView side_home, side_nova_ocorrencia, side_conf, side_user_perfil;
	private HashMap<Integer, Integer> categoriaMap;
	private String md5Image = "";

	// Upload	
	int serverResponseCode = 0;
	ProgressDialog dialog = null;
	String upLoadServerUri = "http://ec2-54-200-36-55.us-west-2.compute.amazonaws.com/dizae/index.php/problemas/upload";

	/**********  File Path *************/
	final String uploadFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()+"/";
	final String uploadFileName = "foto.jpg";

	//Puxa o nome do usuário que vem da Home, que já veio do Login
	public static void pegarUser(String userName) {
		user = userName;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rep_probl);

		initSideBar();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (LinearLayout) findViewById(R.id.sidebar);

		proDAO = new ProblemaDAO(this);
		proDAO.open();

		userDAO=new UserDAO(this);
		userDAO=userDAO.open();

		etTitulo=(EditText)findViewById(R.id.etTitulo);
		etDescricao=(EditText)findViewById(R.id.etDescricao);		



		//Identifica o Spinner no layout
		aspn = (Spinner) findViewById(R.id.spinner1);
		//Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList listaSelecione


		new ProblemasAsyncTask(this,ProblemasAction.BUSCAR_CATEGORIAS,"").execute();

		//metodo que conecta o layout de reportar problema com a função já existente no banco
		btReportar=(Button)findViewById(R.id.btVoltar);
		btReportar.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				uploadFoto();		
				
			}
		});

		//botao "visualizar lista de problemas" que estava sem função
		btLogin = (Button)findViewById(R.id.btLogin);
		btLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				chamaRanking();
			}
		});

		btFoto = (Button)findViewById(R.id.button2);
		btFoto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				tirarFoto();

			}
		});
	}

	protected void registarProblema() {
		// TODO Auto-generated method stub
		String descricao=etDescricao.getText().toString();
		String titulo=etTitulo.getText().toString();
		String categoria = String.valueOf(categoriaMap.get(aspn.getSelectedItemPosition()));

		if(descricao.equals("")) {
			Toast.makeText(getApplicationContext(), "Descrição não informada!", Toast.LENGTH_LONG).show();
			return;
		} if(categoria.equals("")) {
			Toast.makeText(getApplicationContext(), "Categoria não selecionada!", Toast.LENGTH_LONG).show();
			return;
		} else {
			//problema = new Problema (userDAO.getUser(user), titulo, descricao, categoria, "foto", 0, 0);
			//proDAO.inserEntry(problema);
			//Toast.makeText(getApplicationContext(), "Problema cadastrado com Sucesso!", Toast.LENGTH_LONG).show();
			//chamaRanking();
			Problema p = new Problema();
			p.setTitulo(titulo);
			p.setCategoria(categoria);
			p.setDescricao(descricao);
			p.setLongitude(longitude);
			p.setLatitude(latitude);
			p.setFoto(md5Image);


			new ProblemasAsyncTask(this,ProblemasAction.CASDASTRAR,p).execute();
		}

	}

	private void preencherCategorias(JSONObject object){

		try {
			categoriaMap = new HashMap<Integer,Integer>();


			categoriasList.clear();

			JSONArray categorias = object.getJSONArray("categorias");

			for (int i =0;i<categorias.length();i++) {

				JSONObject categoria = categorias.getJSONObject(i);
				categoriaMap.put(i,categoria.getInt("id") );
				categoriasList.add(categoria.getString("nome"));
			}

			//Identifica o Spinner no layout
			aspn = (Spinner) findViewById(R.id.spinner1);
			//Cria um ArrayAdapter usando um padrão de layout da classe R do android, passando o ArrayList listaSelecione
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, categoriasList);
			ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
			spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			aspn.setAdapter(spinnerArrayAdapter);



		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	public void chamaRanking(){
		Intent i = new Intent(this, HomeRankingActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(i);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}	

	protected void onDestroy() {
		super.onDestroy();
		// Close The Database
		userDAO.close();
		proDAO.close();
	}

	private void uploadFoto(){
		dialog = ProgressDialog.show(RepProblActivity.this, "", "Uploading file...", true);

		new Thread(new Runnable() {
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						//messageText.setText("uploading started.....");
					}
				});                      

				uploadFile(uploadFilePath + "" + uploadFileName);

			}
		}).start();
		
			
	}

	private void tirarFoto(){
		
		geolocalizar();
		File picsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

		File imageFile = new File(picsDir, "foto.jpg");	
		imageFile.delete();
		Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile));
		startActivity(i);

		dialog = ProgressDialog.show(RepProblActivity.this, "", "Uploading file...", true);

		new Thread(new Runnable() {
			public void run() {
				runOnUiThread(new Runnable() {
					public void run() {
						//messageText.setText("uploading started.....");
					}
				});                      

				uploadFile(uploadFilePath + "" + uploadFileName);

			}
		}).start();

	}

	@Override
	public void onSalvaProblema(JSONObject object) {
		// TODO Auto-generated method stub
		try {
			boolean error = object.getBoolean("error");
			if(!error){
				chamaRanking();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

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

	}

	@Override
	public void onBuscarProblemaCategoria(JSONObject object) {
		// TODO Auto-generated method stub

	}


	@Override
	public void onGetCategorias(JSONObject object) {
		// TODO Auto-generated method stub
		preencherCategorias(object);
	}

	public void uploadFile(String sourceFileUri) {


		String fileName = sourceFileUri;

		HttpURLConnection conn = null;
		DataOutputStream dos = null;
		InputStreamReader in = null;
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		int bytesRead, bytesAvailable, bufferSize;
		byte[] buffer;
		int maxBufferSize = 1 * 1024 * 1024; 
		File sourceFile = new File(sourceFileUri); 

		if (!sourceFile.isFile()) {

			dialog.dismiss(); 

			Log.e("uploadFile", "Source File not exist :"
					+uploadFilePath + "" + uploadFileName);

			runOnUiThread(new Runnable() {
				public void run() {
					//
				}
			}); 

			

		}
		else
		{
			try { 

				// open a URL connection to the Servlet
				FileInputStream fileInputStream = new FileInputStream(sourceFile);
				URL url = new URL(upLoadServerUri);

				// Open a HTTP  connection to  the URL
				conn = (HttpURLConnection) url.openConnection(); 
				conn.setDoInput(true); // Allow Inputs
				conn.setDoOutput(true); // Allow Outputs
				conn.setUseCaches(false); // Don't use a Cached Copy
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Connection", "Keep-Alive");
				conn.setRequestProperty("ENCTYPE", "multipart/form-data");
				conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
				conn.setRequestProperty("uploaded_file", fileName); 
				
				
				dos = new DataOutputStream(conn.getOutputStream());
								

				dos.writeBytes(twoHyphens + boundary + lineEnd); 
				dos.writeBytes("Content-Disposition: form-data; name='uploaded_file';filename='"
						+ fileName + "'" + lineEnd);

				dos.writeBytes(lineEnd);

				// create a buffer of  maximum size
				bytesAvailable = fileInputStream.available(); 

				bufferSize = Math.min(bytesAvailable, maxBufferSize);
				buffer = new byte[bufferSize];

				// read file and write it into form...
				bytesRead = fileInputStream.read(buffer, 0, bufferSize);  

				while (bytesRead > 0) {

					dos.write(buffer, 0, bufferSize);
					bytesAvailable = fileInputStream.available();
					bufferSize = Math.min(bytesAvailable, maxBufferSize);
					bytesRead = fileInputStream.read(buffer, 0, bufferSize);   

				}

				// send multipart form data necesssary after file data...
				dos.writeBytes(lineEnd);
				dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

				// Responses from the server (code and message)
				serverResponseCode = conn.getResponseCode();
				String serverResponseMessage = conn.getResponseMessage();				
				
				
				
				
				Log.i("uploadFile", "HTTP Response is : " 
						+ serverResponseMessage + ": " + serverResponseCode);

				if(serverResponseCode == 200){

					runOnUiThread(new Runnable() {
						public void run() {
							
							Toast.makeText(RepProblActivity.this, "File Upload Complete.", 
									Toast.LENGTH_SHORT).show();
							
						}
					});                
				}    

				//close the streams //
				fileInputStream.close();
				dos.flush();
				dos.close();
				
				in = new InputStreamReader((InputStream) conn.getContent());
				BufferedReader buffIn = new BufferedReader(in);
				
				StringBuffer response = new StringBuffer();
				String line;
				do{
					line = buffIn.readLine();
				 	response.append(line+"\n");
				}while(line!=null);
				
				JSONObject responseJson = new JSONObject(response.toString());
				
				if(!responseJson.getBoolean("erro")){
					md5Image = responseJson.getString("arquivo");
				}

			} catch (MalformedURLException ex) {

				dialog.dismiss();  
				ex.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						//messageText.setText("MalformedURLException Exception : check script url.");
						Toast.makeText(RepProblActivity.this, "MalformedURLException", 
								Toast.LENGTH_SHORT).show();
					}
				});

				Log.e("Upload file to server", "error: " + ex.getMessage(), ex);  
			} catch (Exception e) {

				dialog.dismiss();  
				e.printStackTrace();

				runOnUiThread(new Runnable() {
					public void run() {
						//messageText.setText("Got Exception : see logcat ");
						Toast.makeText(RepProblActivity.this, "Got Exception : see logcat ", 
								Toast.LENGTH_SHORT).show();
					}
				});
				Log.e("Upload file to server Exception", "Exception : " 
						+ e.getMessage(), e);  
			}
			dialog.dismiss();       
			if(serverResponseCode==200){
				registarProblema();
			}

		} // End else block 

	}

	public void geolocalizar(){

		LocationManager service = (LocationManager) getSystemService(LOCATION_SERVICE);

		// Verifica se o GPS está ativo
		boolean enabled = service.isProviderEnabled(LocationManager.GPS_PROVIDER);

		// Caso não esteja ativo abre um novo diálogo com as configurações para
		// realizar se ativamento
		if (!enabled) {
			Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(intent);
		}

		//----------------------------------------------------------------------------

		// Adquire a referência ao Location Manager
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		/* 
		// Define o listener que responde às atualizações de localização
		LocationListener locationListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				// Chamado quando uma nova localização é encontrada

				location.getLatitude();// Latitude coletada
				location.getLongitude();// Longitude coletada
			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) { }

			@Override
			public void onProviderEnabled(String provider) { }

			@Override
			public void onProviderDisabled(String provider) { }
		};

		// Registra o listener com o Location Manager desejado para receber as atualizações
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 	0, locationListener);

		//----------------------------------------------------------------------------

		locationManager.removeUpdates(locationListener);

		//----------------------------------------------------------------------------
		 */


		// Define um critério para selecionar o location provider
		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, false);

		// Retorna a localização com a data da última localização conhecida
		Location location = locationManager.getLastKnownLocation(provider);

		//Toast.makeText(RepProblActivity.this, "Lat:"+location.getLatitude()+"; Long:"+location.getLongitude(), Toast.LENGTH_LONG).show();
		latitude = location.getLatitude();
		longitude = location.getLongitude();

	}
}





