package com.pinkelstar.android.ui.util;

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
	
	public void loadSettingsFromXml(XmlResourceParser parser) {
		if (SETTINGS_LOADED)
			return;
		
		try {
			int eventType = parser.getEventType();

			String currentTag = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType){
					case XmlPullParser.START_TAG:
						currentTag = parser.getName().toLowerCase();
						break;
	            	case XmlPullParser.TEXT:
	            		parseKeyValue(currentTag, parser.getText());
	            		break;
	            	case XmlPullParser.END_TAG:
	            		currentTag = null;
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

	private void parseKeyValue(String currentTag, String value) {
		if(currentTag == null || currentTag.equals("settings"))
			return;
		
		if (currentTag.equals("key")) {
			KEY = value;
		} else if (currentTag.equals("secret")) {
			SECRET = value;
		} else if (currentTag.equals("preloadimages")) {
			PRELOAD_IMAGES = value.equals("true");
		} else {
			throw new RuntimeException("Settings Tag not recognized : " + currentTag);
		}
	}
}
