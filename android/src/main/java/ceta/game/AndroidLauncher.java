package ceta.game;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import ceta.game.util.osc.OSCReceiver;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	
	
    private static final String TAG = AndroidLauncher.class.getName();

    private MenuItem mItemWizardOfOz;
    private boolean wizardOfOz;
    

	private String localIp;
    CetaGame cetaGamte;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.hideStatusBar=false;
		this.wizardOfOz = false;
	    WifiManager wm = (WifiManager)getSystemService(WIFI_SERVICE);
		this.localIp = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
		this.cetaGamte = new CetaGame(this.localIp);
		initialize(cetaGamte, config);
	}
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.i(TAG, "called onCreateOptionsMenu");
        mItemWizardOfOz = menu.add("Start WizardOfOz");
        return true;
    }
	
	
	public boolean onOptionsItemSelected(MenuItem item){
        Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
        if (item == mItemWizardOfOz) {
        	if(!wizardOfOz){
	            this.cetaGamte.startOSCReceiver();
        		item.setTitle("Stop WizardOfOz - " + this.localIp);
        	}else{
	            this.cetaGamte.stopOSCReceiver();
        		item.setTitle("Start WizardOfOz");
        	}
        	this.wizardOfOz= !this.wizardOfOz;
        } 
        return true;
    }
	
	
	
}
