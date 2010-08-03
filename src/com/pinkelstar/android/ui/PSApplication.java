package com.pinkelstar.android.ui;

import pinkelstar.android.R;
import android.app.Application;

import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.ui.tasks.SessionTask;

public class PSApplication extends Application implements PSApplicationState {

//	private Server psServer;
	private ImageCache imageCache;
	
	public void onCreate() {
		Server.initialize((Application) this, getString(R.string.app_key), getString(R.string.app_secret));
		
		//psServer = new Server((Application) this, getString(R.string.app_key), getString(R.string.app_secret));
		imageCache  = new ImageCache();
		
		SessionTask.initialize(imageCache, getString(R.string.preloadimages));
	}

//	public Server getPinkelstarServer() {
//		return this.psServer;
//	}

	public ImageCache getPinkelstarImageCache() {
		return this.imageCache;
	}
	
}
