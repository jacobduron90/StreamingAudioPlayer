package com.duron.streamingaudioplayer.callbacks;

import org.json.JSONObject;

public interface PostCommand {
	
	public void onReceive(String success, String failure, JSONObject result);
	
	
}
