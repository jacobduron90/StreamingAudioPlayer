package com.duron.streamingaudioplayer.fragments;

import java.net.URL;
import java.util.Date;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.duron.streamingaudioplayer.constants.Constants;
import com.duron.streamingaudioplayer.interfaces.SeekListener;
import com.duron.streamingaudioplayer.managers.PlayerManager;
import com.example.streamingaudioplayer.R;

public class PlayerFragment extends Fragment implements SeekListener{

	private ImageButton buttonPlayPause;
	private ImageButton	buttonStop;
	private SeekBar seekBarProgress;

	private AmazonS3Client s3Client = new AmazonS3Client(
			new BasicAWSCredentials(Constants.ACCESS_ID,
					Constants.ACCESS_KEY));
	
	String trackName;
	String trackNumber;
	String trackUrl;
	TextView tvtrackName;
	TextView tvtrackNumber;
	String prefix;
	String songUrl;
	Uri songUri;
	private boolean hasBeenClicked = true;
	
	PlayerManager playerM;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		playerM = PlayerManager.getInstance();

		
	}
	
	
	
	public void setSongInfo(String trackName, String trackNumber, String trackUrl){
		this.trackName = trackName;
		this.trackNumber = trackNumber;
		this.trackUrl = trackUrl;
		if(trackUrl.contains("duronSongs/")){
			
			int index = trackUrl.indexOf("duronSongs/");
			Log.i("adf", index+"");
			this.trackUrl = trackUrl.substring(11);
		}
		Log.i("track_url", trackUrl);
		tvtrackName.setText(trackName);
		tvtrackNumber.setText(trackNumber);
		
		
		new S3GeneratePresignedUrlTask().execute();
		
	}
	
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		View view = inflater.inflate(R.layout.activity_main, container, false);
		buttonPlayPause = (ImageButton)view.findViewById(R.id.ButtonTestPlayPause);
		buttonPlayPause.setOnClickListener(playPauseListener);
		
		buttonStop = (ImageButton)view.findViewById(R.id.stopButton);
		buttonStop.setOnClickListener(stopListener);
		
		seekBarProgress = (SeekBar)view.findViewById(R.id.SeekBarTestPlay);
		seekBarProgress.setMax(99);
		//seekBarProgress.setOnTouchListener(this);
		tvtrackName = (TextView) view.findViewById(R.id.trackName);
		tvtrackNumber = (TextView) view.findViewById(R.id.trackNumber);
		
		
		return view;
	}
	
	
	
	@Override
	public void onStart(){
		super.onStart();
		
		
		
	}




	
	OnClickListener playPauseListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			if(hasBeenClicked){
				hasBeenClicked = false;
				playerM.pausePlayer();
			}else{
				hasBeenClicked = true;
				playerM.startPlayer();
				
			}
			
			
			
		}
		
	};
	
	OnClickListener stopListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			playerM.stopPlayer();
			
		}
		
	};
	
	public void resultCallBack(S3TaskResult result){
		songUrl  = result.urlss;
		songUri = result.getUri();
		playSong(songUri);
		
		Log.i("afadsf", songUrl);
	}
	
	
	private void playSong(Uri myUri){
		playerM.setSongAndPlay(myUri);
		playerM.setSeekListener(this);
		
	}
	
	
	
	private class S3GeneratePresignedUrlTask extends AsyncTask<Void, Void, S3TaskResult> {

	protected S3TaskResult doInBackground(Void... voids) {

	S3TaskResult result = new S3TaskResult();

	try {
		ResponseHeaderOverrides override = new ResponseHeaderOverrides();
		override.setContentType("audio/mpeg");
		
		// Added an hour's worth of milliseconds to the current time.
		Date expirationDate = new Date(
				System.currentTimeMillis() + 3600000);
		GeneratePresignedUrlRequest urlRequest = new GeneratePresignedUrlRequest(
				Constants.BUCKET_NAME, trackUrl);
		urlRequest.setExpiration(expirationDate);

		URL url = s3Client.generatePresignedUrl(urlRequest);
		result.urlss = url.toURI().toString();
		result.setUri(Uri.parse(url.toURI().toString()));

	} catch (Exception exception) {

		result.setErrorMessage(exception.getMessage());
	}

	return result;
}

	protected void onPostExecute(S3TaskResult result) {
	
	if (result.getErrorMessage() != null) {

		
	} else if (result.getUri() != null) {

		resultCallBack(result);
		
	}
	}
}
	
	private class S3TaskResult {
		String errorMessage = null;
		Uri uri = null;
		public String urlss;

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		public Uri getUri() {
			return uri;
		}

		public void setUri(Uri uri) {
			this.uri = uri;
		}
	}

	@Override
	public void getSeekBarProgress(int currentPos, int duration) {
		seekBarProgress.setProgress((int)(((float)currentPos / 
				duration) * 100));
		
	}
	
	
	
	
	
	
	
	
	
	

}
