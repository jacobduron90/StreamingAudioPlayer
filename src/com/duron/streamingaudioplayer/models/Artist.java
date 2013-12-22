package com.duron.streamingaudioplayer.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Artist {
	public static final String ARTIST_KEY_ARTISTNAME = "ARTIST_KEY_ARTISTNAME";
	public static final String ARTIST_KEY_ALBUMS = "ARTIST_KEY_ALBUMS";
	public String artistName;
	public ArrayList<Album> albums;
	
	
	
	public Artist(){
		albums = new ArrayList<Album>();
	}
	public Artist(String name){
		this();
		this.artistName = name;
		
	}
	
	public Artist(JSONObject artistJ)
	{
		this();
			try 
			{
				if(artistJ.has(ARTIST_KEY_ARTISTNAME))
				artistName = artistJ.getString((ARTIST_KEY_ARTISTNAME));
				
				if(artistJ.has(ARTIST_KEY_ALBUMS))
				{
					JSONArray albumArray = artistJ.getJSONArray(ARTIST_KEY_ALBUMS);
					for(int i = 0; i < albumArray.length(); i++)
					{
						Album tempAlbum  = new Album(albumArray.getJSONObject(i));
						albums.add(tempAlbum);
					}
				}
				
					
				
			} catch (JSONException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}
	
	
	
	public JSONObject save()
	{
		JSONObject albumData = new JSONObject();
		try 
		{
			albumData.put(ARTIST_KEY_ARTISTNAME, artistName);
			JSONArray albumArray = new JSONArray();
			for(Album album : albums)
			{
				albumArray.put(album.save());
			}
			albumData.put(ARTIST_KEY_ALBUMS, albumArray);
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return albumData;
	}
	
	public void addAlbums(ArrayList<Album> albumsToAdd){
		for(Album album : albumsToAdd){
			albums.add(album);
		}
	}
	
	public void addSongs(Album album, ArrayList<Song> songs){
		album.addSongs(songs);
	}
}
