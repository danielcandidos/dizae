package com.dizae.models.business;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.dizae.database.ConexaoHttpClient;

import android.os.AsyncTask;

public class UserController extends AsyncTask<ArrayList<NameValuePair>, Void, JSONObject>{

	private RegisterUserListener listenet;
	private final String URL_STRING= "http://ec2-54-200-36-55.us-west-2.compute.amazonaws.com/dizae/index.php/usuarios/register/";
	private ConexaoHttpClient conexaoHttpClient;

	public UserController(RegisterUserListener listener){
		this.listenet = listener;
		conexaoHttpClient = new ConexaoHttpClient();
	}
	@Override
	protected JSONObject doInBackground(ArrayList<NameValuePair>... params) {
		// TODO Auto-generated method stub

		

		try {
			String result = conexaoHttpClient.executaHttpPost(URL_STRING, params[0]);
			return returnAnalise(result);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	private JSONObject returnAnalise(String result) throws JSONException {
		// TODO Auto-generated method stub
		JSONObject object = new JSONObject(result);

		return object ;
	}

	private String registerUser(List<NameValuePair> params) throws IOException{
		InputStream in = null;
		OutputStream out = null;	

		try {
			URL url = new URL(URL_STRING);			

			HttpURLConnection con = (HttpURLConnection)url.openConnection();
			con.setReadTimeout(10000);
			con.setConnectTimeout(15000);
			con.setRequestMethod("POST");
			
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			con.setDoInput(true);
			con.setDoOutput(true);			
			out = con.getOutputStream();
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));
			String urlParamenters = getQuery(params);
			writer.write(urlParamenters);
			writer.flush();
			writer.close();
			con.connect();
			int responseCode = con.getResponseCode();

			Reader reader = null;
			reader = new InputStreamReader(in);
			char[] buffer = new char[2048];
			reader.read(buffer);
			return new String(buffer);

		} finally{
			if(in!=null){
				in.close();
			}
		}


	}

	protected void onPostExecute(JSONObject result){
		listenet.onRegisterUser(result);
	}

	private String getQuery(List<NameValuePair> params) throws UnsupportedEncodingException
	{
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : params)
		{
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		}

		return result.toString();
	}

	public interface RegisterUserListener{
		void onRegisterUser(JSONObject object);		
	}







}
