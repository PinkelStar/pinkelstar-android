package com.pinkelstar.android.ui;

import pinkelstar.android.R;
import android.app.Application;

import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.server.Settings;
import com.pinkelstar.android.ui.tasks.SessionTask;

public class PSApplication extends Application implements PSApplicationState {

	private Server psServer;
	private ImageCache imageCache;

	public void onCreate() {
		Settings.initialize(getResources().getXml(R.xml.pinkelstar));
		
		psServer = new Server((Application) this, Settings.key(), Settings.secret());
		imageCache = new ImageCache();

		SessionTask.initialize(psServer, imageCache, Settings.preloadImages());
	}

	public Server getPinkelstarServer() {
		return this.psServer;
	}

	public ImageCache getPinkelstarImageCache() {
		return this.imageCache;
	}
}
