package com.example.streamingaudioplayer;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.duron.streamingaudioplayer.arrayadapters.ArtistAdapter;
import com.duron.streamingaudioplayer.callbacks.ClientCommand;
import com.duron.streamingaudioplayer.clients.ArtistClient;
import com.duron.streamingaudioplayer.managers.PlayListManager;
import com.duron.streamingaudioplayer.models.Artist;

public class ArtistFragment extends Fragment {
	
	private ArrayList<Artist> artists;
	private ArtistAdapter aAdapter;
	private ListView artistView;
	private ControllerActivity parent;
	private ArtistClient aClient = new ArtistClient(new ClientCommand(){
		
		@SuppressWarnings("unchecked")
		@Override
		public void execute(Object param) {
			//Success
			if(param instanceof ArrayList<?>){
				artists = (ArrayList<Artist>) param;
				PlayListManager.getInstance().artists = artists;
				setView();
			}
			
		}}, new ClientCommand(){

		@Override
		public void execute(Object param) {
			//Failure
			
		}});
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

	}
	
	public void setView(){
		aAdapter = new ArtistAdapter(getActivity(), R.layout.cell_row, artists);
		artistView.setAdapter(aAdapter);
	}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
				View view = inflater.inflate(R.layout.activity_artist, container, false);
				artistView = (ListView)view.findViewById(R.id.artist_listview);
				artistView.setOnItemClickListener(itemClickListener);
				parent = (ControllerActivity)getActivity();
		
				return view;
		
	}
	
	private OnItemClickListener itemClickListener = new OnItemClickListener(){

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			parent.startAlbumFragment(artists.get(arg2).artistName);
			
		}};

	
	@Override
	public void onStart(){
		super.onStart();
		if(!PlayListManager.getInstance().artists.isEmpty()){
			artists = PlayListManager.getInstance().artists;
			setView();
		}else{
			aClient.getArtist();
		}
		
		
		
	}
	
	


}
