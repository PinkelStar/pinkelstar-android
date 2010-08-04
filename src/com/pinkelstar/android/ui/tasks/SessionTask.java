package com.pinkelstar.android.ui.tasks;

import android.os.AsyncTask;

import com.pinkelstar.android.server.Server;

public class SessionTask extends AsyncTask<Void, Void, Void> {
	
	private Server server;
	private boolean preloadImages;
	
	public SessionTask(Server server, boolean preloadImages) {
		this.server = server;
		this.preloadImages = preloadImages;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		server.initPS();
		return null;
	}
	
	public static void initialize(Server server, boolean preloadImages) {
		SessionTask st = new SessionTask(server, preloadImages);
		st.execute();
	}
	
	protected void onPostExecute(Void result) {
		if(this.preloadImages) {
			ImagePreloaderTask.initialize(server);
		}
	}
	
}