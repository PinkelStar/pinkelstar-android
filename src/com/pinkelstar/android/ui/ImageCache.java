package com.pinkelstar.android.ui;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.pinkelstar.android.ui.tasks.ImageTask;

public class ImageCache {
	private HashMap<String, SoftReference<Drawable>> cache;

	public ImageCache() {
		cache = new HashMap<String, SoftReference<Drawable>>();
	}

	public void preloadDrawable(String url) {
		loadDrawable(url, null);
	}

	public void loadDrawable(String url, ImageCallback imageCallback) {
		if (cache.containsKey(url)) {
			Drawable image = cache.get(url).get();
			if(wasImageSet(image, imageCallback)) {
				return;
			}
		}

		new ImageTask(imageCallback, this).execute(url);
	}

	public Drawable loadAndStoreImage(String url) {
		Drawable drawable = new BitmapDrawable();

		try {
			drawable = Drawable.createFromStream(new URL(url).openStream(), "src");
		} catch (Exception e) {
			Log.d("Pinkelstar", "could not get image from "+url+" "+e.toString());
			drawable = null;
		}

		cache.put(url, new SoftReference<Drawable>(drawable));

		return drawable;
	}

	private boolean wasImageSet(Drawable image, ImageCallback imageCallback) {
		if (image != null) {
			if (imageCallback != null) {
				imageCallback.setDrawable(image);
			}
			return true;
		}

		return false;
	}

}
