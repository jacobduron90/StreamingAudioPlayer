package com.duron.streamingaudioplayer.models;

import java.util.ArrayList;

public class Album {
	public String albumName;
	public ArrayList<Song> songs;
	
	public Album(){
		songs = new ArrayList<Song>();
	}
	
	
	public Album(String albu){
		this();
		albumName = albu;
	}
	
	public void addSongs(ArrayList<Song> songsToAdd){
		for(Song song : songsToAdd){
			songs.add(song);
		}
	}
	
}
