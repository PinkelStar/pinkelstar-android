package com.pinkelstar.android.ui;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.res.XmlResourceParser;

public final class Settings {
	
	private static Settings instance = null;
	
	private String KEY = null;
	private String SECRET = null;
	private boolean PRELOAD_IMAGES;
	
	private boolean SETTINGS_LOADED = false;
	
	private Settings() { }

	public static synchronized Settings getInstance() {
		if(instance == null) {
			instance = new Settings();
	    }
	    return instance;
	}
	
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
	
	public static Settings initialize(XmlResourceParser parser) {
		Settings settings = Settings.getInstance();
		settings.loadSettings(parser);
		return settings;
	}
	
	public static String key(){
		return Settings.getInstance().getKey();
	}
	
	public static String secret(){
		return Settings.getInstance().getSecret();
	}
	
	public static boolean preloadImages(){
		return Settings.getInstance().getPreloadImages();
	}
	
	
	public String getKey() {
		checkIfSettingsAreLoaded();
		return KEY;
	}
	
	public String getSecret() {
		checkIfSettingsAreLoaded();
		return SECRET;
	}
	
	public boolean getPreloadImages() {
		checkIfSettingsAreLoaded();
		return PRELOAD_IMAGES;
	}
	
	private void checkIfSettingsAreLoaded() {
		if(!SETTINGS_LOADED)
			throw new IllegalStateException("Settings have not been loaded, please initialize the settings by calling initialize or loadSettings first");
	}
	
	public void loadSettings(XmlResourceParser parser) {
		if (SETTINGS_LOADED)
			return;
		
		try {
			int eventType = parser.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				String name = null;
				switch (eventType){
	            	case XmlPullParser.START_TAG:
	            		name = parser.getName().toLowerCase();
	            		if (name.equals("applicationkey")) {
	            			KEY = parser.getText();
	            		} else if (name.equals("applicationsecret")) {
	            			SECRET = parser.getText();
	            		} else if (name.equals("preloadimages")) {
	            			PRELOAD_IMAGES = parser.getText().equals("true");
	            		}
	            		break;
	            	case XmlPullParser.END_TAG:
	            		name = parser.getName();
	            		break;
				}
				eventType = parser.next();
			}
			SETTINGS_LOADED = true;
		} catch (XmlPullParserException e) {
			throw new RuntimeException("Cannot parse PinkelStar settings XML");
		} catch (IOException e) {
			throw new RuntimeException("Cannot parse PinkelStar settings XML");
		} finally {
			parser.close();
		}
	}
}
