package com.example.myproject;
import jakarta.websocket.OnMessage;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.OnOpen;
import io.helidon.websocket.WsListener;
import io.helidon.websocket.WsSession;
import java.util.logging.Logger;
public class MyService implements WsListener {
    //private String id;
private static final Logger log=Logger.getLogger(MyService.class.getName());
    public MyService(WsSession session){
    	log.info(session.requestUri());	
    }
    //public MyService(String id){
    //	this.id=id;
    //}
    //@OnOpen
    //public void onOpen(Session session) {
    //    String path = session.getRequestURI().getPath();
    //    id = path.substring(path.lastIndexOf('/') + 1);
	//  System.out.println(id+" connected");
    //}

    //@OnMessage
    //public void onMessage(String message, Session session) {
    //    System.out.println(id +" -> "+ message);
    //}

    @Override
    public void onOpen(WsSession session) {
        //id = session.path().pathParameters().get("id");
       	//if(id==null)id="unknown";
       	//id = path.substring(path.lastIndexOf('/') + 1);
	//session.send(" connected");
    }

    @Override
    public void onMessage(WsSession session,String message,boolean last) {
	    session.send("->"+message,last);
    }
}

