package com.pinkelstar.android.ui;

import java.util.TimerTask;

import pinkelstar.android.R;
import android.app.Application;
import android.os.Handler;

import com.pinkelstar.android.server.Constants;
import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.server.Utils;
import com.pinkelstar.android.ui.tasks.SessionTask;

public class PSApplication extends Application implements PSApplicationState {

	private Server psServer;
	private ImageCache imageCache;
	private Handler mHandler;
	
	public void onCreate() {
		psServer = new Server((Application) this, getString(R.string.app_key), getString(R.string.app_secret));
		imageCache  = new ImageCache();
		mHandler = new Handler();
		ImagePreloadTimerTask iptt = new ImagePreloadTimerTask();
		mHandler.removeCallbacks(iptt);
		mHandler.postDelayed(iptt, 100);		
		SessionTask.initialize(psServer);
	}

	public Server getPinkelstarServer() {
		return this.psServer;
	}

	public ImageCache getPinkelstarImageCache() {
		return this.imageCache;
	}

	
	public class ImagePreloadTimerTask extends TimerTask {
		@Override
		public void run() {
			switch (psServer.getState()) {
			case Server.STATE_INITIALIZED:
				mHandler.removeCallbacks(this);
				preloadIcons();
				break;
			case Server.STATE_LOADING:
				mHandler.postDelayed(this, 1000);
				break;
			case Server.STATE_ERROR:
				//showConnectionErrorMessage();
				break;
			}
		}
		private void preloadIcons() {
			for (String networkName : psServer.getKnownNetworks()) {
				String networkUrl = Utils.buildImageUrl(networkName, Constants.LARGE_IMAGES);
				imageCache.preloadDrawable(networkUrl);
				imageCache.preloadDrawable(psServer.getIconUrl());
			}
		}
	}
}
