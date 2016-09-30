package ceta.game.util.osc;

import java.net.SocketException;
import java.util.Date;

import com.badlogic.gdx.Gdx;
import com.illposed.osc.OSCListener;
import com.illposed.osc.OSCMessage;
import com.illposed.osc.OSCPortIn;

public class OSCReceiver {

	private String address;
	private int port;
	private OSCPortIn oscPortIn;
    private static final String TAG = OSCReceiver.class.getName();

	public OSCReceiver(String address, int port) {
		this.address = address;
		this.port = port;
		
		try{
			// Connect to some IP address and port
	    	oscPortIn = new OSCPortIn(this.port);
	       	oscPortIn.addListener(this.address, new OSCListener() {
				@Override
				public void acceptMessage(Date arg0, OSCMessage arg1) {
					  Gdx.app.log(TAG, "message received!!!");
					
					for(int i =0;i< arg1.getArguments().size();i++){
						 Gdx.app.log(TAG,"arg("+i+")="+arg1.getArguments().get(i));
					}
					 Gdx.app.log(TAG, "----------- end of message ------------");
				}
			});
    	}catch(SocketException e){
        	e.printStackTrace();
        	 Gdx.app.log(TAG,  null, e);
        }	
	}
	
	
	public void start(){
		oscPortIn.startListening();
		Gdx.app.log(TAG, "----------- OSCReceiver: Start listening ------------");
	}
	
	public void stop(){
		oscPortIn.stopListening();
		Gdx.app.log(TAG, "----------- OSCReceiver: Stop listening ------------");
	}
	
	
}
