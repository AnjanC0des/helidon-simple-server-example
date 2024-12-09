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
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.ConcurrentHashMap;
public class MyService implements WsListener {
	private AtomicReference<String> id= new AtomicReference<>();
	private AtomicReference<Boolean> auth= new AtomicReference<>(false);
	private final ConcurrentHashMap<String,WsSession> sessions;
	public MyService(ConcurrentHashMap<String,WsSession> sessions){
		this.sessions=sessions;
	}
	@Override
	public void onOpen(WsSession session){
		
	}

	@Override
    public void onMessage(WsSession session,String message,boolean last) {
		if(Boolean.TRUE.equals(auth.get())){
			Message msg=parseMessage(message);
			if(!msg.validate())session.send("Invalid message",last);
			else {
				for(String x:msg.getRecipients()){
					WsSession sesh=sessions.get(x);
					if(sesh!=null) sesh.send(msg.getSender()+" -> "+msg.getMessage(),true);
				//session.send(msg.getSender()+ " -> "+msg.getMessage(),last);
				}
			} 
		}
		else{
			if(checkAndSetId(message)){
				session.send("hello "+id.get(),last);
				sessions.put(id.get(),session);
			}
			else session.send("Id not authed or something went wrong.",last);
		}
   }

   @Override 
   public void onClose(WsSession session,int status, String reason){
	  	sessions.remove(id.get());
		this.id=null;
		this.auth=null;
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
		this.id.set(x);
		this.auth.set(true);
		return true;
	}
}

