package com.duron.streamingaudioplayer.arrayadapters;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.duron.streamingaudioplayer.models.Artist;
import com.example.streamingaudioplayer.R;

public class ArtistAdapter extends ArrayAdapter<Artist>{

	private int layoutResourceId;
	private ArrayList<Artist> data;
	private Context context;

	
	public ArtistAdapter(Context context, int resource, ArrayList<Artist> object) {
		super(context, resource, object);
		// TODO Auto-generated constructor stub
		this.layoutResourceId = resource;
		this.data = object;
		this.context = context;
		
	}
	
	@Override
	public View getView(int position, View row, ViewGroup parent){
		Cell cell = null;
		if(row == null){
			LayoutInflater inflater = ((Activity)context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, null);
			cell = new Cell();
			cell.value = (TextView)row.findViewById(R.id.cell_text_view);
			row.setTag(cell);
		}else{
			cell = (Cell)row.getTag();
		}

		
		String valueName = data.get(position).artistName;
		cell.value.setText(valueName);
		
		

		return row;
		
	}
	
	static class Cell{
		TextView value;
	}

}
