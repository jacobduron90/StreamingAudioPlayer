package com.duron.streamingaudioplayer.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Song {
	public String trackNumber;
	public String trackUrl;
	public String trackName;
	public static final String SONG_TRACKNAME_KEY = "track_name";
	public static final String SONG_TRACKURL_KEY = "track_url";
	public static final String SONG_TRACKNUMBER_KEY = "track_number";
	
	public Song(JSONObject json){
		
		try {
		if(json.has(SONG_TRACKNAME_KEY)){
			this.trackName = json.getString(SONG_TRACKNAME_KEY);
			
		}
		
		if(json.has(SONG_TRACKURL_KEY)){
			this.trackUrl = json.getString(SONG_TRACKURL_KEY);
		}
		
		if(json.has(SONG_TRACKNUMBER_KEY)){
			this.trackNumber = json.getString(SONG_TRACKNUMBER_KEY);
		}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JSONObject save() 
	{
		JSONObject songData = new JSONObject();
		try
		{
			songData.put(SONG_TRACKNAME_KEY, trackName);
			songData.put(SONG_TRACKNUMBER_KEY, trackNumber);
			songData.put(SONG_TRACKURL_KEY, trackUrl);
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return songData;
	}
	
	
}
