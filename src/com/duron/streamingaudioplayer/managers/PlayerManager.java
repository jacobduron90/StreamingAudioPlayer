package com.duron.streamingaudioplayer.managers;

import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Handler;

import com.duron.streamingaudioplayer.fragments.MyApplication;
import com.duron.streamingaudioplayer.interfaces.SeekListener;

public class PlayerManager{

	private static PlayerManager instance;
	private MediaPlayer mediaPlayer;
	private boolean isStopped = false;
	private boolean hasBeenStopped = false;
	private Uri globalUri = null;
	private boolean isPlaying = false;
	private Context context;
	private final Handler handler = new Handler();
	private SeekListener sListener = null;
	private String trackId = null;
	
	private PlayerManager(){
		loadPlayer();
		context = MyApplication.getAppContext();
	}
	
	public static synchronized PlayerManager getInstance(){
		if(instance == null){
			instance = new PlayerManager();
		}
		return instance;
	}
	
	
	public void loadPlayer(){
		mediaPlayer = new MediaPlayer();
		
	}
	
	
	public void setSongAndPlay(Uri myUri){
		globalUri = myUri;
		if(isPlaying == false){
			try {
				mediaPlayer.setDataSource(context, myUri);
				mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
				isPlaying = true;
				mediaPlayer.prepareAsync();
				mediaPlayer.setOnPreparedListener(onPreparedListener);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			reloadAndPlay(myUri);
		}
		
	}
	
	private OnPreparedListener onPreparedListener = new OnPreparedListener(){

		@Override
		public void onPrepared(MediaPlayer mp) {
			
			isStopped = false;
			startPlayer();
		}
		
	};
	
	
	Runnable notification = new Runnable(){
		public void run(){
			if(isStopped == false && mediaPlayer.isPlaying()){
			updateSeek();
			handler.postDelayed(notification, 1000);
			}
		}
	};
	
	public void updateSeekListener(){
		
			
			handler.postDelayed(notification, 1000);
		
	}
	
	public void updateSeek(){
		sListener.getSeekBarProgress(mediaPlayer.getCurrentPosition(), mediaPlayer.getDuration());
	}
	
	public void setSeekListener(SeekListener sListener){
		this.sListener = sListener;
			
		
	}
	
	public void pausePlayer(){
		if(isStopped == false){
			mediaPlayer.pause();
		}
		
	}
	
	public void stopPlayer(){
		if(isStopped == false){
			mediaPlayer.stop();
			mediaPlayer.reset();
			hasBeenStopped = true;
			isStopped = true;
			isPlaying = false;
		}
	}
	
	public void startPlayer(){
		if(hasBeenStopped == false){
			mediaPlayer.start();
			updateSeekListener();
		}else{
			
			setSongAndPlay(globalUri);
			
		}
	}
	
	public boolean isPlaying(){
		if(isStopped == false && mediaPlayer.isPlaying() == true){
			return true;
		}else{
			return false;
		}
	}
	
	public void reloadAndPlay(Uri uri){
		stopPlayer();
		hasBeenStopped = false;
		isPlaying = false;
		setSongAndPlay(uri);
		
	}
	
	public int getCurrentPosition(){
		
		
		return mediaPlayer.getCurrentPosition();
	}
	
	public int getLength(){
		return mediaPlayer.getDuration();
	}

	public void setTrackId(String songUrl) {
		trackId = songUrl;
		
	}

	public String getTrackId() {
		
		return trackId;
	}
	
	
	
	
}
