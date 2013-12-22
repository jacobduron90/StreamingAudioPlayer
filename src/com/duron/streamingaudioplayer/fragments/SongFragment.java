package com.duron.streamingaudioplayer.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.duron.streamingaudioplayer.arrayadapters.SongAdapter;
import com.duron.streamingaudioplayer.callbacks.ClientCommand;
import com.duron.streamingaudioplayer.clients.SongClient;
import com.duron.streamingaudioplayer.managers.PlayListManager;
import com.duron.streamingaudioplayer.models.Album;
import com.duron.streamingaudioplayer.models.Song;
import com.example.streamingaudioplayer.R;

public class SongFragment extends Fragment {
	private Album album;
	private ListView songListView;
	private SongAdapter songAdapter;
	private ControllerActivity parent;
	private final SongClient songClient = new SongClient(new ClientCommand(){

		@SuppressWarnings("unchecked")
		@Override
		public void execute(Object param) {
			if(param instanceof ArrayList<?>){
				album.songs = (ArrayList<Song>)param;
				PlayListManager.getInstance().save();
				setView();
				
			}
			
		}}, new ClientCommand(){

		@Override
		public void execute(Object param) {
			//Failure
			
		}});
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			Song song = album.songs.get(arg2);
			parent.changePlayer(song.trackName, song.trackNumber, song.trackUrl);
			
		}};
	
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.activity_song, container, false);
		songListView = (ListView)view.findViewById(R.id.song_listview);
		songListView.setOnItemClickListener(itemClickListener);
		parent = (ControllerActivity)getActivity();
		
		return view;
	}
	
	public void setAlbum(){
		album = PlayListManager.getInstance().getCurrentAlbum();
	}
	
	
	@Override
	public void onStart(){
		super.onStart();
		setAlbum();
		if(!album.songs.isEmpty())
		{
			Log.i("CACHE HIT", "SONGS");
			setView();
		}
		else
		{
			Log.i("CACHE MISS", "SONGS");
			songClient.getSong(album.albumName, getResources().getString(R.string.get_songs));
		}
		
		
	}
	
	public void setView()
	{
		songAdapter = new SongAdapter(getActivity(), R.layout.cell_song, album.songs);
		songListView.setAdapter(songAdapter);
	}
	


	

}
