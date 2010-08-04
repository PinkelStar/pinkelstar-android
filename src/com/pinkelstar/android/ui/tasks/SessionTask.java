package com.pinkelstar.android.ui.tasks;

import pinkelstar.android.R;
import android.app.Application;
import android.os.AsyncTask;

import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.ui.util.Settings;

public class SessionTask extends AsyncTask<Void, Void, Void> {
	
	private Application application;
	private String key;
	private String secret;
	private boolean preloadImages;
	
	public SessionTask(Application application, String key, String secret, boolean preloadImages) {
		this.application = application;
		this.key = key;
		this.secret = secret;
		this.preloadImages = preloadImages;
	}
	
	public static void initialize(Application application) {
		Settings settings = Settings.getInstance();
		settings.loadSettingsFromXml(application.getResources().getXml(R.xml.pinkelstar));
		
		initialize(application, Settings.key(), Settings.secret(), Settings.preloadImages());
	}

	public static void initialize(Application application, String key, String secret, boolean preloadImages) {
		SessionTask st = new SessionTask(application, key, secret, preloadImages);
		st.execute();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		Server.initialize(application, key, secret);
		return null;
	}
	
	protected void onPostExecute(Void result) {
		if(this.preloadImages) {
			ImagePreloaderTask.initialize();
		}
	}
}