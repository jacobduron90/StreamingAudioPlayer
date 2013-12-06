package com.duron.streamingaudioplayer.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Song {
	public String trackNumber;
	public String trackUrl;
	public String trackName;
	
	public Song(JSONObject json){
		
		try {
		if(json.has("track_name")){
			this.trackName = json.getString("track_name");
			
		}
		
		if(json.has("track_url")){
			this.trackUrl = json.getString("track_url");
		}
		
		if(json.has("track_number")){
			this.trackNumber = json.getString("track_number");
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
