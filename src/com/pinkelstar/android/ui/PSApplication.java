package com.pinkelstar.android.ui;

import pinkelstar.android.R;
import android.app.Application;

import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.ui.tasks.SessionTask;

public class PSApplication extends Application implements PSApplicationState {

	private Server server;
	private ImageCache imageCache;

	public void onCreate() {
		server = new Server((Application) this, getString(R.string.app_key), getString(R.string.app_secret));
		imageCache  = new ImageCache();
		
		SessionTask.initialize(server);
	}

	public Server getPinkelstarServer() {
		return this.server;
	}

	public ImageCache getPinkelstarImageCache() {
		return this.imageCache;
	}
	
}
