package com.pinkelstar.android.ui.tasks;

import pinkelstar.android.R;
import android.os.AsyncTask;
import android.widget.Toast;

import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.ui.PSSettings;

public class RevokeTask extends AsyncTask<String, Void, Boolean> {
	String networkname;

	private Server psServer;
	private PSSettings psSettings;
	
	public RevokeTask(Server server, PSSettings settings) {
		this.psServer = server;
		this.psSettings = settings;
	}
	
	@Override
	protected void onPreExecute() {
		Toast.makeText(psSettings, R.string.revoking, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		networkname = params[0];
		boolean b = psServer.removeNetwork(networkname);
		return new Boolean(b);
	}

	protected void onPostExecute(Boolean b) {
		if (b.booleanValue() == Server.REQUESTOK) {
			Toast.makeText(psSettings, R.string.revoked, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(psSettings, R.string.couldnotrevoke, Toast.LENGTH_SHORT).show();
		}
	}
}
