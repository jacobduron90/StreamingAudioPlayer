package com.duron.streamingaudioplayer.managers;

import java.util.ArrayList;

import com.duron.streamingaudioplayer.models.Album;
import com.duron.streamingaudioplayer.models.Artist;
import com.duron.streamingaudioplayer.models.Song;

public class PlayListManager {

	private static PlayListManager instance;
	public ArrayList<Artist> artists;
	
	
	
	private PlayListManager(){
		artists = new ArrayList<Artist>();
		
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
	
	
	
	
	
	
	
	
	
}
