package com.pinkelstar.android.ui.tasks;

import android.os.AsyncTask;

import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.ui.ImageCache;

public class SessionTask extends AsyncTask<Void, Void, Void> {
	
	private ImageCache imageCache;
	private String preloadImages;
	
	public SessionTask(ImageCache imageCache, String preloadImages) {
		this.imageCache = imageCache;
		this.preloadImages = preloadImages;
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		Server.getInstance().initPS();
		return null;
	}
	
	public static void initialize(ImageCache imageCache, String preloadImages) {
		SessionTask st = new SessionTask(imageCache, preloadImages);
		st.execute();
	}
	
	protected void onPostExecute(Void result) {
		if(this.preloadImages.equals("true")) {
			ImagePreloaderTask.initialize(imageCache);
		}
	}
	
}