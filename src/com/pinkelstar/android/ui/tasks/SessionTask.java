package com.pinkelstar.android.ui.tasks;

import android.os.AsyncTask;

import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.ui.ImageCache;

public class SessionTask extends AsyncTask<Void, Void, Void> {
	
	private Server server;
	private ImageCache imageCache;
	private boolean preloadImages;
	
	public SessionTask(Server server, ImageCache imageCache, boolean preloadImages) {
		this.server = server;
		this.imageCache = imageCache;
		this.preloadImages = preloadImages;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		server.initPS();
		return null;
	}
	
	public static void initialize(Server server, ImageCache imageCache, boolean preloadImages) {
		SessionTask st = new SessionTask(server, imageCache, preloadImages);
		st.execute();
	}
	
	protected void onPostExecute(Void result) {
		if(this.preloadImages) {
			ImagePreloaderTask.initialize(server, imageCache);
		}
	}
	
}