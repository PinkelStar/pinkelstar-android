package com.pinkelstar.android.ui.tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.pinkelstar.android.server.Constants;
import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.server.Utils;
import com.pinkelstar.android.ui.ImageCache;

public class ImagePreloaderTask extends AsyncTask<Void, Void, Void> {
	private Server server;
	private ImageCache imageCache;
	
	public static void initialize(Server server, ImageCache imageCache) {
		new ImagePreloaderTask(server, imageCache);
	}
	
	public ImagePreloaderTask(Server server, ImageCache imageCache) {
		this.server = server;
		this.imageCache = imageCache;
		this.execute();
	}
	
	@Override
	protected Void doInBackground(Void... params) {
		Log.d("PinkelStar", "preloading images");
		switch (server.getState()) {
		case Server.STATE_INITIALIZED:
			preloadIcons();
			Log.d("PinkelStar", "images preloaded");
			break;
		case Server.STATE_ERROR:
			Log.d("PinkelStar", "preloading of images cancelled due to an issue initializing the server");
			break;
		}
		return null;
	}
	
	private void preloadIcons() {
		imageCache.preloadDrawable(server.getIconUrl());
		for (String networkName : server.getKnownNetworks()) {
			String networkUrl = Utils.buildImageUrl(networkName, Constants.LARGE_IMAGES);
			imageCache.preloadDrawable(networkUrl);
		}
	}
}