package com.pinkelstar.android.ui.tasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.pinkelstar.android.ui.util.ImageCache;
import com.pinkelstar.android.ui.util.ImageCallback;

public class ImageTask extends AsyncTask<String, Void, Drawable> {

	ImageCache imageCache;
	ImageCallback imageCallback;

	public ImageTask(ImageCallback ds, ImageCache imageCache) {
		this.imageCallback = ds;
		this.imageCache = imageCache;
	}

	@Override
	protected Drawable doInBackground(String... params) {
		return imageCache.loadAndStoreImage(params[0]);
	}
	
	@Override
	protected void onPostExecute(Drawable result) {
		if(imageCallback != null)
			imageCallback.setDrawable(result);
	}
}