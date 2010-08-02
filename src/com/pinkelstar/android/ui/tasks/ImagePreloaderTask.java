package com.pinkelstar.android.ui.tasks;

import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

import com.pinkelstar.android.server.Constants;
import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.server.Utils;
import com.pinkelstar.android.ui.ImageCache;

public class ImagePreloaderTask extends TimerTask {
	private Timer timer;
	private Server server;
	private ImageCache imageCache;
	
	public static void initialize(Server server, ImageCache imageCache, String preload) {
		if(preload.equals("true")) {
			new ImagePreloaderTask(server, imageCache);
		}
	}
	
	public ImagePreloaderTask(Server server, ImageCache imageCache) {
		this.server = server;
		this.imageCache = imageCache;
		this.timer = new Timer();
		this.timer.schedule(this, 2000, 2000);
	}
	
	@Override
	public void run() {
		Log.d("PinkelStar", "preloading images");
		switch (server.getState()) {
		case Server.STATE_INITIALIZED:
			preloadIcons();
			Log.d("PinkelStar", "images preloaded");
			timer.cancel();
			break;
		case Server.STATE_LOADING:
			break;
		case Server.STATE_ERROR:
			Log.d("PinkelStar", "preloading of images cancelled due to an issue initializing the server");
			timer.cancel();
			break;
		}
	}
	
	private void preloadIcons() {
		for (String networkName : server.getKnownNetworks()) {
			String networkUrl = Utils.buildImageUrl(networkName, Constants.LARGE_IMAGES);
			imageCache.preloadDrawable(networkUrl);
			imageCache.preloadDrawable(server.getIconUrl());
		}
	}
}