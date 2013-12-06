package com.duron.streamingaudioplayer.clients;

import org.json.JSONObject;

import com.duron.streamingaudioplayer.callbacks.ClientCommand;
import com.duron.streamingaudioplayer.callbacks.PostCommand;

public class BaseClient implements PostCommand{
	
	ClientCommand successCommand;
	ClientCommand failureCommand;
	
	public BaseClient(ClientCommand sCommand, ClientCommand fCommand){
		this.successCommand = sCommand;
		this.failureCommand = fCommand;
	}

	@Override
	public void onReceive(String success, String failure, JSONObject result) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
