package com.duron.streamingaudioplayer.models;

import java.util.ArrayList;

public class Artist {
	public String artistName;
	public ArrayList<Album> albums;
	
	
	
	public Artist(){
		albums = new ArrayList<Album>();
	}
	public Artist(String name){
		this();
		this.artistName = name;
		
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
