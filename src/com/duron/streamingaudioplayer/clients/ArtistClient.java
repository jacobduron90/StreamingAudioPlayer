package com.duron.streamingaudioplayer.clients;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.duron.streamingaudioplayer.callbacks.ClientCommand;
import com.duron.streamingaudioplayer.models.Artist;
import com.duron.streamingaudioplayer.serverpings.PostServerPing;

public class ArtistClient extends BaseClient{

	public ArtistClient(ClientCommand sCommand, ClientCommand fCommand) {
		super(sCommand, fCommand);
		// TODO Auto-generated constructor stub
	}
	
	public void getArtist(){
		
		
		PostServerPing postServerPing = new PostServerPing("http://184.73.210.31:9000/api/getArtist", null, "ArtistClient", this);
		postServerPing.sendAsyncPost();
	}
	
	@Override
	public void onReceive(String success, String failure, JSONObject result){
		ArrayList<Artist> artists = new ArrayList<Artist>();
		if(failure == null && result != null){
			try {
				JSONArray artistArray = result.getJSONArray("artists");
				
				for(int i = 0; i < artistArray.length(); i++){
					Artist artistToCreate = new Artist(artistArray.getString(i));
					artists.add(artistToCreate);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			successCommand.execute(artists);
			
		}
	}

}
