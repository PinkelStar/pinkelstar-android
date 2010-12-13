package com.pinkelstar.android.ui;

import pinkelstar.android.R;

import com.pinkelstar.android.server.Constants;
import com.pinkelstar.android.server.Server;
import com.pinkelstar.android.server.Utils;
import com.pinkelstar.android.ui.util.ImageCache;
import com.pinkelstar.android.ui.util.ImageCallback;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ToggleButton;

public class NetworkListAdapter extends BaseAdapter {

	private Context context;

    public NetworkListAdapter(Context c) {
        context = c;
    }
    
	public int getCount() {
		return Server.getInstance().getKnownNetworks().length;
	}

	public Object getItem(int arg) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		String networkName = Server.getInstance().getKnownNetworks()[position];
		String imageUrl = Utils.buildImageUrl(networkName, Constants.LARGE_IMAGES);
		
		final ToggleButton tb = new ToggleButton(context);
		tb.setPadding(0, 10, 0, 15);
		tb.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.placeholder_button_icon, 0, 0);
		
		ImageCache.getInstance().loadDrawable(imageUrl, new ImageCallback() {
			public void setDrawable(Drawable d) {
				tb.setCompoundDrawablesWithIntrinsicBounds(null, d, null, null);
			}
		});

		tb.setTextSize(11);
		tb.setBackgroundResource(R.drawable.buttonstates);
		tb.setTextOn(networkName);
		tb.setTextOff(networkName);
		tb.setTextColor(Color.WHITE);
		tb.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);
		tb.setChecked(Server.getInstance().isNetworkAuthenticated(networkName));

		return tb;
	}

}
