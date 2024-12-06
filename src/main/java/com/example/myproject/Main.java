
package com.example.myproject;
import java.util.logging.Logger;

import io.helidon.logging.common.LogConfig;
import io.helidon.config.Config;
import io.helidon.webserver.WebServer;
import io.helidon.webserver.http.HttpRouting;

import io.helidon.webserver.Routing;
import io.helidon.webserver.websocket.WsRouting;
//import io.helidon.webserver.websocket.WebSocketService;


/**
 * The application main class.
 */
public class Main {
	private static final Logger log= Logger.getLogger(Main.class.getName());

    /**
     * Cannot be instantiated.
     */
    private Main() {
    }


    /**
     * Application main entry point.
     * @param args command line arguments.
     */
    public static void main(String[] args) {
        
        // load logging configuration
        LogConfig.configureRuntime();

        // initialize global config from default configuration
        Config config = Config.create();
        Config.global(config);

        WebServer server = WebServer.builder()
                .config(config.get("server"))
                .routing(Main::routing)
		.addRouting(	
		WsRouting.builder()
            .endpoint("/message", new MyService()))
                .build()
                .start();


        System.out.println("WEB server is up! http://localhost:" + server.port() + "/simple-greet");

    }


    /**
     * Updates HTTP Routing.
     */
    static void routing(HttpRouting.Builder routing) {
        routing
               .register("/greet", new GreetService())
               .get("/simple-greet", (req, res) -> res.send("Hello World!")); 
    }
}
