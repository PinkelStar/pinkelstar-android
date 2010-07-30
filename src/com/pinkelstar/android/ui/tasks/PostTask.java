package com.pinkelstar.android.ui.tasks;

import pinkelstar.android.R;
import android.os.AsyncTask;
import android.widget.Toast;

import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.ui.PSSharing;

public class PostTask extends AsyncTask<String, Void, Boolean> {

	private Server psServer;
	private PSSharing sharing;
	
	public PostTask(Server server, PSSharing sharing) {
		this.psServer = server;
		this.sharing = sharing;
	}
	
	@Override
	protected void onPreExecute() {
		Toast.makeText(sharing, R.string.posting, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String[] networks = params[0].split(",");
		boolean b = psServer.publishMessage(networks, params[1], params[2]);

		return new Boolean(b);
	}

	protected void onPostExecute(Boolean b) {
		if (b.booleanValue() == Server.REQUESTOK) {
			Toast.makeText(sharing, R.string.posted, Toast.LENGTH_SHORT).show();
			sharing.finish();
		} else {
			Toast.makeText(sharing, R.string.couldnotpost, Toast.LENGTH_SHORT).show();
		}
	}
}