package com.duron.streamingaudioplayer.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.duron.streamingaudioplayer.arrayadapters.AlbumAdapter;
import com.duron.streamingaudioplayer.callbacks.ClientCommand;
import com.duron.streamingaudioplayer.clients.AlbumClient;
import com.duron.streamingaudioplayer.managers.PlayListManager;
import com.duron.streamingaudioplayer.models.Album;
import com.duron.streamingaudioplayer.models.Artist;
import com.example.streamingaudioplayer.R;

public class AlbumFragment extends Fragment {

	private String artistName;
	private ListView album_listview;
	private ArrayList<Album> albumList;
	private AlbumAdapter aAdapter;
	private ControllerActivity parent;
	private Artist currentArtist;
	private AlbumClient aClient = new AlbumClient(new ClientCommand(){
	
	
	
		@SuppressWarnings("unchecked")
		@Override
		public void execute(Object param) {
			if(param instanceof ArrayList<?>){
				albumList = (ArrayList<Album>)param;
				if(!albumList.isEmpty()){
					currentArtist.albums = albumList;
					setView();
				}
				
				
				
			}
			
		}}, new ClientCommand(){

		@Override
		public void execute(Object param) {
			// TODO Auto-generated method stub
			
		}});
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.activity_album, container, false);
		album_listview = (ListView)view.findViewById(R.id.album_listview);
		album_listview.setOnItemClickListener(itemClickListener);
		parent = (ControllerActivity)getActivity();
		
		return view;
	}
	
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			parent.startSongFragment(albumList.get(arg2));
		}};
	
	
	public void setArtist(){
		currentArtist = PlayListManager.getInstance().getCurrentArtist();
		artistName = currentArtist.artistName;
	}
	
	
	@Override
	public void onStart(){
		super.onStart();
		setArtist();
		if(!currentArtist.albums.isEmpty()){
			albumList = currentArtist.albums;
			setView();
		}else{
			aClient.getAlbums(artistName, getResources().getString(R.string.get_albums));
		}
		
		
	}
	
	public void setView(){
		aAdapter = new AlbumAdapter(getActivity(), R.layout.cell_row, albumList);
		album_listview.setAdapter(aAdapter);
	}
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState){
		savedInstanceState.putString("artist", artistName);
	}
	



}
