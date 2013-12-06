package com.duron.streamingaudioplayer.clients;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

import com.duron.streamingaudioplayer.callbacks.ClientCommand;
import com.duron.streamingaudioplayer.models.Album;
import com.duron.streamingaudioplayer.serverpings.PostServerPing;

public class AlbumClient extends BaseClient{

	public AlbumClient(ClientCommand sCommand, ClientCommand fCommand) {
		super(sCommand, fCommand);
		// TODO Auto-generated constructor stub
	}
	
	public void getAlbums(String artist, String url){
		
		String art = Uri.encode(artist);
		
		PostServerPing pServerPing = new PostServerPing(url+art, null, "AlbumClient", this);
		pServerPing.sendAsyncPost();
	}
	
	
	@Override 
	public void onReceive(String success, String failure, JSONObject result){
		if(failure == null && result != null){
			ArrayList<Album> albums = new ArrayList<Album>();
			JSONArray albumsJ;
			try {
				albumsJ = result.getJSONArray("albums");
				for(int i = 0; i < albumsJ.length(); i++){
					albums.add(new Album(albumsJ.getString(i)));
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			successCommand.execute(albums);
			
			
		}
	}

}
