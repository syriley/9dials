package controllers;


import play.Logger;
import play.mvc.Http.WebSocketClose;
import play.mvc.Http.WebSocketEvent;
import play.mvc.Http.WebSocketFrame;
import play.mvc.WebSocketController;

public class MyWebSocket extends WebSocketController {
 
    public static void hello(String name) {
    	while(inbound.isOpen()) {
	        try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
	        WebSocketEvent e = await(inbound.nextEvent());
            if(e instanceof WebSocketFrame) {
                 WebSocketFrame frame = (WebSocketFrame)e;
                 if(!frame.isBinary) {
                     if(frame.textData.equals("quit")) {
                         outbound.send("Bye!");
                         disconnect();
                     } else {
                         outbound.send("Echo: %s", frame.textData);
                     }
                 }
            }
            if(e instanceof WebSocketClose) {
                Logger.info("Socket closed!");
            }
    	}
    }
    
    public static void listen() {
	     while(inbound.isOpen()) {
	        String event = "asdf";
	        outbound.send(event);
	     }
     }
 
}