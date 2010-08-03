package com.pinkelstar.android.ui;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParserException;

import pinkelstar.android.R;
import android.app.Application;
import android.content.res.XmlResourceParser;

import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.ui.tasks.SessionTask;

public class PSApplication extends Application implements PSApplicationState {

	private Server psServer;
	private ImageCache imageCache;
	String key;
	String secret;
	boolean preload;

	public void onCreate() {
		
		try {
			loadSettings();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		psServer = new Server((Application) this, key, secret);
		imageCache = new ImageCache();

		SessionTask.initialize(psServer, imageCache, getString(R.string.preloadimages));
	}

	public Server getPinkelstarServer() {
		return this.psServer;
	}

	public ImageCache getPinkelstarImageCache() {
		return this.imageCache;
	}

	private void loadSettings() throws XmlPullParserException, IOException {
		XmlResourceParser parser = getResources().getXml(R.xml.settings);
		while (parser.getName() == null || !parser.getName().equals("key"))
			parser.next();
		parser.next();
		key = parser.getText();
		while (parser.getName() == null || !parser.getName().equals("secret"))
			parser.next();
		parser.next();
		secret = parser.getText();
		
		while (parser.getName() == null || !parser.getName().equals("preload"))
				parser.next();
		parser.next();
		preload = parser.getText().equals("true");
	}

}
