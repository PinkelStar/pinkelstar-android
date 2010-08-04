package com.pinkelstar.android.ui;

import pinkelstar.android.R;
import android.app.Application;

import com.pinkelstar.android.server.PinkelstarStatable;
import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.ui.tasks.SessionTask;
import com.pinkelstar.android.ui.util.Settings;

public class PSApplication extends Application implements PinkelstarStatable {

	private Server psServer;

	public void onCreate() {
		Settings.initialize(getResources().getXml(R.xml.pinkelstar));
		
		psServer = new Server((Application) this, Settings.key(), Settings.secret());

		SessionTask.initialize(psServer, Settings.preloadImages());
	}

	public Server getPinkelstarServer() {
		return this.psServer;
	}
	
}
