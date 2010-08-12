package com.pinkelstar.android.ui.tasks;

import pinkelstar.android.R;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.pinkelstar.android.server.PinkelStarException;
import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.ui.PSSettings;

public class RevokeTask extends AsyncTask<String, Void, Boolean> {
	String networkname;

	private PSSettings psSettings;
	
	public RevokeTask(PSSettings settings) {
		this.psSettings = settings;
	}
	
	@Override
	protected void onPreExecute() {
		Toast.makeText(psSettings, R.string.revoking, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		networkname = params[0];
		try {
			Server.getInstance().removeNetwork(networkname);
			return new Boolean(true);
		} catch (PinkelStarException pse) {
			Log.d("PinkelStar","failure during revoke "+pse.toString());
			return new Boolean(false);
		}
	}

	protected void onPostExecute(Boolean b) {
		if (b.booleanValue() == Server.REQUESTOK) {
			Toast.makeText(psSettings, R.string.revoked, Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(psSettings, R.string.couldnotrevoke, Toast.LENGTH_SHORT).show();
		}
	}
}
