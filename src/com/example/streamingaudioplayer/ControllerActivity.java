package com.example.streamingaudioplayer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

public class ControllerActivity extends FragmentActivity {

	private AlbumFragment albumFragment;
	private ArtistFragment artistFragment;
	private SongFragment songFragment;
	private PlayerFragment playerFragment;
	private int fragmentFrame;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_controller);
		fragmentFrame = R.id.controller_frame_layout;
		
		initView();
	}
	
	public void initView(){
		if(artistFragment == null){
			artistFragment = new ArtistFragment();
		}
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(fragmentFrame, artistFragment);
		ft.commit();
	}
	
	public void switchView(Fragment frag){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(fragmentFrame, frag);
		ft.addToBackStack("true");
		ft.commit();
	}
	
	public void startAlbumFragment(String artist){
		if(albumFragment == null){
			albumFragment = new AlbumFragment();
		}
		albumFragment.setArtist(artist);
		switchView(albumFragment);
		
		
	}
	
	public void startSongFragment(String album){
		
		if(songFragment == null){
			songFragment = new SongFragment();
		}
		songFragment.setAlbum(album);
		switchView(songFragment);
	}
	
	public void changePlayer(String trackName, String trackNumber, String trackUrl){
		playerFragment = (PlayerFragment) getSupportFragmentManager().findFragmentById(R.id.controller_player_fragment);
		playerFragment.setSongInfo(trackName, trackNumber, trackUrl);
	}
	
	
	
	
	
	

	
	
	
	
	
}
