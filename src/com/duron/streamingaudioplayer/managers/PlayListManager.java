package com.duron.streamingaudioplayer.managers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.duron.streamingaudioplayer.fragments.MyApplication;
import com.duron.streamingaudioplayer.models.Album;
import com.duron.streamingaudioplayer.models.Artist;
import com.duron.streamingaudioplayer.models.Song;

public class PlayListManager {

	private static final String PLAYLIST_KEY = "PLAYLIST_KEY";
	private static final String APP_SHAREDPREFS_KEY = "APP_SHAREDPREFS_KEY";
	private static final String PLAYLIST_SPREFS_KEY = "PLAYLIST_SPREFS_KEY";
	private static PlayListManager instance;
	public ArrayList<Artist> artists;
	private Artist currentArtist;
	private Album currentAlbum;
	
	
	
	private PlayListManager()
	{
		artists = loadPlayList();
		if(artists.isEmpty())
		{
			artists = new ArrayList<Artist>();
		}
		
		
		
		
	}
	
	
	public static synchronized PlayListManager getInstance(){
		if(instance == null){
			instance = new PlayListManager();
		}
		return instance;
	}
	
	public Artist getArtist(String artistName){
		Artist artist = null;
		for(Artist currentArtist : artists){
			if(currentArtist.artistName.equals(artistName)){
				artist = currentArtist;
				break;
			}
		}
		
		
		
		return artist;
	}
	
	public void addArtists(ArrayList<String> newArtists){
		if(artists.isEmpty()){
			for(String name: newArtists){
				Artist currArtist = new Artist(name);
				artists.add(currArtist);
			}
		}else{
			//TODO clear list
		}
	}
	
	public void addAlbums(Artist artist, ArrayList<Album> albums){
		artist.addAlbums(albums);
	}
	
	public void addSongs(Artist artist, Album album, ArrayList<Song> songs){
		artist.addSongs(album, songs);
	}
	
	public Artist getCurrentArtist(){
		return currentArtist;
	}
	
	public void setCurrentArtist(Artist art){
		this.currentArtist = art;
	}
	
	public Album getCurrentAlbum(){
		return currentAlbum;
	}
	
	public void setCurrentAlbum(Album alb){
		this.currentAlbum = alb;
	}
	
	public void save()
	{
		SharedPreferences sPrefs = MyApplication.getAppContext().getSharedPreferences(APP_SHAREDPREFS_KEY, Context.MODE_PRIVATE);
		Editor edit = sPrefs.edit();
		edit.putString(PLAYLIST_SPREFS_KEY, getDataToSave().toString());
		edit.commit();
		
	}
	
	public JSONObject getDataToSave()
	{
		JSONObject playListData = new JSONObject();
		JSONArray playList = new JSONArray();
		for(Artist artist : artists)
		{
			playList.put(artist.save());
		}
		try 
		{
			playListData.put(PLAYLIST_KEY, playList);
		}
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return playListData;
	}
	
	public ArrayList<Artist> loadPlayList()
	{
		ArrayList<Artist> artists = new ArrayList<Artist>();
		SharedPreferences sPrefs = MyApplication.getAppContext().getSharedPreferences(APP_SHAREDPREFS_KEY, Context.MODE_PRIVATE);
		String json = sPrefs.getString(PLAYLIST_SPREFS_KEY, "");
		
		if(json == "") return artists;
		
		try 
		{
			JSONObject playList = new JSONObject(json);
			JSONArray playListArray = playList.getJSONArray(PLAYLIST_KEY);
			for(int i = 0; i < playListArray.length(); i++)
			{
				Artist tempArtist = new Artist(playListArray.getJSONObject(i));
				artists.add(tempArtist);
			}
			
		} 
		catch (JSONException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return artists;
	}
	
	
	
	
	
	
	
}
