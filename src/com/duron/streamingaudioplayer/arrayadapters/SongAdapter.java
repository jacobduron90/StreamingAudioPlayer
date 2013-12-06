package com.duron.streamingaudioplayer.arrayadapters;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.duron.streamingaudioplayer.models.Song;
import com.example.streamingaudioplayer.R;

public class SongAdapter extends ArrayAdapter<Song>{

	Context context;
	List<Song> data;
	int layoutResourceId;
	
	public SongAdapter(Context context, int resourceId, List<Song> objects) {
		super(context, resourceId, objects);
		this.context = context;
		this.data = objects;
		this.layoutResourceId = resourceId;
	}
	
	@Override
	public View getView(int position, View row, ViewGroup parent){
		final Song activeSong = data.get(position);
		SongCell cell = null;
		if(row == null){
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			cell = new SongCell();
			cell.trackName = (TextView)row.findViewById(R.id.cell_song_trackname_textview);
			cell.trackNumber = (TextView)row.findViewById(R.id.cell_song_tracknumber_textview);
			row.setTag(cell);
		}else{
			cell = (SongCell) row.getTag();
		}
		
		
		cell.trackName.setText(activeSong.trackName);
		cell.trackNumber.setText(activeSong.trackNumber);
		
		
		
		
		return row;
	}
	
	static class SongCell{
		Song activeData;
		TextView trackName;
		TextView trackNumber;
		
	}

}
