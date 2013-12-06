package com.duron.streamingaudioplayer.serverpings;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.duron.streamingaudioplayer.callbacks.PostCommand;

public class PostServerPing {
	String url;
	String clientName;
	List<NameValuePair> params;
	PostCommand callback;
	
	
	public PostServerPing(String url, List<NameValuePair> params, String name, PostCommand callback){
		this.url = url;
		this.clientName = name;
		this.callback = callback;
		this.params = params;
	}
	
	public void sendAsyncPost(){
		new postToServer().execute();
	}
	
	public class postToServer extends AsyncTask<Void, Void, JSONObject>{
		JSONObject jObj;
		String json;
		@Override
		protected JSONObject doInBackground(Void... arg0) {
			try {
				System.out.println("In the try");
				// defaultHttpClient
				DefaultHttpClient httpClient = new DefaultHttpClient();
				HttpGet httpGet = new HttpGet(url);
				System.out.println(url);
				//httpPost.setEntity(new UrlEncodedFormEntity(params));
				
				HttpResponse httpResponse = httpClient.execute(httpGet);
				
				HttpEntity httpEntity = httpResponse.getEntity();
				InputStream is = httpEntity.getContent();
				
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(
						is, "iso-8859-1"), 8);
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line);// + "n");
				}
				is.close();
				json = sb.toString();
				Log.i("json", "json"+json);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}


			// try parse the string to a JSON object
			try {
				jObj = new JSONObject(json);            
			} catch (JSONException e) {
			}

			return jObj;
		}
		
		@Override
		public void onPostExecute(JSONObject result){
			PostServerPing.this.callback.onReceive("success", null, result);
		}
		
	}
	
}	
	
	

