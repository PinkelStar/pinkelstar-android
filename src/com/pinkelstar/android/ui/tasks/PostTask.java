package com.pinkelstar.android.ui.tasks;

import pinkelstar.android.R;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.pinkelstar.android.server.PinkelStarException;
import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.ui.PSSharing;

public class PostTask extends AsyncTask<String, Void, Boolean> {

	private PSSharing sharing;
	
	public PostTask(PSSharing sharing) {
		this.sharing = sharing;
	}
	
	@Override
	protected void onPreExecute() {
		Toast.makeText(sharing, R.string.posting, Toast.LENGTH_SHORT).show();
	}

	@Override
	protected Boolean doInBackground(String... params) {
		String[] networks = params[0].split(",");
		try {
			Server.getInstance().publishMessage(networks, params[1], params[2], params[3]);
			return new Boolean(true);
		} catch (PinkelStarException pse) {
			Log.d("PinkelStar", "failure during message publish : " + pse.toString());
			return new Boolean(false);
		}
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