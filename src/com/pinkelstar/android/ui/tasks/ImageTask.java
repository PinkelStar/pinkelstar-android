package com.pinkelstar.android.ui.tasks;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.pinkelstar.android.ui.util.ImageCache;
import com.pinkelstar.android.ui.util.ImageCallback;

public class ImageTask extends AsyncTask<String, Void, Drawable> {

	ImageCallback imageCallback;

	public ImageTask(ImageCallback ds) {
		this.imageCallback = ds;
	}

	@Override
	protected Drawable doInBackground(String... params) {
		return ImageCache.getInstance().loadAndStoreImage(params[0]);
	}
	
	@Override
	protected void onPostExecute(Drawable result) {
		if(imageCallback != null)
			imageCallback.setDrawable(result);
	}
}