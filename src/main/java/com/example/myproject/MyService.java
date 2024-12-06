package com.example.myproject;
import io.helidon.websocket.WsListener;
import io.helidon.websocket.WsSession;
import java.util.logging.Logger;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.json.bind.JsonbException;
import java.util.List;
import java.util.ArrayList;
import jakarta.json.JsonObject;

public class MyService implements WsListener {
    private String id;
	private boolean auth;
	public MyService(){
		this.auth=false;		
	}
	@Override
    public void onMessage(WsSession session,String message,boolean last) {
		if(auth){
			Message msg=parseMessage(message);
			if(!msg.validate())session.send("Invalid message",last);
			else session.send(msg.getSender()+"->"+msg.getMessage(),last);
		}
		else{
			if(checkAndSetId(message)){
				session.send("hello "+id,last);
			}
			else session.send("Id not authed or something went wrong.",last);
		}
   }

   public Message parseMessage(String message){
		Jsonb jsonb= JsonbBuilder.create();
		return jsonb.fromJson(message,Message.class);
	}
	public boolean checkAndSetId(String message){
		Jsonb jsonb=JsonbBuilder.create();
		JsonObject obj= jsonb.fromJson(message,JsonObject.class);
		String x=obj.containsKey("id")?obj.getString("id"):null;
		if(x==null) return false;
		this.id=x;
		this.auth=true;
		return true;
	}
}

