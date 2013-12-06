package com.duron.streamingaudioplayer.clients;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

import com.duron.streamingaudioplayer.callbacks.ClientCommand;
import com.duron.streamingaudioplayer.models.Song;
import com.duron.streamingaudioplayer.serverpings.PostServerPing;

public class SongClient extends BaseClient{

	public SongClient(ClientCommand sCommand, ClientCommand fCommand) {
		super(sCommand, fCommand);
	}
	
	public void getSong(String album, String url){
		
		album = Uri.encode(album);
		PostServerPing postServerPing = new PostServerPing(url+album, null, "SongClient", this);
		postServerPing.sendAsyncPost();
	}
	
	
	@Override
	public void onReceive(String success, String failure, JSONObject result){
		if(failure == null && result != null){
			ArrayList<Song> songs = new ArrayList<Song>();
			JSONArray songsJson = null;
			try {
				songsJson = result.getJSONArray("songs");
				if(songsJson != null){
					for(int i = 0; i < songsJson.length(); i++){
						Song song = new Song(songsJson.getJSONObject(i));
						songs.add(song);
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
			
			successCommand.execute(songs);
		}
	}
	

	
	
}
