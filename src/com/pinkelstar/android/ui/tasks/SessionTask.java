package com.pinkelstar.android.ui.tasks;

import android.os.AsyncTask;

import com.pinkelstar.android.server.Server;

public class SessionTask extends AsyncTask<Void, Void, Void> {
	
	private Server psServer;
	
	public SessionTask(Server server) {
		this.psServer = server;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		psServer.initPS();
		return null;
	}
	
	public static void initialize(Server server) {
		SessionTask st = new SessionTask(server);
		st.execute();
	}
	
}