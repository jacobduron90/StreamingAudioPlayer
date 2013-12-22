package com.duron.streamingaudioplayer.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Album {
	public static final String ALBUM_KEY_ALBUMNAME = "ALBUM_KEY_ALBUMNAME";
	private static final String ALBUM_KEY_SONGS = "ALBUM_KEY_SONGS";
	public String albumName;
	public ArrayList<Song> songs;
	
	public Album(){
		songs = new ArrayList<Song>();
	}
	
	
	public Album(String albu){
		this();
		albumName = albu;
	}
	
	public Album(JSONObject jsonObject) 
	{
		this();
			try 
			{
				if(jsonObject.has(ALBUM_KEY_ALBUMNAME))
				albumName = jsonObject.getString(ALBUM_KEY_ALBUMNAME);
				
				if(jsonObject.has(ALBUM_KEY_SONGS))
				{
					JSONArray songArray = jsonObject.getJSONArray(ALBUM_KEY_SONGS);
					for(int i = 0; i < songArray.length(); i++)
					{
						Song tempSong = new Song(songArray.getJSONObject(i));
						songs.add(tempSong);
					}
				}
				
			} 
			catch (JSONException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}


	public void addSongs(ArrayList<Song> songsToAdd){
		for(Song song : songsToAdd){
			songs.add(song);
		}
	}


	public JSONObject save()
	{
		JSONObject albumData = new JSONObject();
		try 
		{
			albumData.put(ALBUM_KEY_ALBUMNAME, albumName);
			JSONArray songArray = new JSONArray();
			for(Song song : songs)
			{
				songArray.put(song.save());
			}
			albumData.put(ALBUM_KEY_SONGS, songArray);
			
		}
		catch (JSONException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return albumData;
	}
	
}
